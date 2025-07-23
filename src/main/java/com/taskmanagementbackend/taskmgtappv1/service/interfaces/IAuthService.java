package com.taskmanagementbackend.taskmgtappv1.service.interfaces;

import com.taskmanagementbackend.taskmgtappv1.dto.AuthResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignInDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignUpDto;

/**
 * Interface for authentication services.// for high level Abstraction Principle (HLA)
 * Follows Interface Segregation Principle (ISP) from SOLID by defining
 * a specific contract for authentication operations.
 */
public interface IAuthService {
    
    /**
     * Registers a new user.
     * @param signUpDto User registration data.
     * @return AuthResponseDto containing user data and a JWT token.
     */
    AuthResponseDto signUp(UserSignUpDto signUpDto);
    
    /**
     * Authenticates a user and provides a JWT token.
     * @param signInDto User login credentials.
     * @return AuthResponseDto containing user data and a JWT token.
     */
    AuthResponseDto signIn(UserSignInDto signInDto);
}