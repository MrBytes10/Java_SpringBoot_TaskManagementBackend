package com.taskmanagementbackend.taskmgtappv1.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagementbackend.taskmgtappv1.model.TaskModel;

/**
 * Repository interface for TaskModel entity.
 * Provides custom query methods for task operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    
    /**
     * Finds all tasks belonging to a specific user, ordered by creation date descending.
     * @param userId The user's ID.
     * @return A list of tasks.
     */
    List<TaskModel> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    /**
     * Finds tasks by user ID and status, ordered by creation date descending.
     * @param userId The user's ID.
     * @param status The task status to filter by.
     * @return A list of matching tasks.
     */
    List<TaskModel> findByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, String status);
    
    /**
     * Finds a specific task by its ID and the user's ID to ensure a user
     * can only access their own tasks.
     * @param id The task ID.
     * @param userId The user's ID.
     * @return Optional containing the task if found and it belongs to the user.
     */
    Optional<TaskModel> findByIdAndUserId(UUID id, UUID userId);
}