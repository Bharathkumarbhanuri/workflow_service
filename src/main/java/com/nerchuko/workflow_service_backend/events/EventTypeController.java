package com.nerchuko.workflow_service_backend.events;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/event-types")
public class EventTypeController {

    @GetMapping
    public List<String> getEventTypes(){
        return List.of(
                "ORDER_PLACED",
                "ORDER_DELIVERED",
                "PAYMENT_FAILED"
        );
    }
}
