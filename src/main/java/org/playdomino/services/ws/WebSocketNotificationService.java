package org.playdomino.services.ws;

import org.playdomino.models.ws.WebSocketNotification;

public interface WebSocketNotificationService {
    void sendNotification(WebSocketNotification notification);
}
