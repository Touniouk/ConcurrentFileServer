import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable {

    private String name;
    private FileServer server;

    public Client(String name, FileServer server) {
        this.name = name;
        this.server = server;
    }

    @Override
    public void run() {
        println(name + " starting");
        Set<String> availableFiles;
        Optional<String> filename;
        Optional<File> file;
        // Run a client for 1-5 minutes
        LocalDateTime end = LocalDateTime.now().plusMinutes(ThreadLocalRandom.current().nextInt(1, 6));
        while (LocalDateTime.now().isBefore(end)) {

//            println(name + " getting the list of available files");
//            availableFiles = server.availableFiles();
//            // Pick a random file among available files
//            filename = availableFiles.stream().skip(ThreadLocalRandom.current().nextInt(0, availableFiles.size())).findFirst();
//            // If we found a file
//            if (filename.isPresent()) {
//                if (ThreadLocalRandom.current().nextBoolean()) {
//                    // Try and read the file
//                    println(name + " trying to open file \"" + filename.get() + "\" in READABLE mode");
//                    file = server.open(filename.get(), Mode.READABLE);
//                    // If a file has been opened, read for 1-10 seconds
//                    if (file.isPresent()) {
//                        println(name + " reading file " + filename.get());
//                        sleep(ThreadLocalRandom.current().nextInt(1, 11));
//                        // Close the file
//                        println(name + " closing file " + filename.get());
//                        server.close(file.get());
//                    }
//                } else {
//                    // Try and write the file
//                    println(name + " trying to open file \"" + filename.get() + "\" in READWRITEABLE mode");
//                    file = server.open(filename.get(), Mode.READWRITEABLE);
//                    // If a file has been opened, read for 1-5 seconds
//                    if (file.isPresent()) {
//                        println(name + " reading file " + filename.get());
//                        sleep(ThreadLocalRandom.current().nextInt(1, 6));
//                        // Change the content of the file
//                        println(name + " is re-writing the file " + filename.get());
//                        file.get().write("Modified by " + name + " on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM 'at' hh:mm:ss.SSSSSS")) + "\n" + file.get().read());
//                        // Read the file again for 1-5 seconds
//                        println(name + " re-reading file " + filename.get());
//                        sleep(ThreadLocalRandom.current().nextInt(1, 6));
//                        // Close the file
//                        println(name + " closing file " + filename.get());
//                        server.close(file.get());
//                    }
//                }
//            }

            // Try and read the file
            println(name + " trying to open file \"" + "The Lighthouse Among the Rocks" + "\" in READABLE mode");
            file = server.open("The Lighthouse Among the Rocks", Mode.READABLE);
            // If a file has been opened, read for 1-10 seconds
            if (file.isPresent()) {
                println(name + " reading file " + "The Lighthouse Among the Rocks");
                sleep(ThreadLocalRandom.current().nextInt(1, 11));
                // Close the file
                println(name + " closing file " + "The Lighthouse Among the Rocks");
                server.close(file.get());
            }
            sleep(5);
        }
        println(name + " is finished");
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void println(String stringToPrint) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSSSSS")) + " - " + stringToPrint);
    }

    public static void main(String... args) {
        FileServer server = new FileServerImplementation();
        server.create("The Lighthouse Among the Rocks",
                "Just off the swirling sea, \n" +
                        "There's a lighthouse,\n" +
                        "Bigger than an aged oaken tree,\n" +
                        "Thicker in the base,\n" +
                        "And lighter on it's face,\n" +
                        "A homing beacon,\n" +
                        "For the ships,\n" +
                        "just off the shore,\n" +
                        "A warning, to be cautious of the waves and more.\n" +
                        "\n" +
                        "- Laura Williams");
        server.create("Une Charogne",
                "Rappelez-vous l'objet que nous vîmes, mon âme,\n" +
                        "Ce beau matin d'été si doux :\n" +
                        "Au détour d'un sentier une charogne infâme\n" +
                        "Sur un lit semé de cailloux,\n" +
                        "\n" +
                        "Les jambes en l'air, comme une femme lubrique,\n" +
                        "Brûlante et suant les poisons,\n" +
                        "Ouvrait d'une façon nonchalante et cynique\n" +
                        "Son ventre plein d'exhalaisons.\n" +
                        "\n" +
                        "Le soleil rayonnait sur cette pourriture,\n" +
                        "Comme afin de la cuire à point,\n" +
                        "Et de rendre au centuple à la grande Nature\n" +
                        "Tout ce qu'ensemble elle avait joint ;\n" +
                        "\n" +
                        "Et le ciel regardait la carcasse superbe\n" +
                        "Comme une fleur s'épanouir.\n" +
                        "La puanteur était si forte, que sur l'herbe\n" +
                        "Vous crûtes vous évanouir.\n" +
                        "\n" +
                        "Les mouches bourdonnaient sur ce ventre putride,\n" +
                        "D'où sortaient de noirs bataillons\n" +
                        "De larves, qui coulaient comme un épais liquide\n" +
                        "Le long de ces vivants haillons.\n" +
                        "\n" +
                        "Tout cela descendait, montait comme une vague,\n" +
                        "Ou s'élançait en pétillant ;\n" +
                        "On eût dit que le corps, enflé d'un souffle vague,\n" +
                        "Vivait en se multipliant.\n" +
                        "\n" +
                        "Et ce monde rendait une étrange musique,\n" +
                        "Comme l'eau courante et le vent,\n" +
                        "Ou le grain qu'un vanneur d'un mouvement rythmique\n" +
                        "Agite et tourne dans son van.\n" +
                        "\n" +
                        "Les formes s'effaçaient et n'étaient plus qu'un rêve,\n" +
                        "Une ébauche lente à venir,\n" +
                        "Sur la toile oubliée, et que l'artiste achève\n" +
                        "Seulement par le souvenir.\n" +
                        "\n" +
                        "Derrière les rochers une chienne inquiète\n" +
                        "Nous regardait d'un oeil fâché,\n" +
                        "Epiant le moment de reprendre au squelette\n" +
                        "Le morceau qu'elle avait lâché.\n" +
                        "\n" +
                        "- Et pourtant vous serez semblable à cette ordure,\n" +
                        "A cette horrible infection,\n" +
                        "Etoile de mes yeux, soleil de ma nature,\n" +
                        "Vous, mon ange et ma passion !\n" +
                        "\n" +
                        "Oui ! telle vous serez, ô la reine des grâces,\n" +
                        "Après les derniers sacrements,\n" +
                        "Quand vous irez, sous l'herbe et les floraisons grasses,\n" +
                        "Moisir parmi les ossements.\n" +
                        "\n" +
                        "Alors, ô ma beauté ! dites à la vermine\n" +
                        "Qui vous mangera de baisers,\n" +
                        "Que j'ai gardé la forme et l'essence divine\n" +
                        "De mes amours décomposés !\n" +
                        "\n" +
                        "- Charles Baudelaire");
        server.create("Alone",
                "From childhood's hour I have not been\n" +
                        "As others were; I have not seen\n" +
                        "As others saw; I could not bring\n" +
                        "My passions from a common spring.\n" +
                        "From the same source I have not taken\n" +
                        "My sorrow; I could not awaken\n" +
                        "My heart to joy at the same tone;\n" +
                        "And all I loved, I loved alone.\n" +
                        "Then- in my childhood, in the dawn\n" +
                        "Of a most stormy life- was drawn\n" +
                        "From every depth of good and ill\n" +
                        "The mystery which binds me still:\n" +
                        "From the torrent, or the fountain,\n" +
                        "From the red cliff of the mountain,\n" +
                        "From the sun that round me rolled\n" +
                        "In its autumn tint of gold,\n" +
                        "From the lightning in the sky\n" +
                        "As it passed me flying by,\n" +
                        "From the thunder and the storm,\n" +
                        "And the cloud that took the form\n" +
                        "(When the rest of Heaven was blue)\n" +
                        "Of a demon in my view. \n" +
                        "\n" +
                        "- Edgar Allan Poe");
        server.create("Le Loup et l'Agneau",
                "La raison du plus fort est toujours la meilleure :\n" +
                        "Nous l'allons montrer tout à l'heure.\n" +
                        "Un Agneau se désaltérait\n" +
                        "Dans le courant d'une onde pure.\n" +
                        "Un Loup survient à jeun qui cherchait aventure,\n" +
                        "Et que la faim en ces lieux attirait.\n" +
                        "Qui te rend si hardi de troubler mon breuvage ?\n" +
                        "Dit cet animal plein de rage :\n" +
                        "Tu seras châtié de ta témérité.\n" +
                        "- Sire, répond l'Agneau, que votre Majesté\n" +
                        "Ne se mette pas en colère ;\n" +
                        "Mais plutôt qu'elle considère\n" +
                        "Que je me vas désaltérant\n" +
                        "Dans le courant,\n" +
                        "Plus de vingt pas au-dessous d'Elle,\n" +
                        "Et que par conséquent, en aucune façon,\n" +
                        "Je ne puis troubler sa boisson.\n" +
                        "- Tu la troubles, reprit cette bête cruelle,\n" +
                        "Et je sais que de moi tu médis l'an passé.\n" +
                        "- Comment l'aurais-je fait si je n'étais pas né ?\n" +
                        "Reprit l'Agneau, je tette encor ma mère.\n" +
                        "- Si ce n'est toi, c'est donc ton frère.\n" +
                        "- Je n'en ai point. - C'est donc quelqu'un des tiens :\n" +
                        "Car vous ne m'épargnez guère,\n" +
                        "Vous, vos bergers, et vos chiens.\n" +
                        "On me l'a dit : il faut que je me venge.\n" +
                        "Là-dessus, au fond des forêts\n" +
                        "Le Loup l'emporte, et puis le mange,\n" +
                        "Sans autre forme de procès.\n" +
                        "\n" +
                        "- Jean de la Fontaine");
        server.create("Hope is the thing with feathers",
                "Hope is the thing with feathers\n" +
                        "That perches in the soul,\n" +
                        "And sings the tune without the words,\n" +
                        "And never stops at all,\n" +
                        "\n" +
                        "And sweetest in the gale is heard;\n" +
                        "And sore must be the storm\n" +
                        "That could abash the little bird\n" +
                        "That kept so many warm.\n" +
                        "\n" +
                        "I've heard it in the chillest land,\n" +
                        "And on the strangest sea;\n" +
                        "Yet, never, in extremity,\n" +
                        "It asked a crumb of me.\n" +
                        "\n" +
                        "- Emily Dickinson");
        server.create("L'Albatros",
                "Souvent, pour s’amuser, les hommes d’équipage\n" +
                        "Prennent des albatros, vastes oiseaux des mers,\n" +
                        "Qui suivent, indolents compagnons de voyage,\n" +
                        "Le navire glissant sur les gouffres amers.\n" +
                        "\n" +
                        "A peine les ont-ils déposés sur les planches,\n" +
                        "Que ces rois de l’azur, maladroits et honteux,\n" +
                        "Laissent piteusement leurs grandes ailes blanches\n" +
                        "Comme des avirons traîner à coté d’eux.\n" +
                        "\n" +
                        "Ce voyageur ailé, comme il est gauche et veule!\n" +
                        "Lui, naguère si beau, qu’il est comique et laid!\n" +
                        "L’un agace son bec avec un brûle-gueule,\n" +
                        "L’autre mime, en boitant, l’infirme qui volait!\n" +
                        "\n" +
                        "Le Poète est semblable au prince des nuées\n" +
                        "Qui hante la tempête et se rit de l’archer;\n" +
                        "Exilé sur le sol au milieu des huées,\n" +
                        "Ses ailes de géant l’empêchent de marcher.\n" +
                        "\n" +
                        "- Charles Baudelaire");
        server.create("The Horace Poem",
                "Much to his dad and mum's dismay \n" +
                        "Horace ate himself one day \n" +
                        "He didn't stop to say his grace \n" +
                        "He just sat down and ate his face \n" +
                        "\"We can't have this!\" his dad declared \n" +
                        "\"If that lad's ate he should be shared\" \n" +
                        "But even as he spoke they saw \n" +
                        "Horace eating more and more: \n" +
                        "First his legs and then his thighs, \n" +
                        "His arms, his nose, his hair, his eyes \n" +
                        "\"Stop him someone!\" Mother cried \n" +
                        "\"Those eyeballs would be better fried!\" \n" +
                        "But all too late for they were gone, \n" +
                        "And he had started on his dong... \n" +
                        "\"Oh foolish child!\" the father mourned \n" +
                        "\"You could have deep-fried those with prawns, \n" +
                        "Some parsely and some tartar sauce...\" \n" +
                        "But H was on his second course; \n" +
                        "His liver and his lights and lung, \n" +
                        "His ears, his neck, his chin, his tongue \n" +
                        "\"To think I raised himn from the cot \n" +
                        "And now he's gone to scoff the lot!\" \n" +
                        "His mother cried what shall we do? \n" +
                        "What's left won't even make a stew...\" \n" +
                        "And as she wept her son was seen \n" +
                        "To eat his head his heart his spleen \n" +
                        "And there he lay, a boy no more \n" +
                        "Just a stomach on the floor... \n" +
                        "None the less since it was his \n" +
                        "They ate it - and that's what haggis is \n" +
                        "\n" +
                        "- Monthy Python");

        for (int i = 1; i <= 5; i++) {
            new Thread(new Client("Client " + i, server)).start();
        }
    }
}
