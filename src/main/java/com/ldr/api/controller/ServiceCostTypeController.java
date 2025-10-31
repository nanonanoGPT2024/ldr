// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.ServiceCostType;
// import com.ldr.api.service.ServiceCostTypeService;
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
// @RequestMapping("/api/service-cost-types")
// @Tag(name = "Service Cost Type", description = "Service Cost Type management APIs")
// public class ServiceCostTypeController {

//     private final ServiceCostTypeService serviceCostTypeService;

//     @Autowired
//     public ServiceCostTypeController(ServiceCostTypeService serviceCostTypeService) {
//         this.serviceCostTypeService = serviceCostTypeService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Service Cost Types", description = "Retrieve a list of all service cost types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<ServiceCostType>> getAllServiceCostTypes() {
//         List<ServiceCostType> serviceCostTypes = serviceCostTypeService.findAll();
//         return ResponseEntity.ok(serviceCostTypes);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get Service Cost Type by ID", description = "Retrieve a specific service cost type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved service cost type"),
//         @ApiResponse(responseCode = "404", description = "Service cost type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<ServiceCostType> getServiceCostTypeById(
//             @Parameter(description = "Service Cost Type ID") @PathVariable String id) {
//         try {
//             ServiceCostType serviceCostType = serviceCostTypeService.findById(id);
//             return ResponseEntity.ok(serviceCostType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create Service Cost Type", description = "Create a new service cost type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "Service cost type created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<ServiceCostType> createServiceCostType(
//             @Parameter(description = "Service Cost Type object") @Valid @RequestBody ServiceCostType serviceCostType) {
//         try {
//             ServiceCostType createdServiceCostType = serviceCostTypeService.save(serviceCostType);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceCostType);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update Service Cost Type", description = "Update an existing service cost type")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Service cost type updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "Service cost type not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<ServiceCostType> updateServiceCostType(
//             @Parameter(description = "Service Cost Type ID") @PathVariable String id,
//             @Parameter(description = "Updated Service Cost Type object") @Valid @RequestBody ServiceCostType serviceCostType) {
//         try {
//             ServiceCostType updatedServiceCostType = serviceCostTypeService.update(id, serviceCostType);
//             return ResponseEntity.ok(updatedServiceCostType);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Service Cost Type", description = "Delete a service cost type by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "Service cost type deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "Service cost type not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete service cost type as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteServiceCostType(
//             @Parameter(description = "Service Cost Type ID") @PathVariable String id) {
//         try {
//             serviceCostTypeService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Service Cost Types", description = "Retrieve all active service cost types")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<ServiceCostType>> getActiveServiceCostTypes() {
//         List<ServiceCostType> activeServiceCostTypes = serviceCostTypeService.findByIsActive();
//         return ResponseEntity.ok(activeServiceCostTypes);
//     }
// }
