import java.util.*;
import java.util.concurrent.locks.*;

class ServerFile {
    private int numberOfReaders = 0; // Keeps track of how many users have the file opened for reading mode
    private final Object statusLock = new Object(); // A lock on accessing the status so that it's not changed while you read it and whatnot
    private final Object numberOfReadersLock = new Object(); // Same but for numberOfReaders
    private Lock openLock; // You need this lock to open the file
    private Lock readLock; // Manages the opening of a file in readable mode
    private Lock writeLock; // You need this lock to write
    private String content; // The text content
    private Mode status;

    ServerFile(String content) {
        this.status = Mode.CLOSED;
        this.content = content;
        this.openLock = new ReentrantLock(true);
        this.readLock = new ReentrantLock(true);
        this.writeLock = new ReentrantLock(true);
    }

    public Lock getOpenLock() {
        return this.openLock;
    }

    public Lock getReadLock() {
        return this.readLock;
    }

    public Lock getWriteLock() {
        return this.writeLock;
    }

    public String content() {
        return this.content;
    }

    public void addReader() {
        synchronized (numberOfReadersLock) {
            numberOfReaders++;
        }
    }

    public void removeReader() {
        synchronized (numberOfReadersLock) {
            numberOfReaders--;
        }
    }

    public int getNumberOfReaders() {
        synchronized (numberOfReadersLock) {
            return numberOfReaders;
        }
    }

    // Not necessary to sync this as the locks make sure only one person can do it and nobody else can access the file in the meantime
    public void setContent(String content) {
        this.content = content;
    }

    public Mode getStatus() {
        synchronized (statusLock) {
            return status;
        }
    }

    public void setStatus(Mode status) {
        synchronized (statusLock) {
            this.status = status;
        }
    }
}

public class FileServerImplementation implements FileServer {

    private HashMap<String, ServerFile> files;
    private Thread readMonitor;

    public FileServerImplementation() {
        this.files = new HashMap<>();
        readMonitor = new Thread(() -> { while (true); });
        readMonitor.start();
    }

    // Sync so we don't try and create the same file twice at the same time
    @Override
    public synchronized void create(String filename, String content) {
        if (!files.containsKey(filename)) {
            files.put(filename, new ServerFile(content));
            System.out.println("File \"" + filename + "\" successfully created");
        } else {
            System.out.println("File \"" + filename + "\" already exist on the server");
        }
    }

    @Override
    public Optional<File> open(String filename, Mode mode) {
        // Lock the file to prevent anyone trying to open it
        // People who already have it open can still close it
        // Add and remove reader both require the same lock so they can't be called at the same time, avoiding race condition on numberOfReaders
        files.get(filename).getOpenLock().lock();
        ServerFile file = files.get(filename);
        try {
            switch (mode) { // Check the opening request
                case READABLE: // Request to get a readable version of the file
                    if (file.getNumberOfReaders() == 0) { // Many people can read the file at once
                        // You're the first one opening the file so make sure to lock so people can't write in it
                        file.getWriteLock().lock();
                        file.setStatus(Mode.CLOSED);
                    }
                    file.addReader(); // Add yourself as a reader
                    // Return the file in readable mode
                    return Optional.of(new File(filename, file.content(), Mode.READABLE));
                case READWRITEABLE: // Request to get a writable version of the file
                    // Try to get the writable lock
                    // This will wait until everyone has closed the file, nobody can open it since you have the open lock
                    // This is to make sure that you don't wait for the write lock forever while other people continue opening it in reading mode, blocking the write lock
                    file.getWriteLock().lock();
                    file.setStatus(Mode.READWRITEABLE);
                    // Return the file in writable mode
                    return Optional.of(new File(filename, file.content(), Mode.READWRITEABLE));
                default: return Optional.empty(); // This shouldn't be used normally, as people request the file either in readable or readwritable mode
            }
        } finally {
            // Release the open lock so other people can try and open the file
            file.getOpenLock().unlock();
        }
    }

    private void addReader() {

    }

    @Override
    public synchronized void close(File file) {
        ServerFile serverFile = files.get(file.filename());
        // Check if the file you're closing is in readable or writable mode
        // If the person had it in readable mode, we need to reduce the number of readers
        // If they had it in writable mode, that means only one person was accessing it
        // So we can release the write lock as they close the file
        switch (file.mode()) {
            case READABLE:
                serverFile.removeReader(); // Remove yourself as a reader
                if (serverFile.getNumberOfReaders() == 0) { // Check if other people had the file open too
                    // The file is not open by anyone anymore
                    serverFile.setStatus(Mode.CLOSED);
                    serverFile.getWriteLock().unlock();
                }
                break;
            case READWRITEABLE:
                serverFile.setContent(file.read());
                serverFile.setStatus(Mode.CLOSED);
                serverFile.getWriteLock().unlock();
                break;
        }
    }

    @Override
    public Mode fileStatus(String filename) {
        return files.get(filename).getStatus();
    }

    @Override
    public Set<String> availableFiles() {
        return files.keySet();
    }
}
