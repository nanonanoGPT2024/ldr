package com.ldr.api.service;

import com.ldr.api.model.Priority;
import com.ldr.api.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    /**
     * Find all active Priority
     * 
     * @return List<Priority>
     */
    @Transactional(readOnly = true)
    public List<Priority> findByIsActive() {
        return priorityRepository.findByIsActive(true);
    }
}
