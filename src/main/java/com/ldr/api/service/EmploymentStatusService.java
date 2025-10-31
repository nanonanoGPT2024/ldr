package com.ldr.api.service;

import com.ldr.api.model.EmploymentStatus;
import com.ldr.api.repository.EmploymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Find all active EmploymentStatus
     * 
     * @return List<EmploymentStatus>
     */
    @Transactional(readOnly = true)
    public List<EmploymentStatus> findByIsActive() {
        return employmentStatusRepository.findByIsActive(true);
    }
}
