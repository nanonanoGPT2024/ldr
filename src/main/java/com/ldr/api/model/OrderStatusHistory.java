package com.ldr.api.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_status_history")
@EntityListeners(AuditingEntityListener.class)
public class OrderStatusHistory {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderData order;

    @Column(name = "from_status_code", length = 20)
    private String fromStatus;

    @Column(name = "to_status_code", nullable = false, length = 20)
    private String toStatus;

    @Column(name = "changed_by", nullable = false, length = 36)
    private String changedBy;

    @Column(name = "change_reason", columnDefinition = "TEXT")
    private String changeReason;

    @CreatedDate
    @Column(name = "changed_at", nullable = false, updatable = false)
    private LocalDateTime changedAt;

    // Constructors
    public OrderStatusHistory() {}

    public OrderStatusHistory(String id, OrderData order, String fromStatus, String toStatus, String changedBy, String changeReason) {
        this.id = id;
        this.order = order;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
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

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public String toString() {
        return "OrderStatusHistory{" +
                "id='" + id + '\'' +
                ", order=" + (order != null ? order.getOrderNumber() : null) +
                ", fromStatus='" + fromStatus + '\'' +
                ", toStatus='" + toStatus + '\'' +
                ", changedBy='" + changedBy + '\'' +
                ", changeReason='" + changeReason + '\'' +
                ", changedAt=" + changedAt +
                '}';
    }
}
