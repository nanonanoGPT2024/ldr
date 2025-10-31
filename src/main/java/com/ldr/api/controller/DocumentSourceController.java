// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.DocumentSource;
// import com.ldr.api.service.DocumentSourceService;
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
// @RequestMapping("/api/document-sources")
// @Tag(name = "Document Source", description = "Document Source management APIs")
// public class DocumentSourceController {

//     private final DocumentSourceService documentSourceService;

//     @Autowired
//     public DocumentSourceController(DocumentSourceService documentSourceService) {
//         this.documentSourceService = documentSourceService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Document Sources", description = "Retrieve a list of all document sources")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<DocumentSource>> getAllDocumentSources() {
//         List<DocumentSource> documentSources = documentSourceService.findAll();
//         return ResponseEntity.ok(documentSources);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Document Source by ID", description = "Retrieve a specific document source by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved document source"),
//         @ApiResponse(responseCode = "404", description = "Document source not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentSource> getDocumentSourceById(
//             @Parameter(description = "Document Source ID") @PathVariable String id) {
//         try {
//             DocumentSource documentSource = documentSourceService.findById(id);
//             return ResponseEntity.ok(documentSource);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Document Source", description = "Create a new document source")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Document source created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentSource> createDocumentSource(
//             @Parameter(description = "Document Source object") @Valid @RequestBody DocumentSource documentSource) {
//         try {
//             DocumentSource createdDocumentSource = documentSourceService.save(documentSource);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdDocumentSource);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Document Source", description = "Update an existing document source")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Document source updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Document source not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<DocumentSource> updateDocumentSource(
//             @Parameter(description = "Document Source ID") @PathVariable String id,
//             @Parameter(description = "Updated Document Source object") @Valid @RequestBody DocumentSource documentSource) {
//         try {
//             DocumentSource updatedDocumentSource = documentSourceService.update(id, documentSource);
//             return ResponseEntity.ok(updatedDocumentSource);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Document Source", description = "Delete a document source by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Document source deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Document source not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete document source as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteDocumentSource(
//             @Parameter(description = "Document Source ID") @PathVariable String id) {
//         try {
//             documentSourceService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Document Sources", description = "Retrieve all active document sources")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<DocumentSource>> getActiveDocumentSources() {
//         List<DocumentSource> activeDocumentSources = documentSourceService.findByIsActive();
//         return ResponseEntity.ok(activeDocumentSources);
//     }
// }
