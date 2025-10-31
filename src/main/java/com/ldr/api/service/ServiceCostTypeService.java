package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.ServiceCostType;
import com.ldr.api.repository.ServiceCostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
     * Find all ServiceCostType entities
     * @return List<ServiceCostType>
     */
    @Transactional(readOnly = true)
    public List<ServiceCostType> findAll() {
        return serviceCostTypeRepository.findAll();
    }

    /**
     * Find ServiceCostType by ID
     * @param id the ServiceCostType ID
     * @return ServiceCostType
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public ServiceCostType findById(String id) {
        return serviceCostTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceCostType not found with id: " + id));
    }

    /**
     * Save a new ServiceCostType
     * @param serviceCostType the ServiceCostType to save
     * @return saved ServiceCostType
     * @throws ValidationException if validation fails
     */
    public ServiceCostType save(ServiceCostType serviceCostType) {
        validateServiceCostType(serviceCostType);

        try {
            return serviceCostTypeRepository.save(serviceCostType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save ServiceCostType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing ServiceCostType
     * @param id the ServiceCostType ID
     * @param serviceCostType the updated ServiceCostType
     * @return updated ServiceCostType
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public ServiceCostType update(String id, ServiceCostType serviceCostType) {
        ServiceCostType existingServiceCostType = findById(id);

        validateServiceCostType(serviceCostType);

        // Update fields
        existingServiceCostType.setName(serviceCostType.getName());
        existingServiceCostType.setDescription(serviceCostType.getDescription());
        existingServiceCostType.setActive(serviceCostType.isActive());

        try {
            return serviceCostTypeRepository.save(existingServiceCostType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update ServiceCostType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete ServiceCostType by ID
     * @param id the ServiceCostType ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        ServiceCostType serviceCostType = findById(id);
        try {
            serviceCostTypeRepository.delete(serviceCostType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete ServiceCostType as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active ServiceCostType
     * @return List<ServiceCostType>
     */
    @Transactional(readOnly = true)
    public List<ServiceCostType> findByIsActive() {
        return serviceCostTypeRepository.findByIsActive(true);
    }

    /**
     * Validate ServiceCostType entity
     * @param serviceCostType the ServiceCostType to validate
     * @throws ValidationException if validation fails
     */
    private void validateServiceCostType(ServiceCostType serviceCostType) {
        if (serviceCostType == null) {
            throw new ValidationException("ServiceCostType cannot be null");
        }

        if (serviceCostType.getName() == null || serviceCostType.getName().trim().isEmpty()) {
            throw new ValidationException("ServiceCostType name is required");
        }

        if (serviceCostType.getName().length() > 100) {
            throw new ValidationException("ServiceCostType name cannot exceed 100 characters");
        }
    }
}
