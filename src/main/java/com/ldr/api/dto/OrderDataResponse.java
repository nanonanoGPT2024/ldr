package com.ldr.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDataResponse {

    private String id;
    private String orderNumber;
    private String title;
    private String description;

    // Client Information
    private String clientName;
    private String clientTradeName;
    private String clientContactPerson;
    private String clientEmail;
    private String clientPhone;

    // Requestor Information
    private UserDto requestor;
    private String requestorName;
    private String requestorEmail;
    private String requestorDepartment;

    // Cooperation Details
    private CooperationTypeDto cooperationType;
    private DocumentTypeDto documentType;
    private String cooperationPeriod;
    private EmploymentStatusDto employmentStatus;
    private String position;

    // Financial Information
    private ServiceCostTypeDto serviceCostType;
    private String serviceCostDescription;
    private String paymentTerms;
    private String taxInfo;
    private String penaltyClause;
    private BigDecimal contractValue;

    // Document Source
    private DocumentSourceDto documentSource;
    private String additionalNotes;

    // Workflow & Status
    private OrderStatusDto currentStatus;
    private WorkflowDto workflow;
    private PriorityDto priority;
    private UserDto assignedTo;
    private String currentRole;

    // Timeline Management
    private LocalDate submissionDate;
    private LocalDate deadlineDate;
    private LocalDate completionDate;

    // Metadata
    private Long version;
    private boolean isDeleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto createdBy;
    private UserDto updatedBy;

    // Constructors
    public OrderDataResponse() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientTradeName() {
        return clientTradeName;
    }

    public void setClientTradeName(String clientTradeName) {
        this.clientTradeName = clientTradeName;
    }

    public String getClientContactPerson() {
        return clientContactPerson;
    }

    public void setClientContactPerson(String clientContactPerson) {
        this.clientContactPerson = clientContactPerson;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public UserDto getRequestor() {
        return requestor;
    }

    public void setRequestor(UserDto requestor) {
        this.requestor = requestor;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestorEmail() {
        return requestorEmail;
    }

    public void setRequestorEmail(String requestorEmail) {
        this.requestorEmail = requestorEmail;
    }

    public String getRequestorDepartment() {
        return requestorDepartment;
    }

    public void setRequestorDepartment(String requestorDepartment) {
        this.requestorDepartment = requestorDepartment;
    }

    public CooperationTypeDto getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(CooperationTypeDto cooperationType) {
        this.cooperationType = cooperationType;
    }

    public DocumentTypeDto getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDto documentType) {
        this.documentType = documentType;
    }

    public String getCooperationPeriod() {
        return cooperationPeriod;
    }

    public void setCooperationPeriod(String cooperationPeriod) {
        this.cooperationPeriod = cooperationPeriod;
    }

    public EmploymentStatusDto getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatusDto employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ServiceCostTypeDto getServiceCostType() {
        return serviceCostType;
    }

    public void setServiceCostType(ServiceCostTypeDto serviceCostType) {
        this.serviceCostType = serviceCostType;
    }

    public String getServiceCostDescription() {
        return serviceCostDescription;
    }

    public void setServiceCostDescription(String serviceCostDescription) {
        this.serviceCostDescription = serviceCostDescription;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(String taxInfo) {
        this.taxInfo = taxInfo;
    }

    public String getPenaltyClause() {
        return penaltyClause;
    }

    public void setPenaltyClause(String penaltyClause) {
        this.penaltyClause = penaltyClause;
    }

    public BigDecimal getContractValue() {
        return contractValue;
    }

    public void setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
    }

    public DocumentSourceDto getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(DocumentSourceDto documentSource) {
        this.documentSource = documentSource;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public OrderStatusDto getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(OrderStatusDto currentStatus) {
        this.currentStatus = currentStatus;
    }

    public WorkflowDto getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowDto workflow) {
        this.workflow = workflow;
    }

    public PriorityDto getPriority() {
        return priority;
    }

    public void setPriority(PriorityDto priority) {
        this.priority = priority;
    }

    public UserDto getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserDto assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public UserDto getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserDto updatedBy) {
        this.updatedBy = updatedBy;
    }
}
