package org.playdomino.controllers;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.generic.GenericResponse;
import org.playdomino.services.health.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health-check")
@RequiredArgsConstructor
public class HealthCheckController {
    private final HealthCheckService healthCheckService;
    private final MessagesComponent messagesComponent;

    @GetMapping
    public ResponseEntity<GenericResponse> checkDatabaseConnection() {
        healthCheckService.checkDatabaseConnection();
        return ResponseEntity.ok(GenericResponse.builder().message(messagesComponent.getMessage("domino.healthy")).build());
    }
}

