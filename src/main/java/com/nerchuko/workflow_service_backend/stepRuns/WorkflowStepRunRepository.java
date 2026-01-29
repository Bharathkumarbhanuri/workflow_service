package com.nerchuko.workflow_service_backend.stepRuns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowStepRunRepository extends JpaRepository<WorkflowStepRun, Long> {

    List<WorkflowStepRun> findByWorkflowRunIdOrderByIdAsc(Long workflowRunId);
}
