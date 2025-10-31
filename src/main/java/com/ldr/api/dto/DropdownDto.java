package com.ldr.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for dropdown responses.
 * Provides a consistent format for dropdown options with id and name fields.
 * Used across the application for various dropdown endpoints.
 */
public class DropdownDto {

    /**
     * The unique identifier for the dropdown option.
     * This is typically the value that gets submitted.
     */
    private String id;

    /**
     * The display name for the dropdown option.
     * This is what users see in the dropdown list.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    // Constructors
    public DropdownDto() {}

    /**
     * Constructor with id and name parameters.
     *
     * @param id the unique identifier
     * @param name the display name
     */
    public DropdownDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}