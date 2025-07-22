package com.taskmanagementbackend.taskmgtappv1.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanagementbackend.taskmgtappv1.model.User;

/**
 * Repository interface for User entity
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * Follows Repository pattern from SOLID principles.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Finds a user by their email address. Spring Data JPA automatically
     * implements this method based on its name.
     * @param email The email to search for.
     * @return Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Checks if a user exists with the given email. This is more efficient
     * than fetching the entire user object.
     * @param email The email to check.
     * @return true if a user exists, false otherwise.
     */
    boolean existsByEmail(String email);
}