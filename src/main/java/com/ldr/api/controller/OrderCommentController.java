package com.ldr.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderComment;
import com.ldr.api.service.OrderCommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order-comments")
@Tag(name = "OrderComment", description = "Order comment management APIs")
public class OrderCommentController {

    private final OrderCommentService orderCommentService;

    @Autowired
    public OrderCommentController(OrderCommentService orderCommentService) {
        this.orderCommentService = orderCommentService;
    }

    @PostMapping
    @Operation(summary = "Create OrderComment", description = "Create a new order comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderComment> createOrderComment(
            @Parameter(description = "OrderComment object") @Valid @RequestBody OrderComment orderComment) {
        try {
            OrderComment createdOrderComment = orderCommentService.save(orderComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderComment);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
