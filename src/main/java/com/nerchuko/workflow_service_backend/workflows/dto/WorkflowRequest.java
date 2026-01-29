package com.nerchuko.workflow_service_backend.workflows.dto;

// DTO for incoming API requests (create / update)
// Contains ONLY fields that client is allowed to send

public class WorkflowRequest {
    public String name;
    public String description;
    public String triggerEventType;
    public Boolean active;
}
