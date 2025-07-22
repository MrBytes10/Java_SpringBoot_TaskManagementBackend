package com.taskmanagementbackend.taskmgtappv1.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagementbackend.taskmgtappv1.dto.TaskCreateDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskUpdateDto;
import com.taskmanagementbackend.taskmgtappv1.model.TaskModel;
import com.taskmanagementbackend.taskmgtappv1.repository.TaskRepository;
import com.taskmanagementbackend.taskmgtappv1.service.interfaces.ITaskService;

// import com.taskmanagement.dto.TaskCreateDto;
// import com.taskmanagement.dto.TaskResponseDto;
// import com.taskmanagement.dto.TaskUpdateDto;
// import com.taskmanagement.model.TaskModel;
// import com.taskmanagement.repository.TaskRepository;
// import com.taskmanagement.service.interfaces.ITaskService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the task management service.
 * Follows Single Responsibility Principle by handling only task-related business logic.
 */
@Service
public class TaskService implements ITaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static final List<String> VALID_STATUSES = Arrays.asList("TODO", "IN_PROGRESS", "DONE");
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @Override
    public List<TaskResponseDto> getAllTasks(UUID userId, String statusFilter) {
        List<TaskModel> tasks;
        
        if (statusFilter != null && !statusFilter.equalsIgnoreCase("All") && VALID_STATUSES.contains(statusFilter.toUpperCase())) {
            tasks = taskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, statusFilter.toUpperCase());
        } else {
            tasks = taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }
        
        List<TaskResponseDto> taskDtos = tasks.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        
        logger.info("Retrieved {} tasks for user {}", tasks.size(), userId);
        return taskDtos;
    }
    
    @Override
    public TaskResponseDto getTaskById(UUID taskId, UUID userId) {
        TaskModel task = taskRepository.findByIdAndUserId(taskId, userId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        
        return convertToDto(task);
    }
    
    @Override
    public TaskResponseDto createTask(TaskCreateDto taskDto, UUID userId) {
        String status = taskDto.getStatus() != null ? taskDto.getStatus().toUpperCase() : "TODO";
        if (!VALID_STATUSES.contains(status)) {
            status = "TODO"; // Default to TODO if status is invalid
        }
        
        TaskModel task = new TaskModel(userId, taskDto.getTitle(), taskDto.getDescription(), status);
        task = taskRepository.save(task);
        
        logger.info("Task {} created successfully for user {}", task.getId(), userId);
        return convertToDto(task);
    }
    
    @Override
    public TaskResponseDto updateTask(UUID taskId, TaskUpdateDto taskDto, UUID userId) {
        TaskModel task = taskRepository.findByIdAndUserId(taskId, userId)
             .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        
        String status = taskDto.getStatus().toUpperCase();
        if (!VALID_STATUSES.contains(status)) {
            logger.warn("Invalid status '{}' provided for task {}", status, taskId);
            throw new IllegalArgumentException("Invalid status value provided.");
        }
        
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(status);
        
        TaskModel updatedTask = taskRepository.save(task);
        logger.info("Task {} updated successfully by user {}", taskId, userId);
        return convertToDto(updatedTask);
    }
    
    @Override
    public boolean deleteTask(UUID taskId, UUID userId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found with id: " + taskId);
        }
        // Ensure the task belongs to the user before deleting
        TaskModel task = taskRepository.findByIdAndUserId(taskId, userId)
            .orElseThrow(() -> new SecurityException("User does not have permission to delete this task."));

        taskRepository.delete(task);
        logger.info("Task {} deleted successfully by user {}", taskId, userId);
        return true;
    }

    /**
     * Helper method to convert a TaskModel entity to a TaskResponseDto.
     * @param task The entity to convert.
     * @return The resulting DTO.
     */
    private TaskResponseDto convertToDto(TaskModel task) {
        return new TaskResponseDto(
            task.getId(),
            task.getUserId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }
}