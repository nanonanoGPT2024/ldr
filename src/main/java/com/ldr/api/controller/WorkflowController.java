// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.Workflow;
// import com.ldr.api.service.WorkflowService;
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
// @RequestMapping("/api/workflows")
// @Tag(name = "Workflow", description = "Workflow management APIs")
// public class WorkflowController {

//     private final WorkflowService workflowService;

//     @Autowired
//     public WorkflowController(WorkflowService workflowService) {
//         this.workflowService = workflowService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Workflows", description = "Retrieve a list of all workflows")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Workflow>> getAllWorkflows() {
//         List<Workflow> workflows = workflowService.findAll();
//         return ResponseEntity.ok(workflows);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Workflow by ID", description = "Retrieve a specific workflow by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved workflow"),
//         @ApiResponse(responseCode = "404", description = "Workflow not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Workflow> getWorkflowById(
//             @Parameter(description = "Workflow ID") @PathVariable String id) {
//         try {
//             Workflow workflow = workflowService.findById(id);
//             return ResponseEntity.ok(workflow);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Workflow", description = "Create a new workflow")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Workflow created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Workflow> createWorkflow(
//             @Parameter(description = "Workflow object") @Valid @RequestBody Workflow workflow) {
//         try {
//             Workflow createdWorkflow = workflowService.save(workflow);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkflow);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Workflow", description = "Update an existing workflow")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Workflow updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Workflow not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Workflow> updateWorkflow(
//             @Parameter(description = "Workflow ID") @PathVariable String id,
//             @Parameter(description = "Updated Workflow object") @Valid @RequestBody Workflow workflow) {
//         try {
//             Workflow updatedWorkflow = workflowService.update(id, workflow);
//             return ResponseEntity.ok(updatedWorkflow);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Workflow", description = "Delete a workflow by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Workflow deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Workflow not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete workflow as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteWorkflow(
//             @Parameter(description = "Workflow ID") @PathVariable String id) {
//         try {
//             workflowService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Workflows", description = "Retrieve all active workflows")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<Workflow>> getActiveWorkflows() {
//         List<Workflow> activeWorkflows = workflowService.findByIsActive(true);
//         return ResponseEntity.ok(activeWorkflows);
//     }
// }
