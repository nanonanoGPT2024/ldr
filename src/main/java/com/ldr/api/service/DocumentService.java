package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.Document;
import com.ldr.api.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Find all Document entities
     * @return List<Document>
     */
    @Transactional(readOnly = true)
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    /**
     * Find Document by ID
     * @param id the Document ID
     * @return Document
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Document findById(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
    }

    /**
     * Save a new Document
     * @param document the Document to save
     * @return saved Document
     * @throws ValidationException if validation fails
     */
    public Document save(Document document) {
        validateDocument(document);

        try {
            return documentRepository.save(document);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save Document due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing Document
     * @param id the Document ID
     * @param document the updated Document
     * @return updated Document
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public Document update(String id, Document document) {
        Document existingDocument = findById(id);

        validateDocument(document);

        // Update fields
        existingDocument.setDocumentSource(document.getDocumentSource());
        existingDocument.setName(document.getName());
        existingDocument.setDeskripsi(document.getDeskripsi());
        existingDocument.setActive(document.isActive());

        try {
            return documentRepository.save(existingDocument);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update Document due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete Document by ID
     * @param id the Document ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        Document document = findById(id);
        try {
            documentRepository.delete(document);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete Document as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all active Document
     * @return List<Document>
     */
    @Transactional(readOnly = true)
    public List<Document> findByIsActive() {
        return documentRepository.findByIsActive(true);
    }

    /**
     * Find all Document by document source ID
     * @param documentSourceId the document source ID
     * @return List<Document>
     */
    @Transactional(readOnly = true)
    public List<Document> findByDocumentSourceId(String documentSourceId) {
        return documentRepository.findByDocumentSourceId(documentSourceId);
    }

    /**
     * Validate Document entity
     * @param document the Document to validate
     * @throws ValidationException if validation fails
     */
    private void validateDocument(Document document) {
        if (document == null) {
            throw new ValidationException("Document cannot be null");
        }

        if (document.getDocumentSource() == null) {
            throw new ValidationException("Document source is required");
        }

        if (document.getName() == null || document.getName().trim().isEmpty()) {
            throw new ValidationException("Document name is required");
        }

        if (document.getName().length() > 255) {
            throw new ValidationException("Document name cannot exceed 255 characters");
        }
    }
}
