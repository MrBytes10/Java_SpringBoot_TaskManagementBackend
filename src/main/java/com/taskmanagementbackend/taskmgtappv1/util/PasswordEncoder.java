
/* B:\mytestProjects\JavaPROJECTS\TaskMngtAppBackend\taskmgtappv1\src\main\java\com\taskmanagementbackend\taskmgtappv1/util\PasswordEncoder.java */ 
package com.taskmanagementbackend.taskmgtappv1.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for handling password hashing and verification.
 * Uses BCrypt, the industry standard for password storage.
 */
public class PasswordEncoder {
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}