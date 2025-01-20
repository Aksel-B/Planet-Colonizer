import extensions.*;

class PlanetColonizer extends Program{

//-----------------------------Constantes---------------------------------------------------------------------------------------------------------------
    final char ANSI_ESCAPE_CHAR = '\033';
    final char ANSI_CODE_END_CHAR = 'm';
    final int ASCII_LOWERCASE_A = 'a';
    final int ASCII_LOWERCASE_Z = 'z';
    final int ASCII_UPPERCASE_A = 'A';
    final int ASCII_UPPERCASE_Z = 'Z';
    final int ASCII_CASE_DIFFERENCE = 32;
    final double POLLUTION_VAISSEAU = 0.0001;
    final double POLLUTION_DORTOIR = 0.0005;
    final double POLLUTION_ENTREPOT = 0.0002;
    final double POLLUTION_CCT = 0.0007;
    final double POLLUTION_CINEMA = 0.0007;
    final double POLLUTION_CAPTEUR = 0.0001;
    final double POLLUTION_FERME = -0.0005;
    final double POLLUTION_RECYCLEUR = 0.0001;
    final double POLLUTION_PANNEAU = 0.0001;
    final double POLLUTION_CENTRALE = 0.0001;
    final double POLLUTION_PUIT = 0.005;
    final int VAISSEAU_INDEX = 0;
    final int DORTOIR_INDEX = 1;
    final int ENTREPOT_INDEX = 2;
    final int CCT_INDEX = 3;
    final int MAX_BUILDING_INDEX = 10;
    final int INFINITE_RESOURCE = -1;
    final int DORTOIR_CAPACITY = 15;
    final int ENTREPOT_CAPACITY = 150;
    final int[] QUANTITE_RESSOURCES_CONSO_PUIT_DF = {10, 12, 15, 15, 15};
    final int[] QUANTITE_RESSOURCES_GENEREE_PUIT_DF = {10, 7, 5, 5, 1};
    final int BASE_RESOURCE_AMOUNT = 600;
    final int RESOURCE_DECREMENT = 100;
    final String ANSI_MAGENTA = "\033[35m";
    final String ANSI_ORANGE = "\033[38;2;255;165;0m";
    final String ANSI_GREENKINDOF = "\033[38;5;42m";
    final String ANSI_DARK_BG = "\033[48;2;52;40;61m";
    final String ANSI_BROWN = "\033[38;2;139;69;19m";
    final String ANSI_GRAY = "\033[38;2;128;128;128m";
    final String ANSI_BLUE_LIGHT = "\033[38;2;173;216;230m";
    final String ANSI_GREEN_LIGHT = "\033[38;2;144;238;144m";
    final String ANSI_CYAN_LIGHT = "\033[38;2;224;255;255m";
    final String ANSI_YELLOW_BRIGHT = "\033[38;2;255;255;0m";
    final String ANSI_YELLOW =  "\033[38;5;11m";
    final String ANSI_ORANGE_DARK = "\033[38;2;255;140;0m";
    final String ANSI_RED_BRIGHT = "\033[38;2;255;0;0m";
    final int RESOURCE_INDEX_MIN = 2;
    final int RESOURCE_INDEX_MAX = 7;
    final int PUITS_INDEX = 10;
    final double RANDOM_CORRECTION = 0.0005;
    final int RANDOM_MULTIPLIER = 1000;
    final int AGE_REPRODUCTION_MIN = 20; // Âge minimum pour la reproduction
    final int AGE_REPRODUCTION_MAX = 50; // Âge maximum pour la reproduction
    final double PROBABILITE_REPRODUCTION = 0.1; // Probabilité qu'un colon puisse se reproduire

//-----------------------------FONCTIONS POUR TOUT------------------------------------------------------------------------------------------------------
    
    // Fonction pour trouver le maximum entre deux entiers
    int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    // Fonction pour trouver le minimum entre deux entiers
    int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    int maxLength(String[] tab, int idDebut){
        int max=length(tab[idDebut]);
        for(int i=idDebut+1;i<length(tab);i++){
            if (length(tab[i])>max){
                max=length(tab[i]);
            }
        }
        return max;
    }

    int maxLength(int[] tab, int idDebut){
        int max=length(""+tab[idDebut]);
        for(int i=idDebut+1;i<length(tab);i++){
            if (length(""+tab[i])>max){
                max=length(""+tab[i]);
            }
        }
        return max;
    }

    // Fonction pour formater une caractéristique
    String formatCharacteristic(String name, int maxLength) {
        return name + repeatChar(' ', maxLength - length(name));
    }

    // Fonction pour répéter un caractère un certain nombre de fois
    String repeatChar(char ch, int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += ch;
        }
        return result;
    }

    int findLastIndex(String[] tab){
        int id=0;
        while (id<length(tab) && !equals(tab[id],"")){
            id++;
        }
        return id;
    }

    boolean TabIsEmpty(Terrain[] tab){
        boolean isEmpty=false;
        int nullCmpt=0;
        int id=0;
        while(id<length(tab)){
            if(tab[id]==null){
                nullCmpt++;
            }
            id++;
        }
        if (nullCmpt==length(tab)){
            isEmpty=true;
        }
        return isEmpty;
    }

    boolean TabIsEmpty(String[] tab){
        boolean isEmpty=false;
        int nullCmpt=0;
        int id=0;
        while(id<length(tab)){
            if(equals(tab[id],"")){
                nullCmpt++;
            }
            id++;
        }
        if (nullCmpt==length(tab)){
            isEmpty=true;
        }
        return isEmpty;
    }

    boolean TabIsEmpty(int[] tab){
        boolean isEmpty=false;
        int nullCmpt=0;
        int id=0;
        while(id<length(tab)){
            if(tab[id]==0){
                nullCmpt++;
            }
            id++;
        }
        if (nullCmpt==length(tab)){
            isEmpty=true;
        }
        return isEmpty;
    }

    void tabEmptier(String[] tab){
        for (int i=0;i<length(tab);i++){
            tab[i]="";
        }
    }

    // Convertit un entier en caractère (utile pour l'affichage de la carte)
    char IntToChar(int n) {
        String str = "";
        return charAt(str += n, 0); // Transforme l'entier en chaîne, puis retourne le premier caractère
    }

    boolean stringToBoolean(String bool){
        if (equals(bool,"true")){
            return true;
        }
        return false;
    }

        // Permet à l'utilisateur de saisir un numéro de ligne valide
    int saisirLigne (int nombreLignes){
        int ligne;
        boolean valide=true;
        do {
            valide=true;
            ligne = readIntSecurise(ANSI_BOLD + "Saisissez une ligne" + ANSI_RESET + "   (1,2,3,...) : "); // Lire un entier pour la ligne
            if (ligne < 1 || ligne > nombreLignes-1){
                valide=false;
                println(ANSI_PURPLE+"La valeur n'est pas dans l'intervalle !"+ANSI_RESET);
            }
        } while (valide==false); // Valide uniquement si dans l'intervalle
        return ligne;
    }


    int saisirColonne(int nombreColonne) {
        int colonne;
        do {
            // Utiliser la nouvelle méthode sécurisée
            char caractere = readCharMajSecurise(ANSI_BOLD + "Saisissez une colonne" + ANSI_RESET + " (A,B,C,...) : ");
            
            // Convertir le caractère en numéro de colonne
            colonne = caractere - 'A' + 1;
        } while (colonne < 1 || colonne > nombreColonne);
        
        return colonne; // Pour correspondre à l'indexation du tableau
    }

    String spaceEraser(String chaine){
        String output="";
        // regarde chaque caractère et ne retiens que ceux qui ne sont pas espaces
        for (int i=0;i<length(chaine);i++){
            if(charAt(chaine,i)!=' '){
                output+=charAt(chaine,i);
            }
        }
        return output;
    }

    String readStringSecurise(String prompt) {
        String input;
        boolean inputValide = false;
        
        while (!inputValide) {
            try {
                // Afficher le message de prompt
                print(prompt);
                
                // Lire l'entrée utilisateur
                input = readString();
                
                // Vérifier que l'entrée n'est pas nulle
                if (input == null) {
                    println(ANSI_RED+"Erreur : La saisie ne peut pas être nulle. Veuillez réessayer."+ANSI_RESET);
                    continue;
                }
                
                // Vérifier la longueur de l'entrée après suppression des espaces
                input = spaceEraser(input);
                
                // Vérifier que l'entrée n'est pas vide ou composée uniquement d'espaces
                if (length(input) == 0) {
                    println(ANSI_RED+"Erreur : La saisie ne peut pas être vide ou composée uniquement d'espaces. Veuillez réessayer."+ANSI_RESET);
                    continue;
                }

                // Vérifier les caractères spéciaux
                boolean contientCaractereSpecial = false;
                String caracteresInterdits = "\\,;:|/<>\"'`~!@#$%^&*()+={}[]";
                
                for(int i = 0; i < length(input); i++) {
                    char c = charAt(input, i);
                    // Vérifier si le caractère est dans la liste des caractères interdits
                    for(int j = 0; j < length(caracteresInterdits); j++) {
                        if(c == charAt(caracteresInterdits, j)) {
                            contientCaractereSpecial = true;
                            break;
                        }
                    }
                    if(contientCaractereSpecial) {
                        break;
                    }
                }
                
                if(contientCaractereSpecial) {
                    println(ANSI_RED+"Erreur : Les caractères spéciaux ne sont pas autorisés. Veuillez réessayer."+ANSI_RESET);
                    continue;
                }
                
                inputValide = true;
                return input;
                
            } catch (Exception e) {
                // Gestion des exceptions inattendues
                println(ANSI_RED+"Une erreur est survenue lors de la saisie. Veuillez réessayer."+ANSI_RESET);
            }
        }
        
        // Cette ligne ne devrait jamais être atteinte
        return "";
    }
    int readIntSecurise(String prompt) {
        while (true) {
            try {
                // Utiliser readStringSecurise pour obtenir une entrée valide
                String input = readStringSecurise(prompt);
                
                // Convertir la chaîne en entier
                int valeur = stringToInt(input);
                
                return valeur;
                
            } catch (Exception e) {
                println(ANSI_RED+"Erreur : Veuillez saisir un nombre entier valide."+ANSI_RESET);
            }
        }
    }

    char readCharSecurise(String prompt) {
        while (true) {
            try {
                // Utiliser readStringSecurise pour obtenir une entrée validée
                String input = readStringSecurise(prompt);
                
                // Vérifier que l'entrée est exactement un caractère
                if (length(input) != 1) {
                    println(ANSI_RED+"Erreur : Veuillez saisir exactement un caractère."+ANSI_RESET);
                    continue;
                }
                
                // Retourner le premier (et seul) caractère de l'entrée
                return charAt(input, 0);
                
            } catch (Exception e) {
                // Gestion des exceptions inattendues
                println(ANSI_RED+"Une erreur est survenue lors de la saisie. Veuillez réessayer."+ANSI_RESET);
            }
        }
    }

    char readCharMajSecurise(String prompt) {
        while (true) {
            char caractere = readCharSecurise(prompt);
            caractere = convertToUpperCase(caractere);

            if (!isLetter(caractere)) {
                println(ANSI_RED + ANSI_BOLD + "Erreur : Veuillez saisir une lettre." + ANSI_RESET);
                continue;
            }

            return caractere;
        }
    }

    char convertToUpperCase(char caractere) {
        if (caractere >= ASCII_LOWERCASE_A && caractere <= ASCII_LOWERCASE_Z) {
            caractere -= ASCII_CASE_DIFFERENCE;
        }
        return caractere;
    }

    boolean isLetter(char caractere) {
        return caractere >= ASCII_UPPERCASE_A && caractere <= ASCII_UPPERCASE_Z;
    }

    int getVisibleLength(String str) {
        int visibleLength = 0;
        boolean inAnsiCode = false;

        for (int i = 0; i < length(str); i++) {
            char currentChar = charAt(str, i);
            if (currentChar == ANSI_ESCAPE_CHAR) {
                inAnsiCode = true;
                continue;
            }
            if (inAnsiCode) {
                if (currentChar == ANSI_CODE_END_CHAR) {
                    inAnsiCode = false;
                }
                continue;
            }
            visibleLength++;
        }
        return visibleLength;
    }
    
    void formatEmptyLine(String[] tab) {
        int maxLine = maxLength(tab, 0);
        for (int y = 0; y < length(tab); y++) {
            tab[y] = formatCharacteristic(tab[y], maxLine + (length(tab[y]) - getVisibleLength(tab[y])));
        }
    }

    void formatEmptyLine(String[][] tab, int id) {
        int maxLine = maxLength(tab[id], 0);
        for (int y = 0; y < length(tab[id]); y++) {
            tab[id][y] = formatCharacteristic(tab[id][y], maxLine + (length(tab[id][y]) - getVisibleLength(tab[id][y])));
        }
    }

    String replace(String str, String oldStr, String newStr) {
        if ((str == null || oldStr == null || newStr == null) || (equals(str,"") || equals(oldStr,"") || equals(newStr,""))) {
            return str;
        }
        
        String result = "";
        int i = 0;
        
        while (i < length(str)) {
            boolean found = true;
            // Vérifie si oldStr est présent à la position i
            if (i + length(oldStr) <= length(str)) {
                for (int j = 0; j < length(oldStr); j++) {
                    if (charAt(str, i + j) != charAt(oldStr, j)) {
                        found = false;
                        break;
                    }
                }
            } else {
                found = false;
            }
            
            if (found) {
                result = result + newStr;
                i = i + length(oldStr);
            } else {
                result = result + charAt(str, i);
                i = i + 1;
            }
        }
        
        return result;
    }


