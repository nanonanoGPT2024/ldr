// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.EmploymentStatus;
// import com.ldr.api.service.EmploymentStatusService;
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
// @RequestMapping("/api/employment-statuses")
// @Tag(name = "Employment Status", description = "Employment Status management APIs")
// public class EmploymentStatusController {

//     private final EmploymentStatusService employmentStatusService;

//     @Autowired
//     public EmploymentStatusController(EmploymentStatusService employmentStatusService) {
//         this.employmentStatusService = employmentStatusService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Employment Status", description = "Retrieve a list of all employment statuses")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<EmploymentStatus>> getAllEmploymentStatuses() {
//         List<EmploymentStatus> employmentStatuses = employmentStatusService.findAll();
//         return ResponseEntity.ok(employmentStatuses);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Employment Status by ID", description = "Retrieve a specific employment status by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved employment status"),
//         @ApiResponse(responseCode = "404", description = "Employment status not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<EmploymentStatus> getEmploymentStatusById(
//             @Parameter(description = "Employment Status ID") @PathVariable String id) {
//         try {
//             EmploymentStatus employmentStatus = employmentStatusService.findById(id);
//             return ResponseEntity.ok(employmentStatus);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Employment Status", description = "Create a new employment status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Employment status created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<EmploymentStatus> createEmploymentStatus(
//             @Parameter(description = "Employment Status object") @Valid @RequestBody EmploymentStatus employmentStatus) {
//         try {
//             EmploymentStatus createdEmploymentStatus = employmentStatusService.save(employmentStatus);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdEmploymentStatus);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Employment Status", description = "Update an existing employment status")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Employment status updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Employment status not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<EmploymentStatus> updateEmploymentStatus(
//             @Parameter(description = "Employment Status ID") @PathVariable String id,
//             @Parameter(description = "Updated Employment Status object") @Valid @RequestBody EmploymentStatus employmentStatus) {
//         try {
//             EmploymentStatus updatedEmploymentStatus = employmentStatusService.update(id, employmentStatus);
//             return ResponseEntity.ok(updatedEmploymentStatus);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Employment Status", description = "Delete an employment status by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Employment status deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Employment status not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete employment status as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteEmploymentStatus(
//             @Parameter(description = "Employment Status ID") @PathVariable String id) {
//         try {
//             employmentStatusService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Employment Status", description = "Retrieve all active employment statuses")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<EmploymentStatus>> getActiveEmploymentStatuses() {
//         List<EmploymentStatus> activeEmploymentStatuses = employmentStatusService.findByIsActive();
//         return ResponseEntity.ok(activeEmploymentStatuses);
//     }
// }
