// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.DocumentType;
// import com.ldr.api.service.DocumentTypeService;
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
// @RequestMapping("/api/document-types")
// @Tag(name = "Document Type", description = "Document Type management APIs")
// public class DocumentTypeController {

//     private final DocumentTypeService documentTypeService;

//     @Autowired
//     public DocumentTypeController(DocumentTypeService documentTypeService) {
//         this.documentTypeService = documentTypeService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Document Types", description = "Retrieve a list of all document types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
//         List<DocumentType> documentTypes = documentTypeService.findAll();
//         return ResponseEntity.ok(documentTypes);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Document Type by ID", description = "Retrieve a specific document type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved document type"),
//         @ApiResponse(responseCode = "404", description = "Document type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentType> getDocumentTypeById(
//             @Parameter(description = "Document Type ID") @PathVariable String id) {
//         try {
//             DocumentType documentType = documentTypeService.findById(id);
//             return ResponseEntity.ok(documentType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Document Type", description = "Create a new document type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Document type created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentType> createDocumentType(
//             @Parameter(description = "Document Type object") @Valid @RequestBody DocumentType documentType) {
//         try {
//             DocumentType createdDocumentType = documentTypeService.save(documentType);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdDocumentType);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Document Type", description = "Update an existing document type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Document type updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Document type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentType> updateDocumentType(
//             @Parameter(description = "Document Type ID") @PathVariable String id,
//             @Parameter(description = "Updated Document Type object") @Valid @RequestBody DocumentType documentType) {
//         try {
//             DocumentType updatedDocumentType = documentTypeService.update(id, documentType);
//             return ResponseEntity.ok(updatedDocumentType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Document Type", description = "Delete a document type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Document type deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Document type not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete document type as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteDocumentType(
//             @Parameter(description = "Document Type ID") @PathVariable String id) {
//         try {
//             documentTypeService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Document Types", description = "Retrieve all active document types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<DocumentType>> getActiveDocumentTypes() {
//         List<DocumentType> activeDocumentTypes = documentTypeService.findByIsActive();
//         return ResponseEntity.ok(activeDocumentTypes);
//     }
// }
