package com.nerchuko.workflow_service_backend.runs;

import com.nerchuko.workflow_service_backend.runs.dto.WorkflowRunResponse;
import com.nerchuko.workflow_service_backend.stepRuns.dto.WorkflowStepRunResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/workflow-runs")
public class WorkflowRunController {

    private final WorkflowRunService workflowRunService;

    public WorkflowRunController(WorkflowRunService workflowRunService){
        this.workflowRunService = workflowRunService;
    }

    @GetMapping
    public ResponseEntity<List<WorkflowRunResponse>> listRuns(){
        return ResponseEntity.ok(workflowRunService.getAllRuns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowRunResponse> getRun(@PathVariable Long id){
        return ResponseEntity.ok(workflowRunService.getRun(id));
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<List<WorkflowStepRunResponse>> getStepRuns(@PathVariable Long id){
        return ResponseEntity.ok(workflowRunService.getStepRuns(id));
    }
}
