package com.ldr.api.controller;

import com.ldr.api.dto.FileUploadResponse;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.security.JwtUtil;
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

@RestController
@RequestMapping("/api/upload")
@Tag(name = "Upload", description = "File upload and management APIs")
@SecurityRequirement(name = "bearerAuth")
public class UploadController {

    private final OrderAttachmentService orderAttachmentService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UploadController(OrderAttachmentService orderAttachmentService,
            JwtUtil jwtUtil) {
        this.orderAttachmentService = orderAttachmentService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image file", description = "Upload an image file and associate it with an order and optional document")
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

}