package com.nerchuko.workflow_service_backend.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository repo;

    public NotificationController(NotificationRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<Notification> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) Boolean unreadOnly
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdAt"));

        if(unreadOnly !=null && unreadOnly){
            return repo.findByReadFalse(pageable);
        }
        return repo.findAll(pageable);
    }

    @PatchMapping("/{id}/read")
    public Notification markRead(@PathVariable Long id){
        Notification n = repo.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        return repo.save(n);
    }
}
