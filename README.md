# Task Manager

Un'applicazione Spring Boot per la gestione delle task con interfaccia web moderna.

## Caratteristiche

- ✅ **API REST complete** per la gestione delle task
- ✅ **Database PostgreSQL** con Flyway per le migration
- ✅ **Interfaccia web moderna** e responsive
- ✅ **Docker Compose** per il deployment completo
- ✅ **Validazione dei dati** e gestione errori
- ✅ **Ricerca e filtri avanzati**
- ✅ **Statistiche in tempo reale**

## Struttura del Progetto

```
taskmanager/
├── src/main/java/com/example/taskmanager/
│   ├── TaskmanagerApplication.java          # Classe principale
│   ├── entity/                             # Entità JPA
│   │   ├── Task.java
│   │   ├── TaskStatus.java
│   │   └── TaskPriority.java
│   ├── repository/                         # Repository JPA
│   │   └── TaskRepository.java
│   ├── service/                           # Logica business
│   │   └── TaskService.java
│   ├── controller/                        # Controller REST e Web
│   │   ├── TaskController.java
│   │   └── WebController.java
│   └── dto/                              # Data Transfer Objects
│       ├── TaskRequest.java
│       └── TaskResponse.java
├── src/main/resources/
│   ├── application.yml                    # Configurazione principale
│   ├── application-prod.yml              # Configurazione produzione
│   ├── db/migration/                     # Migration Flyway
│   │   ├── V1__Create_tasks_table.sql
│   │   └── V2__Insert_sample_data.sql
│   └── static/
│       └── index.html                    # Interfaccia web
├── docker-compose.yml                    # Configurazione Docker
├── Dockerfile                           # Immagine Docker
└── pom.xml                             # Dipendenze Maven
```

## Tecnologie Utilizzate

- **Spring Boot 3.5.5** - Framework principale
- **Spring Data JPA** - Persistenza dati
- **PostgreSQL** - Database
- **Flyway** - Gestione migration database
- **Spring Validation** - Validazione dati
- **SpringDoc OpenAPI** - Documentazione API
- **Docker** - Containerizzazione
- **jQuery** - Frontend JavaScript

## Avvio Rapido

### Prerequisiti

- Docker e Docker Compose
- Java 17+ (per sviluppo locale)
- Maven 3.6+ (per sviluppo locale)

### Con Docker Compose (Raccomandato)

1. **Clona il repository**
   ```bash
   git clone <repository-url>
   cd taskmanager
   ```

2. **Avvia l'applicazione**
   ```bash
   docker-compose up -d
   ```

3. **Accedi all'applicazione**
   - Web Interface: http://localhost:8080
   - API Documentation: http://localhost:8080/swagger-ui.html
   - Health Check: http://localhost:8080/actuator/health

### Sviluppo Locale

1. **Avvia PostgreSQL**
   ```bash
   docker-compose up db -d
   ```

2. **Configura le variabili d'ambiente**
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/taskdb
   export DB_USERNAME=taskuser
   export DB_PASSWORD=taskpass
   ```

3. **Compila e avvia l'applicazione**
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

## API Endpoints

### Task Management

| Metodo | Endpoint | Descrizione |
|--------|----------|-------------|
| GET | `/api/tasks` | Lista task con paginazione |
| GET | `/api/tasks/all` | Lista tutte le task |
| GET | `/api/tasks/{id}` | Dettagli task specifica |
| POST | `/api/tasks` | Crea nuova task |
| PUT | `/api/tasks/{id}` | Aggiorna task |
| DELETE | `/api/tasks/{id}` | Elimina task |
| PATCH | `/api/tasks/{id}/status` | Aggiorna status task |

### Filtri e Ricerca

| Metodo | Endpoint | Descrizione |
|--------|----------|-------------|
| GET | `/api/tasks/status/{status}` | Task per status |
| GET | `/api/tasks/priority/{priority}` | Task per priorità |
| GET | `/api/tasks/overdue` | Task scadute |
| GET | `/api/tasks/search` | Ricerca per titolo |
| GET | `/api/tasks/search/advanced` | Ricerca avanzata |
| GET | `/api/tasks/stats` | Statistiche |

### Esempi di Utilizzo

**Crea una nuova task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nuova Task",
    "description": "Descrizione della task",
    "priority": "HIGH",
    "status": "PENDING",
    "dueDate": "2024-02-01T15:00:00"
  }'
```

**Aggiorna status di una task:**
```bash
curl -X PATCH "http://localhost:8080/api/tasks/1/status?status=IN_PROGRESS"
```

**Ricerca task:**
```bash
curl "http://localhost:8080/api/tasks/search?title=importante"
```

## Configurazione Database

### Credenziali PostgreSQL

- **Database**: `taskdb`
- **Username**: `taskuser`
- **Password**: `taskpass`
- **Port**: `5432`

### Migration

Le migration Flyway sono automaticamente eseguite all'avvio dell'applicazione:

- `V1__Create_tasks_table.sql` - Crea la tabella tasks
- `V2__Insert_sample_data.sql` - Inserisce dati di esempio

## Interfaccia Web

L'interfaccia web è accessibile all'indirizzo `http://localhost:8080` e include:

- **Dashboard** con statistiche in tempo reale
- **Form** per creare nuove task
- **Lista task** con filtri e azioni
- **Design responsive** per dispositivi mobili
- **Aggiornamento automatico** dei dati

## Monitoraggio

L'applicazione include endpoint di monitoraggio Spring Actuator:

- `/actuator/health` - Stato dell'applicazione
- `/actuator/info` - Informazioni applicazione
- `/actuator/metrics` - Metriche applicazione

## Sviluppo

### Aggiungere Nuove Funzionalità

1. **Nuova Entità**: Crea classe in `entity/`
2. **Repository**: Crea interfaccia in `repository/`
3. **Service**: Aggiungi logica in `service/`
4. **Controller**: Esponi API in `controller/`
5. **Migration**: Aggiungi script SQL in `db/migration/`

### Testing

```bash
# Test unitari
mvn test

# Test integrazione
mvn verify

# Test con Docker
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

## Troubleshooting

### Problemi Comuni

1. **Database non raggiungibile**
   - Verifica che PostgreSQL sia in esecuzione
   - Controlla le credenziali in `application.yml`

2. **Migration fallite**
   - Verifica la sintassi SQL nei file di migration
   - Controlla i log dell'applicazione

3. **Porta già in uso**
   - Cambia la porta in `application.yml`
   - Aggiorna `docker-compose.yml`

### Log

```bash
# Log dell'applicazione
docker-compose logs app

# Log del database
docker-compose logs db

# Log in tempo reale
docker-compose logs -f app
```

## Contribuire

1. Fork del repository
2. Crea un branch per la feature (`git checkout -b feature/nuova-feature`)
3. Commit delle modifiche (`git commit -am 'Aggiunge nuova feature'`)
4. Push del branch (`git push origin feature/nuova-feature`)
5. Crea una Pull Request

## Licenza

Questo progetto è rilasciato sotto licenza MIT. Vedi il file `LICENSE` per i dettagli.