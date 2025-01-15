class Events{

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
                        {"Votre" +ANSI_GREENKINDOF+"vaisseau (V)"+ ANSI_RESET +" s'est posé en toute sécurité sur une nouvelle planète encore inexplorée.",
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
                        ""},

                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""},
                        {"",""}};
}