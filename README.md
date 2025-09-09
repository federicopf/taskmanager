# 📋 Task Manager

Un'applicazione web completa per la gestione delle task, sviluppata con **Spring Boot** e **Mustache** per un'esperienza utente moderna e intuitiva.

## 🚀 Caratteristiche Principali

### ✨ Funzionalità Core
- **Dashboard interattiva** con statistiche in tempo reale
- **CRUD completo** per la gestione delle task (Create, Read, Update, Delete)
- **Ricerca avanzata** con filtri multipli (titolo, status, priorità)
- **Paginazione intelligente** per gestire grandi quantità di dati
- **Cambio status rapido** con un click
- **Interfaccia responsive** ottimizzata per tutti i dispositivi

### 🎨 Design e UX
- **Design moderno** con Bootstrap 5 e Font Awesome
- **Animazioni fluide** per un'esperienza utente premium
- **Colori distintivi** per priorità e status delle task
- **Layout responsive** che si adatta perfettamente a:
  - 📱 **Mobile** (320px+)
  - 📱 **Tablet** (768px+)
  - 💻 **Desktop** (1024px+)
  - 🖥️ **Large Desktop** (1200px+)
- **Icone intuitive** per una navigazione immediata

## 🏗️ Architettura Tecnica

### Backend
- **Spring Boot 3.5.5** - Framework principale
- **Spring Data JPA** - Persistenza dei dati
- **PostgreSQL 16** - Database principale di produzione
- **H2** - Database in-memory per sviluppo e test
- **Flyway** - Migrazione del database
- **Spring Validation** - Validazione dei dati

### Frontend
- **Mustache** - Template engine server-side
- **Bootstrap 5** - Framework CSS
- **Font Awesome** - Icone vettoriali
- **CSS personalizzato** - Stili custom

### DevOps
- **Docker** - Containerizzazione
- **Docker Compose** - Orchestrazione servizi
- **Maven** - Gestione dipendenze
- **Spring Actuator** - Monitoring e health check
- **Script dev.sh** - Automazione sviluppo con hot reload

## 📁 Struttura del Progetto

```
taskmanager/
├── src/main/
│   ├── java/com/example/taskmanager/
│   │   ├── controller/
│   │   │   ├── TaskController.java      # API REST
│   │   │   └── WebController.java       # Controller web
│   │   ├── entity/
│   │   │   ├── Task.java               # Entità principale
│   │   │   ├── TaskStatus.java         # Enum status
│   │   │   └── TaskPriority.java       # Enum priorità
│   │   ├── dto/
│   │   │   ├── TaskRequest.java        # DTO input
│   │   │   └── TaskResponse.java       # DTO output
│   │   ├── service/
│   │   │   └── TaskService.java        # Logica business
│   │   ├── repository/
│   │   │   └── TaskRepository.java     # Accesso dati
│   │   └── config/
│   │       └── OpenApiConfig.java      # Configurazione Swagger
│   └── resources/
│       ├── templates/                  # Template Mustache
│       │   ├── index.mustache          # Dashboard
│       │   ├── task-form.mustache      # Form creazione/modifica
│       │   ├── task-detail.mustache   # Dettaglio task
│       │   └── search-results.mustache # Risultati ricerca
│       ├── static/css/
│       │   └── style.css              # Stili personalizzati
│       └── application.yml            # Configurazione
├── docker-compose.yml                 # Orchestrazione Docker
├── Dockerfile                        # Immagine Docker
├── dev.sh                           # Script sviluppo con hot reload
├── load-env.sh                      # Script caricamento variabili d'ambiente
├── env.example                      # Template variabili d'ambiente
├── SECRETS.md                       # Documentazione gestione segreti
└── pom.xml                          # Dipendenze Maven
```

## 🎯 Modelli di Dati

