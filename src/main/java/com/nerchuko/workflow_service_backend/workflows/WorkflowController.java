package com.nerchuko.workflow_service_backend.workflows;

import com.nerchuko.workflow_service_backend.workflows.dto.WorkflowRequest;
import com.nerchuko.workflow_service_backend.workflows.dto.WorkflowResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for workflow APIs
// Uses DTOs, NEVER use entities directly
@RestController
@CrossOrigin
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService service){
        this.workflowService = service;
    }

    @PostMapping
    public ResponseEntity<WorkflowResponse> create(@RequestBody WorkflowRequest request){
        return ResponseEntity.ok(workflowService.createWorkflow(request));
    }

    @GetMapping
    public ResponseEntity<List<WorkflowResponse>> list(){
        return ResponseEntity.ok(workflowService.getAllWorkflows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(workflowService.getWorkflow(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowResponse> update(@PathVariable Long id, @RequestBody WorkflowRequest request){
        return ResponseEntity.ok(workflowService.updateWorkflow(id, request));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> setActive(@PathVariable Long id, @RequestParam boolean active){
        workflowService.updateStatus(id, active);
        return ResponseEntity.ok().build();
    }
}
