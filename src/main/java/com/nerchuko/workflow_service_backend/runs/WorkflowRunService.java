package com.nerchuko.workflow_service_backend.runs;

import com.nerchuko.workflow_service_backend.runs.dto.WorkflowRunResponse;
import com.nerchuko.workflow_service_backend.stepRuns.WorkflowStepRun;
import com.nerchuko.workflow_service_backend.stepRuns.WorkflowStepRunRepository;
import com.nerchuko.workflow_service_backend.stepRuns.dto.WorkflowStepRunResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowRunService {

    private final WorkflowRunRepository workflowRunRepository;
    private final WorkflowStepRunRepository workflowStepRunRepository;

    public WorkflowRunService(WorkflowRunRepository workflowRunRepository, WorkflowStepRunRepository workflowStepRunRepository){
        this.workflowRunRepository = workflowRunRepository;
        this.workflowStepRunRepository = workflowStepRunRepository;
    }

    public List<WorkflowRunResponse> getAllRuns(){
        return workflowRunRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toRunResponse)
                .collect(Collectors.toList());
    }

    public WorkflowRunResponse getRun(Long id){
        WorkflowRun run = workflowRunRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Run not found"));
        return toRunResponse(run);
    }

    public List<WorkflowStepRunResponse> getStepRuns(Long id){
        return workflowStepRunRepository.findByWorkflowRunIdOrderByIdAsc(id)
                .stream()
                .map(this::toStepRunResponse)
                .collect(Collectors.toList());
    }

    private WorkflowRunResponse toRunResponse(WorkflowRun run) {
        WorkflowRunResponse res = new WorkflowRunResponse();
        res.id = run.getId();
        res.workflowId = run.getWorkflowId();
        res.eventType = run.getEventType();
        res.sourceSystem = run.getSourceSystem();
        res.status = run.getStatus();
        res.createdAt = run.getCreatedAt();
        res.startedAt = run.getStartedAt();
        res.finishedAt = run.getFinishedAt();
        res.errorMessage = run.getErrorMessage();
        return res;
    }

    private WorkflowStepRunResponse toStepRunResponse(WorkflowStepRun sr) {
        WorkflowStepRunResponse res = new WorkflowStepRunResponse();
        res.id = sr.getId();
        res.workflowStepId = sr.getWorkflowStepId();
        res.status = sr.getStatus();
        res.inputData = sr.getInputData();
        res.outputData = sr.getOutputData();
        res.errorMessage = sr.getErrorMessage();
        res.startedAt = sr.getStartedAt();
        res.finishedAt = sr.getFinishedAt();
        return res;
    }

}
