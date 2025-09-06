#!/bin/bash

echo "🚀 Task Manager - Modalità Sviluppo Intelligente"

# Verifica prerequisiti
if ! command -v docker &> /dev/null; then
    echo "❌ Docker non trovato. Installa Docker."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose non trovato. Installa Docker Compose."
    exit 1
fi

# Funzione per avviare Docker
start_docker() {
    echo "🐳 Controllo stato Docker..."
    
    # Verifica se Docker è già in esecuzione
    if docker info &> /dev/null; then
        echo "✅ Docker è già in esecuzione"
        return 0
    fi
    
    echo "🛑 Docker non è in esecuzione, avvio..."
    
    # Termina eventuali processi Docker esistenti
    echo "🧹 Pulizia processi Docker esistenti..."
    pkill docker 2>/dev/null || true
    
    # Aspetta che i processi si chiudano completamente
    echo "⏳ Attendo 3 secondi per la pulizia..."
    sleep 3
    
    # Avvia Docker daemon
    echo "🚀 Avvio Docker daemon..."
    dockerd &
    
    # Aspetta che Docker sia pronto
    echo "⏳ Attendo che Docker sia pronto..."
    local attempts=0
    while [ $attempts -lt 30 ]; do
        if docker info &> /dev/null; then
            echo "✅ Docker avviato con successo!"
            return 0
        fi
        sleep 1
        attempts=$((attempts + 1))
    done
    
    echo "❌ Timeout: Docker non si è avviato dopo 30 secondi"
    exit 1
}

# Funzione per build dell'immagine Docker (solo quando necessario)
build_docker_image() {
    echo "🐳 Build dell'immagine Docker..."
    docker-compose build
}

# Funzione per mostrare l'help
show_help() {
    echo ""
    echo "📖 Comandi disponibili:"
    echo "  ./dev.sh start     - Avvio con hot reload AUTOMATICO (solo volumi montati!)"
    echo "  ./dev.sh build     - Build dell'immagine Docker (solo se necessario)"
    echo "  ./dev.sh rebuild   - Rebuild completo dell'immagine Docker"
    echo "  ./dev.sh docker    - Avvia solo Docker daemon"
    echo "  ./dev.sh stop      - Ferma i container"
    echo "  ./dev.sh restart   - Riavvia senza rebuild"
    echo "  ./dev.sh logs      - Mostra i log"
    echo "  ./dev.sh help      - Mostra questo help"
    echo ""
    echo "🚀 HOT RELOAD AUTOMATICO: Modifica qualsiasi file Java e l'app si aggiorna automaticamente!"
    echo "💡 Nessun download Docker inutile - solo volumi montati!"
}

# Gestione dei comandi
case "$1" in
    "start")
        echo "🚀 Avvio con HOT RELOAD AUTOMATICO"
        
        # Avvia Docker se necessario
        start_docker
        
        echo "🛑 Fermando container precedenti..."
        docker-compose down 2>/dev/null || true
        
        echo "🎯 Avviando con DevTools per hot reload automatico..."
        echo "📱 App: http://localhost:8080"
        echo "🗄️  DB: localhost:5432"
        echo ""
        echo "🔥 HOT RELOAD ATTIVO!"
        echo "   ✨ Modifica qualsiasi file Java e l'app si aggiorna automaticamente"
        echo "   ✨ Maven compila e riavvia automaticamente"
        echo "   ✨ Spring Boot DevTools gestisce tutto"
        echo ""
        echo "⏹️  Ctrl+C per fermare"
        echo ""
        
        docker-compose up
        ;;
        
    "build")
        echo "🐳 Build dell'immagine Docker..."
        
        # Avvia Docker se necessario
        start_docker
        
        echo "🛑 Fermando container precedenti..."
        docker-compose down 2>/dev/null || true
        
        build_docker_image
        
        echo "✅ Build completato! Ora puoi usare './dev.sh start'"
        ;;
        
    "rebuild")
        echo "🔄 Rebuild COMPLETO dell'immagine Docker"
        
        # Avvia Docker se necessario
        start_docker
        
        echo "🛑 Fermando container precedenti..."
        docker-compose down 2>/dev/null || true
        
        echo "🧹 Pulizia completa dell'immagine Docker..."
        docker-compose build --no-cache
        
        echo "✅ Rebuild completato! Ora puoi usare './dev.sh start'"
        ;;
        
    "docker")
        echo "🐳 Avvio Docker daemon..."
        start_docker
        echo "✅ Docker è pronto per l'uso!"
        ;;
        
    "stop")
        echo "🛑 Fermando Task Manager..."
        docker-compose down
        ;;
        
    "restart")
        echo "🔄 Riavvio rapido..."
        docker-compose restart app
        ;;
        
    "logs")
        echo "📋 Log dell'applicazione:"
        docker-compose logs -f app
        ;;
        
    "help"|"")
        show_help
        ;;
        
    *)
        echo "❌ Comando non riconosciuto: $1"
        show_help
        exit 1
        ;;
esac

echo "✅ Operazione completata."
