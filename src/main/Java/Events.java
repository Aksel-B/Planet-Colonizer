class Events{
    // final String ANSI_RESET = "\033[m";
    // final String ANSI_MAGENTA = "\033[35m";
    // final String ANSI_GREENKINDOF = "\033[38;5;42m";
    // final String ANSI_BROWN = "\033[38;2;139;69;19m";
    // final String ANSI_GRAY = "\033[38;2;128;128;128m";
    // final String ANSI_BLUE_LIGHT = "\033[38;2;173;216;230m";
    // final String ANSI_BLUE= "\033[0;34m"; 
    // final String ANSI_GREEN_LIGHT = "\033[38;2;144;238;144m";
    // final String ANSI_CYAN_LIGHT = "\033[38;2;224;255;255m";
    // final String ANSI_YELLOW_BRIGHT = "\033[38;2;255;255;0m";
    // final String ANSI_YELLOW =  "\033[38;5;11m";

    boolean[] entrepotPlein=new boolean[]{false};
    String entrepotPleinSTR="Les Entrepôts sont pleins !";

    boolean[] dortoirsPlein=new boolean[]{false};
    String dortoirsPleinSTR="Les Dortoirs sont pleins !";

    String[] ressourceEstEpuiseeSTR;
    String[] filonEpuise=new String[20];

    String[] naissance=new String[20];
    String[] deces=new String[20];

    boolean[] BatimentsPosed;
    String[][] posedTxt;
}