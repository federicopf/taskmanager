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
}
