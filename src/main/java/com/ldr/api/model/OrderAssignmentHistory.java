package com.ldr.api.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_assignment_history")
@EntityListeners(AuditingEntityListener.class)
public class OrderAssignmentHistory {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderData order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private User assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false)
    private AssignmentType assignmentType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    // Constructors
    public OrderAssignmentHistory() {}

    public OrderAssignmentHistory(String id, OrderData order, User assignedTo, User assignedBy, AssignmentType assignmentType, String notes) {
        this.id = id;
        this.order = order;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.assignmentType = assignmentType;
        this.notes = notes;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderData getOrder() {
        return order;
    }

    public void setOrder(OrderData order) {
        this.order = order;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    @Override
    public String toString() {
        return "OrderAssignmentHistory{" +
                "id='" + id + '\'' +
                ", order=" + (order != null ? order.getOrderNumber() : null) +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getUsername() : null) +
                ", assignedBy=" + (assignedBy != null ? assignedBy.getUsername() : null) +
                ", assignmentType=" + assignmentType +
                ", notes='" + notes + '\'' +
                ", assignedAt=" + assignedAt +
                '}';
    }
}
