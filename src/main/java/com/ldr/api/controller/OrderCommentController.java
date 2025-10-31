// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderComment;
// import com.ldr.api.service.OrderCommentService;
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
// @RequestMapping("/api/order-comments")
// @Tag(name = "OrderComment", description = "Order comment management APIs")
// public class OrderCommentController {

//     private final OrderCommentService orderCommentService;

//     @Autowired
//     public OrderCommentController(OrderCommentService orderCommentService) {
//         this.orderCommentService = orderCommentService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderComments", description = "Retrieve a list of all order comments")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getAllOrderComments() {
//         List<OrderComment> orderComments = orderCommentService.findAll();
//         return ResponseEntity.ok(orderComments);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderComment by ID", description = "Retrieve a specific order comment by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order comment"),
//         @ApiResponse(responseCode = "404", description = "Order comment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderComment> getOrderCommentById(
//             @Parameter(description = "OrderComment ID") @PathVariable String id) {
//         try {
//             OrderComment orderComment = orderCommentService.findById(id);
//             return ResponseEntity.ok(orderComment);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderComment", description = "Create a new order comment")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order comment created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderComment> createOrderComment(
//             @Parameter(description = "OrderComment object") @Valid @RequestBody OrderComment orderComment) {
//         try {
//             OrderComment createdOrderComment = orderCommentService.save(orderComment);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderComment);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderComment", description = "Update an existing order comment")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order comment updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order comment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderComment> updateOrderComment(
//             @Parameter(description = "OrderComment ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderComment object") @Valid @RequestBody OrderComment orderComment) {
//         try {
//             OrderComment updatedOrderComment = orderCommentService.update(id, orderComment);
//             return ResponseEntity.ok(updatedOrderComment);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderComment", description = "Delete an order comment by its ID (soft delete)")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order comment deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order comment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderComment(
//             @Parameter(description = "OrderComment ID") @PathVariable String id) {
//         try {
//             orderCommentService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderComments by Order ID", description = "Retrieve all order comments for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getOrderCommentsByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderComment> orderComments = orderCommentService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderComments);
//     }

//     @GetMapping("/user/{userId}")
//     @Operation(summary = "Get OrderComments by User ID", description = "Retrieve all order comments for a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getOrderCommentsByUserId(
//             @Parameter(description = "User ID") @PathVariable String userId) {
//         List<OrderComment> orderComments = orderCommentService.findByUserId(userId);
//         return ResponseEntity.ok(orderComments);
//     }

//     @GetMapping("/type/{commentType}")
//     @Operation(summary = "Get OrderComments by Comment Type", description = "Retrieve all order comments by comment type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getOrderCommentsByCommentType(
//             @Parameter(description = "Comment Type") @PathVariable String commentType) {
//         List<OrderComment> orderComments = orderCommentService.findByCommentType(commentType);
//         return ResponseEntity.ok(orderComments);
//     }

//     @GetMapping("/created-at")
//     @Operation(summary = "Get OrderComments by Created At Between", description = "Retrieve all order comments created between start and end dates")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getOrderCommentsByCreatedAtBetween(
//             @Parameter(description = "Start Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         List<OrderComment> orderComments = orderCommentService.findByCreatedAtBetween(start, end);
//         return ResponseEntity.ok(orderComments);
//     }

//     @GetMapping("/deleted/{isDeleted}")
//     @Operation(summary = "Get OrderComments by Deleted Status", description = "Retrieve all order comments by deleted status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderComment>> getOrderCommentsByIsDeleted(
//             @Parameter(description = "Is Deleted") @PathVariable boolean isDeleted) {
//         List<OrderComment> orderComments = orderCommentService.findByIsDeleted(isDeleted);
//         return ResponseEntity.ok(orderComments);
//     }
// }
