package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.OrderStatus;
import com.ldr.api.model.OrderStatusHistory;
import com.ldr.api.model.User;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.OrderStatusHistoryRepository;
import com.ldr.api.repository.OrderStatusRepository;
import com.ldr.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderStatusHistoryService {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderDataRepository orderDataRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderStatusHistoryService(OrderStatusHistoryRepository orderStatusHistoryRepository,
                                     OrderDataRepository orderDataRepository,
                                     OrderStatusRepository orderStatusRepository,
                                     UserRepository userRepository) {
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderDataRepository = orderDataRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderStatusHistory entities
     * @return List<OrderStatusHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderStatusHistory> findAll() {
        return orderStatusHistoryRepository.findAll();
    }

    /**
     * Find OrderStatusHistory by ID
     * @param id the OrderStatusHistory ID
     * @return OrderStatusHistory
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderStatusHistory findById(String id) {
        return orderStatusHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatusHistory not found with id: " + id));
    }

    /**
     * Save a new OrderStatusHistory
     * @param orderStatusHistory the OrderStatusHistory to save
     * @return saved OrderStatusHistory
     * @throws ValidationException if validation fails
     */
    public OrderStatusHistory save(OrderStatusHistory orderStatusHistory) {
        validateOrderStatusHistory(orderStatusHistory);

        try {
            return orderStatusHistoryRepository.save(orderStatusHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderStatusHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderStatusHistory
     * @param id the OrderStatusHistory ID
     * @param orderStatusHistory the updated OrderStatusHistory
     * @return updated OrderStatusHistory
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderStatusHistory update(String id, OrderStatusHistory orderStatusHistory) {
        OrderStatusHistory existingOrderStatusHistory = findById(id);

        validateOrderStatusHistory(orderStatusHistory);

        // Update fields
        existingOrderStatusHistory.setOrder(orderStatusHistory.getOrder());
        existingOrderStatusHistory.setFromStatus(orderStatusHistory.getFromStatus());
        existingOrderStatusHistory.setToStatus(orderStatusHistory.getToStatus());
        existingOrderStatusHistory.setChangedBy(orderStatusHistory.getChangedBy());
        existingOrderStatusHistory.setChangeReason(orderStatusHistory.getChangeReason());
        // Note: changedAt is automatically set by @CreatedDate annotation

        try {
            return orderStatusHistoryRepository.save(existingOrderStatusHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderStatusHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderStatusHistory by ID
     * @param id the OrderStatusHistory ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderStatusHistory orderStatusHistory = findById(id);
        try {
            orderStatusHistoryRepository.delete(orderStatusHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderStatusHistory as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all OrderStatusHistory by order ID
     * @param orderId the order ID
     * @return List<OrderStatusHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderStatusHistory> findByOrderId(String orderId) {
        return orderStatusHistoryRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderStatusHistory by changed by user ID
     * @param changedBy the user ID who changed the status
     * @return List<OrderStatusHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderStatusHistory> findByChangedBy(String changedBy) {
        return orderStatusHistoryRepository.findByChangedById(changedBy);
    }

    /**
     * Find all OrderStatusHistory by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderStatusHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderStatusHistory> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderStatusHistoryRepository.findByChangedAtBetween(startDate, endDate);
    }

    /**
     * Validate OrderStatusHistory entity
     * @param orderStatusHistory the OrderStatusHistory to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderStatusHistory(OrderStatusHistory orderStatusHistory) {
        if (orderStatusHistory == null) {
            throw new ValidationException("OrderStatusHistory cannot be null");
        }

        if (orderStatusHistory.getId() == null || orderStatusHistory.getId().trim().isEmpty()) {
            throw new ValidationException("OrderStatusHistory ID is required");
        }

        if (orderStatusHistory.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderStatusHistory.getOrder().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderStatusHistory.getOrder().getId()));

        if (orderStatusHistory.getToStatus() == null) {
            throw new ValidationException("To status is required");
        }

        // Validate toStatus exists
        OrderStatus toStatus = orderStatusRepository.findById(orderStatusHistory.getToStatus().getId())
                .orElseThrow(() -> new ValidationException("To status not found with id: " + orderStatusHistory.getToStatus().getId()));

        if (orderStatusHistory.getChangedBy() == null) {
            throw new ValidationException("Changed by user is required");
        }

        // Validate changedBy exists
        User changedBy = userRepository.findById(orderStatusHistory.getChangedBy().getId())
                .orElseThrow(() -> new ValidationException("Changed by user not found with id: " + orderStatusHistory.getChangedBy().getId()));

        // Validate fromStatus exists if provided
        if (orderStatusHistory.getFromStatus() != null) {
            OrderStatus fromStatus = orderStatusRepository.findById(orderStatusHistory.getFromStatus().getId())
                    .orElseThrow(() -> new ValidationException("From status not found with id: " + orderStatusHistory.getFromStatus().getId()));
        }

        if (orderStatusHistory.getChangeReason() != null && orderStatusHistory.getChangeReason().length() > 1000) {
            throw new ValidationException("Change reason cannot exceed 1000 characters");
        }
    }
}
