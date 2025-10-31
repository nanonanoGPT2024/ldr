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

    @Column(name = "assigned_to_role", length = 50)
    private String assignedToRole;

    @Column(name = "assigned_by_role", length = 50)
    private String assignedByRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false)
    private AssignmentType assignmentType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "sla")
    private Integer sla;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    // Constructors
    public OrderAssignmentHistory() {
    }

    public OrderAssignmentHistory(String id, OrderData order, String assignedToRole, String assignedByRole,
            AssignmentType assignmentType, String notes) {
        this.id = id;
        this.order = order;
        this.assignedToRole = assignedToRole;
        this.assignedByRole = assignedByRole;
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

    public String getAssignedToRole() {
        return assignedToRole;
    }

    public void setAssignedToRole(String assignedToRole) {
        this.assignedToRole = assignedToRole;
    }

    public String getAssignedByRole() {
        return assignedByRole;
    }

    public void setAssignedByRole(String assignedByRole) {
        this.assignedByRole = assignedByRole;
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

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
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
                ", assignedToRole='" + assignedToRole + '\'' +
                ", assignedByRole='" + assignedByRole + '\'' +
                ", assignmentType=" + assignmentType +
                ", notes='" + notes + '\'' +
                ", sla=" + sla +
                ", completedAt=" + completedAt +
                ", assignedAt=" + assignedAt +
                '}';
    }
}
