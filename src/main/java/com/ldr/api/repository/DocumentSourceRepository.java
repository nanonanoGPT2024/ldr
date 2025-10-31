package com.ldr.api.repository;

import com.ldr.api.model.DocumentSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentSourceRepository extends JpaRepository<DocumentSource, String> {

    /**
     * Find all DocumentSource by active status
     * @param isActive the active status
     * @return List<DocumentSource>
     */
    List<DocumentSource> findByIsActive(boolean isActive);
}
