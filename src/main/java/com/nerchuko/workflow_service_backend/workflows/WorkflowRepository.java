package com.nerchuko.workflow_service_backend.workflows;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    List<Workflow> findByTriggerEventTypeAndActive(String triggerEventType, boolean active);
}
