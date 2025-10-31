package com.ldr.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldr.api.dto.CreateOrderCommentRequest;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderComment;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.repository.OrderCommentRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.UserRepository;

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
     * Find all OrderComment by order ID
     *
     * @param orderId the order ID
     * @return List<OrderComment>
     */
    @Transactional(readOnly = true)
    public List<OrderComment> findByOrderId(String orderId) {
        return orderCommentRepository.findByOrderId(orderId);
    }

    /**
     * Save a new OrderComment from DTO
     *
     * @param request the CreateOrderCommentRequest DTO
     * @return saved OrderComment
     * @throws ValidationException if validation fails
     */
    public OrderComment save(CreateOrderCommentRequest request) {
        try {
            // Find related entities
            OrderData order = orderDataRepository.findById(request.getOrderId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Order not found with id: " + request.getOrderId()));

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

            OrderComment parentComment = null;
            if (request.getParentCommentId() != null && !request.getParentCommentId().trim().isEmpty()) {
                parentComment = orderCommentRepository.findById(request.getParentCommentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Parent comment not found with id: " + request.getParentCommentId()));
            }

            // Create OrderComment entity
            OrderComment orderComment = new OrderComment();
            orderComment.setId(java.util.UUID.randomUUID().toString());
            orderComment.setOrder(order);
            orderComment.setUser(user);
            orderComment.setCommentType(request.getCommentType());
            orderComment.setCommentText(request.getCommentText());
            orderComment.setParentComment(parentComment);

            return orderCommentRepository.save(orderComment);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to save OrderComment due to data integrity violation: " + e.getMessage());
        }
    }

}
