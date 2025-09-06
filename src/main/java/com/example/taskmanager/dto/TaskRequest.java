package com.example.taskmanager.dto;

import com.example.taskmanager.constants.TaskConstants;
import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public class TaskRequest {
    
    @NotBlank(message = TaskConstants.TITLE_REQUIRED_MESSAGE)
    @Size(max = TaskConstants.TITLE_MAX_LENGTH, message = TaskConstants.TITLE_LENGTH_MESSAGE)
    private String title;
    
    @Size(max = TaskConstants.DESCRIPTION_MAX_LENGTH, message = TaskConstants.DESCRIPTION_LENGTH_MESSAGE)
    private String description;
    
    private TaskStatus status;
    
    private TaskPriority priority;
    
    @Future(message = TaskConstants.DUE_DATE_FUTURE_MESSAGE)
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
