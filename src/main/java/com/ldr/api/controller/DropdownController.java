package com.ldr.api.controller;

import com.ldr.api.dto.DropdownDto;
import com.ldr.api.repository.*;
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

    private final OrderStatusRepository orderStatusRepository;
    private final PriorityRepository priorityRepository;
    private final CooperationTypeRepository cooperationTypeRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final EmploymentStatusRepository employmentStatusRepository;
    private final ServiceCostTypeRepository serviceCostTypeRepository;
    private final DocumentSourceRepository documentSourceRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public DropdownController(
            OrderStatusRepository orderStatusRepository,
            PriorityRepository priorityRepository,
            CooperationTypeRepository cooperationTypeRepository,
            DocumentTypeRepository documentTypeRepository,
            EmploymentStatusRepository employmentStatusRepository,
            ServiceCostTypeRepository serviceCostTypeRepository,
            DocumentSourceRepository documentSourceRepository,
            EntityMapper entityMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.priorityRepository = priorityRepository;
        this.cooperationTypeRepository = cooperationTypeRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.employmentStatusRepository = employmentStatusRepository;
        this.serviceCostTypeRepository = serviceCostTypeRepository;
        this.documentSourceRepository = documentSourceRepository;
        this.entityMapper = entityMapper;
    }

    @GetMapping("/{code}")
    @Operation(summary = "Get dropdown options by reference table code",
               description = "Retrieve dropdown options for the specified reference table code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved dropdown options"),
        @ApiResponse(responseCode = "400", description = "Invalid reference table code"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<DropdownDto>> getDropdownOptions(
            @Parameter(description = "Reference table code (order-status, priority, cooperation-type, document-type, employment-status, service-cost-type, document-source)")
            @PathVariable String code) {

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
        return orderStatusRepository.findByIsActive(true).stream()
                .map(status -> new DropdownDto(status.getId(), status.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getPriorityOptions() {
        return priorityRepository.findByIsActive(true).stream()
                .map(priority -> new DropdownDto(priority.getId(), priority.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getCooperationTypeOptions() {
        return cooperationTypeRepository.findByIsActive(true).stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getDocumentTypeOptions() {
        return documentTypeRepository.findByIsActive(true).stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getEmploymentStatusOptions() {
        return employmentStatusRepository.findByIsActive(true).stream()
                .map(status -> new DropdownDto(status.getId(), status.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getServiceCostTypeOptions() {
        return serviceCostTypeRepository.findByIsActive(true).stream()
                .map(type -> new DropdownDto(type.getId(), type.getName()))
                .collect(Collectors.toList());
    }

    private List<DropdownDto> getDocumentSourceOptions() {
        return documentSourceRepository.findByIsActive(true).stream()
                .map(source -> new DropdownDto(source.getId(), source.getName()))
                .collect(Collectors.toList());
    }
}