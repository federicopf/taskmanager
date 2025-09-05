package com.example.taskmanager.entity;

public enum TaskStatus {
    PENDING("In Attesa"),
    IN_PROGRESS("In Corso"),
    COMPLETED("Completata"),
    CANCELLED("Cancellata");
    
    private final String displayName;
    
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    // Metodo di utilità per ottenere il colore CSS associato allo status
    public String getColorClass() {
        return switch (this) {
            case PENDING -> "text-warning";
            case IN_PROGRESS -> "text-info";
            case COMPLETED -> "text-success";
            case CANCELLED -> "text-danger";
        };
    }
    
    // Metodo di utilità per ottenere l'icona Font Awesome associata allo status
    public String getIconClass() {
        return switch (this) {
            case PENDING -> "fa-clock";
            case IN_PROGRESS -> "fa-play-circle";
            case COMPLETED -> "fa-check-circle";
            case CANCELLED -> "fa-times-circle";
        };
    }
}
