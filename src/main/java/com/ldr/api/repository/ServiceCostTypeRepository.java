package com.ldr.api.repository;

import com.ldr.api.model.ServiceCostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCostTypeRepository extends JpaRepository<ServiceCostType, String> {

    /**
     * Find all ServiceCostType by active status
     * @param isActive the active status
     * @return List<ServiceCostType>
     */
    List<ServiceCostType> findByIsActive(boolean isActive);
}
