package com.ldr.api.service;

import com.ldr.api.model.ServiceCostType;
import com.ldr.api.repository.ServiceCostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceCostTypeService {

    private final ServiceCostTypeRepository serviceCostTypeRepository;

    @Autowired
    public ServiceCostTypeService(ServiceCostTypeRepository serviceCostTypeRepository) {
        this.serviceCostTypeRepository = serviceCostTypeRepository;
    }

    /**
     * Find all active ServiceCostType
     * 
     * @return List<ServiceCostType>
     */
    @Transactional(readOnly = true)
    public List<ServiceCostType> findByIsActive() {
        return serviceCostTypeRepository.findByIsActive(true);
    }
}
