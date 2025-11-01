package com.ldr.api.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_data")
@EntityListeners(AuditingEntityListener.class)
public class OrderData {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "order_number", unique = true, nullable = false, length = 50)
    private String orderNumber;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Client Information
    @Column(name = "client_name", nullable = false, length = 255)
    private String clientName;

    @Column(name = "client_trade_name", length = 255)
    private String clientTradeName;

    @Column(name = "client_contact_person", length = 255)
    private String clientContactPerson;

    @Column(name = "client_email", length = 255)
    private String clientEmail;

    @Column(name = "client_phone", length = 50)
    private String clientPhone;

    // Requestor Information
    @Column(name = "requestor_id", nullable = false, length = 36)
    private String requestor;

    @Column(name = "requestor_name", nullable = false, length = 255)
    private String requestorName;

    @Column(name = "requestor_email", length = 255)
    private String requestorEmail;

    @Column(name = "requestor_department", length = 100)
    private String requestorDepartment;

    // Cooperation Details
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperation_type_id", nullable = false)
    private CooperationType cooperationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name = "cooperation_period", length = 100)
    private String cooperationPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_status_id")
    private EmploymentStatus employmentStatus;

    @Column(name = "position", length = 255)
    private String position;

    // Financial Information
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_cost_type_id")
    private ServiceCostType serviceCostType;

    @Column(name = "service_cost_description", columnDefinition = "TEXT")
    private String serviceCostDescription;

    @Column(name = "payment_terms", length = 255)
    private String paymentTerms;

    @Column(name = "tax_info", length = 100)
    private String taxInfo;

    @Column(name = "penalty_clause", length = 255)
    private String penaltyClause;

    @Column(name = "contract_value", precision = 15, scale = 2)
    private BigDecimal contractValue;

    // Document Source
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_source_id")
    private DocumentSource documentSource;

    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    // Workflow & Status
    @Column(name = "current_status_code", nullable = false, length = 20)
    private String currentStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", nullable = false)
    private Priority priority;

    @Column(name = "assigned_to", length = 36)
    private String assignedTo;

    @Column(name = "current_role", length = 50)
    private String currentRole;

    @Column(name = "sla")
    private Integer sla;

    // Timeline Management
    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    // Metadata
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 36)
    private String createdBy;

    @Column(name = "updated_by", length = 36)
    private String updatedBy;

    // Constructors
    public OrderData() {}

    public OrderData(String id, String orderNumber, String title, String clientName) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.title = title;
        this.clientName = clientName;
    }

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

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
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

    public CooperationType getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(CooperationType cooperationType) {
        this.cooperationType = cooperationType;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getCooperationPeriod() {
        return cooperationPeriod;
    }

    public void setCooperationPeriod(String cooperationPeriod) {
        this.cooperationPeriod = cooperationPeriod;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ServiceCostType getServiceCostType() {
        return serviceCostType;
    }

    public void setServiceCostType(ServiceCostType serviceCostType) {
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

    public DocumentSource getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(DocumentSource documentSource) {
        this.documentSource = documentSource;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "id='" + id + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientTradeName='" + clientTradeName + '\'' +
                ", clientContactPerson='" + clientContactPerson + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", requestor='" + requestor + '\'' +
                ", requestorName='" + requestorName + '\'' +
                ", requestorEmail='" + requestorEmail + '\'' +
                ", requestorDepartment='" + requestorDepartment + '\'' +
                ", cooperationType=" + (cooperationType != null ? cooperationType.getName() : null) +
                ", documentType=" + (documentType != null ? documentType.getName() : null) +
                ", cooperationPeriod='" + cooperationPeriod + '\'' +
                ", employmentStatus=" + (employmentStatus != null ? employmentStatus.getName() : null) +
                ", position='" + position + '\'' +
                ", serviceCostType=" + (serviceCostType != null ? serviceCostType.getName() : null) +
                ", serviceCostDescription='" + serviceCostDescription + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", taxInfo='" + taxInfo + '\'' +
                ", penaltyClause='" + penaltyClause + '\'' +
                ", contractValue=" + contractValue +
                ", documentSource=" + (documentSource != null ? documentSource.getName() : null) +
                ", additionalNotes='" + additionalNotes + '\'' +
                ", currentStatusCode='" + currentStatusCode + '\'' +
                ", workflow=" + (workflow != null ? workflow.getNama() : null) +
                ", priority=" + (priority != null ? priority.getName() : null) +
                ", assignedTo='" + assignedTo + '\'' +
                ", currentRole='" + currentRole + '\'' +
                ", sla=" + sla +
                ", submissionDate=" + submissionDate +
                ", deadlineDate=" + deadlineDate +
                ", completionDate=" + completionDate +
                ", version=" + version +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
