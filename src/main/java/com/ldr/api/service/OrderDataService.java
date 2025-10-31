package com.ldr.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldr.api.dto.CreateOrderCommentRequest;
import com.ldr.api.dto.OrderActivityResponse;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.OrderApproval;
import com.ldr.api.model.OrderAttachment;
import com.ldr.api.model.OrderComment;
import com.ldr.api.model.OrderData;
import com.ldr.api.dto.OrderActionRequest;
import com.ldr.api.model.AssignmentType;
import com.ldr.api.model.OrderAssignmentHistory;
import com.ldr.api.model.OrderStatus;
import com.ldr.api.model.OrderStatusHistory;
import com.ldr.api.model.User;
import com.ldr.api.model.Workflow;
import com.ldr.api.model.WorkflowDetail;
import com.ldr.api.repository.OrderApprovalRepository;
import com.ldr.api.repository.OrderAssignmentHistoryRepository;
import com.ldr.api.repository.OrderAttachmentRepository;
import com.ldr.api.repository.OrderCommentRepository;
import com.ldr.api.repository.OrderDataRepository;
import com.ldr.api.repository.OrderStatusHistoryRepository;
import com.ldr.api.repository.OrderStatusRepository;
import com.ldr.api.repository.UserRepository;
import com.ldr.api.repository.WorkflowDetailRepository;
import com.ldr.api.repository.WorkflowRepository;
import com.ldr.api.service.OrderCommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderDataService {

    private final OrderDataRepository orderDataRepository;
    private final WorkflowRepository workflowRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final UserRepository userRepository;
    private final OrderCommentRepository orderCommentRepository;
    private final OrderAttachmentRepository orderAttachmentRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final WorkflowDetailRepository workflowDetailRepository;
    private final OrderAssignmentHistoryRepository orderAssignmentHistoryRepository;
    private final OrderCommentService orderCommentService;

    @Autowired
    public OrderDataService(OrderDataRepository orderDataRepository,
            WorkflowRepository workflowRepository,
            OrderApprovalRepository orderApprovalRepository,
            UserRepository userRepository,
            OrderCommentRepository orderCommentRepository,
            OrderAttachmentRepository orderAttachmentRepository,
            OrderStatusRepository orderStatusRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            WorkflowDetailRepository workflowDetailRepository,
            OrderAssignmentHistoryRepository orderAssignmentHistoryRepository,
            OrderCommentService orderCommentService) {
        this.orderDataRepository = orderDataRepository;
        this.workflowRepository = workflowRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.userRepository = userRepository;
        this.orderCommentRepository = orderCommentRepository;
        this.orderAttachmentRepository = orderAttachmentRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.workflowDetailRepository = workflowDetailRepository;
        this.orderAssignmentHistoryRepository = orderAssignmentHistoryRepository;
        this.orderCommentService = orderCommentService;
    }

    /**
     * Find all OrderData entities with pagination and search
     * 
     * @param searchTerm  search term
     * @param statusId    status filter
     * @param priorityId  priority filter
     * @param requestorId requestor filter
     * @param startDate   date from filter
     * @param endDate     date to filter
     * @param pageable    pagination info
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
     * 
     * @return List<OrderData>
     */
    @Transactional(readOnly = true)
    public List<OrderData> findAll() {
        return orderDataRepository.findAll();
    }

    /**
     * Find OrderData by ID
     * 
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
     * 
     * @param orderData the OrderData to save
     * @return saved OrderData
     * @throws ValidationException if validation fails
     */
    public OrderData save(OrderData orderData) {
        try {
            return orderDataRepository.save(orderData);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(
                    "Failed to save OrderData due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing OrderData
     * 
     * @param id        the OrderData ID
     * @param orderData the updated OrderData
     * @return updated OrderData
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException       if validation fails
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
            throw new ValidationException(
                    "Failed to update OrderData due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete OrderData by ID
     * 
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
     * 
     * @param isDeleted the deleted status
     * @return List<OrderData>
     */
    @Transactional(readOnly = true)
    public List<OrderData> findByIsDeleted(boolean isDeleted) {
        return orderDataRepository.findByIsDeleted(isDeleted);
    }

    /**
     * Find Workflow by name
     * 
     * @param name the workflow name
     * @return Workflow
     */
    @Transactional(readOnly = true)
    public Workflow findWorkflowByName(String name) {
        return workflowRepository.findByNama(name);
    }

    /**
     * Approve order and create approval record with status history
     *
     * @param orderId          the order ID
     * @param approverUsername the username of the approver
     * @param approverRole     the role of the approver from JWT
     * @return updated OrderData
     * @throws ResourceNotFoundException if order not found
     */
    public OrderData approveOrder(String orderId, String approverUsername, String approverRole) {
        OrderData orderData = findById(orderId);

        // Find approver user
        User approver = userRepository.findByUsername(approverUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Approver user not found: " + approverUsername));

        // Find status codes
        OrderStatus fromStatus = orderStatusRepository.findByCode("SUBMITTED")
                .orElseThrow(() -> new ResourceNotFoundException("Status 'SUBMITTED' not found"));
        OrderStatus toStatus = orderStatusRepository.findByCode("APPROVED")
                .orElseThrow(() -> new ResourceNotFoundException("Status 'APPROVED' not found"));

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

        // Create order status history record
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setId(java.util.UUID.randomUUID().toString());
        statusHistory.setOrder(updatedOrder);
        statusHistory.setFromStatus(fromStatus);
        statusHistory.setToStatus(toStatus);
        statusHistory.setChangedBy(approver);
        statusHistory.setChangeReason("Order approved by " + approverRole + " - " + approverUsername);

        orderStatusHistoryRepository.save(statusHistory);

        return updatedOrder;
    }

    /**
     * Get order activity data including comments and attachments with document info
     * 
     * @param orderId the order ID
     * @return OrderActivityResponse containing comments and attachment activities
     */
    @Transactional(readOnly = true)
    public OrderActivityResponse getOrderActivity(String orderId) {
        // Validate order exists
        OrderData order = findById(orderId);

        // Get all comments for the order
        List<OrderComment> comments = orderCommentRepository.findByOrderId(orderId);

        // Get attachment activities with document info using custom query
        List<OrderActivityResponse.OrderAttachmentActivity> attachmentActivities = getAttachmentActivities(orderId);

        return new OrderActivityResponse(comments, attachmentActivities);
    }

    /**
     * Get attachment activities with document information for an order
     *
     * @param orderId the order ID
     * @return List<OrderAttachmentActivity>
     */
    @Transactional(readOnly = true)
    public List<OrderActivityResponse.OrderAttachmentActivity> getAttachmentActivities(String orderId) {
        // This would typically use a custom repository method or @Query
        // For now, we'll implement it using existing repository methods
        List<OrderAttachment> attachments = orderAttachmentRepository.findByOrderId(orderId);

        return attachments.stream()
                .filter(attachment -> attachment.getDocument() != null)
                .map(attachment -> new OrderActivityResponse.OrderAttachmentActivity(
                        attachment.getDocument().getName(),
                        attachment.getCreatedAt(),
                        attachment.getKeterangan()))
                .collect(Collectors.toList());
    }

    /**
     * Execute workflow action (next-stage, return-stage, reject-stage)
     *
     * @param actionStage the action stage (next-stage, return-stage, reject-stage)
     * @param request     the order action request containing orderId and
     *                    commentText
     * @param username    the username from JWT token
     * @param role        the role from JWT token
     * @return updated OrderData
     * @throws ResourceNotFoundException if order or workflow detail not found
     * @throws ValidationException       if validation fails
     */
    public OrderData executeWorkflowAction(String actionStage, OrderActionRequest request, String username,
            String role) {
        // Find order
        OrderData order = findById(request.getOrderId());

        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        // Get workflow ID from order
        String workflowId = order.getWorkflow().getId();

        // Find workflow detail based on current role
        WorkflowDetail workflowDetail = workflowDetailRepository.findByWorkflowId(workflowId).stream()
                .filter(wfd -> wfd.getCurrentStage().equals(role) && wfd.isActive())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Workflow detail not found for role: " + role + " in workflow: " + workflowId));

        // Determine next stage based on action
        String nextStage;
        AssignmentType assignmentType;

        switch (actionStage.toLowerCase()) {
            case "next-stage":
                nextStage = workflowDetail.getNextStage();
                assignmentType = AssignmentType.NEXT;
                break;
            case "return-stage":
                nextStage = workflowDetail.getReturnStage();
                assignmentType = AssignmentType.RETURN;
                break;
            case "reject-stage":
                nextStage = workflowDetail.getRejectStage();
                assignmentType = AssignmentType.REJECT;
                break;
            default:
                throw new ValidationException("Invalid action stage: " + actionStage);
        }

        if (nextStage == null || nextStage.trim().isEmpty()) {
            throw new ValidationException("No " + actionStage + " defined for current role: " + role);
        }

        // Update order current role
        order.setCurrentRole(nextStage);
        OrderData updatedOrder = orderDataRepository.save(order);

        // Create order comment record
        CreateOrderCommentRequest commentRequest = new CreateOrderCommentRequest();
        commentRequest.setOrderId(updatedOrder.getId());
        commentRequest.setUserId(user.getId());
        commentRequest.setCommentType(actionStage.toLowerCase());
        commentRequest.setCommentText(request.getCommentText());

        orderCommentService.save(commentRequest);

        // Create order assignment history record
        OrderAssignmentHistory assignmentHistory = new OrderAssignmentHistory();
        assignmentHistory.setId(java.util.UUID.randomUUID().toString());
        assignmentHistory.setOrder(updatedOrder);
        assignmentHistory.setAssignedToRole(nextStage); // Assign to the role (next stage)
        assignmentHistory.setAssignedByRole(role); // Role performing the action
        assignmentHistory.setAssignmentType(assignmentType);
        assignmentHistory
                .setNotes(request.getCommentText() + " - Action: " + actionStage + " - Assigned to role: " + nextStage);

        orderAssignmentHistoryRepository.save(assignmentHistory);

        return updatedOrder;
    }

}
