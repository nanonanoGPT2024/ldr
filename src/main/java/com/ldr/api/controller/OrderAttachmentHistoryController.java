// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.ChangeType;
// import com.ldr.api.model.OrderAttachmentHistory;
// import com.ldr.api.service.OrderAttachmentHistoryService;
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
// @RequestMapping("/api/order-attachment-history")
// @Tag(name = "OrderAttachmentHistory", description = "Order attachment history management APIs")
// public class OrderAttachmentHistoryController {

//     private final OrderAttachmentHistoryService orderAttachmentHistoryService;

//     @Autowired
//     public OrderAttachmentHistoryController(OrderAttachmentHistoryService orderAttachmentHistoryService) {
//         this.orderAttachmentHistoryService = orderAttachmentHistoryService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderAttachmentHistory", description = "Retrieve a list of all order attachment history records")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getAllOrderAttachmentHistory() {
//         List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findAll();
//         return ResponseEntity.ok(orderAttachmentHistory);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderAttachmentHistory by ID", description = "Retrieve a specific order attachment history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order attachment history"),
//         @ApiResponse(responseCode = "404", description = "Order attachment history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachmentHistory> getOrderAttachmentHistoryById(
//             @Parameter(description = "OrderAttachmentHistory ID") @PathVariable String id) {
//         try {
//             OrderAttachmentHistory orderAttachmentHistory = orderAttachmentHistoryService.findById(id);
//             return ResponseEntity.ok(orderAttachmentHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderAttachmentHistory", description = "Create a new order attachment history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order attachment history created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachmentHistory> createOrderAttachmentHistory(
//             @Parameter(description = "OrderAttachmentHistory object") @Valid @RequestBody OrderAttachmentHistory orderAttachmentHistory) {
//         try {
//             OrderAttachmentHistory createdOrderAttachmentHistory = orderAttachmentHistoryService.save(orderAttachmentHistory);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderAttachmentHistory);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderAttachmentHistory", description = "Update an existing order attachment history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order attachment history updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order attachment history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachmentHistory> updateOrderAttachmentHistory(
//             @Parameter(description = "OrderAttachmentHistory ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderAttachmentHistory object") @Valid @RequestBody OrderAttachmentHistory orderAttachmentHistory) {
//         try {
//             OrderAttachmentHistory updatedOrderAttachmentHistory = orderAttachmentHistoryService.update(id, orderAttachmentHistory);
//             return ResponseEntity.ok(updatedOrderAttachmentHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderAttachmentHistory", description = "Delete an order attachment history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order attachment history deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order attachment history not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order attachment history as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderAttachmentHistory(
//             @Parameter(description = "OrderAttachmentHistory ID") @PathVariable String id) {
//         try {
//             orderAttachmentHistoryService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/attachment/{orderAttachmentId}")
//     @Operation(summary = "Get OrderAttachmentHistory by Order Attachment ID", description = "Retrieve all order attachment history records for a specific order attachment")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByOrderAttachmentId(
//             @Parameter(description = "Order Attachment ID") @PathVariable String orderAttachmentId) {
//         List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByOrderAttachmentId(orderAttachmentId);
//         return ResponseEntity.ok(orderAttachmentHistory);
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderAttachmentHistory by Order ID", description = "Retrieve all order attachment history records for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderAttachmentHistory);
//     }

//     @GetMapping("/document/{documentId}")
//     @Operation(summary = "Get OrderAttachmentHistory by Document ID", description = "Retrieve all order attachment history records for a specific document")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByDocumentId(
//             @Parameter(description = "Document ID") @PathVariable String documentId) {
//         List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByDocumentId(documentId);
//         return ResponseEntity.ok(orderAttachmentHistory);
//     }

//     @GetMapping("/changed-by/{changedBy}")
//     @Operation(summary = "Get OrderAttachmentHistory by Changed By", description = "Retrieve all order attachment history records changed by a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByChangedBy(
//             @Parameter(description = "Changed By User ID") @PathVariable String changedBy) {
//         List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByChangedBy(changedBy);
//         return ResponseEntity.ok(orderAttachmentHistory);
//     }

//     @GetMapping("/change-type/{changeType}")
//     @Operation(summary = "Get OrderAttachmentHistory by Change Type", description = "Retrieve all order attachment history records by change type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid change type"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByChangeType(
//             @Parameter(description = "Change Type") @PathVariable String changeType) {
//         try {
//             ChangeType enumChangeType = ChangeType.valueOf(changeType.toUpperCase());
//             List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByChangeType(enumChangeType);
//             return ResponseEntity.ok(orderAttachmentHistory);
//         } catch (IllegalArgumentException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @GetMapping("/changed-at")
//     @Operation(summary = "Get OrderAttachmentHistory by Changed At Date Range", description = "Retrieve all order attachment history records within a date range")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachmentHistory>> getOrderAttachmentHistoryByChangedAtBetween(
//             @Parameter(description = "Start Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         try {
//             List<OrderAttachmentHistory> orderAttachmentHistory = orderAttachmentHistoryService.findByChangedAtBetween(start, end);
//             return ResponseEntity.ok(orderAttachmentHistory);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
// }
