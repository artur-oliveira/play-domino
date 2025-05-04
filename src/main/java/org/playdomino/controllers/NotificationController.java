package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notification")
public class NotificationController {

    private final WebSocketNotificationService webSocketNotificationService;

    @PostMapping
    public void sendNotification(
            @Valid @RequestBody WebSocketNotification financialNotification
    ) {
        webSocketNotificationService.sendNotification(financialNotification);
    }
}
