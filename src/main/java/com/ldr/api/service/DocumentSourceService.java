package com.ldr.api.service;

import com.ldr.api.model.DocumentSource;
import com.ldr.api.repository.DocumentSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Find all active DocumentSource
     * 
     * @return List<DocumentSource>
     */
    @Transactional(readOnly = true)
    public List<DocumentSource> findByIsActive() {
        return documentSourceRepository.findByIsActive(true);
    }
}
