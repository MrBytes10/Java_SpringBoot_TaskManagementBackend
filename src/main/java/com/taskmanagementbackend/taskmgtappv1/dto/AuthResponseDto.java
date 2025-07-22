package com.taskmanagementbackend.taskmgtappv1.dto;

/**
 * DTO for authentication response
 * Contains user data and JWT token
 */
public record AuthResponseDto(
    UserResponseDto user,
    String token
) {}