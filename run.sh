#!/bin/bash

# Naviguer dans le dossier contenant les classes
cd ./src/main/Class || { echo "Le dossier 'Class' est introuvable."; exit 1; }

# Vérifier si la classe principale existe
if [ ! -f PlanetColonizer.class ]; then
  echo "Erreur : La classe PlanetColonizer.class est introuvable."
  exit 1
fi

# Boucle principale
while true; do
  # Exécuter le programme
  echo "Exécution de PlanetColonizer..."
  sleep 1
  clear
  java -cp ../program.jar:. PlanetColonizer
  
  # Demande à l'utilisateur s'il veut rejouer
  echo "Voulez-vous rejouer ? (O/N)"
  read choix
  
  # Convertir la réponse en minuscule pour uniformiser
  choix=$(echo "$choix" | tr '[:upper:]' '[:lower:]')
  
  # Vérifie le choix de l'utilisateur
  if [ "$choix" != "o" ]; then
    echo "Merci d'avoir joué ! À bientôt."
    break
  fi
done
