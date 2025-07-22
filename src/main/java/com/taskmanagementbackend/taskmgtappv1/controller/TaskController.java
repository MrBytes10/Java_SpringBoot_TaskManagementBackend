package com.taskmanagementbackend.taskmgtappv1.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.taskmanagementbackend.taskmgtappv1.dto.TaskCreateDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskUpdateDto;
import com.taskmanagementbackend.taskmgtappv1.service.interfaces.ITaskService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * Controller for handling all task-related CRUD operations.
 * All endpoints are protected and require a valid JWT token.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final ITaskService taskService;
    private final com.taskmanagementbackend.taskmgtappv1.repository.UserRepository userRepository;

    @Autowired // used for dependency injection// in java, @Autowired is used to inject dependencies
    public TaskController(ITaskService taskService, com.taskmanagementbackend.taskmgtappv1.repository.UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    /**
     * Gets the UUID of the currently authenticated user.
     * @return The user's UUID.
     */
    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userPrincipal = (User) authentication.getPrincipal();
        return userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found in token"))
                .getId();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@RequestParam(required = false) String status) {
        UUID userId = getCurrentUserId();
        List<TaskResponseDto> tasks = taskService.getAllTasks(userId, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        try {
            UUID userId = getCurrentUserId();
            TaskResponseDto task = taskService.getTaskById(id, userId);
            return ResponseEntity.ok(task);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskCreateDto taskDto) {
        UUID userId = getCurrentUserId();
        TaskResponseDto createdTask = taskService.createTask(taskDto, userId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable UUID id, @Valid @RequestBody TaskUpdateDto taskDto) {
        try {
            UUID userId = getCurrentUserId();
            TaskResponseDto updatedTask = taskService.updateTask(id, taskDto, userId);
            return ResponseEntity.ok(updatedTask);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        try {
            UUID userId = getCurrentUserId();
            boolean deleted = taskService.deleteTask(id, userId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                // This case should ideally be handled by exceptions
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (SecurityException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}