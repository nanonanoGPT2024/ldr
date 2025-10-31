package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderAttachment;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.model.Document;
import com.ldr.api.repository.OrderAttachmentRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.UserRepository;
import com.ldr.api.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderAttachmentService {

    private final OrderAttachmentRepository orderAttachmentRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public OrderAttachmentService(OrderAttachmentRepository orderAttachmentRepository,
                                  OrderDataRepository orderDataRepository,
                                  UserRepository userRepository,
                                  DocumentRepository documentRepository) {
        this.orderAttachmentRepository = orderAttachmentRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Find all OrderAttachment entities
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findAll() {
        return orderAttachmentRepository.findAll();
    }

    /**
     * Find OrderAttachment by ID
     * @param id the OrderAttachment ID
     * @return OrderAttachment
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderAttachment findById(String id) {
        return orderAttachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderAttachment not found with id: " + id));
    }

    /**
     * Save a new OrderAttachment
     * @param orderAttachment the OrderAttachment to save
     * @return saved OrderAttachment
     * @throws ValidationException if validation fails
     */
    public OrderAttachment save(OrderAttachment orderAttachment) {
        validateOrderAttachment(orderAttachment);

        try {
            return orderAttachmentRepository.save(orderAttachment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderAttachment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderAttachment
     * @param id the OrderAttachment ID
     * @param orderAttachment the updated OrderAttachment
     * @return updated OrderAttachment
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderAttachment update(String id, OrderAttachment orderAttachment) {
        OrderAttachment existingOrderAttachment = findById(id);

        validateOrderAttachment(orderAttachment);

        // Update fields
        existingOrderAttachment.setOrder(orderAttachment.getOrder());
        existingOrderAttachment.setDocument(orderAttachment.getDocument());
        existingOrderAttachment.setKeterangan(orderAttachment.getKeterangan());
        existingOrderAttachment.setFileName(orderAttachment.getFileName());
        existingOrderAttachment.setOriginalFileName(orderAttachment.getOriginalFileName());
        existingOrderAttachment.setFilePath(orderAttachment.getFilePath());
        existingOrderAttachment.setFileSize(orderAttachment.getFileSize());
        existingOrderAttachment.setFileType(orderAttachment.getFileType());
        existingOrderAttachment.setMimeType(orderAttachment.getMimeType());
        existingOrderAttachment.setUploadedBy(orderAttachment.getUploadedBy());
        existingOrderAttachment.setActive(orderAttachment.isActive());

        try {
            return orderAttachmentRepository.save(existingOrderAttachment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderAttachment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderAttachment by ID (soft delete)
     * @param id the OrderAttachment ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderAttachment orderAttachment = findById(id);
        orderAttachment.setDeleted(true);
        orderAttachment.setDeletedAt(LocalDateTime.now());
        orderAttachmentRepository.save(orderAttachment);
    }

    /**
     * Find all OrderAttachment by order ID
     * @param orderId the order ID
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByOrderId(String orderId) {
        return orderAttachmentRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderAttachment by document ID
     * @param documentId the document ID
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByDocumentId(String documentId) {
        return orderAttachmentRepository.findByDocumentId(documentId);
    }

    /**
     * Find all OrderAttachment by uploaded by user ID
     * @param uploadedBy the user ID who uploaded
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByUploadedBy(String uploadedBy) {
        return orderAttachmentRepository.findByUploadedById(uploadedBy);
    }

    /**
     * Find all OrderAttachment by created at between start and end date
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderAttachmentRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Find all OrderAttachment by is active
     * @param isActive the active flag
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByIsActive(boolean isActive) {
        return orderAttachmentRepository.findByIsActive(isActive);
    }

    /**
     * Find all OrderAttachment by is deleted
     * @param isDeleted the deleted flag
     * @return List<OrderAttachment>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachment> findByIsDeleted(boolean isDeleted) {
        return orderAttachmentRepository.findByIsDeleted(isDeleted);
    }

    /**
     * Validate OrderAttachment entity
     * @param orderAttachment the OrderAttachment to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderAttachment(OrderAttachment orderAttachment) {
        if (orderAttachment == null) {
            throw new ValidationException("OrderAttachment cannot be null");
        }

        if (orderAttachment.getId() == null || orderAttachment.getId().trim().isEmpty()) {
            throw new ValidationException("OrderAttachment ID is required");
        }

        if (orderAttachment.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderAttachment.getOrder().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderAttachment.getOrder().getId()));

        if (orderAttachment.getUploadedBy() == null) {
            throw new ValidationException("Uploaded by user is required");
        }

        // Validate user exists
        User user = userRepository.findById(orderAttachment.getUploadedBy().getId())
                .orElseThrow(() -> new ValidationException("User not found with id: " + orderAttachment.getUploadedBy().getId()));

        if (orderAttachment.getFileName() == null || orderAttachment.getFileName().trim().isEmpty()) {
            throw new ValidationException("File name is required");
        }

        if (orderAttachment.getFileName().length() > 255) {
            throw new ValidationException("File name cannot exceed 255 characters");
        }

        if (orderAttachment.getOriginalFileName() == null || orderAttachment.getOriginalFileName().trim().isEmpty()) {
            throw new ValidationException("Original file name is required");
        }

        if (orderAttachment.getOriginalFileName().length() > 255) {
            throw new ValidationException("Original file name cannot exceed 255 characters");
        }

        if (orderAttachment.getFilePath() == null || orderAttachment.getFilePath().trim().isEmpty()) {
            throw new ValidationException("File path is required");
        }

        if (orderAttachment.getFilePath().length() > 500) {
            throw new ValidationException("File path cannot exceed 500 characters");
        }

        if (orderAttachment.getFileType() != null && orderAttachment.getFileType().length() > 100) {
            throw new ValidationException("File type cannot exceed 100 characters");
        }

        if (orderAttachment.getMimeType() != null && orderAttachment.getMimeType().length() > 100) {
            throw new ValidationException("Mime type cannot exceed 100 characters");
        }

        if (orderAttachment.getKeterangan() != null && orderAttachment.getKeterangan().length() > 1000) {
            throw new ValidationException("Keterangan cannot exceed 1000 characters");
        }

        // Validate document if provided
        if (orderAttachment.getDocument() != null) {
            Document document = documentRepository.findById(orderAttachment.getDocument().getId())
                    .orElseThrow(() -> new ValidationException("Document not found with id: " + orderAttachment.getDocument().getId()));
        }
    }
}
