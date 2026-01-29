package com.nerchuko.workflow_service_backend.runs.dto;

import java.time.LocalDateTime;

public class WorkflowRunResponse {
    public Long id;
    public Long workflowId;
    public String eventType;
    public String sourceSystem;
    public String status;
    public LocalDateTime createdAt;
    public LocalDateTime startedAt;
    public LocalDateTime finishedAt;
    public String errorMessage;
}
