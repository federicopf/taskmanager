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

# Funzione per mostrare l'help
show_help() {
    echo ""
    echo "📖 Comandi disponibili:"
    echo "  ./dev.sh start     - Avvio rapido (usa volumi per file statici)"
    echo "  ./dev.sh rebuild   - Rebuild completo (per modifiche Java)"
    echo "  ./dev.sh stop      - Ferma i container"
    echo "  ./dev.sh restart   - Riavvia senza rebuild"
    echo "  ./dev.sh logs      - Mostra i log"
    echo "  ./dev.sh help      - Mostra questo help"
    echo ""
    echo "💡 Tip: Per modifiche a CSS/JS/template usa 'start', per modifiche Java usa 'rebuild'"
}

# Gestione dei comandi
case "$1" in
    "start")
        echo "⚡ Avvio RAPIDO con volumi montati"
        echo "🛑 Fermando container precedenti..."
        docker-compose down 2>/dev/null || true
        
        echo "🔨 Build incrementale..."
        docker-compose build
        
        echo "🎯 Avviando con volumi per sviluppo..."
        echo "📱 App: http://localhost:8080"
        echo "🗄️  DB: localhost:5432"
        echo "⏹️  Ctrl+C per fermare"
        echo ""
        
        docker-compose up
        ;;
        
    "rebuild")
        echo "🔄 Rebuild COMPLETO (per modifiche Java)"
        echo "🛑 Fermando container precedenti..."
        docker-compose down 2>/dev/null || true
        
        echo "🧹 Pulizia completa..."
        docker-compose build --no-cache
        
        echo "🎯 Avviando..."
        echo "📱 App: http://localhost:8080"
        echo "🗄️  DB: localhost:5432"
        echo "⏹️  Ctrl+C per fermare"
        echo ""
        
        docker-compose up
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
