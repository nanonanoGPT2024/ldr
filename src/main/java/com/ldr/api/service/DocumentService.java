package com.ldr.api.service;

import com.ldr.api.model.Document;
import com.ldr.api.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Find all active Document
     * 
     * @return List<Document>
     */
    @Transactional(readOnly = true)
    public List<Document> findByIsActive() {
        return documentRepository.findByIsActive(true);
    }

    /**
     * Find all Document by document source ID
     * 
     * @param documentSourceId the document source ID
     * @return List<Document>
     */
    @Transactional(readOnly = true)
    public List<Document> findByDocumentSourceId(String documentSourceId) {
        return documentRepository.findByDocumentSourceId(documentSourceId);
    }
}
