package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.DocumentSource;
import com.ldr.api.repository.DocumentSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentSourceService {

    private final DocumentSourceRepository documentSourceRepository;

    @Autowired
    public DocumentSourceService(DocumentSourceRepository documentSourceRepository) {
        this.documentSourceRepository = documentSourceRepository;
    }

    /**
     * Find all DocumentSource entities
     * @return List<DocumentSource>
     */
    @Transactional(readOnly = true)
    public List<DocumentSource> findAll() {
        return documentSourceRepository.findAll();
    }

    /**
     * Find DocumentSource by ID
     * @param id the DocumentSource ID
     * @return DocumentSource
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public DocumentSource findById(String id) {
        return documentSourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentSource not found with id: " + id));
    }

    /**
     * Save a new DocumentSource
     * @param documentSource the DocumentSource to save
     * @return saved DocumentSource
     * @throws ValidationException if validation fails
     */
    public DocumentSource save(DocumentSource documentSource) {
        validateDocumentSource(documentSource);

        try {
            return documentSourceRepository.save(documentSource);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save DocumentSource due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing DocumentSource
     * @param id the DocumentSource ID
     * @param documentSource the updated DocumentSource
     * @return updated DocumentSource
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public DocumentSource update(String id, DocumentSource documentSource) {
        DocumentSource existingDocumentSource = findById(id);

        validateDocumentSource(documentSource);

        // Update fields
        existingDocumentSource.setName(documentSource.getName());
        existingDocumentSource.setDescription(documentSource.getDescription());
        existingDocumentSource.setActive(documentSource.isActive());

        try {
            return documentSourceRepository.save(existingDocumentSource);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update DocumentSource due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete DocumentSource by ID
     * @param id the DocumentSource ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        DocumentSource documentSource = findById(id);
        try {
            documentSourceRepository.delete(documentSource);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete DocumentSource as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active DocumentSource
     * @return List<DocumentSource>
     */
    @Transactional(readOnly = true)
    public List<DocumentSource> findByIsActive() {
        return documentSourceRepository.findByIsActive(true);
    }

    /**
     * Validate DocumentSource entity
     * @param documentSource the DocumentSource to validate
     * @throws ValidationException if validation fails
     */
    private void validateDocumentSource(DocumentSource documentSource) {
        if (documentSource == null) {
            throw new ValidationException("DocumentSource cannot be null");
        }

        if (documentSource.getName() == null || documentSource.getName().trim().isEmpty()) {
            throw new ValidationException("DocumentSource name is required");
        }

        if (documentSource.getName().length() > 100) {
            throw new ValidationException("DocumentSource name cannot exceed 100 characters");
        }
    }
}
