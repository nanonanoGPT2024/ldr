package com.ldr.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrderCommentRequest {

    @NotNull(message = "Order ID is required")
    private String orderId;

    @NotNull(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Comment type is required")
    @Size(max = 20, message = "Comment type cannot exceed 20 characters")
    private String commentType;

    @NotBlank(message = "Comment text is required")
    @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
    private String commentText;

    private String parentCommentId;

    // Constructors
    public CreateOrderCommentRequest() {
    }

    public CreateOrderCommentRequest(String orderId, String userId, String commentType, String commentText) {
        this.orderId = orderId;
        this.userId = userId;
        this.commentType = commentType;
        this.commentText = commentText;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}