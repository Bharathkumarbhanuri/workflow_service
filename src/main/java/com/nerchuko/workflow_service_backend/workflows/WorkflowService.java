package com.nerchuko.workflow_service_backend.workflows;

// Service layer: contains business logic
// Converts DTOs <-> Entities

import com.nerchuko.workflow_service_backend.workflows.dto.WorkflowRequest;
import com.nerchuko.workflow_service_backend.workflows.dto.WorkflowResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowService {
    private WorkflowRepository workflowRepository;

    public WorkflowService(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public WorkflowResponse createWorkflow(WorkflowRequest request){
        Workflow workflow = new Workflow();
        workflow.setName(request.name);
        workflow.setDescription(request.description);
        workflow.setTriggerEventType(request.triggerEventType);
        workflow.setActive(request.active != null ? request.active : true);
        workflow.setCreatedAt(LocalDateTime.now());
        workflow = workflowRepository.save(workflow);
        return toResponse(workflow);
    }

    public List<WorkflowResponse> getAllWorkflows(){
        return workflowRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public WorkflowResponse getWorkflow(Long id){
        Workflow workflow = workflowRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Workflow not found"));
        return toResponse(workflow);
    }

    public WorkflowResponse updateWorkflow(Long id, WorkflowRequest request){
        Workflow workflow = workflowRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Workflow not found"));
        workflow.setName(request.name);
        workflow.setDescription(request.description);
        workflow.setTriggerEventType(request.triggerEventType);
        workflow.setUpdatedAt(LocalDateTime.now());
        workflow = workflowRepository.save(workflow);
        return toResponse(workflow);
    }

    public void updateStatus(Long id, boolean active){
        Workflow workflow = workflowRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Workflow not found"));
        workflow.setActive(active);
        workflowRepository.save(workflow);
    }

    private WorkflowResponse toResponse(Workflow workflow){
        WorkflowResponse res =  new WorkflowResponse();
        res.id = workflow.getId();
        res.name = workflow.getName();
        res.description = workflow.getDescription();
        res.triggerEventType = workflow.getTriggerEventType();
        res.active = workflow.isActive();
        res.createdAt = workflow.getCreatedAt();
        return res;
    }
}
