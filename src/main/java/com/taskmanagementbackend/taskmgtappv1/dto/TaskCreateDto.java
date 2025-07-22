package com.taskmanagementbackend.taskmgtappv1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating new tasks
 */
public class TaskCreateDto {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @Size(max = 20, message = "Status should not exceed 20 characters")
    private String status = "TODO";

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}