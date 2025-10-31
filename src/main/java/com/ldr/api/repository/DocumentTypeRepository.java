package com.ldr.api.repository;

import com.ldr.api.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {

    /**
     * Find all DocumentType by active status
     * @param isActive the active status
     * @return List<DocumentType>
     */
    List<DocumentType> findByIsActive(boolean isActive);
}