//-----------------------------TEST---------------------------------------------------------------------------------------------------------------------

    void testMax() {
        assertEquals(5, max(5, 3)); // Vérifie que max(5, 3) retourne 5
        assertEquals(5, max(3, 5)); // Vérifie que max(3, 5) retourne 5
        assertEquals(-1, max(-1, -5)); // Vérifie que max(-1, -5) retourne -1
    }

    void testMin() {
        assertEquals(3, min(5, 3)); // Vérifie que min(5, 3) retourne 3
        assertEquals(3, min(3, 5)); // Vérifie que min(3, 5) retourne 3
        assertEquals(-5, min(-1, -5)); // Vérifie que min(-1, -5) retourne -5
    }

    void testMaxLength(){
        String[] tab1=new String[]{"a","ab","abc"};
        int[] tab2=new int[]{1,12,123,1234};
        assertEquals(3,maxLength(tab1,0));
        assertEquals(4,maxLength(tab2,0));
    }

    void testFormatCharacteristic() {
        assertEquals("Test      ", formatCharacteristic("Test", 10)); // Ajout d'espaces jusqu'à 10 caractères
        assertEquals("LongText", formatCharacteristic("LongText", 5)); // Ne coupe pas les chaînes déjà longues
    }

    void testRepeatChar() {
        assertEquals("*****", repeatChar('*', 5)); // Répète le caractère *
        assertEquals("", repeatChar('a', 0)); // Ne répète rien pour une quantité de 0
    }

    void testFindLastIndex() {
        String[] tab1 = {"a", "b", "", ""};
        String[] tab2 = {"a", "b", "c"};
        assertEquals(2, findLastIndex(tab1)); // Dernier index avant les chaînes vides
        assertEquals(3, findLastIndex(tab2)); // Pas de chaînes vides, longueur totale
    }

    void testTerrainTabIsEmpty() {
        Terrain[] emptyTab = {null, null, null};
        Terrain[] nonEmptyTab = {new Terrain(), null};
        assertTrue(TabIsEmpty(emptyTab)); // Vérifie que le tableau vide retourne true
        assertFalse(TabIsEmpty(nonEmptyTab)); // Vérifie qu'un tableau non vide retourne false
    }

    void testStringTabIsEmpty() {
        String[] emptyTab = {"", "", ""};
        String[] nonEmptyTab = {"a", "", ""};
        assertTrue(TabIsEmpty(emptyTab)); // Doit être vide
        assertFalse(TabIsEmpty(nonEmptyTab)); // Ne doit pas être vide
    }

    void testIntTabIsEmpty() {
        int[] emptyTab = {0,0,0};
        int[] nonEmptyTab = {1,0,0};
        assertTrue(TabIsEmpty(emptyTab)); // Doit être vide
        assertFalse(TabIsEmpty(nonEmptyTab)); // Ne doit pas être vide
    }

    void testTabEmptier() {
        String[] tab = {"a", "b", "c"};
        tabEmptier(tab);
        assertTrue(TabIsEmpty(tab)); // Après vidage, le tableau doit être vide
    }

    void testIntToChar() {
        assertEquals('1', IntToChar(1)); // Convertit 1 en caractère '1'
        assertEquals('9', IntToChar(9)); // Convertit 9 en caractère '9'
    }

    void testStringToBoolean() {
        assertTrue(stringToBoolean("true")); // "true" doit donner true
        assertFalse(stringToBoolean("false")); // "false" doit donner false
    }

    void testSpaceEraser() {
        assertEquals("abc", spaceEraser(" a b c ")); // Retire les espaces
        assertEquals("", spaceEraser("")); // Une chaîne vide reste vide
    }

    void testGetVisibleLength(){
        assertEquals(4,getVisibleLength(ANSI_MAGENTA+"test"+ANSI_RESET));
        assertEquals(0,getVisibleLength(ANSI_RESET));
    }

    void testReplace() {
        assertEquals("Hello World", replace("Hello Earth", "Earth", "World"));
        assertEquals("Test", replace("Test", "xyz", "abc")); // Aucun remplacement
        assertEquals("TestTest", replace("TestTest", "", "xyz")); // Chaîne vide à remplacer
        assertEquals("Test", replace("Test", null, "xyz")); // Null 
    }

