package com.nerchuko.workflow_service_backend.emails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/emails")
public class EmailController {
    private final EmailRecordRepository repo;

    public EmailController(EmailRecordRepository repo) {
        this.repo = repo;
    }

    //list emails
    @GetMapping
    public Page<EmailRecord> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long workflowRunId,
            @RequestParam(required = false) Long stepRunId
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));

        if(workflowRunId != null) {
            return repo.findByWorkflowRunId(workflowRunId, pageable);
        }

        if (stepRunId != null) {
            return repo.findByStepRunId(stepRunId, pageable);
        }
        //hhh
        return repo.findAll(pageable);
    }

    //read one email
    @GetMapping("/{id}")
    public EmailRecord get(@PathVariable Long id){
        return repo.findById(id).orElseThrow(()-> new RuntimeException("Email not found"));
    }
}
