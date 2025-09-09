#!/bin/bash

# Script per caricare le variabili d'ambiente da un file .env
# Uso: source load-env.sh

if [ -f .env ]; then
    echo "Caricamento variabili d'ambiente da .env..."
    export $(cat .env | grep -v '^#' | xargs)
    echo "Variabili d'ambiente caricate!"
else
    echo "File .env non trovato. Creare un file .env con le variabili necessarie."
    echo "Esempio:"
    echo "DB_HOST=localhost"
    echo "DB_PORT=5432"
    echo "DB_NAME=taskdb"
    echo "DB_USERNAME=taskuser"
    echo "DB_PASSWORD=taskpass"
    echo "SERVER_PORT=8080"
fi
