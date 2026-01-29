package com.nerchuko.workflow_service_backend.stepRuns;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WorkflowStepRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workflowRunId;

    @Column(nullable = false)
    private Long WorkflowStepId;

    @Column(nullable = false)
    private String status;

    private String inputData;
    private String outputData;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String errorMessage;

    public WorkflowStepRun() {
    }

    public WorkflowStepRun(Long id, Long workflowRunId, Long getWorkflowStepId, String status, String inputData, String outputData, LocalDateTime startedAt, LocalDateTime finishedAt, String errorMessage) {
        this.id = id;
        this.workflowRunId = workflowRunId;
        this.WorkflowStepId = getWorkflowStepId;
        this.status = status;
        this.inputData = inputData;
        this.outputData = outputData;
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

    public Long getWorkflowRunId() {
        return workflowRunId;
    }

    public void setWorkflowRunId(Long workflowRunId) {
        this.workflowRunId = workflowRunId;
    }

    public Long getWorkflowStepId() {
        return WorkflowStepId;
    }

    public void setWorkflowStepId(Long workflowStepId) {
        this.WorkflowStepId = workflowStepId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
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
        return "WorkflowStepRun{" +
                "id=" + id +
                ", workflowRunId=" + workflowRunId +
                ", getWorkflowStepId=" + WorkflowStepId +
                ", status='" + status + '\'' +
                ", inputData='" + inputData + '\'' +
                ", outputData='" + outputData + '\'' +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
