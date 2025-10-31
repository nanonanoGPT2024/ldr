package com.ldr.api.repository;

import com.ldr.api.model.WorkflowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowDetailRepository extends JpaRepository<WorkflowDetail, String> {

    /**
     * Find all WorkflowDetail by workflow ID
     * @param workflowId the workflow ID
     * @return List<WorkflowDetail>
     */
    List<WorkflowDetail> findByWorkflowId(String workflowId);

    /**
     * Find all WorkflowDetail by current stage
     * @param currentStage the current stage
     * @return List<WorkflowDetail>
     */
    List<WorkflowDetail> findByCurrentStage(String currentStage);

    /**
     * Find all WorkflowDetail by active status
     * @param isActive the active status
     * @return List<WorkflowDetail>
     */
    List<WorkflowDetail> findByIsActive(boolean isActive);
}
