package org.playdomino.services.ws;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.ws.WebSocketNotification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationServiceImpl implements WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(WebSocketNotification notification) {
        messagingTemplate.convertAndSend(notification.getTopic().getTopicFor(notification.getNotifyId()), notification);
    }
}
