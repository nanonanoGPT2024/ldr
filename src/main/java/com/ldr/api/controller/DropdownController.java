package com.ldr.api.controller;

import com.ldr.api.dto.DropdownDto;
import com.ldr.api.service.*;
import com.ldr.api.util.EntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dropdown")
@Tag(name = "Dropdown", description = "Reference table dropdown endpoints")
@SecurityRequirement(name = "bearerAuth")
public class DropdownController {

    private final OrderStatusService orderStatusService;
    private final PriorityService priorityService;
    private final CooperationTypeService cooperationTypeService;
    private final DocumentTypeService documentTypeService;
    private final EmploymentStatusService employmentStatusService;
    private final ServiceCostTypeService serviceCostTypeService;
    private final DocumentSourceService documentSourceService;
    private final EntityMapper entityMapper;

    @Autowired
    public DropdownController(
            OrderStatusService orderStatusService,
            PriorityService priorityService,
            CooperationTypeService cooperationTypeService,
            DocumentTypeService documentTypeService,
            EmploymentStatusService employmentStatusService,
            ServiceCostTypeService serviceCostTypeService,
            DocumentSourceService documentSourceService,
            EntityMapper entityMapper) {
        this.orderStatusService = orderStatusService;
        this.priorityService = priorityService;
        this.cooperationTypeService = cooperationTypeService;
        this.documentTypeService = documentTypeService;
        this.employmentStatusService = employmentStatusService;
        this.serviceCostTypeService = serviceCostTypeService;
        this.documentSourceService = documentSourceService;
        this.entityMapper = entityMapper;
    }

    @GetMapping("/{code}")
    @Operation(summary = "Get dropdown options by reference table code", description = "Retrieve dropdown options for the specified reference table code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved dropdown options"),
            @ApiResponse(responseCode = "400", description = "Invalid reference table code"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DropdownDto>> getDropdownOptions(
            @Parameter(description = "Reference table code (order-status, priority, cooperation-type, document-type, employment-status, service-cost-type, document-source)") @PathVariable String code) {

        List<DropdownDto> options;

        switch (code.toLowerCase()) {
            case "order-status":
                options = getOrderStatusOptions();
                break;
            case "priority":
                options = getPriorityOptions();
                break;
            case "cooperation-type":
                options = getCooperationTypeOptions();
                break;
            case "document-type":
                options = getDocumentTypeOptions();
                break;
            case "employment-status":
                options = getEmploymentStatusOptions();
                break;
            case "service-cost-type":
                options = getServiceCostTypeOptions();
                break;
            case "document-source":
                options = getDocumentSourceOptions();
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(options);
    }

    private List<DropdownDto> getOrderStatusOptions() {
        return orderStatusService.findByIsActive().stream()
                .map(status -> new DropdownDto(status.getId(), status.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getPriorityOptions() {
        return priorityService.findByIsActive().stream()
                .map(priority -> new DropdownDto(priority.getId(), priority.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getCooperationTypeOptions() {
        return cooperationTypeService.findByIsActive().stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getDocumentTypeOptions() {
        return documentTypeService.findByIsActive().stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getEmploymentStatusOptions() {
        return employmentStatusService.findByIsActive().stream()
                .map(status -> new DropdownDto(status.getId(), status.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getServiceCostTypeOptions() {
        return serviceCostTypeService.findByIsActive().stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getDocumentSourceOptions() {
        return documentSourceService.findByIsActive().stream()
                .map(source -> new DropdownDto(source.getId(), source.getName()))
                .collect(Collectors.toList());
    }
}