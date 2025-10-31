package com.ldr.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String type;
    private List<String> details;
    private String path;

    public ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(String message, String type) {
        this();
        this.message = message;
        this.type = type;
    }

    public ApiErrorResponse(String message, String type, List<String> details) {
        this(message, type);
        this.details = details;
    }

    public ApiErrorResponse(String message, String type, List<String> details, String path) {
        this(message, type, details);
        this.path = path;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
