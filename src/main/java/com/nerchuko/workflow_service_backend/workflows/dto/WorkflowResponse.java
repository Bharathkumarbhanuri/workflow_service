package com.nerchuko.workflow_service_backend.workflows.dto;

import java.time.LocalDateTime;

// DTO for API responses
// Controls what data is exposed to UI

public class WorkflowResponse {
    public Long id;
    public String name;
    public String description;
    public String triggerEventType;
    public boolean active;
    public LocalDateTime createdAt;
}
