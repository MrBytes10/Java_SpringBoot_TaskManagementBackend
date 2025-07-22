package com.taskmanagementbackend.taskmgtappv1.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for user response (without password)
 * Used to return user data safely without exposing sensitive information
 */
public record UserResponseDto(
    UUID id,
    String email,
    String name,
    LocalDateTime createdAt
) {}