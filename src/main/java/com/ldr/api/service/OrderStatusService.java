package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderStatus;
import com.ldr.api.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    /**
     * Find all OrderStatus entities
     * @return List<OrderStatus>
     */
    @Transactional(readOnly = true)
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }

    /**
     * Find OrderStatus by ID
     * @param id the OrderStatus ID
     * @return OrderStatus
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderStatus findById(String id) {
        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus not found with id: " + id));
    }

    /**
     * Save a new OrderStatus
     * @param orderStatus the OrderStatus to save
     * @return saved OrderStatus
     * @throws ValidationException if validation fails
     */
    public OrderStatus save(OrderStatus orderStatus) {
        validateOrderStatus(orderStatus);

        // Check for duplicate code
        if (orderStatusRepository.findByCode(orderStatus.getCode()).isPresent()) {
            throw new ValidationException("OrderStatus code already exists: " + orderStatus.getCode());
        }

        try {
            return orderStatusRepository.save(orderStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderStatus due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderStatus
     * @param id the OrderStatus ID
     * @param orderStatus the updated OrderStatus
     * @return updated OrderStatus
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderStatus update(String id, OrderStatus orderStatus) {
        OrderStatus existingOrderStatus = findById(id);

        validateOrderStatus(orderStatus);

        // Check for duplicate code (excluding current record)
        if (orderStatusRepository.existsByCodeAndIdNot(orderStatus.getCode(), id)) {
            throw new ValidationException("OrderStatus code already exists: " + orderStatus.getCode());
        }

        // Update fields
        existingOrderStatus.setCode(orderStatus.getCode());
        existingOrderStatus.setName(orderStatus.getName());
        existingOrderStatus.setDescription(orderStatus.getDescription());
        existingOrderStatus.setColorCode(orderStatus.getColorCode());
        existingOrderStatus.setActive(orderStatus.isActive());
        existingOrderStatus.setSequenceOrder(orderStatus.getSequenceOrder());

        try {
            return orderStatusRepository.save(existingOrderStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderStatus due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderStatus by ID
     * @param id the OrderStatus ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderStatus orderStatus = findById(id);
        try {
            orderStatusRepository.delete(orderStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderStatus as it is referenced by other records: " + id);
        }
    }

    /**
     * Find OrderStatus by code
     * @param code the unique code
     * @return OrderStatus
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderStatus findByCode(String code) {
        return orderStatusRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus not found with code: " + code));
    }

    /**
     * Find all active OrderStatus
     * @return List<OrderStatus>
     */
    @Transactional(readOnly = true)
    public List<OrderStatus> findByIsActive() {
        return orderStatusRepository.findByIsActive(true);
    }

    /**
     * Validate OrderStatus entity
     * @param orderStatus the OrderStatus to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new ValidationException("OrderStatus cannot be null");
        }

        if (orderStatus.getCode() == null || orderStatus.getCode().trim().isEmpty()) {
            throw new ValidationException("OrderStatus code is required");
        }

        if (orderStatus.getCode().length() > 20) {
            throw new ValidationException("OrderStatus code cannot exceed 20 characters");
        }

        if (orderStatus.getName() == null || orderStatus.getName().trim().isEmpty()) {
            throw new ValidationException("OrderStatus name is required");
        }

        if (orderStatus.getName().length() > 100) {
            throw new ValidationException("OrderStatus name cannot exceed 100 characters");
        }

        if (orderStatus.getSequenceOrder() < 0) {
            throw new ValidationException("OrderStatus sequence order cannot be negative");
        }

        if (orderStatus.getColorCode() != null && !orderStatus.getColorCode().matches("^#[0-9A-Fa-f]{6}$")) {
            throw new ValidationException("OrderStatus color code must be a valid hex color (e.g., #FF0000)");
        }
    }
}
