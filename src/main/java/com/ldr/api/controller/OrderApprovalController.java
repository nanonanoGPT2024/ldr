// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderApproval;
// import com.ldr.api.service.OrderApprovalService;
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
// @RequestMapping("/api/order-approvals")
// @Tag(name = "OrderApproval", description = "Order approval management APIs")
// public class OrderApprovalController {

//     private final OrderApprovalService orderApprovalService;

//     @Autowired
//     public OrderApprovalController(OrderApprovalService orderApprovalService) {
//         this.orderApprovalService = orderApprovalService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderApprovals", description = "Retrieve a list of all order approvals")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderApproval>> getAllOrderApprovals() {
//         List<OrderApproval> orderApprovals = orderApprovalService.findAll();
//         return ResponseEntity.ok(orderApprovals);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderApproval by ID", description = "Retrieve a specific order approval by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order approval"),
//         @ApiResponse(responseCode = "404", description = "Order approval not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderApproval> getOrderApprovalById(
//             @Parameter(description = "OrderApproval ID") @PathVariable String id) {
//         try {
//             OrderApproval orderApproval = orderApprovalService.findById(id);
//             return ResponseEntity.ok(orderApproval);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderApproval", description = "Create a new order approval")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order approval created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderApproval> createOrderApproval(
//             @Parameter(description = "OrderApproval object") @Valid @RequestBody OrderApproval orderApproval) {
//         try {
//             OrderApproval createdOrderApproval = orderApprovalService.save(orderApproval);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderApproval);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderApproval", description = "Update an existing order approval")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order approval updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order approval not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderApproval> updateOrderApproval(
//             @Parameter(description = "OrderApproval ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderApproval object") @Valid @RequestBody OrderApproval orderApproval) {
//         try {
//             OrderApproval updatedOrderApproval = orderApprovalService.update(id, orderApproval);
//             return ResponseEntity.ok(updatedOrderApproval);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderApproval", description = "Delete an order approval by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order approval deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order approval not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete order approval as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderApproval(
//             @Parameter(description = "OrderApproval ID") @PathVariable String id) {
//         try {
//             orderApprovalService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderApprovals by Order ID", description = "Retrieve all order approvals for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderApproval>> getOrderApprovalsByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderApproval> orderApprovals = orderApprovalService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderApprovals);
//     }

//     @GetMapping("/approver/{approverId}")
//     @Operation(summary = "Get OrderApprovals by Approver ID", description = "Retrieve all order approvals for a specific approver")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderApproval>> getOrderApprovalsByApproverId(
//             @Parameter(description = "Approver ID") @PathVariable String approverId) {
//         List<OrderApproval> orderApprovals = orderApprovalService.findByApproverId(approverId);
//         return ResponseEntity.ok(orderApprovals);
//     }

//     @GetMapping("/status/{status}")
//     @Operation(summary = "Get OrderApprovals by Status", description = "Retrieve all order approvals by status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderApproval>> getOrderApprovalsByStatus(
//             @Parameter(description = "Status") @PathVariable String status) {
//         List<OrderApproval> orderApprovals = orderApprovalService.findByStatus(status);
//         return ResponseEntity.ok(orderApprovals);
//     }

//     @PostMapping("/{id}/approve")
//     @Operation(summary = "Approve OrderApproval", description = "Approve a specific order approval")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order approval approved successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid operation (already approved/rejected)"),
//         @ApiResponse(responseCode = "404", description = "Order approval not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderApproval> approveOrderApproval(
//             @Parameter(description = "OrderApproval ID") @PathVariable String id,
//             @Parameter(description = "Approval comments") @RequestParam(required = false) String comments) {
//         try {
//             OrderApproval approvedOrderApproval = orderApprovalService.approve(id, comments);
//             return ResponseEntity.ok(approvedOrderApproval);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PostMapping("/{id}/reject")
//     @Operation(summary = "Reject OrderApproval", description = "Reject a specific order approval")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order approval rejected successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid operation (already approved/rejected)"),
//         @ApiResponse(responseCode = "404", description = "Order approval not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderApproval> rejectOrderApproval(
//             @Parameter(description = "OrderApproval ID") @PathVariable String id,
//             @Parameter(description = "Rejection comments") @RequestParam(required = false) String comments) {
//         try {
//             OrderApproval rejectedOrderApproval = orderApprovalService.reject(id, comments);
//             return ResponseEntity.ok(rejectedOrderApproval);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }
// }
