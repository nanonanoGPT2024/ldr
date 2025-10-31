package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.Priority;
import com.ldr.api.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    /**
     * Find all Priority entities
     * @return List<Priority>
     */
    @Transactional(readOnly = true)
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    /**
     * Find Priority by ID
     * @param id the Priority ID
     * @return Priority
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Priority findById(String id) {
        return priorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with id: " + id));
    }

    /**
     * Save a new Priority
     * @param priority the Priority to save
     * @return saved Priority
     * @throws ValidationException if validation fails
     */
    public Priority save(Priority priority) {
        validatePriority(priority);

        // Check for duplicate code
        if (priorityRepository.findByCode(priority.getCode()).isPresent()) {
            throw new ValidationException("Priority code already exists: " + priority.getCode());
        }

        try {
            return priorityRepository.save(priority);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save Priority due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing Priority
     * @param id the Priority ID
     * @param priority the updated Priority
     * @return updated Priority
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public Priority update(String id, Priority priority) {
        Priority existingPriority = findById(id);

        validatePriority(priority);

        // Check for duplicate code (excluding current record)
        if (priorityRepository.existsByCodeAndIdNot(priority.getCode(), id)) {
            throw new ValidationException("Priority code already exists: " + priority.getCode());
        }

        // Update fields
        existingPriority.setCode(priority.getCode());
        existingPriority.setName(priority.getName());
        existingPriority.setDescription(priority.getDescription());
        existingPriority.setColorCode(priority.getColorCode());
        existingPriority.setDefaultDeadlineDays(priority.getDefaultDeadlineDays());
        existingPriority.setActive(priority.isActive());

        try {
            return priorityRepository.save(existingPriority);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update Priority due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete Priority by ID
     * @param id the Priority ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        Priority priority = findById(id);
        try {
            priorityRepository.delete(priority);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete Priority as it is referenced by other records: " + id);
        }
    }

    /**
     * Find Priority by code
     * @param code the unique code
     * @return Priority
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Priority findByCode(String code) {
        return priorityRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with code: " + code));
    }

    /**
     * Find all active Priority
     * @return List<Priority>
     */
    @Transactional(readOnly = true)
    public List<Priority> findByIsActive() {
        return priorityRepository.findByIsActive(true);
    }

    /**
     * Validate Priority entity
     * @param priority the Priority to validate
     * @throws ValidationException if validation fails
     */
    private void validatePriority(Priority priority) {
        if (priority == null) {
            throw new ValidationException("Priority cannot be null");
        }

        if (priority.getCode() == null || priority.getCode().trim().isEmpty()) {
            throw new ValidationException("Priority code is required");
        }

        if (priority.getCode().length() > 20) {
            throw new ValidationException("Priority code cannot exceed 20 characters");
        }

        if (priority.getName() == null || priority.getName().trim().isEmpty()) {
            throw new ValidationException("Priority name is required");
        }

        if (priority.getName().length() > 50) {
            throw new ValidationException("Priority name cannot exceed 50 characters");
        }

        if (priority.getDefaultDeadlineDays() != null && priority.getDefaultDeadlineDays() < 0) {
            throw new ValidationException("Priority default deadline days cannot be negative");
        }

        if (priority.getColorCode() != null && !priority.getColorCode().matches("^#[0-9A-Fa-f]{6}$")) {
            throw new ValidationException("Priority color code must be a valid hex color (e.g., #FF0000)");
        }
    }
}
