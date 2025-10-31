package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.CooperationType;
import com.ldr.api.repository.CooperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CooperationTypeService {

    private final CooperationTypeRepository cooperationTypeRepository;

    @Autowired
    public CooperationTypeService(CooperationTypeRepository cooperationTypeRepository) {
        this.cooperationTypeRepository = cooperationTypeRepository;
    }

    /**
     * Find all CooperationType entities
     * @return List<CooperationType>
     */
    @Transactional(readOnly = true)
    public List<CooperationType> findAll() {
        return cooperationTypeRepository.findAll();
    }

    /**
     * Find CooperationType by ID
     * @param id the CooperationType ID
     * @return CooperationType
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public CooperationType findById(String id) {
        return cooperationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CooperationType not found with id: " + id));
    }

    /**
     * Save a new CooperationType
     * @param cooperationType the CooperationType to save
     * @return saved CooperationType
     * @throws ValidationException if validation fails
     */
    public CooperationType save(CooperationType cooperationType) {
        validateCooperationType(cooperationType);

        try {
            return cooperationTypeRepository.save(cooperationType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save CooperationType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing CooperationType
     * @param id the CooperationType ID
     * @param cooperationType the updated CooperationType
     * @return updated CooperationType
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public CooperationType update(String id, CooperationType cooperationType) {
        CooperationType existingCooperationType = findById(id);

        validateCooperationType(cooperationType);

        // Update fields
        existingCooperationType.setName(cooperationType.getName());
        existingCooperationType.setDescription(cooperationType.getDescription());
        existingCooperationType.setActive(cooperationType.isActive());

        try {
            return cooperationTypeRepository.save(existingCooperationType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update CooperationType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete CooperationType by ID
     * @param id the CooperationType ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        CooperationType cooperationType = findById(id);
        try {
            cooperationTypeRepository.delete(cooperationType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete CooperationType as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active CooperationType
     * @return List<CooperationType>
     */
    @Transactional(readOnly = true)
    public List<CooperationType> findByIsActive() {
        return cooperationTypeRepository.findByIsActive(true);
    }

    /**
     * Validate CooperationType entity
     * @param cooperationType the CooperationType to validate
     * @throws ValidationException if validation fails
     */
    private void validateCooperationType(CooperationType cooperationType) {
        if (cooperationType == null) {
            throw new ValidationException("CooperationType cannot be null");
        }

        if (cooperationType.getName() == null || cooperationType.getName().trim().isEmpty()) {
            throw new ValidationException("CooperationType name is required");
        }

        if (cooperationType.getName().length() > 255) {
            throw new ValidationException("CooperationType name cannot exceed 255 characters");
        }
    }
}
