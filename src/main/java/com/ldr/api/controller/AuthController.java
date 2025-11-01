package com.ldr.api.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldr.api.dto.AuthResponse;
import com.ldr.api.dto.LoginRequest;
import com.ldr.api.dto.RegisterRequest;
import com.ldr.api.exception.DuplicateResourceException;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.UnauthorizedException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.User;
import com.ldr.api.security.JwtUtil;
import com.ldr.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "403", description = "Account locked or inactive"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth", scopes = {})
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Login credentials") @Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Find user by username
            User user = userService.findByUsername(loginRequest.getUsername());

            // Check if account is active
            if (!user.isActive()) {
                throw new UnauthorizedException("Account is inactive");
            }

            // Check if account is locked
            if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
                throw new UnauthorizedException("Account is temporarily locked");
            }

            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Increment failed login attempts
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

                // Lock account after 5 failed attempts
                if (user.getFailedLoginAttempts() >= 5) {
                    user.setLockedUntil(LocalDateTime.now().plusMinutes(30)); // Lock for 30 minutes
                }

                userService.save(user);
                throw new UnauthorizedException("Username Dan Password Salah");
            }

            // Reset failed login attempts on successful login
            user.setFailedLoginAttempts(0);
            user.setLockedUntil(null);
            user.setLastLogin(LocalDateTime.now());
            userService.save(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUsername());
            // Create AuthResponse
            AuthResponse authResponse = new AuthResponse("Login berhasil", true, token);

            return ResponseEntity.ok(authResponse);

        } catch (ResourceNotFoundException e) {
            throw new UnauthorizedException("Invalid credentials");
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registration successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth", scopes = {})
    public ResponseEntity<AuthResponse> register(
            @Parameter(description = "Registration data") @Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Check if username already exists
            try {
                userService.findByUsername(registerRequest.getUsername());
                throw new DuplicateResourceException("Username already exists");
            } catch (ResourceNotFoundException e) {
                // Username is available, continue
            }

            // Check if email already exists
            try {
                userService.findByEmail(registerRequest.getEmail());
                throw new DuplicateResourceException("Email already exists");
            } catch (ResourceNotFoundException e) {
                // Email is available, continue
            }

            // Create new user
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            user.setFullName(registerRequest.getFullName());
            user.setRole("USER"); // Default role
            user.setDepartment(registerRequest.getDepartment());
            user.setPosition(registerRequest.getPosition());
            user.setPhone(registerRequest.getPhone());
            user.setActive(true);
            user.setFailedLoginAttempts(0);

            // Save user
            User savedUser = userService.save(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getUsername());


            // Create AuthResponse
            AuthResponse authResponse = new AuthResponse("Registrasi berhasil", true, token);

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Registration failed: " + e.getMessage());
        }
    }

}