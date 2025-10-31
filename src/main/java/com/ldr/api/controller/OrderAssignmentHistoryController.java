// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderAssignmentHistory;
// import com.ldr.api.service.OrderAssignmentHistoryService;
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
// @RequestMapping("/api/order-assignment-history")
// @Tag(name = "OrderAssignmentHistory", description = "Order assignment history management APIs")
// public class OrderAssignmentHistoryController {

//     private final OrderAssignmentHistoryService orderAssignmentHistoryService;

//     @Autowired
//     public OrderAssignmentHistoryController(OrderAssignmentHistoryService orderAssignmentHistoryService) {
//         this.orderAssignmentHistoryService = orderAssignmentHistoryService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderAssignmentHistory", description = "Retrieve a list of all order assignment history records")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAssignmentHistory>> getAllOrderAssignmentHistory() {
//         List<OrderAssignmentHistory> orderAssignmentHistory = orderAssignmentHistoryService.findAll();
//         return ResponseEntity.ok(orderAssignmentHistory);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderAssignmentHistory by ID", description = "Retrieve a specific order assignment history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order assignment history"),
//         @ApiResponse(responseCode = "404", description = "Order assignment history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAssignmentHistory> getOrderAssignmentHistoryById(
//             @Parameter(description = "OrderAssignmentHistory ID") @PathVariable String id) {
//         try {
//             OrderAssignmentHistory orderAssignmentHistory = orderAssignmentHistoryService.findById(id);
//             return ResponseEntity.ok(orderAssignmentHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderAssignmentHistory", description = "Create a new order assignment history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order assignment history created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAssignmentHistory> createOrderAssignmentHistory(
//             @Parameter(description = "OrderAssignmentHistory object") @Valid @RequestBody OrderAssignmentHistory orderAssignmentHistory) {
//         try {
//             OrderAssignmentHistory createdOrderAssignmentHistory = orderAssignmentHistoryService.save(orderAssignmentHistory);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderAssignmentHistory);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderAssignmentHistory", description = "Update an existing order assignment history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order assignment history updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order assignment history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAssignmentHistory> updateOrderAssignmentHistory(
//             @Parameter(description = "OrderAssignmentHistory ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderAssignmentHistory object") @Valid @RequestBody OrderAssignmentHistory orderAssignmentHistory) {
//         try {
//             OrderAssignmentHistory updatedOrderAssignmentHistory = orderAssignmentHistoryService.update(id, orderAssignmentHistory);
//             return ResponseEntity.ok(updatedOrderAssignmentHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderAssignmentHistory", description = "Delete an order assignment history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order assignment history deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order assignment history not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order assignment history as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderAssignmentHistory(
//             @Parameter(description = "OrderAssignmentHistory ID") @PathVariable String id) {
//         try {
//             orderAssignmentHistoryService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderAssignmentHistory by Order ID", description = "Retrieve all order assignment history records for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAssignmentHistory>> getOrderAssignmentHistoryByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderAssignmentHistory> orderAssignmentHistory = orderAssignmentHistoryService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderAssignmentHistory);
//     }

//     @GetMapping("/assigned-to/{assignedTo}")
//     @Operation(summary = "Get OrderAssignmentHistory by Assigned To", description = "Retrieve all order assignment history records assigned to a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAssignmentHistory>> getOrderAssignmentHistoryByAssignedTo(
//             @Parameter(description = "Assigned To User ID") @PathVariable String assignedTo) {
//         List<OrderAssignmentHistory> orderAssignmentHistory = orderAssignmentHistoryService.findByAssignedTo(assignedTo);
//         return ResponseEntity.ok(orderAssignmentHistory);
//     }

//     @GetMapping("/assigned-at")
//     @Operation(summary = "Get OrderAssignmentHistory by Assigned At Date Range", description = "Retrieve all order assignment history records within a date range")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAssignmentHistory>> getOrderAssignmentHistoryByAssignedAtBetween(
//             @Parameter(description = "Start Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         try {
//             List<OrderAssignmentHistory> orderAssignmentHistory = orderAssignmentHistoryService.findByAssignedAtBetween(start, end);
//             return ResponseEntity.ok(orderAssignmentHistory);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
// }
