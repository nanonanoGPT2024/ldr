package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.EmploymentStatus;
import com.ldr.api.repository.EmploymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmploymentStatusService {

    private final EmploymentStatusRepository employmentStatusRepository;

    @Autowired
    public EmploymentStatusService(EmploymentStatusRepository employmentStatusRepository) {
        this.employmentStatusRepository = employmentStatusRepository;
    }

    /**
     * Find all EmploymentStatus entities
     * @return List<EmploymentStatus>
     */
    @Transactional(readOnly = true)
    public List<EmploymentStatus> findAll() {
        return employmentStatusRepository.findAll();
    }

    /**
     * Find EmploymentStatus by ID
     * @param id the EmploymentStatus ID
     * @return EmploymentStatus
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public EmploymentStatus findById(String id) {
        return employmentStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmploymentStatus not found with id: " + id));
    }

    /**
     * Save a new EmploymentStatus
     * @param employmentStatus the EmploymentStatus to save
     * @return saved EmploymentStatus
     * @throws ValidationException if validation fails
     */
    public EmploymentStatus save(EmploymentStatus employmentStatus) {
        validateEmploymentStatus(employmentStatus);

        try {
            return employmentStatusRepository.save(employmentStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save EmploymentStatus due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing EmploymentStatus
     * @param id the EmploymentStatus ID
     * @param employmentStatus the updated EmploymentStatus
     * @return updated EmploymentStatus
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public EmploymentStatus update(String id, EmploymentStatus employmentStatus) {
        EmploymentStatus existingEmploymentStatus = findById(id);

        validateEmploymentStatus(employmentStatus);

        // Update fields
        existingEmploymentStatus.setName(employmentStatus.getName());
        existingEmploymentStatus.setDescription(employmentStatus.getDescription());
        existingEmploymentStatus.setActive(employmentStatus.isActive());

        try {
            return employmentStatusRepository.save(existingEmploymentStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update EmploymentStatus due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete EmploymentStatus by ID
     * @param id the EmploymentStatus ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        EmploymentStatus employmentStatus = findById(id);
        try {
            employmentStatusRepository.delete(employmentStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete EmploymentStatus as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active EmploymentStatus
     * @return List<EmploymentStatus>
     */
    @Transactional(readOnly = true)
    public List<EmploymentStatus> findByIsActive() {
        return employmentStatusRepository.findByIsActive(true);
    }

    /**
     * Validate EmploymentStatus entity
     * @param employmentStatus the EmploymentStatus to validate
     * @throws ValidationException if validation fails
     */
    private void validateEmploymentStatus(EmploymentStatus employmentStatus) {
        if (employmentStatus == null) {
            throw new ValidationException("EmploymentStatus cannot be null");
        }

        if (employmentStatus.getName() == null || employmentStatus.getName().trim().isEmpty()) {
            throw new ValidationException("EmploymentStatus name is required");
        }

        if (employmentStatus.getName().length() > 100) {
            throw new ValidationException("EmploymentStatus name cannot exceed 100 characters");
        }
    }
}
