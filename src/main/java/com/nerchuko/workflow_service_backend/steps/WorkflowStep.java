package com.nerchuko.workflow_service_backend.steps;

import jakarta.persistence.*;

@Entity
public class WorkflowStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workflowId;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String config;

    public WorkflowStep() {
    }

    public WorkflowStep(Long id, Long workflowId, Integer orderIndex, String type, String config) {
        this.id = id;
        this.workflowId = workflowId;
        this.orderIndex = orderIndex;
        this.type = type;
        this.config = config;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "WorkflowStep{" +
                "id=" + id +
                ", workflowId=" + workflowId +
                ", orderIndex=" + orderIndex +
                ", type='" + type + '\'' +
                ", config='" + config + '\'' +
                '}';
    }
}