### Task
- **ID**: Identificatore univoco
- **Titolo**: Nome della task (obbligatorio, max 255 caratteri)
- **Descrizione**: Dettagli della task (max 1000 caratteri)
- **Status**: Stato corrente (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
- **Priorità**: Livello di urgenza (LOW, MEDIUM, HIGH, URGENT)
- **Data scadenza**: Quando deve essere completata
- **Timestamp**: Data creazione e ultima modifica

### Status delle Task
- 🟡 **PENDING** - In attesa
- 🔵 **IN_PROGRESS** - In corso
- 🟢 **COMPLETED** - Completata
- 🔴 **CANCELLED** - Cancellata

### Priorità delle Task
- 🟢 **LOW** - Bassa
- 🔵 **MEDIUM** - Media
- 🟡 **HIGH** - Alta
- 🔴 **URGENT** - Urgente

## 🚀 Avvio Rapido

### Prerequisiti
- **Java 17+**
- **Maven 3.6+**
- **Docker** (opzionale)
- **PostgreSQL 16+** (se non usi Docker)

### 🎯 Metodo Raccomandato: Script dev.sh

```bash
# 1. Clona il repository
git clone <repository-url>
cd taskmanager

# 2. Rendi eseguibile lo script
chmod +x dev.sh

# 3. Avvia con hot reload automatico
./dev.sh start
```

**Comandi disponibili:**
- `./dev.sh start` - Avvio con hot reload automatico
- `./dev.sh build` - Build dell'immagine Docker
- `./dev.sh rebuild` - Rebuild completo
- `./dev.sh stop` - Ferma i container
- `./dev.sh logs` - Mostra i log
- `./dev.sh help` - Mostra tutti i comandi

### Metodo 1: Avvio Locale con Docker Database

```bash
# 1. Clona il repository
git clone <repository-url>
cd taskmanager

# 2. Avvia solo il database PostgreSQL
docker-compose up db -d

# 3. Compila e avvia l'applicazione
mvn clean compile
mvn spring-boot:run
```

### Metodo 2: Docker Compose Completo

```bash
# 1. Clona il repository
git clone <repository-url>
cd taskmanager

# 2. Avvia tutto con Docker
docker-compose up --build
```

### Metodo 3: Solo Database Docker + App Locale

```bash
# 1. Avvia il database PostgreSQL
docker-compose up db -d

# 2. Avvia l'app localmente
mvn spring-boot:run
```

## 🌐 Accesso all'Applicazione

Una volta avviata, l'applicazione sarà disponibile su:

- **Dashboard principale**: http://localhost:8080
- **Nuova task**: http://localhost:8080/tasks/new
- **Ricerca avanzata**: http://localhost:8080/tasks/search
- **API REST**: http://localhost:8080/api/tasks
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

## 🔧 Configurazione

### Database
L'applicazione supporta due modalità:

**PostgreSQL 16 (Produzione)**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskdb
    username: taskuser
    password: taskpass
    driver-class-name: org.postgresql.Driver
```

**H2 (Sviluppo e Test)**
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password: 
    driver-class-name: org.h2.Driver
```

### Gestione Segreti con Variabili d'Ambiente

**Crea il file `.env`:**
```bash
cp env.example .env
# Modifica .env con le tue credenziali
```

**Variabili d'Ambiente Principali:**
- `DB_HOST`: Host del database (default: localhost)
- `DB_PORT`: Porta del database (default: 5432)
- `DB_NAME`: Nome del database (default: taskdb)
- `DB_USERNAME`: Username del database (default: taskuser)
- `DB_PASSWORD`: Password del database (default: taskpass)
- `SERVER_PORT`: Porta del server (default: 8080)
- `SPRING_PROFILES_ACTIVE`: Profilo attivo (dev/prod/test)

**Carica le variabili:**
```bash
source load-env.sh
./mvnw spring-boot:run
```

📚 **Documentazione completa**: Vedi `SECRETS.md` per la gestione avanzata dei segreti.

## 🛠️ Script di Sviluppo (dev.sh)

Lo script `dev.sh` automatizza completamente il processo di sviluppo con hot reload automatico.

### 🚀 Caratteristiche Principali

- **Hot Reload Automatico**: Modifica qualsiasi file Java e l'app si aggiorna automaticamente
- **Gestione Docker Intelligente**: Avvia Docker automaticamente se necessario
- **Build Ottimizzato**: Solo quando necessario, non ad ogni avvio
- **Logging in Tempo Reale**: Monitora l'applicazione in tempo reale
- **Comandi Semplici**: Interfaccia user-friendly per tutte le operazioni

### 📋 Comandi Disponibili

| Comando | Descrizione | Uso |
|---------|-------------|-----|
| `./dev.sh start` | Avvio con hot reload automatico | Sviluppo quotidiano |
| `./dev.sh build` | Build dell'immagine Docker | Solo quando necessario |
| `./dev.sh rebuild` | Rebuild completo dell'immagine | Dopo modifiche al Dockerfile |
| `./dev.sh stop` | Ferma tutti i container | Fine sessione di lavoro |
| `./dev.sh restart` | Riavvia senza rebuild | Riavvio rapido |
| `./dev.sh logs` | Mostra i log in tempo reale | Debug e monitoraggio |
| `./dev.sh docker` | Avvia solo Docker daemon | Setup iniziale |
| `./dev.sh help` | Mostra tutti i comandi | Riferimento rapido |

### 🔥 Hot Reload in Azione

```bash
# Avvia l'applicazione
./dev.sh start

# In un altro terminale, modifica qualsiasi file Java
# L'applicazione si riavvia automaticamente!
# Nessun rebuild Docker necessario
# Spring Boot DevTools gestisce tutto
```

### 🎯 Workflow di Sviluppo Raccomandato

1. **Primo avvio:**
   ```bash
   chmod +x dev.sh
   ./dev.sh start
   ```

2. **Sviluppo quotidiano:**
   ```bash
   ./dev.sh start  # Una volta al giorno
   # Modifica i file Java - hot reload automatico!
   ```

3. **Debug:**
   ```bash
   ./dev.sh logs   # In un terminale separato
   ```

4. **Fine giornata:**
   ```bash
   ./dev.sh stop
   ```

### ⚡ Vantaggi del Hot Reload

- **Velocità**: Nessun rebuild Docker ad ogni modifica
- **Efficienza**: Solo volumi montati, nessun download
- **Produttività**: Modifica → Salva → Vedi il risultato
- **Debugging**: Log in tempo reale sempre disponibili

## 📱 Design Responsive

L'applicazione è completamente responsive e si adatta perfettamente a tutti i dispositivi.

### 🎯 Breakpoints Responsive

| Dispositivo | Larghezza | Caratteristiche |
|-------------|-----------|-----------------|
| 📱 **Mobile** | 320px - 767px | Layout a colonna singola, menu hamburger |
| 📱 **Tablet** | 768px - 1023px | Layout a 2 colonne, sidebar collassabile |
| 💻 **Desktop** | 1024px - 1199px | Layout a 3 colonne, sidebar fissa |
| 🖥️ **Large Desktop** | 1200px+ | Layout ottimizzato per schermi grandi |

### 🎨 Adattamenti per Dispositivo

#### Mobile (320px+)
- **Dashboard**: Cards impilate verticalmente
- **Tabelle**: Scroll orizzontale con colonne essenziali
- **Form**: Input full-width con label sopra
- **Menu**: Hamburger menu collassabile
- **Bottoni**: Dimensioni touch-friendly (44px+)

#### Tablet (768px+)
- **Dashboard**: Grid 2x2 per le statistiche
- **Tabelle**: Colonne complete con scroll orizzontale
- **Form**: Layout a 2 colonne per input correlati
- **Menu**: Sidebar collassabile
- **Cards**: Layout a 2 colonne

#### Desktop (1024px+)
- **Dashboard**: Grid 4x1 per le statistiche
- **Tabelle**: Tutte le colonne visibili
- **Form**: Layout a 3 colonne
- **Menu**: Sidebar fissa sempre visibile
- **Cards**: Layout a 3 colonne

#### Large Desktop (1200px+)
- **Dashboard**: Grid ottimizzato con spaziature maggiori
- **Tabelle**: Colonne con larghezze ottimali
- **Form**: Layout a 4 colonne per form complessi
- **Menu**: Sidebar espansa con icone e testo
- **Cards**: Layout a 4 colonne

### 🎨 Componenti Responsive

- **Cards**: Si adattano automaticamente al contenitore
- **Tabelle**: Scroll orizzontale su mobile, colonne complete su desktop
- **Form**: Input che si espandono/contraggono in base allo spazio
- **Bottoni**: Dimensioni e spaziature ottimizzate per ogni dispositivo
- **Icone**: Dimensioni scalabili con Font Awesome
- **Testi**: Font size responsive con Bootstrap utilities

### 🔧 Tecnologie Responsive

- **Bootstrap 5**: Grid system e utilities responsive
- **CSS Custom Properties**: Variabili CSS per breakpoints
- **Flexbox**: Layout flessibili e adattivi
- **CSS Grid**: Layout complessi responsive
- **Media Queries**: Stili specifici per ogni dispositivo

## 📚 API REST

### Endpoints Principali

#### Task Management
- `GET /api/tasks` - Lista task con paginazione
- `POST /api/tasks` - Crea nuova task
- `GET /api/tasks/{id}` - Dettaglio task
- `PUT /api/tasks/{id}` - Aggiorna task
- `DELETE /api/tasks/{id}` - Elimina task

#### Ricerca e Filtri
- `GET /api/tasks/search` - Ricerca per titolo
- `GET /api/tasks/search/advanced` - Ricerca avanzata
- `GET /api/tasks/status/{status}` - Task per status
- `GET /api/tasks/priority/{priority}` - Task per priorità
- `GET /api/tasks/overdue` - Task scadute

#### Statistiche
- `GET /api/tasks/stats` - Statistiche generali
- `PATCH /api/tasks/{id}/status` - Cambio status rapido

### Esempio di Utilizzo API

```bash
# Crea una nuova task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completare documentazione",
    "description": "Scrivere la documentazione del progetto",
    "priority": "HIGH",
    "status": "PENDING",
    "dueDate": "2024-01-15T10:00:00"
  }'

# Ottieni tutte le task
curl http://localhost:8080/api/tasks?page=0&size=10&sortBy=createdAt&sortDir=desc

# Cerca task per titolo
curl "http://localhost:8080/api/tasks/search?title=documentazione"
```

## 🎨 Personalizzazione

### Stili CSS
Modifica `src/main/resources/static/css/style.css` per personalizzare:
- Colori e temi
- Animazioni
- Layout responsive
- Componenti personalizzati

### Template Mustache
I template in `src/main/resources/templates/` possono essere modificati per:
- Cambiare il layout delle pagine
- Aggiungere nuove funzionalità
- Personalizzare i messaggi
- Modificare la struttura HTML

### Configurazione Database
Modifica `src/main/resources/application.yml` per:
- Cambiare le impostazioni del database
- Configurare logging
- Impostare profili di ambiente

## 🧪 Testing

### Test Unitari
```bash
# Esegui tutti i test
mvn test

# Test con coverage
mvn test jacoco:report
```

### Test di Integrazione
```bash
# Test con database H2
mvn test -Dspring.profiles.active=test
```

## 🚀 Deployment

### Docker
```bash
# Build dell'immagine
docker build -t taskmanager .

# Run del container
docker run -p 8080:8080 taskmanager
```

### Docker Compose
```bash
# Deploy completo
docker-compose up --build -d
```

### Produzione
```bash
# Build per produzione
mvn clean package -Pprod

# Configura variabili d'ambiente per PostgreSQL
export DB_HOST=your-prod-db-host
export DB_PASSWORD=your-secure-password
export SPRING_PROFILES_ACTIVE=prod

# Run JAR
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```

### Database PostgreSQL per Produzione

**Configurazione PostgreSQL:**
```sql
-- Crea database
CREATE DATABASE taskmanager_prod;

-- Crea utente
CREATE USER taskmanager_user WITH PASSWORD 'secure_password';

-- Assegna privilegi
GRANT ALL PRIVILEGES ON DATABASE taskmanager_prod TO taskmanager_user;
```

**Variabili d'Ambiente Produzione:**
```bash
DB_HOST=your-postgresql-server.com
DB_PORT=5432
DB_NAME=taskmanager_prod
DB_USERNAME=taskmanager_user
DB_PASSWORD=your-secure-production-password
SPRING_PROFILES_ACTIVE=prod
```

## 🔍 Monitoring e Logging

### Health Check
- **Endpoint**: `/actuator/health`
- **Metriche**: `/actuator/metrics`
- **Info**: `/actuator/info`

### Logging
- **Livello DEBUG**: Per sviluppo
- **Livello INFO**: Per produzione
- **SQL Logging**: Abilitato in sviluppo

## 🤝 Contribuire

1. Fork del repository
2. Crea un branch per la feature (`git checkout -b feature/AmazingFeature`)
3. Commit delle modifiche (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Apri una Pull Request

## 📄 Licenza

Questo progetto è distribuito sotto licenza MIT. Vedi il file `LICENSE` per maggiori dettagli.

## 🆘 Supporto

Per problemi o domande:
- Apri una [Issue](https://github.com/your-repo/issues)
- Contatta il team di sviluppo
- Consulta la documentazione API su Swagger UI

## 🎉 Ringraziamenti

- **Spring Boot** per il framework robusto
- **Bootstrap** per il design system
- **Font Awesome** per le icone
- **PostgreSQL** per il database affidabile

---

**Sviluppato con ❤️ per semplificare la gestione delle task**