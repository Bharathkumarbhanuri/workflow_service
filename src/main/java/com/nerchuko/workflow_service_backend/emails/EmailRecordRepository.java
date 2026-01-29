package com.nerchuko.workflow_service_backend.emails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRecordRepository extends JpaRepository<EmailRecord, Long> {

    Page<EmailRecord> findByStepRunId(Long stepRunId, Pageable pageable);

    Page<EmailRecord> findByWorkflowRunId(Long workflowRunId, Pageable pageable);
}
