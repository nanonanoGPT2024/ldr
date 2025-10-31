package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.DocumentType;
import com.ldr.api.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Autowired
    public DocumentTypeService(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    /**
     * Find all DocumentType entities
     * @return List<DocumentType>
     */
    @Transactional(readOnly = true)
    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    /**
     * Find DocumentType by ID
     * @param id the DocumentType ID
     * @return DocumentType
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public DocumentType findById(String id) {
        return documentTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentType not found with id: " + id));
    }

    /**
     * Save a new DocumentType
     * @param documentType the DocumentType to save
     * @return saved DocumentType
     * @throws ValidationException if validation fails
     */
    public DocumentType save(DocumentType documentType) {
        validateDocumentType(documentType);

        try {
            return documentTypeRepository.save(documentType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save DocumentType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing DocumentType
     * @param id the DocumentType ID
     * @param documentType the updated DocumentType
     * @return updated DocumentType
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public DocumentType update(String id, DocumentType documentType) {
        DocumentType existingDocumentType = findById(id);

        validateDocumentType(documentType);

        // Update fields
        existingDocumentType.setName(documentType.getName());
        existingDocumentType.setDescription(documentType.getDescription());
        existingDocumentType.setTemplatePath(documentType.getTemplatePath());
        existingDocumentType.setActive(documentType.isActive());

        try {
            return documentTypeRepository.save(existingDocumentType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update DocumentType due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete DocumentType by ID
     * @param id the DocumentType ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        DocumentType documentType = findById(id);
        try {
            documentTypeRepository.delete(documentType);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete DocumentType as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active DocumentType
     * @return List<DocumentType>
     */
    @Transactional(readOnly = true)
    public List<DocumentType> findByIsActive() {
        return documentTypeRepository.findByIsActive(true);
    }

    /**
     * Validate DocumentType entity
     * @param documentType the DocumentType to validate
     * @throws ValidationException if validation fails
     */
    private void validateDocumentType(DocumentType documentType) {
        if (documentType == null) {
            throw new ValidationException("DocumentType cannot be null");
        }

        if (documentType.getName() == null || documentType.getName().trim().isEmpty()) {
            throw new ValidationException("DocumentType name is required");
        }

        if (documentType.getName().length() > 255) {
            throw new ValidationException("DocumentType name cannot exceed 255 characters");
        }

        if (documentType.getTemplatePath() != null && documentType.getTemplatePath().length() > 255) {
            throw new ValidationException("DocumentType template path cannot exceed 255 characters");
        }
    }
}
