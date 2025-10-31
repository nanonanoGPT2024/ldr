// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.Priority;
// import com.ldr.api.service.PriorityService;
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
// @RequestMapping("/api/priorities")
// @Tag(name = "Priority", description = "Priority management APIs")
// public class PriorityController {

//     private final PriorityService priorityService;

//     @Autowired
//     public PriorityController(PriorityService priorityService) {
//         this.priorityService = priorityService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Priorities", description = "Retrieve a list of all priorities")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Priority>> getAllPriorities() {
//         List<Priority> priorities = priorityService.findAll();
//         return ResponseEntity.ok(priorities);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Priority by ID", description = "Retrieve a specific priority by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved priority"),
//         @ApiResponse(responseCode = "404", description = "Priority not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Priority> getPriorityById(
//             @Parameter(description = "Priority ID") @PathVariable String id) {
//         try {
//             Priority priority = priorityService.findById(id);
//             return ResponseEntity.ok(priority);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Priority", description = "Create a new priority")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Priority created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "409", description = "Priority code already exists"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Priority> createPriority(
//             @Parameter(description = "Priority object") @Valid @RequestBody Priority priority) {
//         try {
//             Priority createdPriority = priorityService.save(priority);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdPriority);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Priority", description = "Update an existing priority")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Priority updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Priority not found"),
//         @ApiResponse(responseCode = "409", description = "Priority code already exists"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Priority> updatePriority(
//             @Parameter(description = "Priority ID") @PathVariable String id,
//             @Parameter(description = "Updated Priority object") @Valid @RequestBody Priority priority) {
//         try {
//             Priority updatedPriority = priorityService.update(id, priority);
//             return ResponseEntity.ok(updatedPriority);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Priority", description = "Delete a priority by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Priority deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Priority not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete priority as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deletePriority(
//             @Parameter(description = "Priority ID") @PathVariable String id) {
//         try {
//             priorityService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/code/{code}")
//     @Operation(summary = "Get Priority by Code", description = "Retrieve a priority by its unique code")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved priority"),
//         @ApiResponse(responseCode = "404", description = "Priority not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Priority> getPriorityByCode(
//             @Parameter(description = "Priority Code") @PathVariable String code) {
//         try {
//             Priority priority = priorityService.findByCode(code);
//             return ResponseEntity.ok(priority);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Priorities", description = "Retrieve all active priorities")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Priority>> getActivePriorities() {
//         List<Priority> activePriorities = priorityService.findByIsActive();
//         return ResponseEntity.ok(activePriorities);
//     }
// }
