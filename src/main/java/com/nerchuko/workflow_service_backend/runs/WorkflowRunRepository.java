package com.nerchuko.workflow_service_backend.runs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRunRepository extends JpaRepository<WorkflowRun, Long> {

    List<WorkflowRun> findAllByOrderByCreatedAtDesc();
}
