package com.ldr.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class WorkflowDto {

    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String nama;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String deskripsi;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto createdBy;
    private UserDto updatedBy;
    private Long version;

    // Constructors
    public WorkflowDto() {}

    public WorkflowDto(String id, String nama, String deskripsi, Boolean isActive) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public UserDto getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserDto updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
