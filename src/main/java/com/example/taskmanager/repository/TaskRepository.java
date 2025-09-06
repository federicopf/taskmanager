package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Trova task per status
    List<Task> findByStatus(TaskStatus status);
    
    // Trova task per priority
    List<Task> findByPriority(TaskPriority priority);
    
    // Trova task per status e priority
    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);
    
    // Trova task scadute (due date < now)
    @Query("SELECT t FROM Task t WHERE t.dueDate < :now AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now);
    
    // Trova task per titolo (ricerca parziale)
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    // Trova task per descrizione (ricerca parziale)
    List<Task> findByDescriptionContainingIgnoreCase(String description);
    
    // Trova task create in un periodo specifico
    List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Trova task con paginazione per status
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    
    // Trova task con paginazione per priority
    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);
    
    // Ricerca generale con paginazione - query nativa per ricerca parziale sicura
    @Query(value = "SELECT * FROM tasks WHERE " +
           "(:title IS NULL OR LOWER(title) LIKE LOWER('%' || :title || '%')) AND " +
           "(:status IS NULL OR status = :status) AND " +
           "(:priority IS NULL OR priority = :priority)", 
           countQuery = "SELECT COUNT(*) FROM tasks WHERE " +
           "(:title IS NULL OR LOWER(title) LIKE LOWER('%' || :title || '%')) AND " +
           "(:status IS NULL OR status = :status) AND " +
           "(:priority IS NULL OR priority = :priority)",
           nativeQuery = true)
    Page<Task> findTasksWithFilters(@Param("title") String title, 
                                   @Param("status") String status, 
                                   @Param("priority") String priority, 
                                   Pageable pageable);
    
    // Conta task per status
    long countByStatus(TaskStatus status);
    
    // Conta task per priority
    long countByPriority(TaskPriority priority);
}
