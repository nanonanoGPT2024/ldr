package com.ldr.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDataSummaryResponse {

    private String id;
    private String orderNumber;
    private String title;
    private String clientName;
    private String requestorName;
    private String cooperationTypeName;
    private String documentTypeName;
    private String currentStatusName;
    private String currentStatusColorCode;
    private String priorityName;
    private String priorityColorCode;
    private String assignedToName;
    private BigDecimal contractValue;
    private LocalDate submissionDate;
    private LocalDate deadlineDate;
    private LocalDate completionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public OrderDataSummaryResponse() {}

    public OrderDataSummaryResponse(String id, String orderNumber, String title, String clientName,
                                   String requestorName, String cooperationTypeName, String documentTypeName,
                                   String currentStatusName, String currentStatusColorCode, String priorityName,
                                   String priorityColorCode, String assignedToName, BigDecimal contractValue,
                                   LocalDate submissionDate, LocalDate deadlineDate, LocalDate completionDate,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.title = title;
        this.clientName = clientName;
        this.requestorName = requestorName;
        this.cooperationTypeName = cooperationTypeName;
        this.documentTypeName = documentTypeName;
        this.currentStatusName = currentStatusName;
        this.currentStatusColorCode = currentStatusColorCode;
        this.priorityName = priorityName;
        this.priorityColorCode = priorityColorCode;
        this.assignedToName = assignedToName;
        this.contractValue = contractValue;
        this.submissionDate = submissionDate;
        this.deadlineDate = deadlineDate;
        this.completionDate = completionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getCooperationTypeName() {
        return cooperationTypeName;
    }

    public void setCooperationTypeName(String cooperationTypeName) {
        this.cooperationTypeName = cooperationTypeName;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getCurrentStatusColorCode() {
        return currentStatusColorCode;
    }

    public void setCurrentStatusColorCode(String currentStatusColorCode) {
        this.currentStatusColorCode = currentStatusColorCode;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityColorCode() {
        return priorityColorCode;
    }

    public void setPriorityColorCode(String priorityColorCode) {
        this.priorityColorCode = priorityColorCode;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public BigDecimal getContractValue() {
        return contractValue;
    }

    public void setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
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
}
