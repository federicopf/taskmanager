package com.example.taskmanager.constants;

public final class TaskConstants {
    
    // Paginazione
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    
    // Ordinamento
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIR = "desc";
    
    // Validazioni
    public static final int TITLE_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 1000;
    
    // Messaggi di errore
    public static final String TITLE_REQUIRED_MESSAGE = "Il titolo è obbligatorio";
    public static final String TITLE_LENGTH_MESSAGE = "Il titolo non può superare i " + TITLE_MAX_LENGTH + " caratteri";
    public static final String DESCRIPTION_LENGTH_MESSAGE = "La descrizione non può superare i " + DESCRIPTION_MAX_LENGTH + " caratteri";
    public static final String DUE_DATE_FUTURE_MESSAGE = "La data di scadenza deve essere nel futuro";
    
    // Messaggi di successo
    public static final String TASK_CREATED_SUCCESS = "Task creata con successo!";
    public static final String TASK_UPDATED_SUCCESS = "Task aggiornata con successo!";
    public static final String TASK_DELETED_SUCCESS = "Task eliminata con successo!";
    public static final String STATUS_UPDATED_SUCCESS = "Status aggiornato con successo!";
    
    // Messaggi di errore
    public static final String TASK_NOT_FOUND = "Task non trovata";
    public static final String TASK_CREATE_ERROR = "Errore nella creazione della task: ";
    public static final String TASK_UPDATE_ERROR = "Errore nell'aggiornamento della task: ";
    public static final String TASK_DELETE_ERROR = "Errore nell'eliminazione della task: ";
    public static final String STATUS_UPDATE_ERROR = "Errore nell'aggiornamento dello status: ";
    
    // Costruttore privato per evitare istanziazione
    private TaskConstants() {
        throw new UnsupportedOperationException("Questa è una classe di costanti e non può essere istanziata");
    }
}
