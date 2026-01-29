package com.nerchuko.workflow_service_backend.emails;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toAddress;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String status = "SENT";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt = LocalDateTime.now();

    private Long workflowRunId;
    private Long stepRunId;

    public EmailRecord() {
    }

    public EmailRecord(Long workflowRunId, Long id, String toAddress, String subject, String body, String status, LocalDateTime createdAt, LocalDateTime sentAt, Long stepRunId) {
        this.workflowRunId = workflowRunId;
        this.id = id;
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
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
                ", workflowRunId=" + workflowRunId +
                ", stepRunId=" + stepRunId +
                '}';
    }
}
