package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // Crea una nuova task
    public TaskResponse createTask(TaskRequest taskRequest) {
        if (taskRequest == null) {
            throw new IllegalArgumentException("TaskRequest non può essere null");
        }
        
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus() != null ? taskRequest.getStatus() : TaskStatus.PENDING);
        task.setPriority(taskRequest.getPriority() != null ? taskRequest.getPriority() : TaskPriority.MEDIUM);
        task.setDueDate(taskRequest.getDueDate());
        
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }
    
    // Ottieni tutte le task con paginazione
    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(TaskResponse::new);
    }
    
    // Ottieni tutte le task senza paginazione
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    // Ottieni task per ID
    @Transactional(readOnly = true)
    public Optional<TaskResponse> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::new);
    }
    
    // Aggiorna una task esistente
    public Optional<TaskResponse> updateTask(Long id, TaskRequest taskRequest) {
        if (id == null) {
            throw new IllegalArgumentException("ID non può essere null");
        }
        if (taskRequest == null) {
            throw new IllegalArgumentException("TaskRequest non può essere null");
        }
        
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(taskRequest.getTitle());
                    existingTask.setDescription(taskRequest.getDescription());
                    if (taskRequest.getStatus() != null) {
                        existingTask.setStatus(taskRequest.getStatus());
                    }
                    if (taskRequest.getPriority() != null) {
                        existingTask.setPriority(taskRequest.getPriority());
                    }
                    existingTask.setDueDate(taskRequest.getDueDate());
                    
                    Task updatedTask = taskRepository.save(existingTask);
                    return new TaskResponse(updatedTask);
                });
    }
    
    // Elimina una task
    public boolean deleteTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID non può essere null");
        }
        
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Ottieni task per status
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    // Ottieni task per priority
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByPriority(TaskPriority priority) {
        List<Task> tasks = taskRepository.findByPriority(priority);
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    // Ottieni task scadute
    @Transactional(readOnly = true)
    public List<TaskResponse> getOverdueTasks() {
        List<Task> tasks = taskRepository.findOverdueTasks(LocalDateTime.now());
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    // Cerca task per titolo
    @Transactional(readOnly = true)
    public List<TaskResponse> searchTasksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return List.of();
        }
        
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title.trim());
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
    
    // Cerca task con filtri avanzati
    @Transactional(readOnly = true)
    public Page<TaskResponse> searchTasksWithFilters(String title, TaskStatus status, 
                                                    TaskPriority priority, Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable non può essere null");
        }
        
        // Normalizza il titolo se presente
        String normalizedTitle = (title != null && !title.trim().isEmpty()) ? title.trim() : null;
        
        // Converti gli enum in stringhe per la query nativa
        String statusStr = (status != null) ? status.name() : null;
        String priorityStr = (priority != null) ? priority.name() : null;
        
        Page<Task> tasks = taskRepository.findTasksWithFilters(normalizedTitle, statusStr, priorityStr, pageable);
        return tasks.map(TaskResponse::new);
    }
    
    // Aggiorna status di una task
    public Optional<TaskResponse> updateTaskStatus(Long id, TaskStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("ID non può essere null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status non può essere null");
        }
        
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(status);
                    Task updatedTask = taskRepository.save(task);
                    return new TaskResponse(updatedTask);
                });
    }
    
    // Ottieni statistiche
    @Transactional(readOnly = true)
    public TaskStats getTaskStats() {
        long totalTasks = taskRepository.count();
        long pendingTasks = taskRepository.countByStatus(TaskStatus.PENDING);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED);
        long cancelledTasks = taskRepository.countByStatus(TaskStatus.CANCELLED);
        
        return new TaskStats(totalTasks, pendingTasks, inProgressTasks, completedTasks, cancelledTasks);
    }
    
    // Classe interna per le statistiche
    public static class TaskStats {
        private final long totalTasks;
        private final long pendingTasks;
        private final long inProgressTasks;
        private final long completedTasks;
        private final long cancelledTasks;
        
        public TaskStats(long totalTasks, long pendingTasks, long inProgressTasks, 
                        long completedTasks, long cancelledTasks) {
            this.totalTasks = totalTasks;
            this.pendingTasks = pendingTasks;
            this.inProgressTasks = inProgressTasks;
            this.completedTasks = completedTasks;
            this.cancelledTasks = cancelledTasks;
        }
        
        // Getters
        public long getTotalTasks() { return totalTasks; }
        public long getPendingTasks() { return pendingTasks; }
        public long getInProgressTasks() { return inProgressTasks; }
        public long getCompletedTasks() { return completedTasks; }
        public long getCancelledTasks() { return cancelledTasks; }
    }
}
