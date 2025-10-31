package com.ldr.api.repository;

import com.ldr.api.model.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentStatusRepository extends JpaRepository<EmploymentStatus, String> {

    /**
     * Find all EmploymentStatus by active status
     * @param isActive the active status
     * @return List<EmploymentStatus>
     */
    List<EmploymentStatus> findByIsActive(boolean isActive);
}
