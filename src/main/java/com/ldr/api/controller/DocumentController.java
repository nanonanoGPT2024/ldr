// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.Document;
// import com.ldr.api.service.DocumentService;
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
// @RequestMapping("/api/documents")
// @Tag(name = "Document", description = "Document management APIs")
// public class DocumentController {

//     private final DocumentService documentService;

//     @Autowired
//     public DocumentController(DocumentService documentService) {
//         this.documentService = documentService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Documents", description = "Retrieve a list of all documents")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Document>> getAllDocuments() {
//         List<Document> documents = documentService.findAll();
//         return ResponseEntity.ok(documents);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Document by ID", description = "Retrieve a specific document by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved document"),
//         @ApiResponse(responseCode = "404", description = "Document not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Document> getDocumentById(
//             @Parameter(description = "Document ID") @PathVariable String id) {
//         try {
//             Document document = documentService.findById(id);
//             return ResponseEntity.ok(document);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Document", description = "Create a new document")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Document created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Document> createDocument(
//             @Parameter(description = "Document object") @Valid @RequestBody Document document) {
//         try {
//             Document createdDocument = documentService.save(document);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Document", description = "Update an existing document")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Document updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Document not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Document> updateDocument(
//             @Parameter(description = "Document ID") @PathVariable String id,
//             @Parameter(description = "Updated Document object") @Valid @RequestBody Document document) {
//         try {
//             Document updatedDocument = documentService.update(id, document);
//             return ResponseEntity.ok(updatedDocument);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Document", description = "Delete a document by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Document deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Document not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete document as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteDocument(
//             @Parameter(description = "Document ID") @PathVariable String id) {
//         try {
//             documentService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Documents", description = "Retrieve all active documents")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Document>> getActiveDocuments() {
//         List<Document> activeDocuments = documentService.findByIsActive();
//         return ResponseEntity.ok(activeDocuments);
//     }

//     @GetMapping("/source/{documentSourceId}")
//     @Operation(summary = "Get Documents by Source", description = "Retrieve all documents by document source ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Document>> getDocumentsBySource(
//             @Parameter(description = "Document Source ID") @PathVariable String documentSourceId) {
//         List<Document> documents = documentService.findByDocumentSourceId(documentSourceId);
//         return ResponseEntity.ok(documents);
//     }
// }
