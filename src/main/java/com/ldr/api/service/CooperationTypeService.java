package com.ldr.api.service;

import com.ldr.api.model.CooperationType;
import com.ldr.api.repository.CooperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Find all active CooperationType
     * 
     * @return List<CooperationType>
     */
    @Transactional(readOnly = true)
    public List<CooperationType> findByIsActive() {
        return cooperationTypeRepository.findByIsActive(true);
    }

}
