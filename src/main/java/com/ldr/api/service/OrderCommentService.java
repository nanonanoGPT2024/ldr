package com.ldr.api.service;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderComment;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.repository.OrderCommentRepository;
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
public class OrderCommentService {

    private final OrderCommentRepository orderCommentRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderCommentService(OrderCommentRepository orderCommentRepository,
                               OrderDataRepository orderDataRepository,
                               UserRepository userRepository) {
        this.orderCommentRepository = orderCommentRepository;
        this.orderDataRepository = orderDataRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderComment entities
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findAll() {
        return orderCommentRepository.findAll();
    }

    /**
     * Find OrderComment by ID
     * @param id the OrderComment ID
     * @return OrderComment
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderComment findById(String id) {
        return orderCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderComment not found with id: " + id));
    }

    /**
     * Save a new OrderComment
     * @param orderComment the OrderComment to save
     * @return saved OrderComment
     * @throws ValidationException if validation fails
     */
    public OrderComment save(OrderComment orderComment) {
        validateOrderComment(orderComment);

        try {
            return orderCommentRepository.save(orderComment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderComment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderComment
     * @param id the OrderComment ID
     * @param orderComment the updated OrderComment
     * @return updated OrderComment
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderComment update(String id, OrderComment orderComment) {
        OrderComment existingOrderComment = findById(id);

        validateOrderComment(orderComment);

        // Update fields
        existingOrderComment.setOrder(orderComment.getOrder());
        existingOrderComment.setUser(orderComment.getUser());
        existingOrderComment.setCommentType(orderComment.getCommentType());
        existingOrderComment.setCommentText(orderComment.getCommentText());
        existingOrderComment.setParentComment(orderComment.getParentComment());
        existingOrderComment.setEdited(true);
        existingOrderComment.setEditedAt(LocalDateTime.now());

        try {
            return orderCommentRepository.save(existingOrderComment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderComment due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderComment by ID (soft delete)
     * @param id the OrderComment ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderComment orderComment = findById(id);
        orderComment.setDeleted(true);
        orderComment.setDeletedAt(LocalDateTime.now());
        orderCommentRepository.save(orderComment);
    }

    /**
     * Find all OrderComment by order ID
     * @param orderId the order ID
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByOrderId(String orderId) {
        return orderCommentRepository.findByOrderId(orderId);
    }

    /**
     * Find all OrderComment by user ID
     * @param userId the user ID
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByUserId(String userId) {
        return orderCommentRepository.findByUserId(userId);
    }

    /**
     * Find all OrderComment by comment type
     * @param commentType the comment type
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByCommentType(String commentType) {
        return orderCommentRepository.findByCommentType(commentType);
    }

    /**
     * Find all OrderComment by created at between start and end date
     * @param startDate the start date
     * @param endDate the end date
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderCommentRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Find all OrderComment by is deleted
     * @param isDeleted the deleted flag
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByIsDeleted(boolean isDeleted) {
        return orderCommentRepository.findByIsDeleted(isDeleted);
    }

    /**
     * Validate OrderComment entity
     * @param orderComment the OrderComment to validate
     * @throws ValidationException if validation fails
     */
    private void validateOrderComment(OrderComment orderComment) {
        if (orderComment == null) {
            throw new ValidationException("OrderComment cannot be null");
        }

        if (orderComment.getId() == null || orderComment.getId().trim().isEmpty()) {
            throw new ValidationException("OrderComment ID is required");
        }

        if (orderComment.getOrder() == null) {
            throw new ValidationException("Order is required");
        }

        // Validate order exists
        OrderData order = orderDataRepository.findById(orderComment.getOrder().getId())
                .orElseThrow(() -> new ValidationException("Order not found with id: " + orderComment.getOrder().getId()));

        if (orderComment.getUser() == null) {
            throw new ValidationException("User is required");
        }

        // Validate user exists
        User user = userRepository.findById(orderComment.getUser().getId())
                .orElseThrow(() -> new ValidationException("User not found with id: " + orderComment.getUser().getId()));

        if (orderComment.getCommentType() == null || orderComment.getCommentType().trim().isEmpty()) {
            throw new ValidationException("Comment type is required");
        }

        if (orderComment.getCommentType().length() > 20) {
            throw new ValidationException("Comment type cannot exceed 20 characters");
        }

        if (orderComment.getCommentText() == null || orderComment.getCommentText().trim().isEmpty()) {
            throw new ValidationException("Comment text is required");
        }

        if (orderComment.getCommentText().length() > 1000) {
            throw new ValidationException("Comment text cannot exceed 1000 characters");
        }

        // Validate parent comment if provided
        if (orderComment.getParentComment() != null) {
            OrderComment parent = orderCommentRepository.findById(orderComment.getParentComment().getId())
                    .orElseThrow(() -> new ValidationException("Parent comment not found with id: " + orderComment.getParentComment().getId()));
        }
    }
}
