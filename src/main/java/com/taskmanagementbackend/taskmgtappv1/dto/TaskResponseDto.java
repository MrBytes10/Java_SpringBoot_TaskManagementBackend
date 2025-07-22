package com.taskmanagementbackend.taskmgtappv1.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for task response
 * Used to return task data to clients
 */
public record TaskResponseDto(
    UUID id,
    UUID userId,
    String title,
    String description,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}