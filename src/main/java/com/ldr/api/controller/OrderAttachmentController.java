// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.OrderAttachment;
// import com.ldr.api.service.OrderAttachmentService;
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
// @RequestMapping("/api/order-attachments")
// @Tag(name = "OrderAttachment", description = "Order attachment management APIs")
// public class OrderAttachmentController {

//     private final OrderAttachmentService orderAttachmentService;

//     @Autowired
//     public OrderAttachmentController(OrderAttachmentService orderAttachmentService) {
//         this.orderAttachmentService = orderAttachmentService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all OrderAttachments", description = "Retrieve a list of all order attachments")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getAllOrderAttachments() {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findAll();
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get OrderAttachment by ID", description = "Retrieve a specific order attachment by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved order attachment"),
//         @ApiResponse(responseCode = "404", description = "Order attachment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachment> getOrderAttachmentById(
//             @Parameter(description = "OrderAttachment ID") @PathVariable String id) {
//         try {
//             OrderAttachment orderAttachment = orderAttachmentService.findById(id);
//             return ResponseEntity.ok(orderAttachment);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create OrderAttachment", description = "Create a new order attachment")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Order attachment created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachment> createOrderAttachment(
//             @Parameter(description = "OrderAttachment object") @Valid @RequestBody OrderAttachment orderAttachment) {
//         try {
//             OrderAttachment createdOrderAttachment = orderAttachmentService.save(orderAttachment);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderAttachment);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update OrderAttachment", description = "Update an existing order attachment")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Order attachment updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Order attachment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<OrderAttachment> updateOrderAttachment(
//             @Parameter(description = "OrderAttachment ID") @PathVariable String id,
//             @Parameter(description = "Updated OrderAttachment object") @Valid @RequestBody OrderAttachment orderAttachment) {
//         try {
//             OrderAttachment updatedOrderAttachment = orderAttachmentService.update(id, orderAttachment);
//             return ResponseEntity.ok(updatedOrderAttachment);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete OrderAttachment", description = "Delete an order attachment by its ID (soft delete)")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Order attachment deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Order attachment not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteOrderAttachment(
//             @Parameter(description = "OrderAttachment ID") @PathVariable String id) {
//         try {
//             orderAttachmentService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/order/{orderId}")
//     @Operation(summary = "Get OrderAttachments by Order ID", description = "Retrieve all order attachments for a specific order")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByOrderId(
//             @Parameter(description = "Order ID") @PathVariable String orderId) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByOrderId(orderId);
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/document/{documentId}")
//     @Operation(summary = "Get OrderAttachments by Document ID", description = "Retrieve all order attachments for a specific document")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByDocumentId(
//             @Parameter(description = "Document ID") @PathVariable String documentId) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByDocumentId(documentId);
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/uploaded-by/{uploadedBy}")
//     @Operation(summary = "Get OrderAttachments by Uploaded By", description = "Retrieve all order attachments uploaded by a specific user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByUploadedBy(
//             @Parameter(description = "Uploaded By User ID") @PathVariable String uploadedBy) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByUploadedBy(uploadedBy);
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/created-at")
//     @Operation(summary = "Get OrderAttachments by Created At Between", description = "Retrieve all order attachments created between start and end dates")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "400", description = "Invalid date format"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByCreatedAtBetween(
//             @Parameter(description = "Start Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//             @Parameter(description = "End Date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByCreatedAtBetween(start, end);
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/active/{isActive}")
//     @Operation(summary = "Get OrderAttachments by Active Status", description = "Retrieve all order attachments by active status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByIsActive(
//             @Parameter(description = "Is Active") @PathVariable boolean isActive) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByIsActive(isActive);
//         return ResponseEntity.ok(orderAttachments);
//     }

//     @GetMapping("/deleted/{isDeleted}")
//     @Operation(summary = "Get OrderAttachments by Deleted Status", description = "Retrieve all order attachments by deleted status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<OrderAttachment>> getOrderAttachmentsByIsDeleted(
//             @Parameter(description = "Is Deleted") @PathVariable boolean isDeleted) {
//         List<OrderAttachment> orderAttachments = orderAttachmentService.findByIsDeleted(isDeleted);
//         return ResponseEntity.ok(orderAttachments);
//     }
// }
