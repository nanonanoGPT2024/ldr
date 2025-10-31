package com.ldr.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderAssignmentHistory;
import com.ldr.api.model.OrderData;
import com.ldr.api.repository.OrderAssignmentHistoryRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.UserRepository;

@Service
@Transactional
public class OrderAssignmentHistoryService {

    private final OrderAssignmentHistoryRepository orderAssignmentHistoryRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderAssignmentHistoryService(OrderAssignmentHistoryRepository orderAssignmentHistoryRepository,
            OrderDataRepository orderDataRepository,
            UserRepository userRepository) {
        this.orderAssignmentHistoryRepository = orderAssignmentHistoryRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderAssignmentHistory entities
     * 
     * @return List<OrderAssignmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAssignmentHistory> findAll() {
        return orderAssignmentHistoryRepository.findAll();
    }

    /**
     * Find OrderAssignmentHistory by ID
     * 
     * @param id the OrderAssignmentHistory ID
     * @return OrderAssignmentHistory
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderAssignmentHistory findById(String id) {
        return orderAssignmentHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderAssignmentHistory not found with id: " + id));
    }

    /**
     * Save a new OrderAssignmentHistory
     * 
     * @param orderAssignmentHistory the OrderAssignmentHistory to save
     * @return saved OrderAssignmentHistory
     * @throws ValidationException if validation fails
     */
    public OrderAssignmentHistory save(OrderAssignmentHistory orderAssignmentHistory) {
        validateOrderAssignmentHistory(orderAssignmentHistory);

        try {
            return orderAssignmentHistoryRepository.save(orderAssignmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to save OrderAssignmentHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderAssignmentHistory
     * 
     * @param id                     the OrderAssignmentHistory ID
     * @param orderAssignmentHistory the updated OrderAssignmentHistory
     * @return updated OrderAssignmentHistory
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException       if validation fails
     */
    public OrderAssignmentHistory update(String id, OrderAssignmentHistory orderAssignmentHistory) {
        OrderAssignmentHistory existingOrderAssignmentHistory = findById(id);

        validateOrderAssignmentHistory(orderAssignmentHistory);

        // Update fields
        existingOrderAssignmentHistory.setOrder(orderAssignmentHistory.getOrder());
        existingOrderAssignmentHistory.setAssignedToRole(orderAssignmentHistory.getAssignedToRole());
        existingOrderAssignmentHistory.setAssignedByRole(orderAssignmentHistory.getAssignedByRole());
        existingOrderAssignmentHistory.setAssignmentType(orderAssignmentHistory.getAssignmentType());
        existingOrderAssignmentHistory.setNotes(orderAssignmentHistory.getNotes());
        // Note: assignedAt is automatically set by @CreatedDate annotation

        try {
            return orderAssignmentHistoryRepository.save(existingOrderAssignmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to update OrderAssignmentHistory due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderAssignmentHistory by ID
     * 
     * @param id the OrderAssignmentHistory ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderAssignmentHistory orderAssignmentHistory = findById(id);
        try {
            orderAssignmentHistoryRepository.delete(orderAssignmentHistory);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Cannot delete OrderAssignmentHistory as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all OrderAssignmentHistory by order ID
     * 
     * @param orderId the order ID
     * @return List<OrderAssignmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAssignmentHistory> findByOrderId(String orderId) {
        return orderAssignmentHistoryRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderAssignmentHistory by assigned to role
     *
     * @param assignedToRole the role who was assigned to
     * @return List<OrderAssignmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAssignmentHistory> findByAssignedToRole(String assignedToRole) {
        return orderAssignmentHistoryRepository.findByAssignedToRole(assignedToRole);
    }

    /**
     * Find all OrderAssignmentHistory by assigned at between dates
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return List<OrderAssignmentHistory>
     */
    @Transactional(readOnly = true)
    public List<OrderAssignmentHistory> findByAssignedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderAssignmentHistoryRepository.findByAssignedAtBetween(startDate, endDate);
    }

    /**
     * Validate OrderAssignmentHistory entity
     * 
     * @param orderAssignmentHistory the OrderAssignmentHistory to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderAssignmentHistory(OrderAssignmentHistory orderAssignmentHistory) {
        if (orderAssignmentHistory == null) {
            throw new ValidationException("OrderAssignmentHistory cannot be null");
        }

        if (orderAssignmentHistory.getId() == null || orderAssignmentHistory.getId().trim().isEmpty()) {
            throw new ValidationException("OrderAssignmentHistory ID is required");
        }

        if (orderAssignmentHistory.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderAssignmentHistory.getOrder().getId())
                .orElseThrow(() -> new ValidationException(
                        "Order not found with id: " + orderAssignmentHistory.getOrder().getId()));

        if (orderAssignmentHistory.getAssignedToRole() == null
                || orderAssignmentHistory.getAssignedToRole().trim().isEmpty()) {
            throw new ValidationException("Assigned to role is required");
        }

        if (orderAssignmentHistory.getAssignedByRole() == null
                || orderAssignmentHistory.getAssignedByRole().trim().isEmpty()) {
            throw new ValidationException("Assigned by role is required");
        }

        if (orderAssignmentHistory.getAssignmentType() == null) {
            throw new ValidationException("Assignment type is required");
        }

        if (orderAssignmentHistory.getNotes() != null && orderAssignmentHistory.getNotes().length() > 1000) {
            throw new ValidationException("Notes cannot exceed 1000 characters");
        }
    }
}
