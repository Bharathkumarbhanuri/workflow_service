package com.nerchuko.workflow_service_backend.events;

import com.nerchuko.workflow_service_backend.engine.WorkflowExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//This controller will accept POST /api/events,
// validate the API key, and save the event data as WorkflowRun.

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final WorkflowExecutionService workflowExecutionService;

    public EventController(WorkflowExecutionService workflowExecutionService) {
        this.workflowExecutionService = workflowExecutionService;
    }

    @PostMapping
    public ResponseEntity<?> receiveEvent(@RequestBody EventRequest eventRequest) {
        workflowExecutionService.processEvent(eventRequest);
        return ResponseEntity.ok("{ \"message\":\"Event processed\"}");
    }
}
