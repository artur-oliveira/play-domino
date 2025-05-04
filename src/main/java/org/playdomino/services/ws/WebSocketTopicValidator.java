package org.playdomino.services.ws;

import org.playdomino.models.ws.NotificationTopic;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface WebSocketTopicValidator {

    void validate(final StompHeaderAccessor accessor);

    NotificationTopic getTopic();
}
