package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class WebController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping("/")
    public String index(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "createdAt") String sortBy,
                       @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<TaskResponse> tasks = taskService.getAllTasks(pageable);
        TaskService.TaskStats stats = taskService.getTaskStats();
        
        model.addAttribute("tasks", tasks.getContent());
        model.addAttribute("stats", stats);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("first", tasks.isFirst());
        model.addAttribute("last", tasks.isLast());
        
        return "index";
    }
    
    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskRequest());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("statuses", TaskStatus.values());
        return "task-form";
    }
    
    @GetMapping("/tasks/search")
    public String searchTasks(@RequestParam(required = false) String title,
                            @RequestParam(required = false) TaskStatus status,
                            @RequestParam(required = false) TaskPriority priority,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());
        Page<TaskResponse> tasks = taskService.searchTasksWithFilters(title, status, priority, pageable);
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("searchTitle", title != null ? title : "");
        model.addAttribute("searchStatus", status);
        model.addAttribute("searchPriority", priority);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("first", tasks.isFirst());
        model.addAttribute("last", tasks.isLast());
        
        return "search-results";
    }
    
    @PostMapping("/tasks")
    public String createTask(@Valid @ModelAttribute("task") TaskRequest taskRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", TaskPriority.values());
            model.addAttribute("statuses", TaskStatus.values());
            return "task-form";
        }
        
        try {
            TaskResponse createdTask = taskService.createTask(taskRequest);
            redirectAttributes.addFlashAttribute("success", "Task creata con successo!");
            return "redirect:/tasks/" + createdTask.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella creazione della task: " + e.getMessage());
            return "redirect:/tasks/new";
        }
    }
    
    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<TaskResponse> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "task-detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "Task non trovata");
            return "redirect:/";
        }
    }
    
    @GetMapping("/tasks/{id}/edit")
    public String editTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<TaskResponse> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            TaskRequest taskRequest = new TaskRequest();
            taskRequest.setTitle(task.get().getTitle());
            taskRequest.setDescription(task.get().getDescription());
            taskRequest.setStatus(task.get().getStatus());
            taskRequest.setPriority(task.get().getPriority());
            taskRequest.setDueDate(task.get().getDueDate());
            
            model.addAttribute("task", taskRequest);
            model.addAttribute("taskId", id);
            model.addAttribute("priorities", TaskPriority.values());
            model.addAttribute("statuses", TaskStatus.values());
            return "task-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Task non trovata");
            return "redirect:/";
        }
    }
    
    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable Long id,
                           @Valid @ModelAttribute("task") TaskRequest taskRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("taskId", id);
            model.addAttribute("priorities", TaskPriority.values());
            model.addAttribute("statuses", TaskStatus.values());
            return "task-form";
        }
        
        try {
            Optional<TaskResponse> updatedTask = taskService.updateTask(id, taskRequest);
            if (updatedTask.isPresent()) {
                redirectAttributes.addFlashAttribute("success", "Task aggiornata con successo!");
                return "redirect:/tasks/" + id;
            } else {
                redirectAttributes.addFlashAttribute("error", "Task non trovata");
                return "redirect:/";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'aggiornamento della task: " + e.getMessage());
            return "redirect:/tasks/" + id + "/edit";
        }
    }
    
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Task eliminata con successo!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Task non trovata");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'eliminazione della task: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    @PostMapping("/tasks/{id}/status")
    public String updateTaskStatus(@PathVariable Long id, 
                                 @RequestParam TaskStatus status,
                                 RedirectAttributes redirectAttributes) {
        try {
            Optional<TaskResponse> updatedTask = taskService.updateTaskStatus(id, status);
            if (updatedTask.isPresent()) {
                redirectAttributes.addFlashAttribute("success", "Status aggiornato con successo!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Task non trovata");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'aggiornamento dello status: " + e.getMessage());
        }
        return "redirect:/tasks/" + id;
    }
    
}
