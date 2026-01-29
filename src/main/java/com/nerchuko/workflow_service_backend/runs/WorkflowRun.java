package com.nerchuko.workflow_service_backend.runs;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WorkflowRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private Long workflowId;
    private String eventType;
    private String sourceSystem;
//    @Column(name = "trigger_payload", columnDefinition = "TEXT")
    private String triggerPayload;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String errorMessage;

    public WorkflowRun() {
    }

    public WorkflowRun(Long id, Long workflowId, String eventType, String sourceSystem, String triggerPayload, String status, LocalDateTime createdAt, LocalDateTime startedAt, LocalDateTime finishedAt, String errorMessage) {
        this.id = id;
        this.workflowId = workflowId;
        this.eventType = eventType;
        this.sourceSystem = sourceSystem;
        this.triggerPayload = triggerPayload;
        this.status = status;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getTriggerPayload() {
        return triggerPayload;
    }

    public void setTriggerPayload(String triggerPayload) {
        this.triggerPayload = triggerPayload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WorkflowRun{" +
                "id=" + id +
                ", workflowId=" + workflowId +
                ", eventType='" + eventType + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", triggerPayload='" + triggerPayload + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
