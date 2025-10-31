package com.ldr.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ldr.api.dto.ApiPageResponse;
import com.ldr.api.dto.CreateOrderDataRequest;
import com.ldr.api.dto.OrderActivityResponse;
import com.ldr.api.dto.UpdateOrderDataRequest;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.Workflow;
import com.ldr.api.security.JwtUtil;
import com.ldr.api.service.OrderDataService;
import com.ldr.api.util.EntityMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "OrderData", description = "OrderData management APIs")
public class OrderDataController {

    private final OrderDataService orderDataService;
    private final EntityMapper entityMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public OrderDataController(OrderDataService orderDataService, EntityMapper entityMapper, JwtUtil jwtUtil) {
        this.orderDataService = orderDataService;
        this.entityMapper = entityMapper;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/request")
    @Operation(summary = "Get all OrderData for request view", description = "Retrieve paginated list of order data for request management view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiPageResponse<OrderData>> getAllOrdersRequest(
            @Parameter(description = "Authorization header") @RequestHeader("Authorization") String token,
            @Parameter(description = "Search term for title, description, or client name") @RequestParam(required = false) String search,
            @Parameter(description = "Filter by priority ID") @RequestParam(required = false) String priorityId,
            @Parameter(description = "Filter by requestor ID") @RequestParam(required = false) String requestorId,
            @Parameter(description = "Filter by submission date from (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Filter by submission date to (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "desc") String sortDir) {

        // Extract role from JWT token
        String role = jwtUtil.extractRole(token.replace("Bearer ", ""));

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Determine status based on role
        String statusId = null;
        if (role != null) {
            String roleLower = role.toLowerCase();
            if ("bd".equals(roleLower)) {
                statusId = "DRAFT";
            } else if ("legal".equals(roleLower)) {
                statusId = "SUBMITTED";
            }
        }

        Page<OrderData> orders = orderDataService.findAllWithFilters(search, statusId, priorityId,
                requestorId, null, startDate, endDate, pageable);

        ApiPageResponse<OrderData> response = new ApiPageResponse<>(true, "Request data retrieved successfully",
                orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tracking")
    @Operation(summary = "Get all OrderData for tracking view", description = "Retrieve paginated list of order data for tracking and monitoring view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiPageResponse<OrderData>> getAllOrdersTracking(
            @Parameter(description = "Authorization header") @RequestHeader("Authorization") String token,
            @Parameter(description = "Search term for title, description, or client name") @RequestParam(required = false) String search,
            @Parameter(description = "Filter by priority ID") @RequestParam(required = false) String priorityId,
            @Parameter(description = "Filter by submission date from (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Filter by deadline date to (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "deadlineDate") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir) {

        // Extract role from JWT token
        String role = jwtUtil.extractRole(token.replace("Bearer ", ""));

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Force status filter to IN_PROGRESS and current role filter
        String statusId = "IN_PROGRESS";

        Page<OrderData> orders = orderDataService.findAllWithFilters(search, statusId, priorityId,
                null, role, startDate, endDate, pageable);

        ApiPageResponse<OrderData> response = new ApiPageResponse<>(true, "Tracking data retrieved successfully",
                orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/final")
    @Operation(summary = "Get all OrderData for final approval view", description = "Retrieve paginated list of order data for final approval and completion view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiPageResponse<OrderData>> getAllOrdersFinal(
            @Parameter(description = "Search term for title, description, or client name") @RequestParam(required = false) String search,
            @Parameter(description = "Filter by priority ID") @RequestParam(required = false) String priorityId,
            @Parameter(description = "Filter by completion date from (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Filter by completion date to (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "completionDate") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "desc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Force status filter to COMPLETED for final view
        String statusId = "COMPLETED";

        Page<OrderData> orders = orderDataService.findAllWithFilters(search, statusId, priorityId,
                null, null, startDate, endDate, pageable);

        ApiPageResponse<OrderData> response = new ApiPageResponse<>(true, "Final data retrieved successfully", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get OrderData by ID", description = "Retrieve a specific order data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order data"),
            @ApiResponse(responseCode = "404", description = "OrderData not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderData> getOrderById(
            @Parameter(description = "OrderData ID") @PathVariable String id) {
        try {
            OrderData orderData = orderDataService.findById(id);
            return ResponseEntity.ok(orderData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create OrderData", description = "Create a new order data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OrderData created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderData> createOrder(
            @Parameter(description = "CreateOrderDataRequest object") @Valid @RequestBody CreateOrderDataRequest request) {
        try {
            OrderData orderData = entityMapper.mapToEntity(request, OrderData.class);

            // Set default workflow to 'LDR' if not provided
            if (orderData.getWorkflow() == null || orderData.getWorkflow().getId() == null) {
                Workflow defaultWorkflow = orderDataService.findWorkflowByName("LDR");
                if (defaultWorkflow != null) {
                    orderData.setWorkflow(defaultWorkflow);
                }
            }

            OrderData createdOrderData = orderDataService.save(orderData);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderData);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update OrderData", description = "Update an existing order data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderData updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "OrderData not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderData> updateOrder(
            @Parameter(description = "OrderData ID") @PathVariable String id,
            @Parameter(description = "UpdateOrderDataRequest object") @Valid @RequestBody UpdateOrderDataRequest request) {
        try {
            OrderData orderData = entityMapper.mapToEntity(request, OrderData.class);
            OrderData updatedOrderData = orderDataService.update(id, orderData);
            return ResponseEntity.ok(updatedOrderData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/approved/{orderId}")
    @Operation(summary = "Approve OrderData", description = "Approve an order data and update status to IN_PROGRESS with BD role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderData approved successfully"),
            @ApiResponse(responseCode = "404", description = "OrderData not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderData> approveOrder(
            @Parameter(description = "OrderData ID") @PathVariable String orderId,
            @Parameter(description = "Authorization header") @RequestHeader("Authorization") String token) {
        try {
            // Extract user and role from JWT token
            String approverUsername = jwtUtil.extractUsername(token.replace("Bearer ", ""));
            String approverRole = jwtUtil.extractRole(token.replace("Bearer ", ""));

            OrderData approvedOrder = orderDataService.approveOrder(orderId, approverUsername, approverRole);
            return ResponseEntity.ok(approvedOrder);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete OrderData", description = "Delete an order data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OrderData deleted successfully"),
            @ApiResponse(responseCode = "404", description = "OrderData not found"),
            @ApiResponse(responseCode = "409", description = "Cannot delete order data as it is referenced by other records"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "OrderData ID") @PathVariable String id) {
        try {
            orderDataService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/activity/{orderId}")
    @Operation(summary = "Get Order Activity", description = "Retrieve order activity including comments and attachment activities with document information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order activity"),
            @ApiResponse(responseCode = "404", description = "OrderData not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderActivityResponse> getOrderActivity(
            @Parameter(description = "OrderData ID") @PathVariable String orderId) {
        try {
            OrderActivityResponse activity = orderDataService.getOrderActivity(orderId);
            return ResponseEntity.ok(activity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
