package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.*;
import com.ldr.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderAttachmentHistoryService {

    private final OrderAttachmentHistoryRepository orderAttachmentHistoryRepository;
    private final OrderAttachmentRepository orderAttachmentRepository;
    private final OrderDataRepository orderDataRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderAttachmentHistoryService(OrderAttachmentHistoryRepository orderAttachmentHistoryRepository,
                                        OrderAttachmentRepository orderAttachmentRepository,
                                        OrderDataRepository orderDataRepository,
                                        DocumentRepository documentRepository,
                                        UserRepository userRepository) {
        this.orderAttachmentHistoryRepository = orderAttachmentHistoryRepository;
        this.orderAttachmentRepository = orderAttachmentRepository;
        this.orderDataRepository = orderDataRepository;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderAttachmentHistory entities
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findAll() {
        return orderAttachmentHistoryRepository.findAll();
    }

    /**
     * Find OrderAttachmentHistory by ID
     * @param id the OrderAttachmentHistory ID
     * @return OrderAttachmentHistory
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderAttachmentHistory findById(String id) {
        return orderAttachmentHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderAttachmentHistory not found with id: " + id));
    }

    /**
     * Save a new OrderAttachmentHistory
     * @param orderAttachmentHistory the OrderAttachmentHistory to save
     * @return saved OrderAttachmentHistory
     * @throws ValidationException if validation fails
     */
    public OrderAttachmentHistory save(OrderAttachmentHistory orderAttachmentHistory) {
        validateOrderAttachmentHistory(orderAttachmentHistory);

        try {
            return orderAttachmentHistoryRepository.save(orderAttachmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderAttachmentHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderAttachmentHistory
     * @param id the OrderAttachmentHistory ID
     * @param orderAttachmentHistory the updated OrderAttachmentHistory
     * @return updated OrderAttachmentHistory
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderAttachmentHistory update(String id, OrderAttachmentHistory orderAttachmentHistory) {
        OrderAttachmentHistory existingOrderAttachmentHistory = findById(id);

        validateOrderAttachmentHistory(orderAttachmentHistory);

        // Update fields
        existingOrderAttachmentHistory.setOrderAttachment(orderAttachmentHistory.getOrderAttachment());
        existingOrderAttachmentHistory.setOrder(orderAttachmentHistory.getOrder());
        existingOrderAttachmentHistory.setFileName(orderAttachmentHistory.getFileName());
        existingOrderAttachmentHistory.setOriginalFileName(orderAttachmentHistory.getOriginalFileName());
        existingOrderAttachmentHistory.setFilePath(orderAttachmentHistory.getFilePath());
        existingOrderAttachmentHistory.setFileSize(orderAttachmentHistory.getFileSize());
        existingOrderAttachmentHistory.setMimeType(orderAttachmentHistory.getMimeType());
        existingOrderAttachmentHistory.setDocument(orderAttachmentHistory.getDocument());
        existingOrderAttachmentHistory.setKeterangan(orderAttachmentHistory.getKeterangan());
        existingOrderAttachmentHistory.setVersionNumber(orderAttachmentHistory.getVersionNumber());
        existingOrderAttachmentHistory.setActive(orderAttachmentHistory.isActive());
        existingOrderAttachmentHistory.setUploadedBy(orderAttachmentHistory.getUploadedBy());
        existingOrderAttachmentHistory.setUploadedAt(orderAttachmentHistory.getUploadedAt());
        existingOrderAttachmentHistory.setChangeType(orderAttachmentHistory.getChangeType());
        existingOrderAttachmentHistory.setChangedBy(orderAttachmentHistory.getChangedBy());
        existingOrderAttachmentHistory.setChangedAt(orderAttachmentHistory.getChangedAt());
        existingOrderAttachmentHistory.setChangeReason(orderAttachmentHistory.getChangeReason());

        try {
            return orderAttachmentHistoryRepository.save(existingOrderAttachmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderAttachmentHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderAttachmentHistory by ID
     * @param id the OrderAttachmentHistory ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderAttachmentHistory orderAttachmentHistory = findById(id);
        try {
            orderAttachmentHistoryRepository.delete(orderAttachmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderAttachmentHistory as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all OrderAttachmentHistory by order attachment ID
     * @param orderAttachmentId the order attachment ID
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByOrderAttachmentId(String orderAttachmentId) {
        return orderAttachmentHistoryRepository.findByOrderAttachmentId(orderAttachmentId);
    }

    /**
     * Find all OrderAttachmentHistory by order ID
     * @param orderId the order ID
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByOrderId(String orderId) {
        return orderAttachmentHistoryRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderAttachmentHistory by document ID
     * @param documentId the document ID
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByDocumentId(String documentId) {
        return orderAttachmentHistoryRepository.findByDocumentId(documentId);
    }

    /**
     * Find all OrderAttachmentHistory by changed by user ID
     * @param changedBy the user ID who changed the attachment
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByChangedBy(String changedBy) {
        return orderAttachmentHistoryRepository.findByChangedById(changedBy);
    }

    /**
     * Find all OrderAttachmentHistory by change type
     * @param changeType the change type
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByChangeType(ChangeType changeType) {
        return orderAttachmentHistoryRepository.findByChangeType(changeType);
    }

    /**
     * Find all OrderAttachmentHistory by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAttachmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAttachmentHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderAttachmentHistoryRepository.findByChangedAtBetween(startDate, endDate);
    }

    /**
     * Validate OrderAttachmentHistory entity
     * @param orderAttachmentHistory the OrderAttachmentHistory to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderAttachmentHistory(OrderAttachmentHistory orderAttachmentHistory) {
        if (orderAttachmentHistory == null) {
            throw new ValidationException("OrderAttachmentHistory cannot be null");
        }

        if (orderAttachmentHistory.getId() == null || orderAttachmentHistory.getId().trim().isEmpty()) {
            throw new ValidationException("OrderAttachmentHistory ID is required");
        }

        if (orderAttachmentHistory.getOrderAttachment() == null) {
            throw new ValidationException("Order attachment is required");
        }

        // Validate order attachment exists
        OrderAttachment orderAttachment = orderAttachmentRepository.findById(orderAttachmentHistory.getOrderAttachment().getId())
                .orElseThrow(() -> new ValidationException("Order attachment not found with id: " + orderAttachmentHistory.getOrderAttachment().getId()));

        if (orderAttachmentHistory.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderAttachmentHistory.getOrder().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderAttachmentHistory.getOrder().getId()));

        if (orderAttachmentHistory.getFileName() == null || orderAttachmentHistory.getFileName().trim().isEmpty()) {
            throw new ValidationException("File name is required");
        }

        if (orderAttachmentHistory.getOriginalFileName() == null || orderAttachmentHistory.getOriginalFileName().trim().isEmpty()) {
            throw new ValidationException("Original file name is required");
        }

        if (orderAttachmentHistory.getFilePath() == null || orderAttachmentHistory.getFilePath().trim().isEmpty()) {
            throw new ValidationException("File path is required");
        }

        if (orderAttachmentHistory.getFileSize() == null || orderAttachmentHistory.getFileSize() <= 0) {
            throw new ValidationException("File size must be greater than 0");
        }

        if (orderAttachmentHistory.getMimeType() == null || orderAttachmentHistory.getMimeType().trim().isEmpty()) {
            throw new ValidationException("MIME type is required");
        }

        if (orderAttachmentHistory.getChangeType() == null) {
            throw new ValidationException("Change type is required");
        }

        if (orderAttachmentHistory.getChangedBy() == null) {
            throw new ValidationException("Changed by user is required");
        }

        // Validate changedBy exists
        User changedBy = userRepository.findById(orderAttachmentHistory.getChangedBy().getId())
                .orElseThrow(() -> new ValidationException("Changed by user not found with id: " + orderAttachmentHistory.getChangedBy().getId()));

        if (orderAttachmentHistory.getChangedAt() == null) {
            throw new ValidationException("Changed at timestamp is required");
        }

        // Validate document exists if provided
        if (orderAttachmentHistory.getDocument() != null) {
            Document document = documentRepository.findById(orderAttachmentHistory.getDocument().getId())
                    .orElseThrow(() -> new ValidationException("Document not found with id: " + orderAttachmentHistory.getDocument().getId()));
        }

        // Validate uploadedBy exists if provided
        if (orderAttachmentHistory.getUploadedBy() != null) {
            User uploadedBy = userRepository.findById(orderAttachmentHistory.getUploadedBy().getId())
                    .orElseThrow(() -> new ValidationException("Uploaded by user not found with id: " + orderAttachmentHistory.getUploadedBy().getId()));
        }

        if (orderAttachmentHistory.getKeterangan() != null && orderAttachmentHistory.getKeterangan().length() > 1000) {
            throw new ValidationException("Keterangan cannot exceed 1000 characters");
        }

        if (orderAttachmentHistory.getChangeReason() != null && orderAttachmentHistory.getChangeReason().length() > 1000) {
            throw new ValidationException("Change reason cannot exceed 1000 characters");
        }
    }
}
