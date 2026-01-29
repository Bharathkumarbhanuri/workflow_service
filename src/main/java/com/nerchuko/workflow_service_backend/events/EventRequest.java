package com.nerchuko.workflow_service_backend.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;


//This is a simple DTO (Data Transfer Object) to map the event payload.

public class EventRequest {

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("sourceSystem")
    private String sourceSystem;

    @JsonProperty("payload")
    private JsonNode payload;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }
}
