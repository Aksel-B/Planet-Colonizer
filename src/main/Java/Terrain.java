class Terrain{
    Recette ResNecessaire; //Ressources nécessaire pour construction d'un batiment

    int[] ressourcesGeneree;//tab d'indices des ressources
    int[] quantiteResGeneree; //electricite est aussi une ressource
    int[] ressourcesConso; //tab d'indices des ressources
    int[] quantiteResConso; // Combien consomme le baptiment pour produire sa ressource
    int quantite; //quantite stockee

    double pollutionGeneree;
    boolean[] fonctionne=new boolean[]{true};

    String nom;
    String symbole;

    double probaApparition; // Probabilité d'apparaître sur une cellule [0 à 1[
}
