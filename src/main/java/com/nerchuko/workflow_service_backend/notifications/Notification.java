package com.nerchuko.workflow_service_backend.notifications;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private boolean read =  false;
    private LocalDateTime createdAt = LocalDateTime.now();

    private Long workflowRunId;
    private Long stepRunId;

    public Notification() {
    }

    public Notification(Long id, String message, boolean read, LocalDateTime createdAt, Long workflowRunId, Long stepRunId) {
        this.id = id;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
        this.workflowRunId = workflowRunId;
        this.stepRunId = stepRunId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getWorkflowRunId() {
        return workflowRunId;
    }

    public void setWorkflowRunId(Long workflowRunId) {
        this.workflowRunId = workflowRunId;
    }

    public Long getStepRunId() {
        return stepRunId;
    }

    public void setStepRunId(Long stepRunId) {
        this.stepRunId = stepRunId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                ", workflowRunId=" + workflowRunId +
                ", stepRunId=" + stepRunId +
                '}';
    }
}
