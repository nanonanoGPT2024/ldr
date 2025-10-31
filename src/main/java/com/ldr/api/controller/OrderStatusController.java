// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderStatus;
// import com.ldr.api.service.OrderStatusService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/order-status")
// @Tag(name = "Order Status", description = "Order Status management APIs")
// public class OrderStatusController {

//     private final OrderStatusService orderStatusService;

//     @Autowired
//     public OrderStatusController(OrderStatusService orderStatusService) {
//         this.orderStatusService = orderStatusService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Order Status", description = "Retrieve a list of all order statuses")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
//         List<OrderStatus> orderStatuses = orderStatusService.findAll();
//         return ResponseEntity.ok(orderStatuses);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Order Status by ID", description = "Retrieve a specific order status by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order status"),
//         @ApiResponse(responseCode = "404", description = "Order status not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatus> getOrderStatusById(
//             @Parameter(description = "Order Status ID") @PathVariable String id) {
//         try {
//             OrderStatus orderStatus = orderStatusService.findById(id);
//             return ResponseEntity.ok(orderStatus);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Order Status", description = "Create a new order status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order status created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "409", description = "Order status code already exists"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatus> createOrderStatus(
//             @Parameter(description = "Order Status object") @Valid @RequestBody OrderStatus orderStatus) {
//         try {
//             OrderStatus createdOrderStatus = orderStatusService.save(orderStatus);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderStatus);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Order Status", description = "Update an existing order status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order status not found"),
//         @ApiResponse(responseCode = "409", description = "Order status code already exists"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatus> updateOrderStatus(
//             @Parameter(description = "Order Status ID") @PathVariable String id,
//             @Parameter(description = "Updated Order Status object") @Valid @RequestBody OrderStatus orderStatus) {
//         try {
//             OrderStatus updatedOrderStatus = orderStatusService.update(id, orderStatus);
//             return ResponseEntity.ok(updatedOrderStatus);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Order Status", description = "Delete an order status by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order status deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order status not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order status as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderStatus(
//             @Parameter(description = "Order Status ID") @PathVariable String id) {
//         try {
//             orderStatusService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/code/{code}")
//     @Operation(summary = "Get Order Status by Code", description = "Retrieve an order status by its unique code")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order status"),
//         @ApiResponse(responseCode = "404", description = "Order status not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderStatus> getOrderStatusByCode(
//             @Parameter(description = "Order Status Code") @PathVariable String code) {
//         try {
//             OrderStatus orderStatus = orderStatusService.findByCode(code);
//             return ResponseEntity.ok(orderStatus);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Order Status", description = "Retrieve all active order statuses")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderStatus>> getActiveOrderStatuses() {
//         List<OrderStatus> activeOrderStatuses = orderStatusService.findByIsActive();
//         return ResponseEntity.ok(activeOrderStatuses);
//     }
// }
