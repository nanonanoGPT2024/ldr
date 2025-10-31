package com.ldr.api.repository;

import com.ldr.api.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    /**
     * Find all Document by active status
     * @param isActive the active status
     * @return List<Document>
     */
    List<Document> findByIsActive(boolean isActive);

    /**
     * Find all Document by document source ID
     * @param documentSourceId the document source ID
     * @return List<Document>
     */
    List<Document> findByDocumentSourceId(String documentSourceId);
}
