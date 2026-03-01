package com.nerchuko.workflow_service_backend.emails;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailRecord {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_SENT = "SENT";
    public static final String STATUS_FAILED = "FAILED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toAddress;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    // PENDING -> SENT / FAILED
    @Column(nullable = false)
    private String status = STATUS_PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    private Long workflowRunId;
    private Long stepRunId;

    public EmailRecord() {
    }

    public EmailRecord(Long id, String toAddress, String subject, String body, String status, LocalDateTime createdAt, LocalDateTime sentAt, String errorMessage, Long workflowRunId, Long stepRunId) {
        this.id = id;
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.errorMessage = errorMessage;
        this.workflowRunId = workflowRunId;
        this.stepRunId = stepRunId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
        return "EmailRecord{" +
                "id=" + id +
                ", toAddress='" + toAddress + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", sentAt=" + sentAt +
                ", errorMessage='" + errorMessage + '\'' +
                ", workflowRunId=" + workflowRunId +
                ", stepRunId=" + stepRunId +
                '}';
    }
}
