package com.ldr.api.service;

import com.ldr.api.model.DocumentType;
import com.ldr.api.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Find all active DocumentType
     * 
     * @return List<DocumentType>
     */
    @Transactional(readOnly = true)
    public List<DocumentType> findByIsActive() {
        return documentTypeRepository.findByIsActive(true);
    }
}
