package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderApproval;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.repository.OrderApprovalRepository;
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
public class OrderApprovalService {

    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderApprovalService(OrderApprovalRepository orderApprovalRepository,
                               OrderDataRepository orderDataRepository,
                               UserRepository userRepository) {
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderApproval entities
     * @return List<OrderApproval>
     */
    @Transactional(readOnly = true)
    public List<OrderApproval> findAll() {
        return orderApprovalRepository.findAll();
    }

    /**
     * Find OrderApproval by ID
     * @param id the OrderApproval ID
     * @return OrderApproval
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderApproval findById(String id) {
        return orderApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderApproval not found with id: " + id));
    }

    /**
     * Save a new OrderApproval
     * @param orderApproval the OrderApproval to save
     * @return saved OrderApproval
     * @throws ValidationException if validation fails
     */
    public OrderApproval save(OrderApproval orderApproval) {
        validateOrderApproval(orderApproval);

        try {
            return orderApprovalRepository.save(orderApproval);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderApproval due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderApproval
     * @param id the OrderApproval ID
     * @param orderApproval the updated OrderApproval
     * @return updated OrderApproval
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderApproval update(String id, OrderApproval orderApproval) {
        OrderApproval existingOrderApproval = findById(id);

        validateOrderApproval(orderApproval);

        // Update fields
        existingOrderApproval.setOrder(orderApproval.getOrder());
        existingOrderApproval.setApprover(orderApproval.getApprover());
        existingOrderApproval.setApproverRole(orderApproval.getApproverRole());
        existingOrderApproval.setStatus(orderApproval.getStatus());
        existingOrderApproval.setComments(orderApproval.getComments());
        existingOrderApproval.setApprovedAt(orderApproval.getApprovedAt());

        try {
            return orderApprovalRepository.save(existingOrderApproval);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderApproval due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderApproval by ID
     * @param id the OrderApproval ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderApproval orderApproval = findById(id);
        try {
            orderApprovalRepository.delete(orderApproval);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderApproval as it is referenced by other records: " + id);
        }
    }

    /**
     * Find all OrderApproval by order ID
     * @param orderId the order ID
     * @return List<OrderApproval>
     */
    @Transactional(readOnly = true)
    public List<OrderApproval> findByOrderId(String orderId) {
        return orderApprovalRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderApproval by approver ID
     * @param approverId the approver ID
     * @return List<OrderApproval>
     */
    @Transactional(readOnly = true)
    public List<OrderApproval> findByApproverId(String approverId) {
        return orderApprovalRepository.findByApproverId(approverId);
    }

    /**
     * Find all OrderApproval by status
     * @param status the status
     * @return List<OrderApproval>
     */
    @Transactional(readOnly = true)
    public List<OrderApproval> findByStatus(String status) {
        return orderApprovalRepository.findByStatus(status);
    }

    /**
     * Approve an order approval
     * @param id the OrderApproval ID
     * @param comments the approval comments
     * @return updated OrderApproval
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if already approved or rejected
     */
    public OrderApproval approve(String id, String comments) {
        OrderApproval orderApproval = findById(id);

        if (!"REQUEST".equals(orderApproval.getStatus())) {
            throw new ValidationException("OrderApproval can only be approved if status is REQUEST");
        }

        orderApproval.setStatus("APPROVED");
        orderApproval.setComments(comments);
        orderApproval.setApprovedAt(LocalDateTime.now());

        return orderApprovalRepository.save(orderApproval);
    }

    /**
     * Reject an order approval
     * @param id the OrderApproval ID
     * @param comments the rejection comments
     * @return updated OrderApproval
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if already approved or rejected
     */
    public OrderApproval reject(String id, String comments) {
        OrderApproval orderApproval = findById(id);

        if (!"REQUEST".equals(orderApproval.getStatus())) {
            throw new ValidationException("OrderApproval can only be rejected if status is REQUEST");
        }

        orderApproval.setStatus("REJECTED");
        orderApproval.setComments(comments);
        orderApproval.setApprovedAt(LocalDateTime.now());

        return orderApprovalRepository.save(orderApproval);
    }

    /**
     * Validate OrderApproval entity
     * @param orderApproval the OrderApproval to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderApproval(OrderApproval orderApproval) {
        if (orderApproval == null) {
            throw new ValidationException("OrderApproval cannot be null");
        }

        if (orderApproval.getId() == null || orderApproval.getId().trim().isEmpty()) {
            throw new ValidationException("OrderApproval ID is required");
        }

        if (orderApproval.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderApproval.getOrder().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderApproval.getOrder().getId()));

        if (orderApproval.getApprover() == null) {
            throw new ValidationException("Approver is required");
        }

        // Validate approver exists
        User approver = userRepository.findById(orderApproval.getApprover().getId())
                .orElseThrow(() -> new ValidationException("Approver not found with id: " + orderApproval.getApprover().getId()));

        if (orderApproval.getApproverRole() == null || orderApproval.getApproverRole().trim().isEmpty()) {
            throw new ValidationException("Approver role is required");
        }

        if (orderApproval.getApproverRole().length() > 50) {
            throw new ValidationException("Approver role cannot exceed 50 characters");
        }

        if (orderApproval.getStatus() == null || orderApproval.getStatus().trim().isEmpty()) {
            throw new ValidationException("Status is required");
        }

        if (!List.of("REQUEST", "APPROVED", "REJECTED").contains(orderApproval.getStatus())) {
            throw new ValidationException("Status must be one of: REQUEST, APPROVED, REJECTED");
        }

        if (orderApproval.getComments() != null && orderApproval.getComments().length() > 1000) {
            throw new ValidationException("Comments cannot exceed 1000 characters");
        }
    }
}