//-----------------------------SAUVEGARDE/Chargement----------------------------------------------------------------------------------------------------

    void sauvegarderJeu(EtatJeu etat, String nomFichier) {
        // Compter le nombre de lignes nécessaires pour le fichier CSV
        String[][] donneesCSV = new String[2500][6];
        int index = 0;

        // Section 1 : Colons
        donneesCSV[index++] = new String[]{"#SECTION", "COLONS", "", "", "", ""};
        donneesCSV[index++] = new String[]{"id", "age", "sante", "satisfaction", "energie", "anneesDerniereNaissance"};
        int morts = 0;
        for (int i = 0; i < length(etat.colons); i++) {
            if (etat.colons[i] != null) {
                donneesCSV[index++] = new String[]{
                    "" + etat.colons[i].id,
                    "" + etat.colons[i].age,
                    "" + etat.colons[i].sante,
                    "" + etat.colons[i].satisfaction,
                    "" + etat.colons[i].energie,
                    "" + etat.colons[i].anneesDerniereNaissance
                };
            } else {
                morts++;
            }
        }
        donneesCSV[index++] = new String[]{"Colons vivants", "" + compterColonsVivants(etat.colons), "", "", "", ""};
        donneesCSV[index++] = new String[]{"Colons morts", "" + morts, "", "", "", ""};

        // Section 2 : État du Jeu
        donneesCSV[index++] = new String[]{"#SECTION", "ETAT_JEU", "", "", "", ""};
        donneesCSV[index++] = new String[]{"nomJeu", "score", "tour", "", "", ""};
        donneesCSV[index++] = new String[]{
            etat.nom,
            "" + etat.score,
            "" + etat.tour,
            "","","", ""
        };

        // Section 3 : Gestion
        donneesCSV[index++] = new String[]{"#SECTION", "GESTION", "", "", "", ""};
        donneesCSV[index++] = new String[]{
            "capaciteTotalPop", "capaciteEntrepot", "vaisseauPlace", "centreDeCommunicationPlace", "nombreVivants", ""
        };
        donneesCSV[index++] = new String[]{
            "" + etat.gestion.capaciteTotalePop,
            "" + etat.gestion.capaciteEntrepot,
            "" + etat.gestion.vaisseauPlace,
            "" + etat.gestion.centreDeCommunicationPlace,
            "" + etat.gestion.nombreVivants,
            ""
        };

        // Section 4 : Inventaire des Ressources
        donneesCSV[index++] = new String[]{"#SECTION", "INVENTAIRE", "", "", "", ""};
        donneesCSV[index++] = new String[]{"quantite", "", "", "", "", ""};
        for (int i = 0; i < length(etat.ressources); i++) {
            donneesCSV[index++] = new String[]{
                "" + etat.ressources[i].quantite,
                "", "", "","",""
            };
        }

        // Section 5: Planete
        donneesCSV[index++] = new String[]{"#SECTION", "POLLUTION", "", "", "", ""};
        donneesCSV[index++] = new String[]{"pollution", "", "", "", "", ""};
        donneesCSV[index++] = new String[]{
            "" + etat.planete.pollution,
            "", "", "", "", ""
        };

        // Section 6 : Carte
        donneesCSV[index++] = new String[]{"#SECTION", "CASES_CARTE", "", "", "", ""};
        donneesCSV[index++] = new String[]{"symbole", "quantiteRestante", "ressourceActuelle", "ressourceCaseInit", "exploitee", "fonctionne"};
        for (int i = 0; i < length(etat.planete.carte); i++) {
            for (int j = 0; j < length(etat.planete.carte[i]); j++) {
                CaseCarte caseCarte = etat.planete.carte[i][j];
                donneesCSV[index++] = new String[]{
                    caseCarte.symbole,
                    "" + caseCarte.quantiteRestante,
                    caseCarte.ressourceActuelle != null ? caseCarte.ressourceActuelle.nom : "null",
                    caseCarte.ressourceCaseInit != null ? caseCarte.ressourceCaseInit.nom : "null",
                    "" + caseCarte.exploitee,
                    "" + caseCarte.ressourceActuelle.fonctionne
                };
            }
        }

        // Section 7 : Événements
        donneesCSV[index++] = new String[]{"#SECTION", "EVENTS", "", "", "", ""};
        donneesCSV[index++] = new String[]{"entrepotPlein", "dortoirsPlein", "ressourceEstEpuiseeSTR", "filonEpuise", "BatimentsPosed", ""};
        donneesCSV[index++] = new String[]{"" + etat.events.entrepotPlein[0], "" + etat.events.dortoirsPlein[0], "", "", "", ""};
        for (int i = 0; i < length(etat.events.ressourceEstEpuiseeSTR); i++) {
            donneesCSV[index++] = new String[]{etat.events.ressourceEstEpuiseeSTR[i], "", "", "", "", ""};
        }
        for (int i = 0; i < length(etat.events.filonEpuise); i++) {
            donneesCSV[index++] = new String[]{etat.events.filonEpuise[i], "", "", "", "", ""};
        }
        for (int i = 0; i < length(etat.events.BatimentsPosed); i++) {
            donneesCSV[index++] = new String[]{"" + etat.events.BatimentsPosed[i], "", "", "", "", ""};
        }

        // Sauvegarde du fichier CSV
        try {
            saveCSV(donneesCSV, "../save/" + nomFichier);
            println(ANSI_BOLD + "Jeu sauvegardé avec succès" + ANSI_RESET + " dans " + nomFichier);
            delay(2000);
        } catch (Exception e) {
            println("Erreur lors de la sauvegarde du jeu : " + e.getMessage());
        }
    }
    boolean isNumeric(String str) {
        if (str == null || length(str) == 0) {
            return false;
        }
        for (int i = 0; i < length(str); i++) {
            char c = charAt(str, i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    EtatJeu chargerJeu(String nomFichier) {
        EtatJeu etatCharge = new EtatJeu();

        try {
            CSVFile fichierSauvegarde = loadCSV("../save/" + nomFichier);

            // Variables temporaires
            int nombreColons = 0;
            int nombreRessources = 0;
            int tailleCarte = 0;

            // Étape 1 : Analyser les sections pour déterminer les tailles
            for (int ligne = 0; ligne < rowCount(fichierSauvegarde); ligne++) {
                String section = getCell(fichierSauvegarde, ligne, 1);

                switch (section) {
                    case "COLONS":
                        nombreColons = compterEntrees(fichierSauvegarde, ligne + 2, "Colons vivants");
                        break;

                    case "INVENTAIRE":
                        nombreRessources = compterEntrees(fichierSauvegarde, ligne + 2, "#SECTION");
                        break;

                    case "CASES_CARTE":
                        tailleCarte = 27; // Taille fixe (ou dynamique si nécessaire)
                        break;

                    default:
                        // Ignorer les autres sections ici
                        break;
                }
            }

            // Initialiser les objets et les tableaux
            etatCharge.colons = new Colon[nombreColons];
            etatCharge.ressources = new Terrain[nombreRessources];
            etatCharge.gestion = newGestion(etatCharge.ressources, new Terrain[0]);
            etatCharge.events = newEvents(etatCharge, new Terrain[0]);

            // Étape 2 : Charger les données pour chaque section
            for (int ligne = 0; ligne < rowCount(fichierSauvegarde); ligne++) {
                String cellule0 = getCell(fichierSauvegarde, ligne, 0);
                String section = getCell(fichierSauvegarde, ligne, 1);

                switch (section) {
                    case "ETAT_JEU":
                        chargerEtatJeu(etatCharge, fichierSauvegarde, ligne + 2);
                        break;

                    case "GESTION":
                        chargerGestion(etatCharge, fichierSauvegarde, ligne + 2, nombreRessources);
                        break;

                    case "INVENTAIRE":
                        chargerInventaire(etatCharge, fichierSauvegarde, ligne + 2, nombreRessources);
                        break;

                    case "POLLUTION":
                        etatCharge.planete = chargerPlanete(etatCharge, fichierSauvegarde, ligne,tailleCarte);
                        break;

                    case "COLONS":
                        chargerColons(etatCharge, fichierSauvegarde, ligne + 2, nombreColons);
                        break;

                    case "CASES_CARTE":
                        chargerCarte(etatCharge, fichierSauvegarde, ligne + 2, tailleCarte);
                        break;

                    case "EVENTS":
                        chargerEvents(etatCharge, fichierSauvegarde, ligne + 2);
                        break;


                    default:
                        // Autres sections ignorées
                        break;
                }
            }

            println("Jeu chargé avec succès depuis " + nomFichier);
            return etatCharge;

        } catch (Exception e) {
            println("Erreur lors du chargement du jeu : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    void chargerEtatJeu(EtatJeu etat, CSVFile fichier, int ligne) {
        etat.nom = getCell(fichier, ligne, 0);
        etat.score = stringToDouble(getCell(fichier, ligne, 1));
        etat.tour = stringToInt(getCell(fichier, ligne, 2));
    }

    void chargerInventaire(EtatJeu etat, CSVFile fichier, int ligne, int nombreRessources) {
        for (int i = 0; i < nombreRessources; i++) {
            int quantite = stringToInt(getCell(fichier, ligne + i, 0));

            if (quantite < 0) {
                // Si une des données est invalide, loggez l'erreur et passez à la ressource suivante
                println("Erreur : Ressource invalide à la ligne " + (ligne + i));
                continue; // Ignore cette ressource et passe à la suivante
            }
            
            etat.ressources[i] = RESSOURCES_INIT[i];
            etat.ressources[i].quantite=quantite;
        }
    }

    Planete chargerPlanete(EtatJeu etat, CSVFile fichier, int ligne,int tailleCarte) {
        etat.planete=newPlanete(etat.ressources, tailleCarte);
        etat.planete.pollution=stringToDouble(getCell(fichier, ligne+2, 0));
        return etat.planete;
    }

    int compterEntrees(CSVFile fichier, int debut, String stopSection) {
        int count = 0;
        for (int i = debut; i < rowCount(fichier) && !equals(getCell(fichier, i, 0), stopSection); i++) {
            if (!equals(getCell(fichier, i, 0), "")) {
                count++;
            }
        }
        return count;
    }

    void chargerGestion(EtatJeu etat, CSVFile fichier, int ligne, int nombreRessources) {
        etat.gestion.capaciteTotalePop = stringToInt(getCell(fichier, ligne, 0));
        etat.gestion.capaciteEntrepot = stringToInt(getCell(fichier, ligne, 1));
        etat.gestion.vaisseauPlace = stringToInt(getCell(fichier, ligne, 2));
        etat.gestion.centreDeCommunicationPlace = stringToInt(getCell(fichier, ligne, 3));
        etat.gestion.nombreVivants = stringToInt(getCell(fichier, ligne, 4));
    }

    void chargerColons(EtatJeu etat, CSVFile fichier, int ligne, int nombreColons) {
        for (int i = 0; i < nombreColons; i++) {
            Colon colon = new Colon();
            colon.id = stringToInt(getCell(fichier, ligne + i, 0));
            colon.age = stringToInt(getCell(fichier, ligne + i, 1));
            colon.sante = stringToDouble(getCell(fichier, ligne + i, 2));
            colon.satisfaction = stringToDouble(getCell(fichier, ligne + i, 3));
            colon.energie = stringToDouble(getCell(fichier, ligne + i, 4));
            colon.anneesDerniereNaissance = stringToInt(getCell(fichier, ligne + i, 5));

            etat.colons[i] = colon;
        }
    }

    void chargerCarte(EtatJeu etat, CSVFile fichier, int ligne, int tailleCarte) {
        for (int l = 0; l < tailleCarte; l++) {
            for (int c = 0; c < tailleCarte; c++) {
                String symbole = getCell(fichier, ligne, 0);
                int quantiteRestante = stringToInt(getCell(fichier, ligne, 1));
                String ressourceCaseActuelleNom = getCell(fichier, ligne, 2);
                String ressourceCaseInitNom = getCell(fichier, ligne, 3);
                boolean exploitee = stringToBoolean(getCell(fichier, ligne, 4));
                boolean fonctionne= stringToBoolean(getCell(fichier, ligne, 5));

                // Identifier les ressources

                int idRes=0;
                while(idRes<length(etat.ressources) && !equals(etat.ressources[idRes].nom,ressourceCaseActuelleNom)){
                    idRes++;
                }
                if (idRes==length(etat.ressources)){
                    int idBat=0;
                    while(idBat<length(listeBatimentsPossibles) && !equals(listeBatimentsPossibles[idBat].nom,ressourceCaseActuelleNom)){
                        idBat++;
                    }
                    Terrain batiment=listeBatimentsPossibles[idBat];
                    if (idBat==10){
                        int idResInit=0;
                        while(idResInit<length(etat.ressources) && !equals(etat.ressources[idResInit].nom,ressourceCaseInitNom)){
                            idResInit++;
                        }
                        CaseCarte nouvelleCarte =newCaseCarte(batiment, quantiteRestante, RESSOURCES_INIT[idResInit], exploitee);
                            nouvelleCarte.ressourceActuelle = newBatiment(
                                batiment.ResNecessaire,
                                batiment.nom,
                                batiment.symbole,
                                batiment.pollutionGeneree,
                                new int[] { idResInit, 10 },                                  // ressourcesConso
                                new int[] { QUANTITE_RESSOURCES_CONSO_PUIT_DF[idResInit-2], 25 }, // quantiteResConso
                                new int[] { idResInit },                                      // ressourcesGeneree
                                new int[] { QUANTITE_RESSOURCES_GENEREE_PUIT_DF[idResInit-2] }    // quantiteResGeneree
                            );
                        etat.planete.carte[l][c] = nouvelleCarte;
                    }else{
                        etat.planete.carte[l][c] = newCaseCarte(batiment, quantiteRestante, batiment, exploitee);
                    }
                    etat.planete.carte[l][c].ressourceActuelle.fonctionne=fonctionne;
                }else{
                    etat.planete.carte[l][c] = newCaseCarte(RESSOURCES_INIT[idRes], quantiteRestante, RESSOURCES_INIT[idRes], exploitee);
                }
                ligne++;
            }
        }
    }

    Events chargerEvents(EtatJeu etat, CSVFile fichier, int ligne) {
        etat.events=newEvents(etat,listeBatimentsPossibles);

        etat.events.entrepotPlein = new boolean[]{stringToBoolean(getCell(fichier, ligne, 0))};
        etat.events.dortoirsPlein = new boolean[]{stringToBoolean(getCell(fichier, ligne, 1))};

        for (int i = 0; i < length(etat.events.ressourceEstEpuiseeSTR); i++) {
            etat.events.ressourceEstEpuiseeSTR[i] = getCell(fichier, ligne + i, 2);
        }
        for (int i = 0; i < length(etat.events.filonEpuise); i++) {
            etat.events.filonEpuise[i] = getCell(fichier, ligne + i, 3);
        }
        for (int i = 0; i < length(etat.events.BatimentsPosed); i++) {
            etat.events.BatimentsPosed[i] = stringToBoolean(getCell(fichier, ligne + i, 0));
        }
        return etat.events;
    }

    Terrain trouverRessource(Terrain[] ressources, String nom) {
        for (int i=0;i<length(ressources);i++){ 
            if (ressources[i] != null && equals(ressources[i].nom, nom)) {
                return ressources[i];
            }
        }
        return null; // Par défaut, retourne null si introuvable
    }

//-----------------------------RESSOURCES---------------------------------------------------------------------------------------------------------------

    final Terrain[] RESSOURCES_INIT=new Terrain[]{ 
                        //Nom,Symbole,probaApparition,quantite
            newRessource("Terre", " + ", 1.0, 0), // Ressource par défaut
            newRessource("Terre", " + ", 0.200, 0),
            newRessource("Fer", "Fe ", 0.100, 100), 
            newRessource("Cuivre", "Cu ", 0.075, 50),
            newRessource("Carbone", " C ", 0.050, 35),
            newRessource("Soufre", " S ", 0.025, 10),
            newRessource("Uranium", " U ", 0.0005, 5), // Ressource rare et polluante
            newRessource("💧 Eau", "H2O", 1.0, 100), // Ressources essentielles
            newRessource("💨 Air", "O2", 1.0, 50), 
            newRessource("🍎 Nourriture", "Rations", 1.0, 50),

            newRessource("⚡ Electricité", "Elec",1.0,100)
        };

    // Fonction pour créer une nouvelle ressource avec des attributs spécifiques
    Terrain newRessource(String nom, String symbole, double probaApparition, int quantite) {
        Terrain r = new Terrain();
        r.nom = nom; // Nom de la ressource
        r.symbole = symbole; // Symbole représentant la ressource
        r.probaApparition = probaApparition; // Probabilité d'apparition de la ressource
        r.quantite = quantite; // Quantité initiale de la ressource
        
        return r; // Retourne une instance de la ressource
    }

    void ressourceEmptyVerif(EtatJeu etat){
        for(int i=0;i<length(etat.events.ressourceEstEpuiseeSTR);i++){
            if(etat.ressources[i].quantite>0){
                etat.events.ressourceEstEpuiseeSTR[i]="";
            }
        }
    }

    String getRessourceColor(String ressource,CaseCarte caseC) {
        if (caseC.ressourceActuelle == null || caseC.ressourceActuelle.symbole == null) {
            return ANSI_RESET; // ou une autre couleur par défaut
        }
        if(caseC.ressourceActuelle.fonctionne==true){
            switch (ressource) {
                // Ressources initiales
                case " + ":
                    return ANSI_WHITE + ANSI_DARK_BG;
                case "Fe ":
                    return ANSI_RED + ANSI_DARK_BG;
                case "Cu ":
                    return ANSI_ORANGE + ANSI_DARK_BG;
                case " C ":
                    return ANSI_GREEN + ANSI_DARK_BG;
                case " S ":
                    return ANSI_BLUE + ANSI_DARK_BG;
                case " U ":
                    return ANSI_PURPLE + ANSI_DARK_BG;
                
                // Bâtiments
                case " V ":  // Vaisseau
                    return ANSI_GREENKINDOF + ANSI_DARK_BG;
                case " ⌂ ":  // Dortoir
                    return ANSI_BROWN + ANSI_DARK_BG;
                case " ⌧ ":  // Entrepôt
                    return ANSI_GRAY + ANSI_DARK_BG;
                case " ☤ ":  // Centre de Communication Terrien
                    return ANSI_MAGENTA + ANSI_DARK_BG;
                case " ◈ ":  // Cinema
                    return ANSI_YELLOW + ANSI_DARK_BG;
                case " ⌯ ":  // Capteur d'Humiditée
                    return ANSI_BLUE_LIGHT + ANSI_DARK_BG;
                case " ✲ ":  // Ferme hydroponique
                    return ANSI_GREEN_LIGHT + ANSI_DARK_BG;
                case " ≎ ":  // Recycleur d'Air
                    return ANSI_CYAN_LIGHT + ANSI_DARK_BG;
                case " ☼ ":  // Panneau Stellaire
                    return ANSI_YELLOW_BRIGHT + ANSI_DARK_BG;
                case " ☢ ":  // Centrale nucléaire
                    return ANSI_YELLOW + ANSI_DARK_BG;
                case " ⍒ ":  // Puit de Forage
                    return getRessourceColor(caseC.ressourceCaseInit.symbole,caseC);
                default:
                    return ANSI_RESET;
            }
        }else{
            switch (ressource) {
                // Bâtiments
                case " V ":  // Vaisseau
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ⌂ ":  // Dortoir
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ⌧ ":  // Entrepôt
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ☤ ":  // Centre de Communication Terrien
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ◈ ":  // Cinema
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ⌯ ":  // Capteur d'Humiditée
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ✲ ":  // Ferme hydroponique
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ≎ ":  // Recycleur d'Air
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ☼ ":  // Panneau Stellaire
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ☢ ":  // Centrale nucléaire
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                case " ⍒ ":  // Puit de Forage
                    return ANSI_RED_BRIGHT + ANSI_DARK_BG;
                default:
                    return ANSI_RESET;
            }
        }
    }

//-----------------------------BATIMENTS----------------------------------------------------------------------------------------------------------------
    
    final Terrain[] listeBatimentsPossibles = new Terrain[]{
        newBatiment(newRecette(), "Vaisseau", " V ", POLLUTION_VAISSEAU, new int[]{10}, new int[]{100}, new int[]{10}, new int[]{250}),
        newBatiment(newRecette(new int[]{2, 3}, new int[]{5, 10}), "Dortoir", " ⌂ ", POLLUTION_DORTOIR, new int[]{10}, new int[]{10}, new int[]{-1}, new int[]{0}),
        newBatiment(newRecette(new int[]{2}, new int[]{15}), "Entrepôt", " ⌧ ", POLLUTION_ENTREPOT, new int[]{10}, new int[]{5}, new int[]{-1}, new int[]{0}),
        newBatiment(newRecette(new int[]{2, 3, 4}, new int[]{125, 35, 30}), "Centre de Communication Terrien", " ☤ ", POLLUTION_CCT, new int[]{10}, new int[]{35}, new int[]{-1}, new int[]{0}),
        newBatiment(newRecette(new int[]{2, 3, 4}, new int[]{75, 25, 10}), "Cinema", " ◈ ", POLLUTION_CINEMA, new int[]{10}, new int[]{50}, new int[]{-1}, new int[]{0}),
        newBatiment(newRecette(new int[]{2, 3, 5}, new int[]{5, 10, 5}), "Capteur d'Humiditée", " ⌯ ", POLLUTION_CAPTEUR, new int[]{10}, new int[]{20}, new int[]{7}, new int[]{10}),
        newBatiment(newRecette(new int[]{2, 4, 7}, new int[]{5, 5, 5}), "Ferme hydroponique", " ✲ ", POLLUTION_FERME, new int[]{7, 10}, new int[]{7, 20}, new int[]{8, 9}, new int[]{5, 10}),
        newBatiment(newRecette(new int[]{3, 7}, new int[]{5, 5}), "Recycleur d'Air", " ≎ ", POLLUTION_RECYCLEUR, new int[]{7, 10}, new int[]{1, 20}, new int[]{8}, new int[]{10}),
        newBatiment(newRecette(new int[]{3, 5}, new int[]{5, 10}), "Panneau Stellaire", " ☼ ", POLLUTION_PANNEAU, new int[]{10}, new int[]{0}, new int[]{10}, new int[]{20}),
        newBatiment(newRecette(new int[]{2, 6, 7}, new int[]{115, 5, 30}), "Centrale nucléaire", " ☢ ", POLLUTION_CENTRALE, new int[]{6, 7, 10}, new int[]{2, 10, 100}, new int[]{10}, new int[]{400}),
        newBatiment(newRecette(new int[]{2, 3}, new int[]{10, 5}), "Puit de Forage", " ⍒ ", POLLUTION_PUIT, new int[]{0, 10}, new int[]{0, 25}, new int[1], new int[1])
    };

    // Si on veut faire des ressources plus complexes et modifier les recettes en conséquence
    //newBatiment(newRecette(new Terrain[]{RESSOURCES_INIT[2],RESSOURCES_INIT[3],RESSOURCES_INIT[4]},new int[]{75,25,10}),"Usine de Transformation"," ⌬ ",0.001,25,new int[1],new int[1]), //Prod/Conso variable selon la ressource

    Recette newRecette() {
        Recette r = new Recette();
        r.coutDeConstruction = new int[0];
        return r;
    }

    Recette newRecette(int[] ressources, int[] quantiteNecessaire) {
        Recette r = new Recette();
        r.coutDeConstruction = ressources;
        r.quantiteNecessaire = quantiteNecessaire;
        return r;
    }

    Terrain newBatiment(Recette ResNecessaire, String nom, String symbole, double pollutionGeneree, int[] ressourcesConso, int[] quantiteResConso, int[] ressourcesGeneree, int[] quantiteResGeneree) {
        Terrain b = new Terrain();
        b.ResNecessaire = ResNecessaire;
        b.ressourcesGeneree = ressourcesGeneree;
        b.quantiteResGeneree = quantiteResGeneree;
        b.ressourcesConso = ressourcesConso;
        b.quantiteResConso = quantiteResConso;
        b.pollutionGeneree = pollutionGeneree;
        b.nom = nom;
        b.symbole = symbole;
        return b;
    }

    Terrain cloneTerrain(Terrain original) {
        return newBatiment(
            original.ResNecessaire,
            original.nom,
            original.symbole,
            original.pollutionGeneree,
            original.ressourcesConso,
            original.quantiteResConso,
            original.ressourcesGeneree,
            original.quantiteResGeneree
        );
    }

    int placerBatiment(EtatJeu etat, Terrain[] listeBatimentsPossibles, int lig, int col, Terrain batiment) {
        etat.gestion.posBat[countLastPos(etat.gestion.posBat)] = new int[]{lig, col};
        int id = 0;
        while (id < length(listeBatimentsPossibles) && batiment != listeBatimentsPossibles[id]) {
            id++;
        }

        // Créer un clone du bâtiment pour que deux bâtiments de même type ne soient pas liés
        Terrain batimentClone = cloneTerrain(batiment);

        for (int i = 0; i < length(batiment.ResNecessaire.coutDeConstruction); i++) {
            int idRes = batiment.ResNecessaire.coutDeConstruction[i];
            etat.ressources[idRes].quantite -= batiment.ResNecessaire.quantiteNecessaire[i];
            etat.gestion.variationPoseRessources[idRes] -= batiment.ResNecessaire.quantiteNecessaire[i];
        }
        
        switch (id) {
            case VAISSEAU_INDEX:
                return placerVaisseau(etat, lig, col, batiment);
            case DORTOIR_INDEX:
                return placerDortoir(etat, lig, col, batiment);
            case ENTREPOT_INDEX:
                return placerEntrepot(etat, lig, col, batiment);
            case CCT_INDEX:
                return placerCCT(etat, lig, col, batiment);
            default:
                if (id < MAX_BUILDING_INDEX) {
                    etat.planete.carte[lig][col] = newCaseCarte(batiment, INFINITE_RESOURCE, batiment, true);
                } else {
                    placerPuitDeForage(etat, lig, col, batiment);
                }
                break;
        }
        return id;
    }

    int placerVaisseau(EtatJeu etat, int lig, int col, Terrain batiment) {
        etat.planete.carte[lig][col] = newCaseCarte(batiment, INFINITE_RESOURCE, batiment, true);
        etat.gestion.vaisseauPlace = 1;
        return etat.gestion.vaisseauPlace;
    }

    int placerDortoir(EtatJeu etat, int lig, int col, Terrain batiment) {
        etat.planete.carte[lig][col] = newCaseCarte(batiment, INFINITE_RESOURCE, batiment, true);
        etat.gestion.capaciteTotalePop += DORTOIR_CAPACITY;
        return etat.gestion.capaciteTotalePop;
    }

    int placerEntrepot(EtatJeu etat, int lig, int col, Terrain batiment) {
        etat.planete.carte[lig][col] = newCaseCarte(batiment, INFINITE_RESOURCE, batiment, true);
        etat.gestion.capaciteEntrepot += ENTREPOT_CAPACITY;
        return etat.gestion.capaciteEntrepot;
    }

    int placerCCT(EtatJeu etat, int lig, int col, Terrain batiment) {
        etat.planete.carte[lig][col] = newCaseCarte(batiment, INFINITE_RESOURCE, batiment, true);
        etat.gestion.centreDeCommunicationPlace = 1;
        return etat.gestion.centreDeCommunicationPlace;
    }

    void placerPuitDeForage(EtatJeu etat, int lig, int col, Terrain batiment) {
        String batimentNom = etat.planete.carte[lig][col].ressourceCaseInit.nom;
        for (int i = 0; i < length(QUANTITE_RESSOURCES_CONSO_PUIT_DF); i++) {
            int j = i + 2;
            if (equals(batimentNom, etat.ressources[j].nom)) {
                CaseCarte nouvelleCarte = newCaseCarte(batiment, (BASE_RESOURCE_AMOUNT - i * RESOURCE_DECREMENT), etat.planete.carte[lig][col].ressourceCaseInit, true);
            
                // Créer de nouveaux tableaux spécifiques à cette case
                nouvelleCarte.ressourceActuelle = newBatiment(
                    batiment.ResNecessaire,
                    batiment.nom,
                    batiment.symbole,
                    batiment.pollutionGeneree,
                    new int[] { j, 10 },                                  // ressourcesConso
                    new int[] { QUANTITE_RESSOURCES_CONSO_PUIT_DF[i], 25 }, // quantiteResConso
                    new int[] { j },                                      // ressourcesGeneree
                    new int[] { QUANTITE_RESSOURCES_GENEREE_PUIT_DF[i] }    // quantiteResGeneree
                );
                etat.planete.carte[lig][col] = nouvelleCarte;
            }
        }
    }

    // Repère la dernière position enregistrée d'un batiment
    int countLastPos(int[][] posBat) {
        int id = 0;
        while (id < length(posBat) && posBat[id][0] != 0) {
            id++;
        }
        return id;
    }

    boolean batimentPosable(EtatJeu etat, Terrain[] listeBatimentsPossibles, int lig, int col, Terrain batiment) {
        boolean posableTerrain = true;
        boolean posableRessource = true;

        // Vérifier si le bâtiment est un puits et la ressource est de type non posable
        if ((equals(etat.planete.carte[lig][col].ressourceCaseInit.nom, etat.ressources[0].nom) || 
            equals(etat.planete.carte[lig][col].ressourceCaseInit.nom, etat.ressources[1].nom)) && 
            equals(batiment.nom, listeBatimentsPossibles[PUITS_INDEX].nom)) {
            posableTerrain = false;
        }

        // Vérifier si la ressource est dans la plage non posable pour les autres bâtiments
        int id = 0;
        while (id < length(etat.ressources) && 
            !equals(etat.planete.carte[lig][col].ressourceCaseInit.nom, etat.ressources[id].nom)) {
            id++;
        }
        if (!equals(batiment.nom, listeBatimentsPossibles[PUITS_INDEX].nom) && 
            (id >= RESOURCE_INDEX_MIN && id < RESOURCE_INDEX_MAX)) {
            posableTerrain = false;
        }

        // Vérifier c'est bâtiments spécifiques sont déjà placés
        if ((equals(batiment.nom,listeBatimentsPossibles[CCT_INDEX].nom) && etat.gestion.centreDeCommunicationPlace == 1) || (batiment == listeBatimentsPossibles[VAISSEAU_INDEX] && etat.gestion.vaisseauPlace == 1)) {
            posableTerrain = false;
        }

        // Vérifier les ressources nécessaires pour la construction
        id = 0;
        while (id < length(batiment.ResNecessaire.coutDeConstruction) && posableRessource) {
            int idRes = batiment.ResNecessaire.coutDeConstruction[id];
            if (batiment.ResNecessaire.quantiteNecessaire[id] > etat.ressources[idRes].quantite) {
                posableRessource = false;
            }
            id++;
        }

        return posableTerrain && posableRessource && !etat.planete.carte[lig][col].exploitee;
    }

    int calcCapaciteEntrepot(EtatJeu etat) {
        int capaciteEntreposee = 0;
        for (int i = 0; i < length(etat.ressources) - 1; i++) { // L'électricité n'est pas comprise dans le stockage
            capaciteEntreposee += etat.ressources[i].quantite;
        }
        return capaciteEntreposee;
    }

    void verifCapaciteEntrepot(EtatJeu etat){
        if(calcCapaciteEntrepot(etat) < etat.gestion.capaciteEntrepot){
            etat.events.entrepotPlein[0]=false;
        }else{
            etat.events.entrepotPlein[0]=true;
        }
    }

    void mettreAJourBatiment(EtatJeu etat, Terrain[] listeBatimentsPossibles) {
        int capaciteEntreposee = calcCapaciteEntrepot(etat);
        for (int i = 0; i < countLastPos(etat.gestion.posBat); i++) {
            verifCapaciteEntrepot(etat);
            CaseCarte batiment = etat.planete.carte[etat.gestion.posBat[i][0]][etat.gestion.posBat[i][1]];

            // Si le bâtiment est un puits de forage, traitement spécial
            if (equals(batiment.ressourceActuelle.nom, listeBatimentsPossibles[10].nom)) {
                batiment.ressourceActuelle.fonctionne=mettreAJourPuitDeForage(etat, listeBatimentsPossibles, etat.gestion.posBat[i][0], etat.gestion.posBat[i][1], capaciteEntreposee);
                continue;
            }
            
            // Pour les autres bâtiments
            if (batiment.ressourceActuelle.ressourcesGeneree[0] != -1) {
                boolean peutConsommer = true;
                
                // Vérifier si le bâtiment peut consommer ses ressources
                for (int j = 0; j < length(batiment.ressourceActuelle.ressourcesConso); j++) {
                    int idRes = batiment.ressourceActuelle.ressourcesConso[j];
                    if (etat.ressources[idRes].quantite < batiment.ressourceActuelle.quantiteResConso[j]) {
                        peutConsommer = false;
                        break;
                    }
                }

                // Si le bâtiment ne fonctionnait pas et peut maintenant consommer
                if (!batiment.ressourceActuelle.fonctionne && peutConsommer && !etat.events.entrepotPlein[0]) {
                    batiment.ressourceActuelle.fonctionne = marcheArret(batiment);
                }
                // Si le bâtiment fonctionnait mais ne peut plus consommer
                else if (batiment.ressourceActuelle.fonctionne && (!peutConsommer || etat.events.entrepotPlein[0])) {
                    batiment.ressourceActuelle.fonctionne = marcheArret(batiment);
                }
                
                // Si le bâtiment fonctionne, gérer la production
                if (batiment.ressourceActuelle.fonctionne) {
                    int id = 0;
                    while (id < length(listeBatimentsPossibles) && !equals(batiment.ressourceActuelle.nom, listeBatimentsPossibles[id].nom)) {
                        id++;
                    }
                    
                    etat.gestion.tabMoyennepollution[id] += batiment.ressourceActuelle.pollutionGeneree;
                    consommer(etat, etat.gestion.posBat[i][0], etat.gestion.posBat[i][1]);
                    batiment.ressourceActuelle.fonctionne=generer(etat, listeBatimentsPossibles, etat.gestion.posBat[i][0], etat.gestion.posBat[i][1], capaciteEntreposee);
                }
            }
            verifCapaciteEntrepot(etat);
        }
    }

   boolean mettreAJourPuitDeForage(EtatJeu etat, Terrain[] listeBatimentsPossibles, int lig, int col, int capaciteEntreposee) {
        CaseCarte batiment = etat.planete.carte[lig][col];
        int id = 0;
        while (id < length(etat.ressources) && batiment.ressourceCaseInit != etat.ressources[id]) {
            id++;
        }
        
        if(batiment.quantiteRestante<=0){
            batiment.ressourceActuelle.fonctionne=marcheArret(batiment);
            String ligSTR="";
            if (lig>9){
                ligSTR+=lig;
            }else{
                ligSTR+=" "+lig;
            }
            etat.events.filonEpuise[findLastIndex(etat.events.filonEpuise)]=ANSI_RED+"Le filon du secteur "+ligSTR+":"+(char)(64+col)+" est épuisé !"+ANSI_RESET;
        
        }else if(etat.events.entrepotPlein[0]==false){
            if(batiment.ressourceActuelle.quantiteResGeneree[0]+capaciteEntreposee <=etat.gestion.capaciteEntrepot){
                batiment.quantiteRestante-=batiment.ressourceActuelle.quantiteResConso[0];
                consoRes(etat,1,batiment);
                batiment.ressourceActuelle.fonctionne=genererRes(etat,0,batiment,capaciteEntreposee, listeBatimentsPossibles, lig, col);

                etat.gestion.tabMoyennepollution[10]+=batiment.ressourceActuelle.pollutionGeneree;
            
            }else{
                batiment.quantiteRestante-=etat.gestion.capaciteEntrepot-capaciteEntreposee;
                consoRes(etat,1,batiment);
                batiment.ressourceActuelle.fonctionne=genererRes(etat,0,batiment,capaciteEntreposee, listeBatimentsPossibles, lig, col);
            
                etat.gestion.tabMoyennepollution[10]+=batiment.ressourceActuelle.pollutionGeneree;
            }
        }else{
            batiment.ressourceActuelle.fonctionne=marcheArret(batiment);
        }
        return batiment.ressourceActuelle.fonctionne;
    }


    void consommer(EtatJeu etat,int lig, int col){
        CaseCarte batiment=etat.planete.carte[lig][col];
        for(int e=0;e<length(batiment.ressourceActuelle.quantiteResConso);e++){
            consoRes(etat,e,batiment);
        }
    }

    void consoRes(EtatJeu etat, int id, CaseCarte batiment){
        int idRes=batiment.ressourceActuelle.ressourcesConso[id];
        etat.ressources[idRes].quantite-=batiment.ressourceActuelle.quantiteResConso[id];
        etat.gestion.variationRessources[idRes]-=batiment.ressourceActuelle.quantiteResConso[id];
    }

    boolean generer(EtatJeu etat, Terrain[] listeBatimentsPossibles, int lig, int col, int capaciteEntreposee) {
        CaseCarte batiment = etat.planete.carte[lig][col];
        if (etat.events.entrepotPlein[0]==false){
            for (int f = 0; f < length(batiment.ressourceActuelle.quantiteResGeneree); f++) {
                batiment.ressourceActuelle.fonctionne=genererRes(etat,f,batiment,capaciteEntreposee, listeBatimentsPossibles, lig, col);
            }
        }else{
            batiment.ressourceActuelle.fonctionne=marcheArret(batiment);
        }
        return batiment.ressourceActuelle.fonctionne;
    }

    boolean genererRes(EtatJeu etat, int id, CaseCarte batiment, int capaciteEntreposee, Terrain[] listeBatimentsPossibles, int lig, int col){
        int idRes = batiment.ressourceActuelle.ressourcesGeneree[id];
        if(((batiment.ressourceActuelle.quantiteResGeneree[id]+capaciteEntreposee)<=etat.gestion.capaciteEntrepot) || batiment.ressourceActuelle.ressourcesGeneree[id]==10){// L'electricite n'est pas comprise dans le stockage
            
            etat.ressources[idRes].quantite+=batiment.ressourceActuelle.quantiteResGeneree[id];
            etat.gestion.variationRessources[idRes]+=batiment.ressourceActuelle.quantiteResGeneree[id];
        }else{
            etat.ressources[idRes].quantite+=etat.gestion.capaciteEntrepot-capaciteEntreposee;
            etat.gestion.variationRessources[idRes]+=etat.gestion.capaciteEntrepot-capaciteEntreposee;
            
            etat.events.entrepotPlein[0]=true;
            batiment.ressourceActuelle.fonctionne=marcheArret(batiment);
        }
        return batiment.ressourceActuelle.fonctionne;
    }


    boolean marcheArret(CaseCarte batiment){
        return !batiment.ressourceActuelle.fonctionne;
    }


//-----------------------------PLANETE------------------------------------------------------------------------------------------------------------------

    // Fonction pour créer une nouvelle planète avec une carte et des ressources
    Planete newPlanete(Terrain[] ressources, int taille) {
        Planete planete = new Planete();
        planete.pollution = 0.0; // Initialisation du niveau de pollution à 0

        // Création de la carte de la planète avec des dimensions spécifiques
        planete.carte = new CaseCarte[taille][taille];
        for (int l = 0; l < taille; l++) {
            for (int c = 0; c < taille; c++) {
                planete.carte[l][c] = newCaseCarte(ressources[0], 500, ressources[0], false);
            }
        }
        init(planete, ressources); // Initialisation de la carte avec les ressources

        return planete; // Retourne l'objet planète
    }

    CaseCarte newCaseCarte(Terrain ressourceActuelle, int quantiteRestante, Terrain ressourceCaseInit, boolean exploitee) {
        CaseCarte c = new CaseCarte();
        c.ressourceActuelle = cloneTerrain(ressourceActuelle);
        // Si ressourceCaseInit est différent de ressourceActuelle, le cloner aussi
        c.ressourceCaseInit = (ressourceCaseInit == ressourceActuelle) ? 
                        c.ressourceActuelle : 
                        cloneTerrain(ressourceCaseInit);
        c.exploitee=exploitee;
        c.symbole=ressourceActuelle.symbole;
        c.quantiteRestante=quantiteRestante;
        return c;
    }

    // Fonction pour trouver l'indice de la ressource correspondant à une probabilité donnée
    int encadrement(Terrain[] ressources, double random) {
        int randomAbs = -1; 
        int i = 0;

        // Parcourt les ressources pour trouver l'intervalle correspondant
        while (randomAbs == -1 && i < length(ressources)) {
            if (ressources[i] != null && ressources[i].probaApparition <= random) {
                randomAbs = i; // Indice trouvé
            }
            i += 1;
        }
        if (randomAbs == -1) {
            return 0; 
        }

        // Calcul de la différence pour ajuster l'indice si nécessaire
        double diffSup = (ressources[randomAbs - 1].probaApparition - random) ;
        double diffInf = random - ressources[randomAbs].probaApparition;

        if (randomAbs > 0 && diffSup < diffInf) {
            randomAbs -=1;
        }

        return randomAbs; // Retourne l'indice correspondant
    }

    // Initialise la carte de la planète avec des ressources naturelles
    void init(Planete planete, Terrain[] RESSOURCES_INIT) {
        for (int l = 0; l < length(planete.carte, 1); l++) {
            for (int c = 0; c < length(planete.carte, 2); c++) {
                double random = (double)(int)(random() * RANDOM_MULTIPLIER) / RANDOM_MULTIPLIER; // Génération d'une probabilité aléatoire
                if (random == 0.0) {
                    random += RANDOM_CORRECTION; // Correction pour éviter une valeur nulle
                }
                // Assignation d'une ressource à la case en fonction de la probabilité
                int IDRessource=encadrement(RESSOURCES_INIT, random);
                planete.carte[l][c] = newCaseCarte(RESSOURCES_INIT[IDRessource],500,RESSOURCES_INIT[IDRessource],false);
            }
        }
    }

    double pollutionTotale(EtatJeu etat){
        double pollutionTotale=0.0;
        for (int i=0;i<length(etat.gestion.tabMoyennepollution);i++){
            pollutionTotale+=etat.gestion.tabMoyennepollution[i];
        }
        int polluant=1;
        for (double e:etat.gestion.tabMoyennepollution){
            if (e!=0.0){
                polluant++;
            }
        }
        // Calculer la nouvelle valeur de la pollution
        double nouvellePollution = (double)(int)(((pollutionTotale/polluant)+etat.planete.pollution)*1000000)/1000000;
        etat.planete.pollution = nouvellePollution;
        // Réinitialisation du tableau de moyennes de pollution pour le prochian tour
        etat.gestion.tabMoyennepollution=new double[length(listeBatimentsPossibles)];
        return nouvellePollution;
    }

//-----------------------------COLONS-------------------------------------------------------------------------------------------------------------------

    // Fonction pour créer un nouveau colon avec des caractéristiques aléatoires
    Colon newColon(int age, int id) {
        Colon colon = new Colon();
        
        colon.age = age; // Âge du colon
        colon.id = id; // Identifiant unique du colon
        colon.sante=1.0;
        colon.satisfaction = 1.0; // Satisfaction initiale du colon
        colon.energie = 1.0; // Énergie initiale du colon
        colon.anneesDerniereNaissance = 0; // Années écoulées depuis la dernière naissance

        return colon; // Retourne une instance de colon
    }

    Colon[] genererColons(int nombreColons) {
        Colon[] colons = new Colon[nombreColons];
        
        for (int i = 0; i < nombreColons; i++) {
            // Générer un âge aléatoire entre 20 et 40 ans
            int age = 20 + (int)(random() * 21);
            
            // Créer un nouveau colon avec un âge et un identifiant unique
            colons[i] = newColon(age, i + 1);
        }
        
        return colons;
    }

    // Fonction pour permettre aux colons de se reproduire
    Colon[] reproduire(Colon[] colons,EtatJeu etat) {
        // Trouver deux colons éligibles pour la reproduction
        Colon[] parents;
        if (etat.gestion.capaciteTotalePop>etat.gestion.nombreVivants){
            parents = trouverParentsEligibles(colons,etat.gestion);
        }else{
            etat.events.dortoirsPlein[0]=true;
            return colons;
        }
        if (parents[0]==null || parents[1]==null){
            return colons;
        }else{
            // Générer un nouvel enfant à partir des deux parents
            Colon nouvelEnfant = newColon(0, trouverProchainId(colons));// genère un enfant
        
            // Ajouter l'enfant à la liste des colons
            colons[etat.gestion.nombreVivants] = nouvelEnfant; // Ajouter l'enfant à l'emplacement disponible
            etat.events.naissance[findLastIndex(etat.events.naissance)]="Colon N°" + nouvelEnfant.id +" (Enfant de N°" + parents[0].id + " et N°" + parents[1].id + ")";
        
            // Réinitialiser le compteur d'années depuis la dernière naissance pour les parents
            parents[0].anneesDerniereNaissance = 0;
            parents[1].anneesDerniereNaissance = 0;
        
            return colons; // Retourner la liste des colons mise à jour
        }
    }

    // Fonction pour trouver deux colons éligibles pour la reproduction
    Colon[] trouverParentsEligibles(Colon[] colons,Gestion gestion) {
        Colon[] parents = new Colon[2];
        boolean mereTrouvee=false,pereTrouve=false;
        
        int cmpt=0;
        while(cmpt < length(colons) && (!mereTrouvee || !pereTrouve) && colons[cmpt]!=null) { 
            // Vérifier les conditions de reproduction
            boolean conditionsReproduction = (
                colons[cmpt].age >= AGE_REPRODUCTION_MIN && 
                colons[cmpt].age <= AGE_REPRODUCTION_MAX &&
                colons[cmpt].sante > 0.5 &&
                colons[cmpt].anneesDerniereNaissance >= 2
            );
            
            // Assigner le colon comme mâle ou femelle s'il remplit les conditions
            if (conditionsReproduction) {
                if (colons[cmpt].id%2==1 && mereTrouvee == false) {
                    parents[0] = colons[cmpt];
                    mereTrouvee=true;
                } else if (colons[cmpt].id%2==0 && pereTrouve == false) {
                    parents[1] = colons[cmpt];
                    pereTrouve=true;
                }
            }
            
            cmpt++;
        }
        return parents; // Retourner le couple de parents éligibles
    }

    // Fonction pour trouver le prochain identifiant unique pour un colon
    int trouverProchainId(Colon[] colons) {
        int cmpt=0;
        while(colons[cmpt]!=null && cmpt<length(colons)){
            cmpt++;
        } //
        return colons[cmpt-1].id+1; // Retourner l'ID suivant
    }

    // Fonction pour vieillir un colon
    void vieillir(EtatJeu etat,int id) {
        boolean estMort=false;
        etat.colons[id].age++; // Augmenter l'âge
 
        if(etat.colons[id].age<=20){ // Diminuer la santé
            etat.colons[id].sante-=0.02;
        }else if (etat.colons[id].age<=45){
            etat.colons[id].sante -= 0.05;
        }else{
            etat.colons[id].sante-=0.10;
        }
        etat.colons[id].sante-=etat.planete.pollution;
        // Si la santé tombe à 0 ou moins, le colon meurt
        if (etat.colons[id].sante <= 0.0) {
            etat.events.deces[findLastIndex(etat.events.deces)]="☠ Le colon N°" + etat.colons[id].id + " est décédé à l'âge de " + etat.colons[id].age + ".";
            etat.colons[id]=null;
            estMort=true;
        }else{
            consoColon(etat,id, 8, 1.00);
            consoColon(etat,id, 7, 0.20);
            consoColon(etat,id, 9, 0.20);
        }
        // Si la santé tombe à 0 ou moins, le colon meurt
        if (!estMort){
            if (etat.colons[id].sante <= 0.0) {
                etat.events.deces[findLastIndex(etat.events.deces)]="☠ Le colon N°" + etat.colons[id].id + " est décédé à l'âge de " + etat.colons[id].age + ".";
                etat.colons[id]=null;
            }else{
                etat.colons[id].anneesDerniereNaissance++; // Incrémenter les années depuis la dernière naissance
            }
        }

    }

    void consoColon(EtatJeu etat, int id, int a,double santePerdue){
        if (etat.ressources[a].quantite==0){ //Si Air=0 alors le colon meurt
            etat.colons[id].sante-=santePerdue;
        }else{
            etat.ressources[a].quantite-=1;
            etat.gestion.variationRessources[a]-=1;
        }
    }

        // Met à jour les colons en les faisant vieillir et en affichant leur état
    void mettreAJourColons(EtatJeu etat) {
        for (int i = 0; i < length(etat.colons); i++) {
            if (etat.colons[i] !=null && etat.colons[i].sante !=0.0) {
                // Faire vieillir le colon et mettre à jour son état
                vieillir(etat,i);
                //satisfactionColon(colon[i]);
            }
        }
        verifCapaciteEntrepot(etat);
        triTableau(etat.colons);
    }

    void triTableau(Colon[] colons){
        for (int i = 1; i < length(colons); i++) {
            Colon current = colons[i];
            if (colons != null) {
                int j = i - 1;

                // Déplace les éléments `null` ou les cases déjà remplies plus loin
                while (j >= 0 && colons[j] == null) {
                    colons[j + 1] = colons[j];
                    j-=1;
                }

                // Place l'élément courant à la bonne position
                colons[j + 1] = current;
            }
        }
    }

    Colon[] incrementationTailleTableau(Colon[] colons,int nombreVivants){
        Colon[] colonsNewTab=new Colon[nombreVivants*2]; //double la taille du tableau pour parer à toute éventualité
        for (int i=0;i<nombreVivants;i++){
            colonsNewTab[i]=colons[i];
        }
        return colonsNewTab;
    }

    // Fonction pour compter le nombre de colons vivants
    int compterColonsVivants(Colon[] colons) {
        int nombreVivants = 0;
        while(nombreVivants<length(colons) && colons[nombreVivants]!=null){
            nombreVivants++;
        }
        return nombreVivants; // Retourner le nombre de colons vivants
    }

//-----------------------------PARTIE-------------------------------------------------------------------------------------------------------------------

    void placerVaisseau(Terrain[] listeBatimentsPossibles,EtatJeu etat){
        Terrain vaisseau=listeBatimentsPossibles[0];
        println("\n" +ANSI_BOLD + "Choisissez une Position de départ " + ANSI_RESET + "où atterir: ");
        int col=saisirColonne(length(etat.planete.carte,2));
        int lig=saisirLigne(length(etat.planete.carte,1));
        
        placerBatiment(etat,listeBatimentsPossibles,lig,col,vaisseau);
    }
    
    // Affiche l'état de la planète, y compris la pollution et les ressources
    String[][] afficherEtatCreerTab(EtatJeu etat) {
        String[][] afficherTab=new String[8][];

        afficherTab[1]=creerTabCarteAffic(etat);
        
        if(etat.tour>0){
            afficherTab[2]=creerSeparateur();
            afficherTab[3]=creerTabResAffic(etat);
            afficherTab[4]=creerSeparateur();
            afficherTab[5]=creerTabEvents(etat);
            afficherTab[6]=creerSeparateur();
            afficherTab[7]=creeTabEtatPlanete(etat);
        }else{
            for (int v=2;v<length(afficherTab,1);v++){
                afficherTab[v]=new String[27];
                for (int k=0;k<length(afficherTab[2]);k++){
                    afficherTab[v][k]="";
                }
            }
        }


        if(etat.tour==0){
            afficherTab[0]=new String[]{ANSI_BOLD + "Carte de la Planète" + ANSI_RESET+"\n"};
        }else{
            afficherTab[0]=new String[]{ANSI_BOLD + formatCharacteristic("Carte de la Planète",length(afficherTab[1][0])+3)+formatCharacteristic("Stockage",length(afficherTab[3][26])+3)+formatCharacteristic("État de la Colonie et de la planète",length(afficherTab[5][26]))+"\n"+ANSI_RESET};
        }
        return afficherTab;
    }

    String[] creerTabCarteAffic(EtatJeu etat){
        String[] carteTabAffic=new String[27];
        for(int i=0;i<length(carteTabAffic);i++){
            carteTabAffic[i]="   ";
        }

        int nbDecimales = length("" + length(etat.planete.carte, 1)); // Pour aligner les numéros de lignes

        // En-tête des colonnes (A, B, C, ...)
        String carteTabAffic0="";
        carteTabAffic0+="  ";
        for (int c = 1; c < length(etat.planete.carte, 2); c++) {
            if (c == 1) {
                String espace = "";
                for (int i = 0; i < nbDecimales; i++) {
                    espace += " ";
                }
                carteTabAffic0+=espace + 'A' + " ";
            } else {
                carteTabAffic0+=" " + (char) (64 + c) + " ";
            }
        }
        carteTabAffic[0]=carteTabAffic0;

        // Afficher chaque ligne de la carte avec les ressources et des informations à côté
        for (int l = 1; l < length(etat.planete.carte, 1); l++) {
            String affichageL = "" + l;
            while (length(affichageL) < nbDecimales) {
                affichageL = "0" + affichageL;
            }
            affichageL = substring(affichageL, 0, nbDecimales);
            
            carteTabAffic[l]=affichageL + " "; // Numéro de ligne
            for (int c = 1; c < length(etat.planete.carte, 2); c++) {
                String ressource = etat.planete.carte[l][c].symbole;
                String colorCode = getRessourceColor(ressource,etat.planete.carte[l][c]);
                carteTabAffic[l]+=(colorCode + ressource + ANSI_RESET); // Ressources sous forme de grille
            }
        }
        return carteTabAffic;
    }

    String[] creerTabResAffic(EtatJeu etat){
        String[] resTabAffic=new String[27];
        for(int i=0;i<length(resTabAffic);i++){
            resTabAffic[i]="   ";
        }

        resTabAffic[0]=ANSI_BOLD + "Ressources Naturelles:"+ ANSI_RESET;
            
        // Ajouter des informations supplémentaires sur la même ligne
        int max=length(etat.ressources[2].nom);
        for(int a=3;a<length(etat.ressources);a++){
            if (length(etat.ressources[a].nom)>max){
                max=length(etat.ressources[a].nom);
            }
        }
        max++;

        int maxQuant=length(""+etat.ressources[2].quantite);
        for(int b=3;b<length(etat.ressources);b++){
            if (length(""+etat.ressources[b].quantite)>maxQuant){
                maxQuant=length(""+etat.ressources[b].quantite);
            }
        }

        int maxVar=maxLength(etat.gestion.variationRessources,2);

        for(int p =0;p<length(etat.planete.carte, 1);p++){
            if (p>=1 && p <= 5) {
                String var=var(etat,p+1);
                resTabAffic[p]=formatCharacteristic(etat.ressources[p+1].nom,max) + " : " + formatCharacteristic(""+etat.ressources[p+1].quantite,maxQuant)+formatCharacteristic(var,maxVar);
            }else if(p>=8 && p<11){
                String var=var(etat,p-1);
                resTabAffic[p]=formatCharacteristic(etat.ressources[p-1].nom,max) + " : " + formatCharacteristic(""+etat.ressources[p-1].quantite,maxQuant)+formatCharacteristic(var,maxVar);
            }else if(p==12){
                String var=varElec(etat,p-2);
                resTabAffic[p]=formatCharacteristic(etat.ressources[p-2].nom,max) + ": " + formatCharacteristic(""+etat.ressources[p-2].quantite,maxQuant)+formatCharacteristic(var,maxVar);
            }
        }
        resTabAffic[7]=ANSI_BOLD + "Ressources Vitales:"+ ANSI_RESET;
        resTabAffic[14]=ANSI_BOLD + "Capacité de l'entrepôt"+ ANSI_RESET+": "+calcCapaciteEntrepot(etat)+"/"+etat.gestion.capaciteEntrepot;
        
        int idDortoirs=16;
        if(etat.events.entrepotPlein[0]){
            idDortoirs++;
            resTabAffic[15]=ANSI_RED+etat.events.entrepotPleinSTR+ANSI_RESET;
            resTabAffic[idDortoirs]=ANSI_BOLD + "Lits occupés"+ ANSI_RESET+": "+etat.gestion.nombreVivants+"/"+etat.gestion.capaciteTotalePop;
        }else{
            resTabAffic[idDortoirs]=ANSI_BOLD + "Lits occupés"+ ANSI_RESET+": "+etat.gestion.nombreVivants+"/"+etat.gestion.capaciteTotalePop;
        }
        if(etat.events.dortoirsPlein[0]){
            resTabAffic[idDortoirs+1]=ANSI_RED+etat.events.dortoirsPleinSTR+ANSI_RESET;
        }

        formatEmptyLine(resTabAffic);
        resTabAffic[12]=substring(resTabAffic[12],0,length(resTabAffic[12])-1);

        return resTabAffic;
    }

    String var(EtatJeu etat,int ind){
        String var="";
        if(etat.gestion.variationRessources[ind]>0){
            var=" (+"+etat.gestion.variationRessources[ind]+")";
        }else if(etat.gestion.variationRessources[ind]<0){
            var=" ("+etat.gestion.variationRessources[ind]+")";
        }
        return var;
    }

    String varElec(EtatJeu etat,int ind){
        String var="";

        if (etat.gestion.variationRessources[ind]+100==etat.gestion.quantiteElecTourPrecedent[0]){
            return var;
        }else{
            if((etat.gestion.variationRessources[ind]+100)-etat.gestion.quantiteElecTourPrecedent[0]>0){
                var=" (+"+((etat.gestion.variationRessources[ind]+100)-etat.gestion.quantiteElecTourPrecedent[0])+")";
            }else{
                var=" (-"+(etat.gestion.quantiteElecTourPrecedent[0]-(etat.gestion.variationRessources[ind]+100))+")";
            }
            return var;
        }
    }

    String[] creerTabEvents(EtatJeu etat){
        String[] eventsTabAffic=new String[27];
        for(int i=0;i<length(eventsTabAffic);i++){
            eventsTabAffic[i]="   ";
        }

        eventsTabAffic[0]=ANSI_BOLD + "Événements:"+ ANSI_RESET;
        if (TabIsEmpty(etat.events.naissance) && TabIsEmpty(etat.events.deces) && TabIsEmpty(etat.events.filonEpuise) && TabIsEmpty(etat.events.ressourceEstEpuiseeSTR)){
            eventsTabAffic[2]="Il n'y à pas eu d'événements cette année";
        }else{
            String[] tabFusion=new String[length(etat.events.naissance)+length(etat.events.deces)+length(etat.events.filonEpuise)+length(etat.events.ressourceEstEpuiseeSTR)];
            
            int h=0;
            for(int r=0;r<length(etat.events.naissance);r++){
                tabFusion[h]=etat.events.naissance[r];
                h++;
            }
            for(int b=0;b<length(etat.events.deces);b++){
                tabFusion[h]=etat.events.deces[b];
                h++;
            }
            for(int w=0;w<length(etat.events.filonEpuise);w++){
                tabFusion[h]=etat.events.filonEpuise[w];
                h++;
            }            
            for(int w=0;w<length(etat.events.ressourceEstEpuiseeSTR);w++){
                tabFusion[h]=etat.events.ressourceEstEpuiseeSTR[w];
                h++;
            }

            int maxFusion=maxLength(tabFusion,0);

            int id=1; 
            if (!TabIsEmpty(etat.events.naissance) || !TabIsEmpty(etat.events.deces)){
                int last=0;
                boolean tooMuch=false;
                eventsTabAffic[id]=ANSI_BOLD +"Colonie:"+ANSI_RESET;
                id+=2;

                if(!TabIsEmpty(etat.events.naissance)){
                    last=findLastIndex(etat.events.naissance);
                    if(last==1){
                        eventsTabAffic[id]=ANSI_BOLD + "Nouvelle naissance ! :" +ANSI_RESET;
                    }else{
                        eventsTabAffic[id]=ANSI_BOLD + "Nouvelles naissances ! :" +ANSI_RESET;
                    }
                    id++;
                    if(last>5){
                        last=5;
                        tooMuch=true;
                    }

                    for (int i=0;i<last;i++){
                        eventsTabAffic[id]=formatCharacteristic(etat.events.naissance[i],maxFusion);
                        id++;
                    }
                    if (tooMuch){
                        eventsTabAffic[id]="+"+(findLastIndex(etat.events.naissance)-5+" ");
                        tooMuch=false;
                        id++;
                    }
                    etat.events.naissance=new String[20];
                    tabEmptier(etat.events.naissance);
                    id++;
                }
                if(!TabIsEmpty(etat.events.deces)){
                    last=findLastIndex(etat.events.deces);
                    tooMuch=false;
                    if(last==1){
                        eventsTabAffic[id]=ANSI_BOLD + "Nouveau décès ! :" +ANSI_RESET;
                    }else{
                        eventsTabAffic[id]=ANSI_BOLD + "Nouveaux décès ! :" +ANSI_RESET;
                    }
                    id++;
                    if(last>5){
                        last=5;
                        tooMuch=true;
                    }
                    for (int m=0;m<last;m++){
                        eventsTabAffic[id]=formatCharacteristic(etat.events.deces[m],maxFusion);
                        id++;
                    }
                    if (tooMuch){
                        eventsTabAffic[id]="+"+(findLastIndex(etat.events.deces)-5+" ");
                        tooMuch=false;
                        id++;
                    }
                    etat.events.deces=new String[20];
                    tabEmptier(etat.events.deces);
                    id++;
                }
            }

            if(!TabIsEmpty(etat.events.filonEpuise) || !TabIsEmpty(etat.events.ressourceEstEpuiseeSTR)){
                String separator="";
                for (int s=0;s<maxFusion;s++){
                    separator=repeatChar('-',maxFusion);
                }
                if (id-1!=0){
                    eventsTabAffic[id]=separator;
                    id++;
                }
                eventsTabAffic[id]="Gestion des Ressources: ";
                id++;

                if(!TabIsEmpty(etat.events.ressourceEstEpuiseeSTR)){
                    for (int q=2;q<length(etat.ressources);q++){
                        if(!equals(etat.events.ressourceEstEpuiseeSTR[q],"")){
                            eventsTabAffic[id]=etat.events.ressourceEstEpuiseeSTR[q];
                            id++;
                        }
                    }
                    id++;
                }
                if(!TabIsEmpty(etat.events.filonEpuise)){
                    for (int a=0;a<findLastIndex(etat.events.filonEpuise);a++){
                        eventsTabAffic[id]=etat.events.filonEpuise[a];
                        id++;
                    }
                    etat.events.filonEpuise=new String[20];
                    tabEmptier(etat.events.filonEpuise);
                    id++;
                }
            }
        }

        formatEmptyLine(eventsTabAffic);

        return eventsTabAffic;
    }

    String[] creeTabEtatPlanete(EtatJeu etat){
        String[] planeteTabAffic=new String[27];
        for(int i=0;i<length(planeteTabAffic);i++){
            planeteTabAffic[i]="   ";
        }
        
        planeteTabAffic[0]=ANSI_BOLD + "État de la Planète: "+ ANSI_RESET;
        if(etat.planete.pollution>0){
            planeteTabAffic[2]=ANSI_BOLD + "Pollution: "+ ANSI_RESET+": "+String.format("%.4f", etat.planete.pollution);
        }else{
            planeteTabAffic[2]=ANSI_BOLD + "Pollution: "+ ANSI_RESET+": 0.0";
        }
        if(etat.planete.pollution>0.05){
            planeteTabAffic[4]=ANSI_YELLOW + "⚠ Niveaux de Pollution inqiuétants ⚠ "+ ANSI_RESET;
        }else if(etat.planete.pollution>0.15){
            planeteTabAffic[4]=ANSI_RED_BRIGHT + "⚠ Niveaux de Pollution Dangereux ⚠ "+ ANSI_RESET;
        }else if(etat.planete.pollution>0.35){
            planeteTabAffic[4]=ANSI_BLACK + ANSI_WHITE_BG + "⚠ Niveaux de Pollution Critiques ⚠ "+ ANSI_RESET;
        }

        formatEmptyLine(planeteTabAffic);

        return planeteTabAffic;
    }

    String[] creerSeparateur(){
        String[] separAffic=new String[27];
        for(int e=0;e<length(separAffic);e++){
            separAffic[e]=" | ";
        }
        return separAffic;
    }

    void afficherEtat(EtatJeu etat, boolean afficherNbVivNecessaire, boolean afficherTipsPedago, boolean optionInvalide) {
        if (etat.tour > 0) {
            //clearScreen(); 
            println("\n=== Année " + etat.tour + " ===\n");
        }

        String[][] affichage = afficherEtatCreerTab(etat);
        String affiche = "";

        println(affichage[0][0]);
        for (int c = 0; c < length(affichage[1]); c++) {
            for (int l = 1; l < length(affichage, 1); l++) {
                affiche += affichage[l][c];
            }
            println(affiche);
            affiche = "";
        }
        println();
        if (afficherNbVivNecessaire) {
            println("\n" + ANSI_BOLD + "Nombre de colons survivants : " + ANSI_RESET + etat.gestion.nombreVivants);
        }

        if (etat.tour >= 1 && afficherTipsPedago) {
            int lastIndex = countLastPos(etat.gestion.posBat) - 1;
            if (lastIndex >= 0 && lastIndex < length(etat.gestion.posBat)) {
                int lig = etat.gestion.posBat[lastIndex][0];
                int col = etat.gestion.posBat[lastIndex][1];

                if (lig >= 0 && lig < length(etat.planete.carte, 1) && col >= 0 && col < length(etat.planete.carte, 2)) {
                    CaseCarte batiment = etat.planete.carte[lig][col];
                    int id = 0;
                    while (id < length(listeBatimentsPossibles) && !equals(batiment.ressourceActuelle.nom, listeBatimentsPossibles[id].nom)) {
                        id++;
                    }

                    if (id < length(listeBatimentsPossibles) && etat.events.BatimentsPosed[id] == false) {
                        String cadre = "        ";
                        int max = maxLength(etat.events.posedTxt[id], 0) + length(cadre);

                        for (int i = 0; i < max + 8; i++) {
                            cadre += "-";
                        }
                        println("\n" + cadre);
                        for (int e = 0; e < length(etat.events.posedTxt[id]); e++) {
                            String ligne = replace(etat.events.posedTxt[id][e],"\\033", "\033");
                            println("                " + ligne);
                        }
                        println(cadre + "\n");
                        etat.events.BatimentsPosed[id] = true;
                    }
                }
            }
        }

        if (optionInvalide) {
            println(ANSI_RED + "\nOption invalide. Veuillez réessayer." + ANSI_RESET);
        }
    }

    Events newEvents(EtatJeu etat,Terrain[] listeBatimentsPossibles){
        Events e=new Events();
        e.ressourceEstEpuiseeSTR=new String[length(etat.ressources)];
        for (int a=0;a<length(etat.ressources);a++){
            e.ressourceEstEpuiseeSTR[a]="";
        }
        for (int i=0;i<length(e.filonEpuise);i++){
            e.filonEpuise[i]="";
            e.naissance[i]="";
            e.deces[i]="";
        }
        e.BatimentsPosed=new boolean[length(listeBatimentsPossibles)];
        for (int o=0;o<length(e.BatimentsPosed);o++){
            e.BatimentsPosed[o]=false;
        }

        final String POSEDTXT = "../../../ressources/CSV-TXT/Posed.csv";
        CSVFile f = loadCSV(POSEDTXT,'/'); // Création d'un objet fichier pour lire le fichier des events
        e.posedTxt=new String[length(listeBatimentsPossibles)][columnCount(f)];

        for(int l=0;l<length(listeBatimentsPossibles);l++){
            int column=columnCount(f,l);
            String[] tmp=new String[column];
            for(int c=0;c<column;c++){
                tmp[c]=getCell(f,l,c);
            }
            e.posedTxt[l]=tmp;
        }
    
        return e;
    }

    Gestion newGestion(Terrain[] RESSOURCES_INIT,Terrain[] listeBatimentsPossibles){
        Gestion g=new Gestion();
        g.tabMoyennepollution=new double[length(listeBatimentsPossibles)];
        g.variationRessources=new int[length(RESSOURCES_INIT)];
        g.variationPoseRessources=new int[length(RESSOURCES_INIT)];
        return g;
    }

    EtatJeu initialiserNouvellePartie(Terrain[] RESSOURCES_INIT, Terrain[] listeBatimentsPossibles) {
        // Créer un nouvel état de jeu
        EtatJeu nouvelEtat = new EtatJeu();
        
        // Initialisation du nom de la colonie (fichier de sauvegarde)
        nouvelEtat.nom = readStringSecurise(ANSI_BOLD + "Entrez le nom " + ANSI_RESET + "de votre nouvelle colonie : ") + ".csv";
     

        // Initialisation du tour à 0
        nouvelEtat.tour = 0;
        
        // Initialisation du score à 0
        nouvelEtat.score = 0.0;

        // Initialisation des ressources disponibles
        nouvelEtat.ressources = new Terrain[11];
        for (int i = 0; i < length(RESSOURCES_INIT); i++) {
            nouvelEtat.ressources[i] = RESSOURCES_INIT[i];
        }

        // Création de la planète 
        int taille = 27;
        nouvelEtat.planete = newPlanete(nouvelEtat.ressources, taille);
        
        // Générer les colons initiaux
        nouvelEtat.colons = genererColons(6); // 6 colons de départ

        // Générer les paramètres initiaux
        nouvelEtat.gestion = newGestion(RESSOURCES_INIT, listeBatimentsPossibles);

        // Initialiser l'objet events
        nouvelEtat.events = newEvents(nouvelEtat, listeBatimentsPossibles);

        println();
        afficherEtat(nouvelEtat, false, false, false);
        placerVaisseau(listeBatimentsPossibles, nouvelEtat);   
        return nouvelEtat;
    }

//-----------------------------MENUS--------------------------------------------------------------------------------------------------------------------

    void afficherMenuJeu(EtatJeu etat,boolean invalide,boolean afficherTipsPedago) {
        afficherEtat(etat,true,afficherTipsPedago,invalide);
        println("\n=== ACTIONS POSSIBLES ===");
        println("1. " + ANSI_BOLD + "Construire" + ANSI_RESET + " un Bâtiment");
        println("2. " + ANSI_BOLD + "Passer" + ANSI_RESET + " cette année");
        println("3. " + ANSI_BOLD + "Sauvegarder" + ANSI_RESET + " la partie");
        println("4. " + ANSI_BOLD + "Quitter" + ANSI_RESET);
        println("-------------------------");
    }

    void menuPlacerBatiment(Terrain[] listeBatimentsPossibles, EtatJeu etat,boolean recursif){
        afficherEtat(etat,true,false,false);
        if (recursif){
            println("\nVous ne pouvez placer aucun batiment sur cette case !\nVeuillez en saisir une autre.");
        }
        println("\n===== CONSTRUIRE =====\n");

        Terrain[] listeBatimentsPosable=new Terrain[length(listeBatimentsPossibles)];

        int col=saisirColonne(length(etat.planete.carte,2));
        int lig=saisirLigne(length(etat.planete.carte,1));

        int id=0;
        int cmpt=0;
        while(cmpt<length(listeBatimentsPossibles)){
            if(batimentPosable(etat,listeBatimentsPossibles,lig,col,listeBatimentsPossibles[cmpt])){
                listeBatimentsPosable[id]=listeBatimentsPossibles[cmpt];
                id++;
            }
            cmpt++;
        }

        id=1;
        if (TabIsEmpty(listeBatimentsPosable)){
            menuPlacerBatiment(listeBatimentsPossibles,etat,true);
            return;
        }else{
            println("\nQuel batiment voulez-vous construire sur cette case ?: \n");
            int maxLenRes=0;
            
            for(int c=0;c<length(listeBatimentsPosable);c++){
                if (listeBatimentsPosable[c] != null && length(listeBatimentsPosable[c].nom)>maxLenRes){
                    maxLenRes=length(listeBatimentsPosable[c].nom);
                    //https://www.youtube.com/watch?v=SUmk20kaPNQ&ab_channel=Solicate
                }
            }

            for(int e=0;e<length(listeBatimentsPosable);e++){
                if(listeBatimentsPosable[e]!=null){
                    String recette=" ( ";
                    for (int j=0;j<length(listeBatimentsPosable[e].ResNecessaire.coutDeConstruction);j++){
                        recette+=""+listeBatimentsPosable[e].ResNecessaire.quantiteNecessaire[j]+" "+etat.ressources[listeBatimentsPosable[e].ResNecessaire.coutDeConstruction[j]].nom+", ";
                    }
                    recette=substring(recette,0,length(recette)-2)+" )";

                    println(id+" - "+ listeBatimentsPosable[e].symbole + " " +formatCharacteristic(listeBatimentsPosable[e].nom,maxLenRes+1)+recette);
                    id++;
                }
            }
            println("\n"+(id)+" - Ne pas poser de batiment\n");
        }

        int choix=0;
        do{
            choix = readIntSecurise(ANSI_BOLD + "Choisissez une action" + ANSI_RESET + " (1-"+(id)+") : ");
            if (choix < 1 || choix > id){
                println(ANSI_RED + "Option invalide. Veuillez réessayer." + ANSI_RESET);
            }
        }while(choix < 1 || choix > id);
        int place=0;
        if (choix==id){
            place=1;
        }
        switch (place) {
                case 0:
                    placerBatiment(etat, listeBatimentsPossibles,lig,col,listeBatimentsPosable[choix-1]);
                    return;
                case 1:
                    gestionMenuJeu(etat,listeBatimentsPossibles);
                    return;
                default:
                    println(ANSI_RED + "Option invalide. Veuillez réessayer." + ANSI_RESET);
        }
    }

    boolean gestionMenuJeu(EtatJeu etatJeu,Terrain[] listeBatimentsPossibles) {
        boolean invalide=false;
        boolean afficherTipsPedago=true;
        while (true) {
            afficherMenuJeu(etatJeu,invalide,afficherTipsPedago);
            int choix = readIntSecurise(ANSI_BOLD + "Choisissez une action" + ANSI_RESET + " (1-4) : ");
            
            switch (choix) {
                case 1:
                    menuPlacerBatiment(listeBatimentsPossibles,etatJeu,false);
                    return true;
                case 2:
                    return true; // Passer l'année
                case 3:
                    gestionSauvegarde(etatJeu);
                    break;
                case 4:
                    return gestionQuitter(etatJeu);
                default:
                    println(ANSI_RED + "Option invalide. Veuillez réessayer." + ANSI_RESET);
                    invalide=true;
                    afficherTipsPedago=false;
            }
        }
    }

    void gestionSauvegarde(EtatJeu etatJeu) {
        afficherEtat(etatJeu,false,false,false);
        String nomFichier=etatJeu.nom;
        println("\n=== OPTIONS DE SAUVEGARDE ===");
        println("1. "+ ANSI_BOLD + "Sauvegarder " + ANSI_RESET + "et continuer");
        println("2. "+ ANSI_BOLD + "Sauvegarder et quitter" + ANSI_RESET);
        println("3. "+ ANSI_BOLD+ "Annuler" + ANSI_RESET);
        
        int choix = readIntSecurise(ANSI_BOLD + "Votre choix : " + ANSI_RESET);
        
        switch (choix) {
            case 1:
                sauvegarderJeu(etatJeu, nomFichier);
                break;
            case 2:
                sauvegarderJeu(etatJeu, nomFichier);
                System.exit(0);
                break;
            case 3:
                return;
            default:
                println(ANSI_RED + "Option invalide." + ANSI_RESET);
        }
    }

    boolean gestionQuitter(EtatJeu etatJeu) {
        // Affiche l'état actuel du jeu
        afficherEtat(etatJeu, false, false, false);

        // Récupère le nom du fichier associé à l'état actuel du jeu
        String nomFichier = etatJeu.nom;

        // Affiche le menu de gestion de la sortie
        println("\n=== QUITTER ===");
        println("1. " + ANSI_BOLD + "Continuer" + ANSI_RESET + " la partie"); // Option pour continuer
        println("2. " + ANSI_BOLD + "Quitter" + ANSI_RESET + " sans sauvegarder"); // Option pour quitter sans sauvegarder
        println("3. " + ANSI_BOLD + "Sauvegarder et quitter" + ANSI_RESET); // Option pour sauvegarder et quitter

        // Demande à l'utilisateur de faire un choix
        int choix = readIntSecurise(ANSI_BOLD + "Votre choix : " + ANSI_RESET);

        // Gère le choix de l'utilisateur à l'aide d'un switch
        switch (choix) {
            case 1:
                // Option 1 : Continuer la partie
                return true; // Renvoie `true` pour indiquer que la partie continue

            case 2:
                // Option 2 : Quitter sans sauvegarder
                return false; // Renvoie `false` pour indiquer que la partie est terminée

            case 3:
                // Option 3 : Sauvegarder la partie avant de quitter
                sauvegarderJeu(etatJeu, nomFichier); // Appelle la fonction pour sauvegarder l'état du jeu
                return false; // Renvoie `false` pour indiquer que la partie est terminée après sauvegarde

            default:
                // Gestion des entrées invalides
                println(ANSI_RED + "Option invalide." + ANSI_RESET);
                return true; // Renvoie `true` pour maintenir le joueur dans le menu
        }
    }

    EtatJeu gestionMenuPrincipal() {
        // Boucle principale du menu
        while (true) {
            // Affichage du menu principal
            println("\n========== MENU PRINCIPAL ==========");
            println("1. Commencer une " + ANSI_BOLD + "nouvelle partie" + ANSI_RESET);
            println("2. " + ANSI_BOLD + "Charger " + ANSI_RESET + "une ancienne sauvegarde");
            println("3. " + ANSI_BOLD + "Quitter" + ANSI_RESET);
            println("------------------------------------");
            
            // Demande à l'utilisateur de choisir une option
            int option = readIntSecurise(ANSI_BOLD + "Choisissez une option" + ANSI_RESET + " (1-3) : ");

            // Gestion des choix avec un switch
            switch (option) {
                case 1: // Option 1 : Démarrer une nouvelle partie
                    clearScreen(); // Effacer l'écran
                    // Retourne l'état d'une nouvelle partie initialisée
                    return initialiserNouvellePartie(RESSOURCES_INIT, listeBatimentsPossibles);

                case 2: // Option 2 : Charger une partie sauvegardée
                    clearScreen(); // Effacer l'écran
                    
                    // Récupère la liste des fichiers du répertoire ../save/
                    String[] fichiersDisponibles = getAllFilesFromDirectory("../save/");
                    int nombreFichiersCSV = 0;

                    // Parcourt les fichiers pour compter ceux qui ont l'extension ".csv"
                    for (int i = 0; i < length(fichiersDisponibles); i++) {
                        // Vérifie si le fichier a au moins 4 caractères et une extension ".csv"
                        if (length(fichiersDisponibles[i]) > 4 &&
                            equals(substring(fichiersDisponibles[i], length(fichiersDisponibles[i]) - 4, length(fichiersDisponibles[i])), ".csv")) {
                            nombreFichiersCSV++;
                        }
                    }

                    // Si aucun fichier CSV n'est disponible
                    if (nombreFichiersCSV == 0) {
                        println(ANSI_BOLD + "Aucune sauvegarde disponible." + ANSI_RESET + " Démarrage d'une nouvelle partie.");
                        return initialiserNouvellePartie(RESSOURCES_INIT, listeBatimentsPossibles);
                    }

                    // Crée un tableau pour stocker les noms des fichiers CSV
                    String[] fichiersCSV = new String[nombreFichiersCSV];
                    int index = 0;

                    // Remplit le tableau avec les fichiers CSV trouvés
                    for (int i = 0; i < length(fichiersDisponibles); i++) {
                        if (length(fichiersDisponibles[i]) > 4 &&
                            equals(substring(fichiersDisponibles[i], length(fichiersDisponibles[i]) - 4, length(fichiersDisponibles[i])), ".csv")) {
                            fichiersCSV[index] = fichiersDisponibles[i];
                            index++;
                        }
                    }

                    // Affiche les sauvegardes disponibles
                    println(ANSI_BOLD + "Sauvegardes disponibles :" + ANSI_RESET);
                    for (int i = 0; i < length(fichiersCSV); i++) {
                        // Affiche le nom du fichier sans l'extension ".csv"
                        println((i + 1) + ". " + substring(fichiersCSV[i], 0, length(fichiersCSV[i]) - 4));
                    }

                    // Demande à l'utilisateur de sélectionner une sauvegarde
                    int choix = -1;
                    do {
                        choix = readIntSecurise(ANSI_BOLD + "Entrez le numéro de la sauvegarde à charger (ou 0 pour annuler) : " + ANSI_RESET);
                    } while (choix < 0 || choix > length(fichiersCSV)); // Validation de l'entrée utilisateur

                    // Si l'utilisateur choisit d'annuler
                    if (choix == 0) {
                        clearScreen(); // Effacer l'écran
                        gestionMenuPrincipal(); // Retour au menu principal
                    }

                    // Charge la sauvegarde sélectionnée
                    String nomFichier = fichiersCSV[choix - 1];
                    EtatJeu etatCharge = chargerJeu(nomFichier); // Fonction pour charger une partie sauvegardée

                    // Si le chargement réussit, retourne l'état de la partie chargée
                    if (etatCharge != null) {
                        return etatCharge;
                    }

                    // Si le chargement échoue, démarre une nouvelle partie
                    println(ANSI_BOLD + "Chargement échoué." + ANSI_RESET + " Démarrage d'une nouvelle partie.");
                    return initialiserNouvellePartie(RESSOURCES_INIT, listeBatimentsPossibles);

                case 3: // Option 3 : Quitter le programme
                    println("\n" + ANSI_BOLD + "Au revoir!" + ANSI_RESET + "\n");
                    System.exit(0); // Quitte le programme

                default: // Gestion des entrées invalides
                    println(ANSI_BOLD + ANSI_RED + "\nOption invalide." + ANSI_RESET + " Veuillez réessayer.");
            }
        }
    }

    void afficherResultatFinal(EtatJeu etatJeu) {
        // Efface l'écran pour afficher les résultats de manière propre
        clearScreen(); 
        
        // Vérifie si la colonie a échoué (tous les colons sont morts)
        if (etatJeu.gestion.nombreVivants == 0) {
            println(ANSI_RED_BRIGHT + "Votre colonie est éteinte. Vous avez échoué." + ANSI_RESET); 
        }
        
        // Affichage de la section de fin de partie
        println("\n=== FIN DE LA PARTIE ===");
        
        // Affiche le nombre total de tours joués
        println(ANSI_BOLD + "Nombre total" + ANSI_RESET + " de tours : " + etatJeu.tour); 
        
        // Affiche le nombre final de colons vivants
        println(ANSI_BOLD + "Nombre final" + ANSI_RESET + " de colons : " + etatJeu.gestion.nombreVivants); 
    }


//-----------------------------VOID ALGORITHM---------------------------------------------------------------------------------------------------------
    // Fonction principale de l'algorithme du jeu
    void algorithm() {
        // Fichier contenant l'ASCII art pour l'introduction
        final String FILENAME = "../../../ressources/CSV-TXT/ASCII-art.txt";
        File f = newFile(FILENAME); // Création d'un objet fichier pour lire le fichier ASCII
        int nbLines = 0; // Variable pour compter le nombre de lignes lues
        
        // Lecture et affichage du contenu du fichier ASCII
        while (ready(f)) {
            String currentLine = readLine(f); // Lecture de la ligne courante
            nbLines++; // Incrémentation du compteur de lignes
            println(currentLine); // Affichage de la ligne
        }
        
        // Appel du menu principal pour initialiser ou charger une partie
        EtatJeu etatJeu = gestionMenuPrincipal();

        // Vérifiez que les ressources sont initialisées correctement
        if (etatJeu.ressources == null || length(etatJeu.ressources) < 11) {
            etatJeu.ressources = new Terrain[11];
            for (int i = 0; i < length(etatJeu.ressources); i++) {
                etatJeu.ressources[i] = new Terrain();
            }
        }

        boolean partieTerminee = false; // Indicateur pour savoir si la partie est terminée
        
        // Boucle principale du jeu
        while (!partieTerminee) {
            
            // Vérification des conditions de fin de partie
            if (etatJeu.gestion.nombreVivants <= 0) { // Si aucun colon n'est vivant, la partie se termine
                partieTerminee = true;
                continue;

            } else {
                etatJeu.gestion.nombreVivants = compterColonsVivants(etatJeu.colons); // Met à jour le nombre de vivants
                
                // Calcul du score (non implémenté ici)
                // etatJeu.score = calculerScore(nombreVivants, etatJeu.planete, etatJeu.ressources);
                
                // Gestion du menu de jeu
                boolean continuerTour = gestionMenuJeu(etatJeu, listeBatimentsPossibles); // Permet au joueur de prendre des décisions pour ce tour
                if (!continuerTour) { // Si le joueur décide d'arrêter, la partie se termine
                    partieTerminee = true;
                    continue;
                }

                // Séparateur visuel entre les tours
                println("\n-----------------------\n");
            }

            // Incrémentation du tour
            etatJeu.tour++;

            // Mise à jour de l'électricité produite au tour précédent si ce n'est pas le premier tour
            if (etatJeu.tour > 1) {
                etatJeu.gestion.quantiteElecTourPrecedent[0] = etatJeu.ressources[10].quantite;
            }

            // Réinitialisation de la ressource "Électricité" à chaque tour
            etatJeu.ressources[10] = newRessource("⚡ Electricité", "Elec", 1.0, 100);
            etatJeu.gestion.variationRessources = new int[length(etatJeu.ressources)]; // Réinitialise les variations de ressources
            if(!TabIsEmpty(etatJeu.gestion.variationPoseRessources)){
                for (int i = 0; i < length(etatJeu.gestion.variationPoseRessources); i++) {
                    etatJeu.gestion.variationRessources[i] = etatJeu.gestion.variationPoseRessources[i];
                }
                etatJeu.gestion.variationPoseRessources = new int[length(etatJeu.ressources)];
            }

            // Vérifications et mises à jour des ressources et des bâtiments
            ressourceEmptyVerif(etatJeu); // Vérifie si des ressources sont épuisées
            verifCapaciteEntrepot(etatJeu); // Vérifie la capacité de stockage des entrepôts
            mettreAJourColons(etatJeu); // Met à jour les colons (santé, besoins, etc.)
            mettreAJourBatiment(etatJeu, listeBatimentsPossibles); // Met à jour les bâtiments en fonction de leur production et de leurs effets
            etatJeu.planete.pollution = pollutionTotale(etatJeu); // Calcule la pollution totale de la planète

            // Gestion de la population
            etatJeu.gestion.nombreVivants = compterColonsVivants(etatJeu.colons); // Compte le nombre de colons vivants
            
            // Expansion du tableau de colons si la population dépasse 70 % de la capacité actuelle
            if ((double) etatJeu.gestion.nombreVivants / (double) length(etatJeu.colons) > 0.70) {
                etatJeu.colons = incrementationTailleTableau(etatJeu.colons, etatJeu.gestion.nombreVivants);
            }

            // Reproduction des colons
            for (int i = 0; i < etatJeu.gestion.nombreVivants; i++) {
                if (random() < PROBABILITE_REPRODUCTION) { // Si la probabilité de reproduction est satisfaite
                    etatJeu.colons = reproduire(etatJeu.colons, etatJeu); // Ajoute un nouveau colon au tableau
                    etatJeu.gestion.nombreVivants = compterColonsVivants(etatJeu.colons); // Met à jour le nombre de vivants
                }
            }
        }
        // Affiche les résultats finaux une fois la partie terminée
        afficherResultatFinal(etatJeu);
    }
}
