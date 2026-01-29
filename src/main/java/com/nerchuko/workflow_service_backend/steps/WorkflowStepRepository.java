package com.nerchuko.workflow_service_backend.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Long> {
    List<WorkflowStep> findByWorkflowIdOrderByOrderIndexAsc(Long workflowId);

    void deleteByWorkflowId(Long workflowId);
}
