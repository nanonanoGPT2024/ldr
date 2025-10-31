package com.ldr.api.repository;

import com.ldr.api.model.CooperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CooperationTypeRepository extends JpaRepository<CooperationType, String> {

    /**
     * Find all CooperationType by active status
     * @param isActive the active status
     * @return List<CooperationType>
     */
    List<CooperationType> findByIsActive(boolean isActive);
}
