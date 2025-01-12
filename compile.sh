#!/bin/bash

# Naviguer dans le répertoire Java
cd ./src/main/Java || { echo "Le dossier 'Java' est introuvable."; exit 1; }

# Vérifier si des fichiers .java existent
if [ $(ls *.java 2>/dev/null | wc -l) -eq 0 ]; then
  echo "Aucun fichier .java trouvé dans le dossier 'Java'."
  exit 1
fi

# Compiler tous les fichiers .java avec la dépendance program.jar
echo "Compilation des fichiers Java..."
javac -cp ../program.jar:. *.java

if [ $? -eq 0 ]; then
  echo "Compilation réussie !"
else
  echo "Erreur lors de la compilation."
  exit 1
fi

# Déplacer les fichiers compilés .class dans le répertoire Class
echo "Déplacement des fichiers compilés dans le dossier 'Class'..."
mv *.class ../Class/

if [ $? -eq 0 ]; then
  echo "Fichiers compilés déplacés avec succès dans 'Class'."
else
  echo "Erreur lors du déplacement des fichiers compilés."
  exit 1
fi

# Retourner à la racine du projet
cd ..

echo "Processus terminé avec succès."
