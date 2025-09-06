package com.example.taskmanager.controller;

import com.example.taskmanager.constants.TaskConstants;
import com.example.taskmanager.dto.SelectOption;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.TaskPriority;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {
    
    private final TaskService taskService;
    
    public WebController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("/")
    public String index(Model model,
                       @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_PAGE}") int page,
                       @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_SIZE}") int size,
                       @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_SORT_BY}") String sortBy,
                       @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_SORT_DIR}") String sortDir) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        Page<TaskResponse> tasks = taskService.getAllTasks(pageable);
        TaskService.TaskStats stats = taskService.getTaskStats();
        
        model.addAttribute("tasks", tasks.getContent());
        model.addAttribute("stats", stats);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentPagePlusOne", page + 1);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("prevPage", Math.max(0, page - 1));
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("lastPage", Math.max(0, tasks.getTotalPages() - 1));
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("first", tasks.isFirst());
        model.addAttribute("last", tasks.isLast());
        
        return "index";
    }
    
    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskRequest());
        
        // Crea le liste di opzioni senza valori selezionati
        List<SelectOption> priorities = createEmptyPriorityOptions();
        List<SelectOption> statuses = createEmptyStatusOptions();
        
        model.addAttribute("priorities", priorities);
        model.addAttribute("statuses", statuses);
        return "task-form";
    }
    
    @GetMapping("/tasks/search")
    public String searchTasks(@RequestParam(required = false) String title,
                            @RequestParam(required = false) TaskStatus status,
                            @RequestParam(required = false) TaskPriority priority,
                            @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_PAGE}") int page,
                            @RequestParam(defaultValue = "#{T(com.example.taskmanager.constants.TaskConstants).DEFAULT_SIZE}") int size,
                            Model model) {
        
        // Normalizza i parametri vuoti
        String normalizedTitle = (title != null && !title.trim().isEmpty()) ? title.trim() : null;
        
        // Normalizza anche status e priority se sono stringhe vuote
        TaskStatus normalizedStatus = status;
        TaskPriority normalizedPriority = priority;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());
        Page<TaskResponse> tasks = taskService.searchTasksWithFilters(normalizedTitle, normalizedStatus, normalizedPriority, pageable);
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalElements", tasks.getTotalElements());
        model.addAttribute("searchTitle", normalizedTitle != null ? normalizedTitle : "");
        model.addAttribute("searchStatus", normalizedStatus != null ? normalizedStatus.name() : "");
        model.addAttribute("searchPriority", normalizedPriority != null ? normalizedPriority.name() : "");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasks.getTotalPages());
        // Crea le liste di opzioni con i valori selezionati per la ricerca
        List<SelectOption> priorities = createPriorityOptions(priority);
        List<SelectOption> statuses = createStatusOptions(status);
        
        model.addAttribute("priorities", priorities);
        model.addAttribute("statuses", statuses);
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
            // Crea le liste di opzioni senza valori selezionati per gli errori
            List<SelectOption> priorities = createEmptyPriorityOptions();
            List<SelectOption> statuses = createEmptyStatusOptions();
            
            model.addAttribute("priorities", priorities);
            model.addAttribute("statuses", statuses);
            return "task-form";
        }
        
        try {
            TaskResponse createdTask = taskService.createTask(taskRequest);
            redirectAttributes.addFlashAttribute("success", TaskConstants.TASK_CREATED_SUCCESS);
            return "redirect:/tasks/" + createdTask.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.TASK_CREATE_ERROR + e.getMessage());
            return "redirect:/tasks/new";
        }
    }
    
    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            TaskResponse task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            
            // Crea le opzioni di status per il cambio rapido
            List<SelectOption> statusOptions = createStatusOptions(task.getStatus());
            
            model.addAttribute("statusOptions", statusOptions);
            return "task-detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.TASK_NOT_FOUND);
            return "redirect:/";
        }
    }
    
    @GetMapping("/tasks/{id}/edit")
    public String editTask(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            TaskResponse task = taskService.getTaskById(id);
            TaskRequest taskRequest = new TaskRequest();
            taskRequest.setTitle(task.getTitle());
            taskRequest.setDescription(task.getDescription());
            taskRequest.setStatus(task.getStatus());
            taskRequest.setPriority(task.getPriority());
            taskRequest.setDueDate(task.getDueDate());
            
            model.addAttribute("task", taskRequest);
            model.addAttribute("taskId", id);
            
            // Crea le liste di opzioni con i valori selezionati
            List<SelectOption> priorities = createPriorityOptions(task.getPriority());
            List<SelectOption> statuses = createStatusOptions(task.getStatus());
            
            model.addAttribute("priorities", priorities);
            model.addAttribute("statuses", statuses);
            return "task-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.TASK_NOT_FOUND);
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
            
            // Crea le liste di opzioni senza valori selezionati per gli errori
            List<SelectOption> priorities = createEmptyPriorityOptions();
            List<SelectOption> statuses = createEmptyStatusOptions();
            
            model.addAttribute("priorities", priorities);
            model.addAttribute("statuses", statuses);
            return "task-form";
        }
        
        try {
            taskService.updateTask(id, taskRequest);
            redirectAttributes.addFlashAttribute("success", TaskConstants.TASK_UPDATED_SUCCESS);
            return "redirect:/tasks/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.TASK_UPDATE_ERROR + e.getMessage());
            return "redirect:/tasks/" + id + "/edit";
        }
    }
    
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("success", TaskConstants.TASK_DELETED_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.TASK_DELETE_ERROR + e.getMessage());
        }
        return "redirect:/";
    }
    
    @PostMapping("/tasks/{id}/status")
    public String updateTaskStatus(@PathVariable Long id, 
                                 @RequestParam TaskStatus status,
                                 RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTaskStatus(id, status);
            redirectAttributes.addFlashAttribute("success", TaskConstants.STATUS_UPDATED_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", TaskConstants.STATUS_UPDATE_ERROR + e.getMessage());
        }
        return "redirect:/tasks/" + id;
    }
    
    // Metodi helper per creare le SelectOption
    private List<SelectOption> createPriorityOptions(TaskPriority selectedPriority) {
        return Arrays.stream(TaskPriority.values())
            .map(p -> new SelectOption(p.name(), p.getDisplayName(), p.equals(selectedPriority)))
            .collect(Collectors.toList());
    }
    
    private List<SelectOption> createStatusOptions(TaskStatus selectedStatus) {
        return Arrays.stream(TaskStatus.values())
            .map(s -> new SelectOption(s.name(), s.getDisplayName(), s.equals(selectedStatus)))
            .collect(Collectors.toList());
    }
    
    private List<SelectOption> createEmptyPriorityOptions() {
        return createPriorityOptions(null);
    }
    
    private List<SelectOption> createEmptyStatusOptions() {
        return createStatusOptions(null);
    }
    
}
