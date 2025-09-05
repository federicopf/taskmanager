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
}
