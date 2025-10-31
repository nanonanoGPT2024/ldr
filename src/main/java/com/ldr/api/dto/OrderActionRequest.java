package com.ldr.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderActionRequest {

    @NotNull(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Comment text is required")
    private String commentText;

    // Constructors
    public OrderActionRequest() {
    }

    public OrderActionRequest(String orderId, String commentText) {
        this.orderId = orderId;
        this.commentText = commentText;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}