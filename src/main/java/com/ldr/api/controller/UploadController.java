package com.ldr.api.controller;

import com.ldr.api.dto.FileUploadResponse;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderAttachmentHistory;
import com.ldr.api.security.JwtUtil;
import com.ldr.api.service.OrderAttachmentHistoryService;
import com.ldr.api.service.OrderAttachmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
@Tag(name = "Upload", description = "File upload and history management APIs")
@SecurityRequirement(name = "bearerAuth")
public class UploadController {

    private final OrderAttachmentService orderAttachmentService;
    private final OrderAttachmentHistoryService orderAttachmentHistoryService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UploadController(OrderAttachmentService orderAttachmentService,
            OrderAttachmentHistoryService orderAttachmentHistoryService,
            JwtUtil jwtUtil) {
        this.orderAttachmentService = orderAttachmentService;
        this.orderAttachmentHistoryService = orderAttachmentHistoryService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image file", description = "Upload an image file and save to both order_attachment and order_attachment_history tables")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or request data"),
            @ApiResponse(responseCode = "404", description = "Order or document not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FileUploadResponse> uploadImage(
            @Parameter(description = "Image file to upload") @RequestParam("file") MultipartFile file,
            @Parameter(description = "Order ID") @RequestParam("orderId") String orderId,
            @Parameter(description = "Document ID (optional)") @RequestParam(value = "documentId", required = false) String documentId,
            @Parameter(description = "Description/notes for the attachment") @RequestParam(value = "keterangan", required = false) String keterangan,
            @Parameter(description = "Authorization header") @RequestHeader("Authorization") String token) {

        try {
            // Extract username from JWT token
            String uploaderUsername = jwtUtil.extractUsername(token.replace("Bearer ", ""));

            FileUploadResponse response = orderAttachmentService.uploadFile(file, orderId, documentId, keterangan,
                    uploaderUsername);
            return ResponseEntity.ok(response);

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/history/{attachmentId}")
    @Operation(summary = "Get upload history by attachment ID", description = "Retrieve all upload history records for a specific attachment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved upload history"),
            @ApiResponse(responseCode = "404", description = "Attachment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<OrderAttachmentHistory>> getUploadHistoryByAttachmentId(
            @Parameter(description = "Attachment ID") @PathVariable String attachmentId) {
        try {
            List<OrderAttachmentHistory> history = orderAttachmentHistoryService.findByOrderAttachmentId(attachmentId);
            return ResponseEntity.ok(history);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}