// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.CooperationType;
// import com.ldr.api.service.CooperationTypeService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/cooperation-types")
// @Tag(name = "Cooperation Type", description = "Cooperation Type management APIs")
// @SecurityRequirement(name = "bearerAuth")
// public class CooperationTypeController {

//     private final CooperationTypeService cooperationTypeService;

//     @Autowired
//     public CooperationTypeController(CooperationTypeService cooperationTypeService) {
//         this.cooperationTypeService = cooperationTypeService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Cooperation Types", description = "Retrieve a list of all cooperation types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<CooperationType>> getAllCooperationTypes() {
//         List<CooperationType> cooperationTypes = cooperationTypeService.findAll();
//         return ResponseEntity.ok(cooperationTypes);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Cooperation Type by ID", description = "Retrieve a specific cooperation type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved cooperation type"),
//         @ApiResponse(responseCode = "404", description = "Cooperation type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<CooperationType> getCooperationTypeById(
//             @Parameter(description = "Cooperation Type ID") @PathVariable String id) {
//         try {
//             CooperationType cooperationType = cooperationTypeService.findById(id);
//             return ResponseEntity.ok(cooperationType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Cooperation Type", description = "Create a new cooperation type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Cooperation type created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<CooperationType> createCooperationType(
//             @Parameter(description = "Cooperation Type object") @Valid @RequestBody CooperationType cooperationType) {
//         try {
//             CooperationType createdCooperationType = cooperationTypeService.save(cooperationType);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdCooperationType);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Cooperation Type", description = "Update an existing cooperation type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Cooperation type updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Cooperation type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<CooperationType> updateCooperationType(
//             @Parameter(description = "Cooperation Type ID") @PathVariable String id,
//             @Parameter(description = "Updated Cooperation Type object") @Valid @RequestBody CooperationType cooperationType) {
//         try {
//             CooperationType updatedCooperationType = cooperationTypeService.update(id, cooperationType);
//             return ResponseEntity.ok(updatedCooperationType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Cooperation Type", description = "Delete a cooperation type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Cooperation type deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Cooperation type not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete cooperation type as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteCooperationType(
//             @Parameter(description = "Cooperation Type ID") @PathVariable String id) {
//         try {
//             cooperationTypeService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Cooperation Types", description = "Retrieve all active cooperation types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<CooperationType>> getActiveCooperationTypes() {
//         List<CooperationType> activeCooperationTypes = cooperationTypeService.findByIsActive();
//         return ResponseEntity.ok(activeCooperationTypes);
//     }
// }
