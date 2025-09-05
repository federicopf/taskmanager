package com.example.taskmanager.entity;

public enum TaskPriority {
    LOW("Bassa"),
    MEDIUM("Media"),
    HIGH("Alta"),
    URGENT("Urgente");
    
    private final String displayName;
    
    TaskPriority(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    // Metodo di utilità per ottenere il colore CSS associato alla priorità
    public String getColorClass() {
        return switch (this) {
            case LOW -> "text-success";
            case MEDIUM -> "text-info";
            case HIGH -> "text-warning";
            case URGENT -> "text-danger";
        };
    }
    
    // Metodo di utilità per ottenere l'icona Font Awesome associata alla priorità
    public String getIconClass() {
        return switch (this) {
            case LOW -> "fa-arrow-down";
            case MEDIUM -> "fa-minus";
            case HIGH -> "fa-arrow-up";
            case URGENT -> "fa-exclamation-triangle";
        };
    }
}
