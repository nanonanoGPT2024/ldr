package com.ldr.api.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_detail")
@EntityListeners(AuditingEntityListener.class)
public class WorkflowDetail {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @Column(name = "current_stage", nullable = false, length = 36)
    private String currentStage;

    @Column(name = "next_stage", length = 36)
    private String nextStage;

    @Column(name = "return_stage", length = 36)
    private String returnStage;

    @Column(name = "reject_stage", length = 36)
    private String rejectStage;

    @Column(name = "sla")
    private Integer sla;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    // Constructors
    public WorkflowDetail() {}

    public WorkflowDetail(String id, Workflow workflow, String currentStage, String nextStage,
                         String returnStage, String rejectStage, Integer sla, boolean isActive) {
        this.id = id;
        this.workflow = workflow;
        this.currentStage = currentStage;
        this.nextStage = nextStage;
        this.returnStage = returnStage;
        this.rejectStage = rejectStage;
        this.sla = sla;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public String getNextStage() {
        return nextStage;
    }

    public void setNextStage(String nextStage) {
        this.nextStage = nextStage;
    }

    public String getReturnStage() {
        return returnStage;
    }

    public void setReturnStage(String returnStage) {
        this.returnStage = returnStage;
    }

    public String getRejectStage() {
        return rejectStage;
    }

    public void setRejectStage(String rejectStage) {
        this.rejectStage = rejectStage;
    }

    public Integer getSla() {
        return sla;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WorkflowDetail{" +
                "id='" + id + '\'' +
                ", workflow=" + (workflow != null ? workflow.getNama() : null) +
                ", currentStage='" + currentStage + '\'' +
                ", nextStage='" + nextStage + '\'' +
                ", returnStage='" + returnStage + '\'' +
                ", rejectStage='" + rejectStage + '\'' +
                ", sla=" + sla +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=null" +
                ", updatedBy=null" +
                ", version=" + version +
                '}';
    }
}
