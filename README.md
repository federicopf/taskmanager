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
- **Layout responsive** che si adatta a qualsiasi schermo
- **Icone intuitive** per una navigazione immediata

## 🏗️ Architettura Tecnica

### Backend
- **Spring Boot 3.5.5** - Framework principale
- **Spring Data JPA** - Persistenza dei dati
- **PostgreSQL** - Database principale
- **H2** - Database in-memory per sviluppo
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
- **PostgreSQL 12+** (se non usi Docker)

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
# 1. Avvia il database
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

**PostgreSQL (Produzione)**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskdb
    username: taskuser
    password: taskpass
```

**H2 (Sviluppo)**
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password: 
```

### Variabili d'Ambiente
- `SERVER_PORT`: Porta del server (default: 8080)
- `DB_URL`: URL del database
- `DB_USERNAME`: Username del database
- `DB_PASSWORD`: Password del database
- `DB_DRIVER`: Driver del database
- `JPA_DDL_AUTO`: Modalità Hibernate (default: validate)
- `FLYWAY_ENABLED`: Abilita Flyway (default: true)

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

# Run JAR
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
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