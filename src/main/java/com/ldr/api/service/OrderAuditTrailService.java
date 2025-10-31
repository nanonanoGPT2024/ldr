package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderAuditTrail;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.repository.OrderAuditTrailRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderAuditTrailService {

    private final OrderAuditTrailRepository orderAuditTrailRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderAuditTrailService(OrderAuditTrailRepository orderAuditTrailRepository,
                                  OrderDataRepository orderDataRepository,
                                  UserRepository userRepository) {
        this.orderAuditTrailRepository = orderAuditTrailRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderAuditTrail entities
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findAll() {
        return orderAuditTrailRepository.findAll();
    }

    /**
     * Find OrderAuditTrail by ID
     * @param id the OrderAuditTrail ID
     * @return OrderAuditTrail
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderAuditTrail findById(String id) {
        return orderAuditTrailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderAuditTrail not found with id: " + id));
    }

    /**
     * Save a new OrderAuditTrail
     * @param orderAuditTrail the OrderAuditTrail to save
     * @return saved OrderAuditTrail
     * @throws ValidationException if validation fails
     */
    public OrderAuditTrail save(OrderAuditTrail orderAuditTrail) {
        validateOrderAuditTrail(orderAuditTrail);

        try {
            return orderAuditTrailRepository.save(orderAuditTrail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderAuditTrail due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderAuditTrail
     * @param id the OrderAuditTrail ID
     * @param orderAuditTrail the updated OrderAuditTrail
     * @return updated OrderAuditTrail
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderAuditTrail update(String id, OrderAuditTrail orderAuditTrail) {
        OrderAuditTrail existingOrderAuditTrail = findById(id);

        validateOrderAuditTrail(orderAuditTrail);

        // Update fields
        existingOrderAuditTrail.setOrderData(orderAuditTrail.getOrderData());
        existingOrderAuditTrail.setTableName(orderAuditTrail.getTableName());
        existingOrderAuditTrail.setOperationType(orderAuditTrail.getOperationType());
        existingOrderAuditTrail.setColumnName(orderAuditTrail.getColumnName());
        existingOrderAuditTrail.setOldValue(orderAuditTrail.getOldValue());
        existingOrderAuditTrail.setNewValue(orderAuditTrail.getNewValue());
        existingOrderAuditTrail.setChangedBy(orderAuditTrail.getChangedBy());
        existingOrderAuditTrail.setChangedAt(orderAuditTrail.getChangedAt());
        existingOrderAuditTrail.setIpAddress(orderAuditTrail.getIpAddress());
        existingOrderAuditTrail.setUserAgent(orderAuditTrail.getUserAgent());

        try {
            return orderAuditTrailRepository.save(existingOrderAuditTrail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderAuditTrail due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderAuditTrail by ID
     * @param id the OrderAuditTrail ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderAuditTrail orderAuditTrail = findById(id);
        try {
            orderAuditTrailRepository.delete(orderAuditTrail);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderAuditTrail as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all OrderAuditTrail by order ID
     * @param orderId the order ID
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findByOrderId(String orderId) {
        return orderAuditTrailRepository.findByOrderDataId(orderId);
    }

    /**
     * Find all OrderAuditTrail by table name
     * @param tableName the table name
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findByTableName(String tableName) {
        return orderAuditTrailRepository.findByTableName(tableName);
    }

    /**
     * Find all OrderAuditTrail by operation type
     * @param operationType the operation type
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findByOperationType(String operationType) {
        try {
            OrderAuditTrail.OperationType opType = OrderAuditTrail.OperationType.valueOf(operationType.toUpperCase());
            return orderAuditTrailRepository.findByOperationType(opType);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid operation type: " + operationType);
        }
    }

    /**
     * Find all OrderAuditTrail by changed by user ID
     * @param changedBy the user ID who changed the data
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findByChangedBy(String changedBy) {
        return orderAuditTrailRepository.findByChangedById(changedBy);
    }

    /**
     * Find all OrderAuditTrail by changed at between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderAuditTrail>
     */
    @Transactional(readOnly = true)
    public List<OrderAuditTrail> findByChangedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderAuditTrailRepository.findByChangedAtBetween(startDate, endDate);
    }

    /**
     * Validate OrderAuditTrail entity
     * @param orderAuditTrail the OrderAuditTrail to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderAuditTrail(OrderAuditTrail orderAuditTrail) {
        if (orderAuditTrail == null) {
            throw new ValidationException("OrderAuditTrail cannot be null");
        }

        if (orderAuditTrail.getId() == null || orderAuditTrail.getId().trim().isEmpty()) {
            throw new ValidationException("OrderAuditTrail ID is required");
        }

        if (orderAuditTrail.getOrderData() == null) {
            throw new ValidationException("Order data is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderAuditTrail.getOrderData().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderAuditTrail.getOrderData().getId()));

        if (orderAuditTrail.getTableName() == null || orderAuditTrail.getTableName().trim().isEmpty()) {
            throw new ValidationException("Table name is required");
        }

        if (orderAuditTrail.getTableName().length() > 100) {
            throw new ValidationException("Table name cannot exceed 100 characters");
        }

        if (orderAuditTrail.getOperationType() == null) {
            throw new ValidationException("Operation type is required");
        }

        if (orderAuditTrail.getChangedBy() == null) {
            throw new ValidationException("Changed by user is required");
        }

        // Validate changedBy exists
        User changedBy = userRepository.findById(orderAuditTrail.getChangedBy().getId())
                .orElseThrow(() -> new ValidationException("Changed by user not found with id: " + orderAuditTrail.getChangedBy().getId()));

        if (orderAuditTrail.getChangedAt() == null) {
            throw new ValidationException("Changed at timestamp is required");
        }

        if (orderAuditTrail.getColumnName() != null && orderAuditTrail.getColumnName().length() > 100) {
            throw new ValidationException("Column name cannot exceed 100 characters");
        }

        if (orderAuditTrail.getIpAddress() != null && orderAuditTrail.getIpAddress().length() > 45) {
            throw new ValidationException("IP address cannot exceed 45 characters");
        }
    }
}
