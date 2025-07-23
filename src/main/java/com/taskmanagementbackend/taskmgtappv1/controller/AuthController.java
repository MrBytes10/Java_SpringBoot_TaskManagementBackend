package com.taskmanagementbackend.taskmgtappv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.taskmanagementbackend.taskmgtappv1.dto.AuthResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignInDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignUpDto;
import com.taskmanagementbackend.taskmgtappv1.service.interfaces.IAuthService;

import jakarta.validation.Valid;

/**
 * Controller for handling user authentication (Sign Up and Sign In).
 */
@RestController
@RequestMapping("/api/auth") // version all apis
public class AuthController {
    
    private final IAuthService authService;
    
    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
    
    /**
     * Endpoint for registering a new user.
     * @param signUpDto User registration data, validated.
     * @return A response containing user data and a JWT token.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody UserSignUpDto signUpDto) {
        try {
            AuthResponseDto response = authService.signUp(signUpDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    /**
     * Endpoint for authenticating an existing user.
     * @param signInDto User login credentials, validated.
     * @return A response containing user data and a JWT token.
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signIn(@Valid @RequestBody UserSignInDto signInDto) {
        try {
            AuthResponseDto response = authService.signIn(signInDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}