package com.ldr.api.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthResponse {

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private boolean success;

    private String token;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public AuthResponse(String message, boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}