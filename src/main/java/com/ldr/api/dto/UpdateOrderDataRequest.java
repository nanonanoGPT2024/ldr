package com.ldr.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateOrderDataRequest {

    @NotBlank(message = "ID cannot be blank")
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    // Client Information
    @NotBlank(message = "Client name cannot be blank")
    @Size(max = 255, message = "Client name must not exceed 255 characters")
    private String clientName;

    @Size(max = 255, message = "Client trade name must not exceed 255 characters")
    private String clientTradeName;

    @Size(max = 255, message = "Client contact person must not exceed 255 characters")
    private String clientContactPerson;

    @Email(message = "Client email must be valid")
    @Size(max = 255, message = "Client email must not exceed 255 characters")
    private String clientEmail;

    @Size(max = 50, message = "Client phone must not exceed 50 characters")
    private String clientPhone;

    // Cooperation Details
    @NotBlank(message = "Cooperation type ID cannot be blank")
    private String cooperationTypeId;

    @NotBlank(message = "Document type ID cannot be blank")
    private String documentTypeId;

    @Size(max = 100, message = "Cooperation period must not exceed 100 characters")
    private String cooperationPeriod;

    private String employmentStatusId;

    @Size(max = 255, message = "Position must not exceed 255 characters")
    private String position;

    // Financial Information
    private String serviceCostTypeId;

    @Size(max = 2000, message = "Service cost description must not exceed 2000 characters")
    private String serviceCostDescription;

    @Size(max = 255, message = "Payment terms must not exceed 255 characters")
    private String paymentTerms;

    @Size(max = 100, message = "Tax info must not exceed 100 characters")
    private String taxInfo;

    @Size(max = 255, message = "Penalty clause must not exceed 255 characters")
    private String penaltyClause;

    @DecimalMin(value = "0.0", inclusive = false, message = "Contract value must be greater than 0")
    @Digits(integer = 15, fraction = 2, message = "Contract value must have at most 15 integer digits and 2 decimal places")
    private BigDecimal contractValue;

    // Document Source
    private String documentSourceId;

    @Size(max = 2000, message = "Additional notes must not exceed 2000 characters")
    private String additionalNotes;

    // Workflow & Status
    private String workflowId;

    @NotBlank(message = "Priority ID cannot be blank")
    private String priorityId;

    // Timeline Management
    @NotNull(message = "Submission date is required")
    private LocalDate submissionDate;

    private LocalDate deadlineDate;

    @NotNull(message = "Version is required for optimistic locking")
    private Long version;

    // Constructors
    public UpdateOrderDataRequest() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCooperationTypeId() {
        return cooperationTypeId;
    }

    public void setCooperationTypeId(String cooperationTypeId) {
        this.cooperationTypeId = cooperationTypeId;
    }

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getCooperationPeriod() {
        return cooperationPeriod;
    }

    public void setCooperationPeriod(String cooperationPeriod) {
        this.cooperationPeriod = cooperationPeriod;
    }

    public String getEmploymentStatusId() {
        return employmentStatusId;
    }

    public void setEmploymentStatusId(String employmentStatusId) {
        this.employmentStatusId = employmentStatusId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getServiceCostTypeId() {
        return serviceCostTypeId;
    }

    public void setServiceCostTypeId(String serviceCostTypeId) {
        this.serviceCostTypeId = serviceCostTypeId;
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

    public String getDocumentSourceId() {
        return documentSourceId;
    }

    public void setDocumentSourceId(String documentSourceId) {
        this.documentSourceId = documentSourceId;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
