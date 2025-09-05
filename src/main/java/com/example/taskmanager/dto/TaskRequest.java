package com.example.taskmanager.dto;

import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class TaskRequest {
    
    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 255, message = "Il titolo non può superare i 255 caratteri")
    private String title;
    
    @Size(max = 1000, message = "La descrizione non può superare i 1000 caratteri")
    private String description;
    
    private TaskStatus status;
    
    private TaskPriority priority;
    
    private LocalDateTime dueDate;
    
    // Costruttori
    public TaskRequest() {}
    
    public TaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    // Getters e Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public TaskPriority getPriority() {
        return priority;
    }
    
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
