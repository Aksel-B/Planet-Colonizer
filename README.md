# PlanetColonizer -- ALPHA

<img src="Planet.webp" alt="PlanetColonizer" width="200" height="200">

## Description

**PlanetColonizer** est un jeu de simulation où vous incarnez un colonisateur chargé de gérer une nouvelle planète. L’objectif est de construire, gérer les ressources, et assurer la survie de vos colons dans un environnement hostile. Le jeu est développé en Java et propose des fonctionnalités intéressantes, comme :

-   La gestion des ressources et des bâtiments.
-   Une simulation réaliste de la vie des colons (naissance, vieillissement, décès). Cela inclut des défis comme l'épuisement des ressources ou la surpopulation, qui impactent directement la satisfaction et la survie des colons. Par exemple, une surpopulation peut rendre les colons insatisfaits, surtout si leurs besoins essentiels ne sont pas satisfaits, tandis qu'une pénurie de ressources vitales peut réduire leur santé à grande vitesse.
-   Le suivi de la pollution planétaire et des stocks de ressources.
-   La possibilité de sauvegarder et de charger votre partie (bien que cette fonctionnalité soit actuellement non fonctionnelle).

## Fonctionnalités principales

### 1. Ressources

-   Plusieurs types de ressources (eau, nourriture, fer, carbone, etc.) avec des probabilités d'apparition différentes.
-   Une gestion de la consommation et de la production des ressources, qui peut créer des goulots d'étranglement si vous n’avez pas assez d’espace de stockage ou si la production est insuffisante pour couvrir la demande. Par exemple, un entrepôt trop petit ralentira la production si les ressources générées n’ont pas de place pour être stockées.

### 2. Bâtiments

-   Vous pouvez construire divers bâtiments comme des dortoirs, des entrepôts, des fermes hydroponiques, etc.
-   Chaque bâtiment a un coût et des effets uniques (pollution, stockage, production), ce qui vous oblige à réfléchir stratégiquement à vos choix pour assurer la survie de votre colonie.

### 3. Colons

-   Une simulation réaliste de la vie des colons : leur âge, leur santé, leur satisfaction et leur reproduction.
-   Vous devez gérer leur survie en fonction des ressources disponibles (air, eau, nourriture). Plus vos colons vivent longtemps, plus ils risquent de se multiplier… mais cela peut aussi poser des problèmes si la planète est trop petite ou mal équipée pour les accueillir tous.

### 4. Sauvegarde et chargement

-   Cette fonctionnalité **n’est pas encore opérationnelle**. Nous travaillons activement à sa mise en place pour la prochaine mise à jour !

## Comment utiliser

### Prérequis

-   Vous devez avoir **Java 8** (ou une version ultérieure) installé sur votre système pour faire tourner le jeu.

### Instructions d'exécution

1. Compilez le projet :
    ```bash
    ./compile.sh
    ```
2. Lancez le programme :
    ```bash
    ./run.sh
    ```

### Navigation dans le jeu

-   **Menu principal :** Commencez une nouvelle partie, chargez une sauvegarde ou quittez le jeu.
-   **Pendant le jeu :** Construisez des bâtiments, passez une année ou sauvegardez votre progression.
-   **Interface :** Vous pouvez interagir avec le jeu en utilisant votre clavier.

## Organisation du code

### Classes principales

-   **PlanetColonizer** : C'est la classe centrale du jeu, qui contient la logique principale et gère les menus et les interactions avec l’utilisateur. Elle s’assure que les entrées utilisateur sont valides pour éviter les erreurs de saisie et garantir une expérience fluide.
-   **EtatJeu** : Représente l’état actuel de la partie (ressources, colons, planète). Elle gère les interactions suivantes :
    -   `Planete` gère la carte et les ressources de la planète.
    -   `Colon` modélise les caractéristiques et le comportement des colons.
    -   Par exemple, l’ajout d’un bâtiment via `EtatJeu` met à jour simultanément les ressources sur la planète et ajuste le comportement des colons en fonction.
-   **Planete** : Gère la carte et les ressources disponibles sur la planète.
-   **Colon** : Modélise les colons et leurs comportements spécifiques (santé, reproduction, etc.).

### Méthodes principales

-   **init()** : Initialise la carte et les ressources de la planète.
-   **placerBatiment()** : Permet de construire un bâtiment à un emplacement donné.
-   **reproduire()** : Gère la reproduction des colons.
-   **sauvegarderJeu() / chargerJeu()** : Sauvegarde ou charge votre partie depuis un fichier CSV.

### Bugs connus

1. **Sauvegarde et chargement non fonctionnels** :  
   Actuellement, la sauvegarde et le chargement des parties ne fonctionnent pas comme prévu. Essayer de sauvegarder ou de charger une partie peut entraîner des erreurs ou ne pas produire l’effet attendu. Nous prévoyons de résoudre ce problème dans la prochaine mise à jour.

## Auteur

Ce projet a été réalisé par **Bouri Aksel** et **Martin Rémy**.
