package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.WorkflowDetail;
import com.ldr.api.repository.WorkflowDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowDetailService {

    private final WorkflowDetailRepository workflowDetailRepository;

    @Autowired
    public WorkflowDetailService(WorkflowDetailRepository workflowDetailRepository) {
        this.workflowDetailRepository = workflowDetailRepository;
    }

    /**
     * Find all WorkflowDetail entities
     * @return List<WorkflowDetail>
     */
    @Transactional(readOnly = true)
    public List<WorkflowDetail> findAll() {
        return workflowDetailRepository.findAll();
    }

    /**
     * Find WorkflowDetail by ID
     * @param id the WorkflowDetail ID
     * @return WorkflowDetail
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public WorkflowDetail findById(String id) {
        return workflowDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkflowDetail not found with id: " + id));
    }

    /**
     * Save a new WorkflowDetail
     * @param workflowDetail the WorkflowDetail to save
     * @return saved WorkflowDetail
     * @throws ValidationException if validation fails
     */
    public WorkflowDetail save(WorkflowDetail workflowDetail) {
        validateWorkflowDetail(workflowDetail);

        try {
            return workflowDetailRepository.save(workflowDetail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save WorkflowDetail due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing WorkflowDetail
     * @param id the WorkflowDetail ID
     * @param workflowDetail the updated WorkflowDetail
     * @return updated WorkflowDetail
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public WorkflowDetail update(String id, WorkflowDetail workflowDetail) {
        WorkflowDetail existingWorkflowDetail = findById(id);

        validateWorkflowDetail(workflowDetail);

        // Update fields
        existingWorkflowDetail.setWorkflow(workflowDetail.getWorkflow());
        existingWorkflowDetail.setCurrentStage(workflowDetail.getCurrentStage());
        existingWorkflowDetail.setNextStage(workflowDetail.getNextStage());
        existingWorkflowDetail.setReturnStage(workflowDetail.getReturnStage());
        existingWorkflowDetail.setRejectStage(workflowDetail.getRejectStage());
        existingWorkflowDetail.setSla(workflowDetail.getSla());
        existingWorkflowDetail.setActive(workflowDetail.isActive());

        try {
            return workflowDetailRepository.save(existingWorkflowDetail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update WorkflowDetail due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete WorkflowDetail by ID
     * @param id the WorkflowDetail ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        WorkflowDetail workflowDetail = findById(id);
        try {
            workflowDetailRepository.delete(workflowDetail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete WorkflowDetail as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all WorkflowDetail by workflow ID
     * @param workflowId the workflow ID
     * @return List<WorkflowDetail>
     */
    @Transactional(readOnly = true)
    public List<WorkflowDetail> findByWorkflowId(String workflowId) {
        return workflowDetailRepository.findByWorkflowId(workflowId);
    }

    /**
     * Find all WorkflowDetail by current stage
     * @param currentStage the current stage
     * @return List<WorkflowDetail>
     */
    @Transactional(readOnly = true)
    public List<WorkflowDetail> findByCurrentStage(String currentStage) {
        return workflowDetailRepository.findByCurrentStage(currentStage);
    }

    /**
     * Find all WorkflowDetail by active status
     * @param isActive the active status
     * @return List<WorkflowDetail>
     */
    @Transactional(readOnly = true)
    public List<WorkflowDetail> findByIsActive(boolean isActive) {
        return workflowDetailRepository.findByIsActive(isActive);
    }

    /**
     * Validate WorkflowDetail entity
     * @param workflowDetail the WorkflowDetail to validate
     * @throws ValidationException if validation fails
     */
    private void validateWorkflowDetail(WorkflowDetail workflowDetail) {
        if (workflowDetail == null) {
            throw new ValidationException("WorkflowDetail cannot be null");
        }

        if (workflowDetail.getWorkflow() == null) {
            throw new ValidationException("Workflow is required");
        }

        if (workflowDetail.getCurrentStage() == null || workflowDetail.getCurrentStage().trim().isEmpty()) {
            throw new ValidationException("Current stage is required");
        }

        if (workflowDetail.getCurrentStage().length() > 36) {
            throw new ValidationException("Current stage cannot exceed 36 characters");
        }

        if (workflowDetail.getNextStage() != null && workflowDetail.getNextStage().length() > 36) {
            throw new ValidationException("Next stage cannot exceed 36 characters");
        }

        if (workflowDetail.getReturnStage() != null && workflowDetail.getReturnStage().length() > 36) {
            throw new ValidationException("Return stage cannot exceed 36 characters");
        }

        if (workflowDetail.getRejectStage() != null && workflowDetail.getRejectStage().length() > 36) {
            throw new ValidationException("Reject stage cannot exceed 36 characters");
        }

        if (workflowDetail.getSla() != null && workflowDetail.getSla() < 0) {
            throw new ValidationException("SLA cannot be negative");
        }
    }
}
