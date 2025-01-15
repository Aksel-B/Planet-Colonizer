class Events{
    final String ANSI_RESET = "\033[m";
    final String ANSI_MAGENTA = "\033[35m";
    final String ANSI_ORANGE = "\033[38;2;255;165;0m";
    final String ANSI_GREENKINDOF = "\033[38;5;42m";
    final String ANSI_BROWN = "\033[38;2;139;69;19m";
    final String ANSI_GRAY = "\033[38;2;128;128;128m";
    final String ANSI_BLUE_LIGHT = "\033[38;2;173;216;230m";
    final String ANSI_GREEN_LIGHT = "\033[38;2;144;238;144m";
    final String ANSI_CYAN_LIGHT = "\033[38;2;224;255;255m";
    final String ANSI_YELLOW_BRIGHT = "\033[38;2;255;255;0m";
    final String ANSI_YELLOW =  "\033[38;5;11m";
    final String ANSI_ORANGE_DARK = "\033[38;2;255;140;0m";
    final String ANSI_RED_BRIGHT = "\033[38;2;255;0;0m";

    boolean[] entrepotPlein=new boolean[]{false};
    String entrepotPleinSTR="Les Entrepôts sont pleins !";

    boolean[] dortoirsPlein=new boolean[]{false};
    String dortoirsPleinSTR="Les Dortoirs sont pleins !";

    String[] ressourceEstEpuiseeSTR;
    String[] filonEpuise=new String[20];

    String[] naissance=new String[20];
    String[] deces=new String[20];

    boolean[] BatimentsPosed;
    String[][] posedTxt=new String[][]{
                        {"Votre" +ANSI_GREENKINDOF+" vaisseau (V)"+ ANSI_RESET +" s'est posé en toute sécurité sur une nouvelle planète encore inexplorée.",
                        "Dans le cadre du Projet «Fondation» votre mission est de veiller à la survie et au développement de votre colonie.",
                        "Afin d'accomplir cet objectif votre vaisseau et son réacteur à fusion peuvent vous apporter les ressources nécessaire à un bon départ !",
                        "Veuilliez prêter initialement attention aux ressources vitales à la réussite de votre mission tels que la nourriture, l'eau ou encore le cuivre.",
                        "",
                        "Bon courage Capitaine !"},

                        {"Afin d'assurer un certain confort et héberger les colons les "+ANSI_BROWN+"Dortoirs (⌂)"+ ANSI_RESET +" sont indispensables.",
                        "Gràce à une solide armature de Fer ce dortoir peut supporter les conditions extrèmes de la planète et ainsi garantir la sécurité.",
                        "Le Cuivre permet à ce dortoir de disposer aussi d'aménagements électriques assurant aux colons du confort"},

                        {"Les rayons de l'"+ANSI_GRAY+"Entrepôt (⌧)"+ ANSI_RESET +" doivent supporter de lourdes charges.",
                        "Le fer est idéal pour créer une structure à la fois robuste et résistante à l’usure."},

                        {"Afin de coordonner leurs activités ou simplement pour communiquer avec leurs proches restés sur Terre, le "+ANSI_MAGENTA+"Centre de communication Terrien (☤)"+ ANSI_RESET +" est indispensables",
                        "La combinaison du fer, du cuivre et du carbone assure un fonctionnement optimal.",
                        "Le fer est essentiel dans la communication par onde grâce à ses propriétés magnétiques, qui permettent de gérer, moduler et confiner les champs électromagnétiques.",
                        "Le Carbone agit comme bouclier contre les interférences électromagnétiques pouvant provenir des vents stellaires, il permet aussi de réduire la dissipation électrique",
                        "Grâce à sa faible résistivité et son excellente conductivité, le cuivre minimise les pertes de signal, ce qui le rend idéal pour le transport des signaux haute fréquence."},

                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""}};
}