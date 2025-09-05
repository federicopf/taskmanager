# Task Manager - Monolita con Mustache

## Panoramica

Questo progetto è stato trasformato da un'API REST a un **monolita web completo** utilizzando:
- **Spring Boot** come framework backend
- **Mustache** come template engine
- **Bootstrap 5** per l'interfaccia utente
- **PostgreSQL** come database

## Caratteristiche

### 🎯 Funzionalità Principali
- **Dashboard** con statistiche delle task
- **CRUD completo** per le task (Create, Read, Update, Delete)
- **Ricerca avanzata** con filtri per titolo, status e priorità
- **Paginazione** per gestire grandi quantità di dati
- **Cambio status rapido** delle task
- **Interfaccia responsive** e moderna

### 🎨 Design
- **Design minimalista** e pulito [[memory:7447723]]
- **Animazioni fluide** per le interazioni
- **Colori distintivi** per priorità e status
- **Icone Font Awesome** per una migliore UX

## Struttura del Progetto

```
src/main/
├── java/com/example/taskmanager/
│   ├── controller/
│   │   ├── TaskController.java      # API REST (mantenute)
│   │   └── WebController.java       # Controller web per Mustache
│   ├── entity/
│   │   ├── Task.java               # Entità principale
│   │   ├── TaskStatus.java         # Enum status
│   │   └── TaskPriority.java       # Enum priorità
│   ├── dto/
│   │   ├── TaskRequest.java        # DTO per input
│   │   └── TaskResponse.java       # DTO per output
│   ├── service/
│   │   └── TaskService.java        # Logica business
│   └── repository/
│       └── TaskRepository.java     # Accesso ai dati
└── resources/
    ├── templates/                  # Template Mustache
    │   ├── index.mustache          # Dashboard principale
    │   ├── task-form.mustache     # Form creazione/modifica
    │   ├── task-detail.mustache   # Dettaglio task
    │   └── search-results.mustache # Risultati ricerca
    ├── static/css/
    │   └── style.css              # Stili personalizzati
    └── application.yml            # Configurazione
```

## Template Mustache

### Pagine Disponibili

1. **Dashboard (`/`)**
   - Lista di tutte le task con paginazione
   - Statistiche in tempo reale
   - Filtri di ordinamento

2. **Form Task (`/tasks/new`, `/tasks/{id}/edit`)**
   - Creazione di nuove task
   - Modifica di task esistenti
   - Validazione lato client e server

3. **Dettaglio Task (`/tasks/{id}`)**
   - Visualizzazione completa della task
   - Cambio status rapido
   - Azioni di modifica ed eliminazione

4. **Ricerca (`/tasks/search`)**
   - Form di ricerca avanzata
   - Filtri per titolo, status e priorità
   - Risultati paginati

## API REST vs Monolita

### API REST (mantenute)
- `GET /api/tasks` - Lista task con paginazione
- `POST /api/tasks` - Crea nuova task
- `GET /api/tasks/{id}` - Dettaglio task
- `PUT /api/tasks/{id}` - Aggiorna task
- `DELETE /api/tasks/{id}` - Elimina task
- E molte altre...

### Monolita Web
- `GET /` - Dashboard principale
- `GET /tasks/new` - Form nuova task
- `POST /tasks` - Crea task (redirect)
- `GET /tasks/{id}` - Dettaglio task
- `GET /tasks/{id}/edit` - Form modifica
- `POST /tasks/{id}` - Aggiorna task (redirect)
- `POST /tasks/{id}/delete` - Elimina task (redirect)
- `GET /tasks/search` - Pagina ricerca

## Configurazione

### Database
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskdb
    username: taskuser
    password: taskpass
```

### Mustache
```yaml
spring:
  mustache:
    enabled: true
    suffix: .mustache
    prefix: classpath:/templates/
```

## Avvio del Progetto

### Prerequisiti
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Comandi
```bash
# Compilazione
mvn clean compile

# Test
mvn test

# Avvio
mvn spring-boot:run
```

### Docker (opzionale)
```bash
# Build e avvio con Docker Compose
docker-compose up --build
```

## Vantaggi del Monolita

### ✅ Pro
- **Semplicità**: Un'unica applicazione da deployare
- **Performance**: Nessuna latenza di rete tra frontend e backend
- **Manutenzione**: Codice centralizzato e più facile da gestire
- **Sicurezza**: Meno superficie di attacco
- **Debugging**: Più facile tracciare i problemi

### ⚠️ Considerazioni
- **Scalabilità**: Più difficile scalare orizzontalmente
- **Tecnologie**: Meno flessibilità nella scelta delle tecnologie
- **Team**: Richiede competenze full-stack

## Personalizzazione

### Stili CSS
Modifica `src/main/resources/static/css/style.css` per personalizzare:
- Colori e temi
- Animazioni
- Layout responsive
- Componenti personalizzati

### Template Mustache
I template sono in `src/main/resources/templates/` e possono essere modificati per:
- Cambiare il layout
- Aggiungere nuove funzionalità
- Personalizzare i messaggi
- Modificare la struttura delle pagine

## Conclusioni

Il monolita con Mustache offre un'esperienza utente completa e moderna, mantenendo la semplicità di deployment e manutenzione. È perfetto per:
- Applicazioni interne
- Prototipi rapidi
- Progetti con team piccoli
- Sistemi che non richiedono scalabilità estrema

La combinazione Spring Boot + Mustache + Bootstrap offre un ottimo equilibrio tra funzionalità, performance e facilità di sviluppo.
