// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderNotification;
// import com.ldr.api.service.OrderNotificationService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.List;

// @RestController
// @RequestMapping("/api/order-notifications")
// @Tag(name = "OrderNotification", description = "OrderNotification management APIs")
// public class OrderNotificationController {

//     private final OrderNotificationService orderNotificationService;

//     @Autowired
//     public OrderNotificationController(OrderNotificationService orderNotificationService) {
//         this.orderNotificationService = orderNotificationService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderNotifications", description = "Retrieve a list of all order notifications")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getAllOrderNotifications() {
//         List<OrderNotification> notifications = orderNotificationService.findAll();
//         return ResponseEntity.ok(notifications);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderNotification by ID", description = "Retrieve a specific order notification by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order notification"),
//         @ApiResponse(responseCode = "404", description = "OrderNotification not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderNotification> getOrderNotificationById(
//             @Parameter(description = "OrderNotification ID") @PathVariable String id) {
//         try {
//             OrderNotification orderNotification = orderNotificationService.findById(id);
//             return ResponseEntity.ok(orderNotification);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderNotification", description = "Create a new order notification")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "OrderNotification created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderNotification> createOrderNotification(
//             @Parameter(description = "OrderNotification object") @Valid @RequestBody OrderNotification orderNotification) {
//         try {
//             OrderNotification createdOrderNotification = orderNotificationService.save(orderNotification);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderNotification);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderNotification", description = "Update an existing order notification")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "OrderNotification updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "OrderNotification not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderNotification> updateOrderNotification(
//             @Parameter(description = "OrderNotification ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderNotification object") @Valid @RequestBody OrderNotification orderNotification) {
//         try {
//             OrderNotification updatedOrderNotification = orderNotificationService.update(id, orderNotification);
//             return ResponseEntity.ok(updatedOrderNotification);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderNotification", description = "Delete an order notification by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "OrderNotification deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "OrderNotification not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order notification as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderNotification(
//             @Parameter(description = "OrderNotification ID") @PathVariable String id) {
//         try {
//             orderNotificationService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderNotifications by Order ID", description = "Retrieve order notifications by order ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getOrderNotificationsByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderNotification> notifications = orderNotificationService.findByOrderId(orderId);
//         return ResponseEntity.ok(notifications);
//     }

//     @GetMapping("/user/{userId}")
//     @Operation(summary = "Get OrderNotifications by User ID", description = "Retrieve order notifications by user ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getOrderNotificationsByUserId(
//             @Parameter(description = "User ID") @PathVariable String userId) {
//         List<OrderNotification> notifications = orderNotificationService.findByUserId(userId);
//         return ResponseEntity.ok(notifications);
//     }

//     @GetMapping("/type/{notificationType}")
//     @Operation(summary = "Get OrderNotifications by Notification Type", description = "Retrieve order notifications by notification type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getOrderNotificationsByType(
//             @Parameter(description = "Notification Type") @PathVariable OrderNotification.NotificationType notificationType) {
//         List<OrderNotification> notifications = orderNotificationService.findByNotificationType(notificationType);
//         return ResponseEntity.ok(notifications);
//     }

//     @GetMapping("/read/{isRead}")
//     @Operation(summary = "Get OrderNotifications by Read Status", description = "Retrieve order notifications by read status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getOrderNotificationsByReadStatus(
//             @Parameter(description = "Is Read") @PathVariable boolean isRead) {
//         List<OrderNotification> notifications = orderNotificationService.findByIsRead(isRead);
//         return ResponseEntity.ok(notifications);
//     }

//     @GetMapping("/created-at")
//     @Operation(summary = "Get OrderNotifications by Created At Range", description = "Retrieve order notifications by created at between start and end dates")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderNotification>> getOrderNotificationsByCreatedAtBetween(
//             @Parameter(description = "Start Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         List<OrderNotification> notifications = orderNotificationService.findByCreatedAtBetween(start, end);
//         return ResponseEntity.ok(notifications);
//     }
// }
