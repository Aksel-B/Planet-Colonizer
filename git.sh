#!/bin/bash

# Vérifier si un message de commit a été fourni
if [ -z "$1" ]; then
    echo "Veuillez fournir un message de commit."
    echo "Usage: ./git_push.sh \"Votre message de commit\""
    exit 1
fi

# Ajouter tous les changements
echo "Ajout des fichiers..."
git add .

# Faire le commit avec le message fourni
echo "Commit en cours..."
git commit -m "$1"

# Pousser vers le dépôt distant
echo "Push vers le dépôt distant..."
git push

# Vérifier si le push a réussi
if [ $? -eq 0 ]; then
    echo "Changements poussés avec succès !"
else
    echo "Échec lors du push. Vérifiez les erreurs ci-dessus."
fi
