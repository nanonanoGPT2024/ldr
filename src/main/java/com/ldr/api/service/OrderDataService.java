package com.ldr.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderApproval;
import com.ldr.api.model.OrderData;
import com.ldr.api.model.User;
import com.ldr.api.model.Workflow;
import com.ldr.api.repository.OrderApprovalRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.UserRepository;
import com.ldr.api.repository.WorkflowRepository;

@Service
@Transactional
public class OrderDataService {

    private final OrderDataRepository orderDataRepository;
    private final WorkflowRepository workflowRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDataService(OrderDataRepository orderDataRepository,
                           WorkflowRepository workflowRepository,
                           OrderApprovalRepository orderApprovalRepository,
                           UserRepository userRepository) {
        this.orderDataRepository = orderDataRepository;
        this.workflowRepository = workflowRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all OrderData entities with pagination and search
     * @param searchTerm search term
     * @param statusId status filter
     * @param priorityId priority filter
     * @param requestorId requestor filter
     * @param startDate date from filter
     * @param endDate date to filter
     * @param pageable pagination info
     * @return Page<OrderData>
     */
    @Transactional(readOnly = true)
    public Page<OrderData> findAllWithFilters(String searchTerm, String statusId, String priorityId,
                                             String requestorId, String currentRole, LocalDate startDate, LocalDate endDate,
                                             Pageable pageable) {
        return orderDataRepository.findWithFilters(searchTerm, statusId, priorityId, requestorId, currentRole,
                                                 startDate, endDate, pageable);
    }

    /**
     * Find all OrderData entities (legacy method)
     * @return List<OrderData>
     */
    @Transactional(readOnly = true)
    public List<OrderData> findAll() {
        return orderDataRepository.findAll();
    }

    /**
     * Find OrderData by ID
     * @param id the OrderData ID
     * @return OrderData
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public OrderData findById(String id) {
        return orderDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderData not found with id: " + id));
    }

    /**
     * Save a new OrderData
     * @param orderData the OrderData to save
     * @return saved OrderData
     * @throws ValidationException if validation fails
     */
    public OrderData save(OrderData orderData) {
        try {
            return orderDataRepository.save(orderData);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save OrderData due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderData
     * @param id the OrderData ID
     * @param orderData the updated OrderData
     * @return updated OrderData
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public OrderData update(String id, OrderData orderData) {
        OrderData existingOrderData = findById(id);

        // Update fields
        existingOrderData.setOrderNumber(orderData.getOrderNumber());
        existingOrderData.setTitle(orderData.getTitle());
        existingOrderData.setDescription(orderData.getDescription());
        existingOrderData.setClientName(orderData.getClientName());
        existingOrderData.setClientTradeName(orderData.getClientTradeName());
        existingOrderData.setClientContactPerson(orderData.getClientContactPerson());
        existingOrderData.setClientEmail(orderData.getClientEmail());
        existingOrderData.setClientPhone(orderData.getClientPhone());
        existingOrderData.setRequestor(orderData.getRequestor());
        existingOrderData.setRequestorName(orderData.getRequestorName());
        existingOrderData.setRequestorEmail(orderData.getRequestorEmail());
        existingOrderData.setRequestorDepartment(orderData.getRequestorDepartment());
        existingOrderData.setCooperationType(orderData.getCooperationType());
        existingOrderData.setDocumentType(orderData.getDocumentType());
        existingOrderData.setCooperationPeriod(orderData.getCooperationPeriod());
        existingOrderData.setEmploymentStatus(orderData.getEmploymentStatus());
        existingOrderData.setPosition(orderData.getPosition());
        existingOrderData.setServiceCostType(orderData.getServiceCostType());
        existingOrderData.setServiceCostDescription(orderData.getServiceCostDescription());
        existingOrderData.setPaymentTerms(orderData.getPaymentTerms());
        existingOrderData.setTaxInfo(orderData.getTaxInfo());
        existingOrderData.setPenaltyClause(orderData.getPenaltyClause());
        existingOrderData.setContractValue(orderData.getContractValue());
        existingOrderData.setDocumentSource(orderData.getDocumentSource());
        existingOrderData.setAdditionalNotes(orderData.getAdditionalNotes());
        existingOrderData.setCurrentStatusCode(orderData.getCurrentStatusCode());
        existingOrderData.setWorkflow(orderData.getWorkflow());
        existingOrderData.setPriority(orderData.getPriority());
        existingOrderData.setAssignedTo(orderData.getAssignedTo());
        existingOrderData.setCurrentRole(orderData.getCurrentRole());
        existingOrderData.setSubmissionDate(orderData.getSubmissionDate());
        existingOrderData.setDeadlineDate(orderData.getDeadlineDate());
        existingOrderData.setCompletionDate(orderData.getCompletionDate());
        existingOrderData.setDeleted(orderData.isDeleted());

        try {
            return orderDataRepository.save(existingOrderData);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update OrderData due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderData by ID
     * @param id the OrderData ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        OrderData orderData = findById(id);
        try {
            orderDataRepository.delete(orderData);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete OrderData as it is referenced by other records: " + id);
        }
    }


    /**
     * Find OrderData by deleted status
     * @param isDeleted the deleted status
     * @return List<OrderData>
     */
    @Transactional(readOnly = true)
    public List<OrderData> findByIsDeleted(boolean isDeleted) {
        return orderDataRepository.findByIsDeleted(isDeleted);
    }

    /**
     * Find Workflow by name
     * @param name the workflow name
     * @return Workflow
     */
    @Transactional(readOnly = true)
    public Workflow findWorkflowByName(String name) {
        return workflowRepository.findByNama(name);
    }

    /**
     * Approve order and create approval record
     * @param orderId the order ID
     * @param approverUsername the username of the approver
     * @param approverRole the role of the approver from JWT
     * @return updated OrderData
     * @throws ResourceNotFoundException if order not found
     */
    public OrderData approveOrder(String orderId, String approverUsername, String approverRole) {
        OrderData orderData = findById(orderId);

        // Find approver user
        User approver = userRepository.findByUsername(approverUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Approver user not found: " + approverUsername));

        // Update order data fields
        orderData.setCurrentRole("bd");
        orderData.setCurrentStatusCode("IN_PROGRESS");

        // Save the updated order
        OrderData updatedOrder = orderDataRepository.save(orderData);

        // Create order approval record
        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setId(java.util.UUID.randomUUID().toString());
        orderApproval.setOrder(updatedOrder);
        orderApproval.setApprover(approver);
        orderApproval.setApproverRole(approverRole); // Use role from JWT
        orderApproval.setStatus("APPROVED");
        orderApproval.setApprovedAt(java.time.LocalDateTime.now());

        orderApprovalRepository.save(orderApproval);

        return updatedOrder;
    }

}
