package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.Workflow;
import com.ldr.api.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowService {

    private final WorkflowRepository workflowRepository;

    @Autowired
    public WorkflowService(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    /**
     * Find all Workflow entities
     * @return List<Workflow>
     */
    @Transactional(readOnly = true)
    public List<Workflow> findAll() {
        return workflowRepository.findAll();
    }

    /**
     * Find Workflow by ID
     * @param id the Workflow ID
     * @return Workflow
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Workflow findById(String id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
    }

    /**
     * Save a new Workflow
     * @param workflow the Workflow to save
     * @return saved Workflow
     * @throws ValidationException if validation fails
     */
    public Workflow save(Workflow workflow) {
        validateWorkflow(workflow);

        try {
            return workflowRepository.save(workflow);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save Workflow due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing Workflow
     * @param id the Workflow ID
     * @param workflow the updated Workflow
     * @return updated Workflow
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public Workflow update(String id, Workflow workflow) {
        Workflow existingWorkflow = findById(id);

        validateWorkflow(workflow);

        // Update fields
        existingWorkflow.setNama(workflow.getNama());
        existingWorkflow.setDeskripsi(workflow.getDeskripsi());
        existingWorkflow.setActive(workflow.isActive());

        try {
            return workflowRepository.save(existingWorkflow);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update Workflow due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete Workflow by ID
     * @param id the Workflow ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        Workflow workflow = findById(id);
        try {
            workflowRepository.delete(workflow);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete Workflow as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all Workflow by active status
     * @param isActive the active status
     * @return List<Workflow>
     */
    @Transactional(readOnly = true)
    public List<Workflow> findByIsActive(boolean isActive) {
        return workflowRepository.findByIsActive(isActive);
    }

    /**
     * Validate Workflow entity
     * @param workflow the Workflow to validate
     * @throws ValidationException if validation fails
     */
    private void validateWorkflow(Workflow workflow) {
        if (workflow == null) {
            throw new ValidationException("Workflow cannot be null");
        }

        if (workflow.getNama() == null || workflow.getNama().trim().isEmpty()) {
            throw new ValidationException("Nama is required");
        }

        if (workflow.getNama().length() > 255) {
            throw new ValidationException("Nama cannot exceed 255 characters");
        }

        if (workflow.getDeskripsi() != null && workflow.getDeskripsi().length() > 1000) {
            throw new ValidationException("Deskripsi cannot exceed 1000 characters");
        }
    }
}
