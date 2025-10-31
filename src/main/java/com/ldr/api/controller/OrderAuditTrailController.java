// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderAuditTrail;
// import com.ldr.api.service.OrderAuditTrailService;
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
// @RequestMapping("/api/order-audit-trail")
// @Tag(name = "OrderAuditTrail", description = "Order audit trail management APIs")
// public class OrderAuditTrailController {

//     private final OrderAuditTrailService orderAuditTrailService;

//     @Autowired
//     public OrderAuditTrailController(OrderAuditTrailService orderAuditTrailService) {
//         this.orderAuditTrailService = orderAuditTrailService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderAuditTrail", description = "Retrieve a list of all order audit trail records")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getAllOrderAuditTrail() {
//         List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findAll();
//         return ResponseEntity.ok(orderAuditTrail);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderAuditTrail by ID", description = "Retrieve a specific order audit trail record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order audit trail"),
//         @ApiResponse(responseCode = "404", description = "Order audit trail not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAuditTrail> getOrderAuditTrailById(
//             @Parameter(description = "OrderAuditTrail ID") @PathVariable String id) {
//         try {
//             OrderAuditTrail orderAuditTrail = orderAuditTrailService.findById(id);
//             return ResponseEntity.ok(orderAuditTrail);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderAuditTrail", description = "Create a new order audit trail record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order audit trail created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAuditTrail> createOrderAuditTrail(
//             @Parameter(description = "OrderAuditTrail object") @Valid @RequestBody OrderAuditTrail orderAuditTrail) {
//         try {
//             OrderAuditTrail createdOrderAuditTrail = orderAuditTrailService.save(orderAuditTrail);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderAuditTrail);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderAuditTrail", description = "Update an existing order audit trail record")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order audit trail updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order audit trail not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAuditTrail> updateOrderAuditTrail(
//             @Parameter(description = "OrderAuditTrail ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderAuditTrail object") @Valid @RequestBody OrderAuditTrail orderAuditTrail) {
//         try {
//             OrderAuditTrail updatedOrderAuditTrail = orderAuditTrailService.update(id, orderAuditTrail);
//             return ResponseEntity.ok(updatedOrderAuditTrail);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderAuditTrail", description = "Delete an order audit trail record by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order audit trail deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order audit trail not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order audit trail as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderAuditTrail(
//             @Parameter(description = "OrderAuditTrail ID") @PathVariable String id) {
//         try {
//             orderAuditTrailService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderAuditTrail by Order ID", description = "Retrieve all order audit trail records for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getOrderAuditTrailByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderAuditTrail);
//     }

//     @GetMapping("/table/{tableName}")
//     @Operation(summary = "Get OrderAuditTrail by Table Name", description = "Retrieve all order audit trail records for a specific table")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getOrderAuditTrailByTableName(
//             @Parameter(description = "Table Name") @PathVariable String tableName) {
//         List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findByTableName(tableName);
//         return ResponseEntity.ok(orderAuditTrail);
//     }

//     @GetMapping("/operation/{operationType}")
//     @Operation(summary = "Get OrderAuditTrail by Operation Type", description = "Retrieve all order audit trail records for a specific operation type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid operation type"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getOrderAuditTrailByOperationType(
//             @Parameter(description = "Operation Type (INSERT, UPDATE, DELETE)") @PathVariable String operationType) {
//         try {
//             List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findByOperationType(operationType);
//             return ResponseEntity.ok(orderAuditTrail);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @GetMapping("/changed-by/{changedBy}")
//     @Operation(summary = "Get OrderAuditTrail by Changed By", description = "Retrieve all order audit trail records changed by a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getOrderAuditTrailByChangedBy(
//             @Parameter(description = "Changed By User ID") @PathVariable String changedBy) {
//         List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findByChangedBy(changedBy);
//         return ResponseEntity.ok(orderAuditTrail);
//     }

//     @GetMapping("/changed-at")
//     @Operation(summary = "Get OrderAuditTrail by Changed At Date Range", description = "Retrieve all order audit trail records within a date range")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAuditTrail>> getOrderAuditTrailByChangedAtBetween(
//             @Parameter(description = "Start Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         try {
//             List<OrderAuditTrail> orderAuditTrail = orderAuditTrailService.findByChangedAtBetween(start, end);
//             return ResponseEntity.ok(orderAuditTrail);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
// }
