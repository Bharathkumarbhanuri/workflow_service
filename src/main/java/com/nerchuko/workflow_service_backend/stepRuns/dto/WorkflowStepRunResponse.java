package com.nerchuko.workflow_service_backend.stepRuns.dto;

import java.time.LocalDateTime;

public class WorkflowStepRunResponse {
    public Long id;
    public Long workflowStepId;
    public String status;
    public String inputData;
    public String outputData;
    public String errorMessage;
    public LocalDateTime startedAt;
    public LocalDateTime finishedAt;
}
