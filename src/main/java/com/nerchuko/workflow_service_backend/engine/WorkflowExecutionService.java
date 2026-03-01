package com.nerchuko.workflow_service_backend.engine;

import com.nerchuko.workflow_service_backend.emails.EmailRecord;
import com.nerchuko.workflow_service_backend.emails.EmailRecordRepository;
import com.nerchuko.workflow_service_backend.emails.EmailService;
import com.nerchuko.workflow_service_backend.emails.EmailTemplates;
import com.nerchuko.workflow_service_backend.events.EventRequest;
import com.nerchuko.workflow_service_backend.notifications.Notification;
import com.nerchuko.workflow_service_backend.notifications.NotificationRepository;
import com.nerchuko.workflow_service_backend.steps.WorkflowStep;
import com.nerchuko.workflow_service_backend.steps.WorkflowStepRepository;
import com.nerchuko.workflow_service_backend.stepRuns.WorkflowStepRun;
import com.nerchuko.workflow_service_backend.stepRuns.WorkflowStepRunRepository;
import com.nerchuko.workflow_service_backend.workflows.Workflow;
import com.nerchuko.workflow_service_backend.workflows.WorkflowRepository;
import com.nerchuko.workflow_service_backend.runs.WorkflowRun;
import com.nerchuko.workflow_service_backend.runs.WorkflowRunRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.JsonNode;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkflowExecutionService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowStepRepository stepRepository;
    private final WorkflowRunRepository runRepository;
    private final WorkflowStepRunRepository stepRunRepository;
    private final EmailRecordRepository emailRecordRepository;
    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public WorkflowExecutionService(WorkflowRepository workflowRepository, WorkflowStepRepository stepRepository, WorkflowRunRepository runRepository, WorkflowStepRunRepository stepRunRepository, EmailRecordRepository emailRecordRepository, NotificationRepository notificationRepository, ObjectMapper objectMapper, EmailService emailService) {
        this.workflowRepository = workflowRepository;
        this.stepRepository = stepRepository;
        this.runRepository = runRepository;
        this.stepRunRepository = stepRunRepository;
        this.emailRecordRepository = emailRecordRepository;
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @Transactional
    public void processEvent(EventRequest eventRequest){
        String eventType = eventRequest.getEventType();
        String sourceSystem = eventRequest.getSourceSystem();
        JsonNode payload = eventRequest.getPayload();

        // 1) Find all active workflows for this event type

        List<Workflow> workflows = workflowRepository.findByTriggerEventTypeAndActive(eventType, true);
        for(Workflow workflow: workflows){
            executeWorkflow(workflow,eventType, sourceSystem, payload);
        }
    }

    private void executeWorkflow(Workflow workflow, String eventType, String sourceSystem, JsonNode payload) {
        // 2) Create WorkflowRun
        WorkflowRun run = new WorkflowRun();
        run.setWorkflowId(workflow.getId());
        run.setEventType(eventType);
        run.setSourceSystem(sourceSystem);
        run.setTriggerPayload(payload.toString());
        run.setStatus("RUNNING");
        run.setCreatedAt(LocalDateTime.now());
        run.setStartedAt(LocalDateTime.now());
        run = runRepository.save(run);

        try {
            // 3) Fetch steps in order
            List<WorkflowStep> steps = stepRepository.findByWorkflowIdOrderByOrderIndexAsc(workflow.getId());
            int i=0;
            while (i < steps.size()){
                WorkflowStep step = steps.get(i);
                WorkflowStepRun stepRun = new WorkflowStepRun();
                stepRun.setWorkflowRunId(run.getId());
                stepRun.setWorkflowStepId(step.getId());
                stepRun.setStatus("RUNNING");
                stepRun.setStartedAt(LocalDateTime.now());
                stepRun.setInputData(payload.toString());
                stepRun = stepRunRepository.save(stepRun);

                try {
                    int stepsToSkip = executeStep(step, stepRun, run, payload);
                    stepRun.setStatus("SUCCESS");
                    stepRun.setFinishedAt(LocalDateTime.now());
                    stepRunRepository.save(stepRun);

                    // handle skipping for CONDITION
                    i += (1 + stepsToSkip);
                } catch (Exception e) {
                    stepRun.setStatus("FAILED");
                    stepRun.setFinishedAt(LocalDateTime.now());
                    stepRun.setErrorMessage("Step failed" + e.getMessage());
                    stepRunRepository.save(stepRun);

                    run.setStatus("FAILED");
                    run.setFinishedAt(LocalDateTime.now());
                    run.setErrorMessage("Step failed" + e.getMessage());
                    runRepository.save(run);
                    return; // stop this workflow
                }
            }

            run.setStatus("SUCCESS");
            run.setFinishedAt(LocalDateTime.now());
            runRepository.save(run);
        } catch (Exception e) {
            run.setStatus("FAILED");
            run.setFinishedAt(LocalDateTime.now());
            run.setErrorMessage("Step failed" + e.getMessage());
            runRepository.save(run);
        }
    }

    private int executeStep(WorkflowStep step, WorkflowStepRun stepRun, WorkflowRun run, JsonNode payload) throws Exception {
        String type = step.getType();
        String configJson = step.getConfig();
        JsonNode config = objectMapper.readTree(configJson);

        switch (type){
            case "ACTION_EMAIL":
                handleActionEmail(config, stepRun, run, payload);
                return 0;

            case "ACTION_NOTIFICATION":
                handleActionNotification(config, stepRun, run, payload);
                return 0;

            default:
                throw new IllegalArgumentException("Unknow step type: " + type);
        }
    }

    private void handleActionEmail(JsonNode config, WorkflowStepRun stepRun, WorkflowRun run, JsonNode payload) throws Exception {
        String toExpression = config.get("toExpression").asText();
        String subjectTemplate= config.get("subjectTemplate").asText();
        String bodyTemplate= config.get("bodyTemplate").asText();

        boolean htmlBody = !config.has("isHtml") || config.get("isHtml").asBoolean(true);

        String to = resolveExpression(toExpression, payload);
        String subject = renderTemplate(subjectTemplate, payload);
        String body = renderTemplate(bodyTemplate, payload);

        if (htmlBody) {
            body = EmailTemplates.wrapBranded(subject, body);
        }

        EmailRecord email = new EmailRecord();
        email.setToAddress(to);
        email.setSubject(subject);
        email.setBody(body);
        email.setWorkflowRunId(run.getId());
        email.setStepRunId(stepRun.getId());
        email.setStatus(EmailRecord.STATUS_PENDING);
        email = emailRecordRepository.save(email);

        try{
            emailService.sendEmail(to, subject, body, htmlBody);
            email.setStatus(EmailRecord.STATUS_SENT);
            email.setSentAt(LocalDateTime.now());
            email.setErrorMessage(null);
            emailRecordRepository.save(email);

            stepRun.setOutputData("EmailRecord id = " + email.getId());
        } catch (Exception ex) {
            email.setStatus(EmailRecord.STATUS_FAILED);
            email.setSentAt(null);
            email.setErrorMessage(ex.getMessage());
            emailRecordRepository.save(email);

            stepRun.setOutputData("Email FAILED. EmailRecord id=" + email.getId() + ", error=" + ex.getMessage());
            throw ex;
        }
    }

    private void handleActionNotification(JsonNode config, WorkflowStepRun stepRun, WorkflowRun run, JsonNode payload) {
        String messageTemplate = config.get("messageTemplate").asText();
        String message = renderTemplate(messageTemplate, payload);

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setWorkflowRunId(run.getId());
        notification.setStepRunId(stepRun.getId());
        notificationRepository.save(notification);

        stepRun.setOutputData("Notification id = " + notification.getId());
    }

    // expression like "payload.customerEmail"
    private String resolveExpression(String expression, JsonNode payload) {
        String exp = expression;
        if(exp.startsWith("payload.")){
            exp = exp.substring("payload.".length());
        }

        String[] parts = exp.split("\\.");
        JsonNode current = payload;
        for(String part: parts){
            current = current.get(part);
            if(current == null){
                return "";
            }
        }
        if(current.isValueNode()){
            return current.asText();
        }
        return current.asString();
    }

    // template like "Hi {{payload.customerName}}, your order {{payload.orderId}}..."
    private String renderTemplate(String template, JsonNode payload) {
        String result = template;
        int start;
        while ((start = result.indexOf("{{")) != -1){
            int end = result.indexOf("}}", start);
            if(end == -1 ) break;
            String placeholder = result.substring(start + 2,end).trim();
            String value = resolveExpression(placeholder,payload);
            result = result.substring(0,start) + value + result.substring(end+2);
        }
        return result;
    }

}
