/// B:\mytestProjects\JavaPROJECTS\TaskMngtAppBackend\taskmgtappv1\src\main\java\com\taskmanagementbackend\taskmgtappv1\service\AuthService.java

package com.taskmanagementbackend.taskmgtappv1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder; // Import Spring Security's PasswordEncoder
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.taskmanagementbackend.taskmgtappv1.dto.AuthResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserResponseDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignInDto;
import com.taskmanagementbackend.taskmgtappv1.dto.UserSignUpDto;
import com.taskmanagementbackend.taskmgtappv1.model.User;
import com.taskmanagementbackend.taskmgtappv1.repository.UserRepository;
import com.taskmanagementbackend.taskmgtappv1.service.interfaces.IAuthService;
import com.taskmanagementbackend.taskmgtappv1.util.JwtUtil;
//import com.taskmanagementbackend.taskmgtappv1.util.PasswordEncoder;

/**
 * Implementation of the authentication service.
 * Follows Single Responsibility Principle (SRP) by handling only auth operations.
 * Uses Dependency Injection (DI) for loose coupling, a tenet of DIP.
 */
@Service
public class AuthService implements IAuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public AuthResponseDto signUp(UserSignUpDto signUpDto) {
        // Check if user already exists
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            logger.warn("User registration failed: Email {} already exists", signUpDto.getEmail());
            throw new IllegalArgumentException("Email is already in use.");
        }
        
        // Hash the password
        String hashedPassword = passwordEncoder.encode(signUpDto.getPassword());
        
        // Create new user
        User user = new User(signUpDto.getEmail(), hashedPassword, signUpDto.getName());
        user = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        
        // Create response DTO
        UserResponseDto userResponse = new UserResponseDto(
            user.getId(), user.getEmail(), user.getName(), user.getCreatedAt()
        );
        
        logger.info("User {} registered successfully", user.getEmail());
        return new AuthResponseDto(userResponse, token);
    }
    
    @Override
    public AuthResponseDto signIn(UserSignInDto signInDto) {
        try {
            // Spring Security handles the authentication check
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
            );
        } catch (AuthenticationException e) {
            logger.warn("Sign in failed for user {}: Invalid credentials", signInDto.getEmail());
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // If authentication is successful, find user and generate token
        User user = userRepository.findByEmail(signInDto.getEmail())
            .orElseThrow(() -> new IllegalStateException("User not found after successful authentication."));

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
            
        // Create response DTO
        UserResponseDto userResponse = new UserResponseDto(
            user.getId(), user.getEmail(), user.getName(), user.getCreatedAt()
        );
            
        logger.info("User {} signed in successfully", user.getEmail());
        return new AuthResponseDto(userResponse, token);
    }
}