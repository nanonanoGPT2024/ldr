package com.ldr.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @Size(max = 100, message = "Position must not exceed 100 characters")
    private String position;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @NotBlank(message = "Role cannot be blank")
    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;

    // Constructors
    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String email, String fullName,
                          String department, String position, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.phone = phone;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}