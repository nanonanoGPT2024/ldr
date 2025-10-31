package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderNotification;
import com.ldr.api.repository.OrderNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderNotificationService {

    private final OrderNotificationRepository orderNotificationRepository;

    @Autowired
    public OrderNotificationService(OrderNotificationRepository orderNotificationRepository) {
        this.orderNotificationRepository = orderNotificationRepository;
    }

    /**
     * Find all OrderNotification entities
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findAll() {
        return orderNotificationRepository.findAll();
    }

    /**
     * Find OrderNotification by ID
     * @param id the OrderNotification ID
     * @return OrderNotification
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderNotification findById(String id) {
        return orderNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderNotification not found with id: " + id));
    }

    /**
     * Save a new OrderNotification
     * @param orderNotification the OrderNotification to save
     * @return saved OrderNotification
     * @throws ValidationException if validation fails
     */
    public OrderNotification save(OrderNotification orderNotification) {
        validateOrderNotification(orderNotification);

        try {
            return orderNotificationRepository.save(orderNotification);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderNotification due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderNotification
     * @param id the OrderNotification ID
     * @param orderNotification the updated OrderNotification
     * @return updated OrderNotification
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderNotification update(String id, OrderNotification orderNotification) {
        OrderNotification existingOrderNotification = findById(id);

        validateOrderNotification(orderNotification);

        // Update fields
        existingOrderNotification.setOrder(orderNotification.getOrder());
        existingOrderNotification.setUser(orderNotification.getUser());
        existingOrderNotification.setNotificationType(orderNotification.getNotificationType());
        existingOrderNotification.setTitle(orderNotification.getTitle());
        existingOrderNotification.setMessage(orderNotification.getMessage());
        existingOrderNotification.setRead(orderNotification.isRead());
        existingOrderNotification.setReadAt(orderNotification.getReadAt());
        existingOrderNotification.setSentViaEmail(orderNotification.isSentViaEmail());
        existingOrderNotification.setEmailSentAt(orderNotification.getEmailSentAt());
        existingOrderNotification.setCreatedAt(orderNotification.getCreatedAt());

        try {
            return orderNotificationRepository.save(existingOrderNotification);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderNotification due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderNotification by ID
     * @param id the OrderNotification ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderNotification orderNotification = findById(id);
        try {
            orderNotificationRepository.delete(orderNotification);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderNotification as it is referenced by other records: " + id);
        }
    }

    /**
     * Find OrderNotification by order ID
     * @param orderId the order ID
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findByOrderId(String orderId) {
        return orderNotificationRepository.findByOrder_Id(orderId);
    }

    /**
     * Find OrderNotification by user ID
     * @param userId the user ID
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findByUserId(String userId) {
        return orderNotificationRepository.findByUser_Id(userId);
    }

    /**
     * Find OrderNotification by notification type
     * @param notificationType the notification type
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findByNotificationType(OrderNotification.NotificationType notificationType) {
        return orderNotificationRepository.findByNotificationType(notificationType);
    }

    /**
     * Find OrderNotification by read status
     * @param isRead the read status
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findByIsRead(boolean isRead) {
        return orderNotificationRepository.findByIsRead(isRead);
    }

    /**
     * Find OrderNotification by created at between
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderNotification>
     */
    @Transactional(readOnly = true)
    public List<OrderNotification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderNotificationRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Validate OrderNotification entity
     * @param orderNotification the OrderNotification to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderNotification(OrderNotification orderNotification) {
        if (orderNotification == null) {
            throw new ValidationException("OrderNotification cannot be null");
        }

        if (orderNotification.getId() == null || orderNotification.getId().trim().isEmpty()) {
            throw new ValidationException("ID is required");
        }

        if (orderNotification.getId().length() > 36) {
            throw new ValidationException("ID cannot exceed 36 characters");
        }

        if (orderNotification.getUser() == null) {
            throw new ValidationException("User is required");
        }

        if (orderNotification.getNotificationType() == null) {
            throw new ValidationException("Notification type is required");
        }

        if (orderNotification.getTitle() == null || orderNotification.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title is required");
        }

        if (orderNotification.getTitle().length() > 255) {
            throw new ValidationException("Title cannot exceed 255 characters");
        }

        if (orderNotification.getMessage() == null || orderNotification.getMessage().trim().isEmpty()) {
            throw new ValidationException("Message is required");
        }
    }
}
