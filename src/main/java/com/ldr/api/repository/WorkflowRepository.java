package com.ldr.api.repository;

import com.ldr.api.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, String> {

    /**
     * Find all Workflow by active status
     * @param isActive the active status
     * @return List<Workflow>
     */
    List<Workflow> findByIsActive(boolean isActive);

    /**
     * Find Workflow by name
     * @param nama the workflow name
     * @return Workflow
     */
    Workflow findByNama(String nama);
}
