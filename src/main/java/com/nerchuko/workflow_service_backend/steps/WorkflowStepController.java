package com.nerchuko.workflow_service_backend.steps;

import com.nerchuko.workflow_service_backend.steps.dto.WorkflowStepRequest;
import com.nerchuko.workflow_service_backend.steps.dto.WorkflowStepResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/workflows/{workflowId}/steps")
public class WorkflowStepController {

    private final WorkflowStepService workflowStepService;

    public WorkflowStepController(WorkflowStepService service){
        this.workflowStepService = service;
    }

    @GetMapping
    public ResponseEntity<List<WorkflowStepResponse>> getSteps(@PathVariable Long workflowId){
        return ResponseEntity.ok(workflowStepService.getSteps(workflowId));
    }

    @PutMapping
    public ResponseEntity<List<WorkflowStepResponse>> replaceSteps(
            @PathVariable Long workflowId,
            @RequestBody List<WorkflowStepRequest> steps){
        return ResponseEntity.ok(workflowStepService.replaceSteps(workflowId,steps));
    }
}
