package com.ldr.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

public class OrderDataSearchRequest {

    private String orderNumber;
    private String title;
    private String clientName;
    private String requestorId;
    private String cooperationTypeId;
    private String documentTypeId;
    private String currentStatusId;
    private String priorityId;
    private String assignedToId;
    private LocalDate submissionDateFrom;
    private LocalDate submissionDateTo;
    private LocalDate deadlineDateFrom;
    private LocalDate deadlineDateTo;
    private LocalDate completionDateFrom;
    private LocalDate completionDateTo;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;

    @Min(value = 0, message = "Page must be non-negative")
    private Integer page = 0;

    @Min(value = 1, message = "Size must be at least 1")
    @Max(value = 100, message = "Size must not exceed 100")
    private Integer size = 20;

    private String sortBy = "createdAt";
    private String sortDirection = "desc";

    // Constructors
    public OrderDataSearchRequest() {}

    // Getters and Setters
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

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
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

    public String getCurrentStatusId() {
        return currentStatusId;
    }

    public void setCurrentStatusId(String currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public String getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(String assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDate getSubmissionDateFrom() {
        return submissionDateFrom;
    }

    public void setSubmissionDateFrom(LocalDate submissionDateFrom) {
        this.submissionDateFrom = submissionDateFrom;
    }

    public LocalDate getSubmissionDateTo() {
        return submissionDateTo;
    }

    public void setSubmissionDateTo(LocalDate submissionDateTo) {
        this.submissionDateTo = submissionDateTo;
    }

    public LocalDate getDeadlineDateFrom() {
        return deadlineDateFrom;
    }

    public void setDeadlineDateFrom(LocalDate deadlineDateFrom) {
        this.deadlineDateFrom = deadlineDateFrom;
    }

    public LocalDate getDeadlineDateTo() {
        return deadlineDateTo;
    }

    public void setDeadlineDateTo(LocalDate deadlineDateTo) {
        this.deadlineDateTo = deadlineDateTo;
    }

    public LocalDate getCompletionDateFrom() {
        return completionDateFrom;
    }

    public void setCompletionDateFrom(LocalDate completionDateFrom) {
        this.completionDateFrom = completionDateFrom;
    }

    public LocalDate getCompletionDateTo() {
        return completionDateTo;
    }

    public void setCompletionDateTo(LocalDate completionDateTo) {
        this.completionDateTo = completionDateTo;
    }

    public LocalDate getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(LocalDate createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public LocalDate getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(LocalDate createdAtTo) {
        this.createdAtTo = createdAtTo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
