package com.nerchuko.workflow_service_backend.steps;

import com.nerchuko.workflow_service_backend.steps.dto.WorkflowStepRequest;
import com.nerchuko.workflow_service_backend.steps.dto.WorkflowStepResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowStepService {

    private final WorkflowStepRepository workflowStepRepository;

    public WorkflowStepService(WorkflowStepRepository repository){
        this.workflowStepRepository = repository;
    }

    public List<WorkflowStepResponse> getSteps(Long workflowId){
        return workflowStepRepository.findByWorkflowIdOrderByOrderIndexAsc(workflowId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WorkflowStepResponse> replaceSteps(Long workflowId, List<WorkflowStepRequest> requests){
        // delete existing steps
        workflowStepRepository.deleteByWorkflowId(workflowId);

        // insert new steps
        List<WorkflowStep> saved = requests.stream().map(req -> {
            WorkflowStep step = new WorkflowStep();
            step.setWorkflowId(workflowId);
            step.setOrderIndex(req.orderIndex);
            step.setType(req.type);
            step.setConfig(req.config);
            return step;
        }).map(workflowStepRepository::save).collect(Collectors.toList());

        return saved.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private WorkflowStepResponse toResponse(WorkflowStep workflowStep) {
        WorkflowStepResponse res = new WorkflowStepResponse();
        res.id = workflowStep.getId();
        res.workflowId = workflowStep.getWorkflowId();
        res.orderIndex = workflowStep.getOrderIndex();
        res.type = workflowStep.getType();
        res.config = workflowStep.getConfig();
        return res;
    }
}
