package com.taskmanagementbackend.taskmgtappv1.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.taskmanagementbackend.taskmgtappv1.dto.TaskCreateDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.TaskUpdateDto;

/**
 * Interface for task management services.
 * Defines the contract for all task-related business logic.
 */
public interface ITaskService {
    
    /**
     * Gets all tasks for a user, with an optional status filter.
     * @param userId The ID of the user whose tasks to retrieve.
     * @param statusFilter Optional status to filter by (e.g., "TODO", "DONE").
     * @return A list of task response DTOs.
     */
    List<TaskResponseDto> getAllTasks(UUID userId, String statusFilter);
    
    /**
     * Gets a specific task by its ID, ensuring it belongs to the user.
     * @param taskId The ID of the task to retrieve.
     * @param userId The ID of the user requesting the task.
     * @return The task response DTO if found, otherwise null.
     */
    TaskResponseDto getTaskById(UUID taskId, UUID userId);
    
    /**
     * Creates a new task for a user.
     * @param taskDto The data for the new task.
     * @param userId The ID of the user creating the task.
     * @return The created task as a response DTO.
     */
    TaskResponseDto createTask(TaskCreateDto taskDto, UUID userId);
    
    /**
     * Updates an existing task.
     * @param taskId The ID of the task to update.
     * @param taskDto The new data for the task.
     * @param userId The ID of the user updating the task.
     * @return The updated task as a response DTO, or null if not found.
     */
    TaskResponseDto updateTask(UUID taskId, TaskUpdateDto taskDto, UUID userId);
    
    /**
     * Deletes a task.
     * @param taskId The ID of the task to delete.
     * @param userId The ID of the user deleting the task.
     * @return true if deleted successfully, false otherwise.
     */
    boolean deleteTask(UUID taskId, UUID userId);
}