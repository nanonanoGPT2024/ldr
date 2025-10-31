// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.WorkflowDetail;
// import com.ldr.api.service.WorkflowDetailService;
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
// @RequestMapping("/api/workflow-details")
// @Tag(name = "WorkflowDetail", description = "WorkflowDetail management APIs")
// public class WorkflowDetailController {

//     private final WorkflowDetailService workflowDetailService;

//     @Autowired
//     public WorkflowDetailController(WorkflowDetailService workflowDetailService) {
//         this.workflowDetailService = workflowDetailService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all WorkflowDetails", description = "Retrieve a list of all workflow details")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<WorkflowDetail>> getAllWorkflowDetails() {
//         List<WorkflowDetail> workflowDetails = workflowDetailService.findAll();
//         return ResponseEntity.ok(workflowDetails);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get WorkflowDetail by ID", description = "Retrieve a specific workflow detail by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved workflow detail"),
//         @ApiResponse(responseCode = "404", description = "WorkflowDetail not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<WorkflowDetail> getWorkflowDetailById(
//             @Parameter(description = "WorkflowDetail ID") @PathVariable String id) {
//         try {
//             WorkflowDetail workflowDetail = workflowDetailService.findById(id);
//             return ResponseEntity.ok(workflowDetail);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create WorkflowDetail", description = "Create a new workflow detail")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "WorkflowDetail created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<WorkflowDetail> createWorkflowDetail(
//             @Parameter(description = "WorkflowDetail object") @Valid @RequestBody WorkflowDetail workflowDetail) {
//         try {
//             WorkflowDetail createdWorkflowDetail = workflowDetailService.save(workflowDetail);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkflowDetail);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update WorkflowDetail", description = "Update an existing workflow detail")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "WorkflowDetail updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "WorkflowDetail not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<WorkflowDetail> updateWorkflowDetail(
//             @Parameter(description = "WorkflowDetail ID") @PathVariable String id,
//             @Parameter(description = "Updated WorkflowDetail object") @Valid @RequestBody WorkflowDetail workflowDetail) {
//         try {
//             WorkflowDetail updatedWorkflowDetail = workflowDetailService.update(id, workflowDetail);
//             return ResponseEntity.ok(updatedWorkflowDetail);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete WorkflowDetail", description = "Delete a workflow detail by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "WorkflowDetail deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "WorkflowDetail not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete workflow detail as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteWorkflowDetail(
//             @Parameter(description = "WorkflowDetail ID") @PathVariable String id) {
//         try {
//             workflowDetailService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/workflow/{workflowId}")
//     @Operation(summary = "Get WorkflowDetails by Workflow ID", description = "Retrieve workflow details by workflow ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<WorkflowDetail>> getWorkflowDetailsByWorkflowId(
//             @Parameter(description = "Workflow ID") @PathVariable String workflowId) {
//         List<WorkflowDetail> workflowDetails = workflowDetailService.findByWorkflowId(workflowId);
//         return ResponseEntity.ok(workflowDetails);
//     }

//     @GetMapping("/stage/{currentStage}")
//     @Operation(summary = "Get WorkflowDetails by Current Stage", description = "Retrieve workflow details by current stage")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<WorkflowDetail>> getWorkflowDetailsByCurrentStage(
//             @Parameter(description = "Current Stage") @PathVariable String currentStage) {
//         List<WorkflowDetail> workflowDetails = workflowDetailService.findByCurrentStage(currentStage);
//         return ResponseEntity.ok(workflowDetails);
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active WorkflowDetails", description = "Retrieve all active workflow details")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<WorkflowDetail>> getActiveWorkflowDetails() {
//         List<WorkflowDetail> activeWorkflowDetails = workflowDetailService.findByIsActive(true);
//         return ResponseEntity.ok(activeWorkflowDetails);
//     }
// }
