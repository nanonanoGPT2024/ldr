// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderStatusHistory;
// import com.ldr.api.service.OrderStatusHistoryService;
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
// @RequestMapping("/api/order-status-history")
// @Tag(name = "OrderStatusHistory", description = "Order status history management APIs")
// public class OrderStatusHistoryController {

//     private final OrderStatusHistoryService orderStatusHistoryService;

//     @Autowired
//     public OrderStatusHistoryController(OrderStatusHistoryService orderStatusHistoryService) {
//         this.orderStatusHistoryService = orderStatusHistoryService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderStatusHistory", description = "Retrieve a list of all order status history records")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatusHistory>> getAllOrderStatusHistory() {
//         List<OrderStatusHistory> orderStatusHistory = orderStatusHistoryService.findAll();
//         return ResponseEntity.ok(orderStatusHistory);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderStatusHistory by ID", description = "Retrieve a specific order status history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order status history"),
//         @ApiResponse(responseCode = "404", description = "Order status history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatusHistory> getOrderStatusHistoryById(
//             @Parameter(description = "OrderStatusHistory ID") @PathVariable String id) {
//         try {
//             OrderStatusHistory orderStatusHistory = orderStatusHistoryService.findById(id);
//             return ResponseEntity.ok(orderStatusHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderStatusHistory", description = "Create a new order status history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order status history created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatusHistory> createOrderStatusHistory(
//             @Parameter(description = "OrderStatusHistory object") @Valid @RequestBody OrderStatusHistory orderStatusHistory) {
//         try {
//             OrderStatusHistory createdOrderStatusHistory = orderStatusHistoryService.save(orderStatusHistory);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderStatusHistory);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderStatusHistory", description = "Update an existing order status history record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order status history updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order status history not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatusHistory> updateOrderStatusHistory(
//             @Parameter(description = "OrderStatusHistory ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderStatusHistory object") @Valid @RequestBody OrderStatusHistory orderStatusHistory) {
//         try {
//             OrderStatusHistory updatedOrderStatusHistory = orderStatusHistoryService.update(id, orderStatusHistory);
//             return ResponseEntity.ok(updatedOrderStatusHistory);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderStatusHistory", description = "Delete an order status history record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order status history deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order status history not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order status history as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderStatusHistory(
//             @Parameter(description = "OrderStatusHistory ID") @PathVariable String id) {
//         try {
//             orderStatusHistoryService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderStatusHistory by Order ID", description = "Retrieve all order status history records for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatusHistory>> getOrderStatusHistoryByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderStatusHistory> orderStatusHistory = orderStatusHistoryService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderStatusHistory);
//     }

//     @GetMapping("/changed-by/{changedBy}")
//     @Operation(summary = "Get OrderStatusHistory by Changed By", description = "Retrieve all order status history records changed by a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatusHistory>> getOrderStatusHistoryByChangedBy(
//             @Parameter(description = "Changed By User ID") @PathVariable String changedBy) {
//         List<OrderStatusHistory> orderStatusHistory = orderStatusHistoryService.findByChangedBy(changedBy);
//         return ResponseEntity.ok(orderStatusHistory);
//     }

//     @GetMapping("/changed-at")
//     @Operation(summary = "Get OrderStatusHistory by Changed At Date Range", description = "Retrieve all order status history records within a date range")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatusHistory>> getOrderStatusHistoryByChangedAtBetween(
//             @Parameter(description = "Start Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         try {
//             List<OrderStatusHistory> orderStatusHistory = orderStatusHistoryService.findByChangedAtBetween(start, end);
//             return ResponseEntity.ok(orderStatusHistory);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
// }
