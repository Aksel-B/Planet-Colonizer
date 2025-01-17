## **Planet Colonizer** -- *Version "Final"* <img src="Planet.png" alt="PlanetColonizer" width="50" height="50" align="center"> 

**Planet Colonizer** est un jeu de simulation captivant où vous incarnez un colonisateur chargé de gérer une nouvelle planète. Construisez des infrastructures, gérez des ressources limitées, et assurez la survie de vos colons dans un environnement hostile.  

---

## **Table des Matières**  
- [Description](#description)  
- [Fonctionnalités](#fonctionnalités)  
- [Installation](#installation)  
- [Guide d'utilisation](#guide-dutilisation)  
- [Organisation du code](#organisation-du-code)  
- [Bugs connus](#bugs-connus)  
- [Licence](#licence)  

---

## **Description**  
**Planet Colonizer** est un jeu en Java où la gestion stratégique des ressources et la survie des colons sont au cœur de l’expérience.  
Affrontez des défis tels que l'épuisement des ressources, la surpopulation, ou la pollution tout en essayant de prospérer.  

---

## **Fonctionnalités**  
### 🌍 Planète et Ressources  
- Différents types de ressources comme l'eau, la nourriture, et le fer.  
- Gestion des ressources avec des limites de stockage et des taux de production variables.  

### 🏗️ Bâtiments  
- Construisez des structures comme des fermes, entrepôts, et dortoirs.  
- Chaque bâtiment a des effets uniques sur la pollution, la production, et la satisfaction des colons.  

### 👩‍🚀 Colons  
- Simulation réaliste de la vie des colons : reproduction, vieillissement, santé et satisfaction.  
- Gérez leurs besoins essentiels : nourriture, eau, oxygène.  

### 💾 Sauvegarde et Chargement *(en développement)*  
- Fonctionnalité de sauvegarde et chargement à venir dans une prochaine version.  

---

## **Installation**  
### **Prérequis**  
- **Java 11** ou version ultérieure.  

### **Étapes**  
1. Clonez le dépôt :  
   ```bash
   git clone https://github.com/Aksel-B/Planet-Colonizer.git
   cd Planet-Colonizer
   ```
2. Compilez le projet :  
   ```bash
   ./compile.sh
   ```
3. Lancez le jeu :  
   ```bash
   ./run.sh
   ```

---

## **Guide d'utilisation**  
- **Menu principal :** Commencez une nouvelle partie, chargez une sauvegarde, ou quittez le jeu.  
- **En jeu :** Construisez des bâtiments, gérez les ressources, et assurez la survie des colons.  

---

## **Organisation du Code**  
### **Classes principales**  
- **PlanetColonizer :**  
  Gère la logique principale et les interactions utilisateur.  

- **EtatJeu :**  
  Modélise l’état actuel de la partie.  

- **Planete :**  
  Gère la carte et les ressources de la planète.  

- **Colon :**  
  Simule les caractéristiques et comportements des colons.  

### **Méthodes clés**  
- `init()` : Initialise la planète et ses ressources.  
- `placerBatiment()` : Ajoute un bâtiment et ajuste les ressources.  
- `reproduire()` : Simule la reproduction et la démographie des colons.  

---

## **Bugs connus**  
1. **Chargement :** Non fonctionnels pour le moment.  
2. **Compatibilité limitée :** Testé principalement sur Linux.  

---

## **Licence**  
Ce projet est sous licence MIT. Consultez le fichier [LICENSE](./LICENSE) pour plus de détails.  

---

## **Auteurs**  
- **Bouri Aksel**  
- **Martin Rémy**  
