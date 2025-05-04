package org.playdomino.services.ws;

import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.ws.NotificationTopic;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WebSocketFinancialTopicValidator implements WebSocketTopicValidator {

    @Override
    public void validate(final StompHeaderAccessor accessor) {
        final String destination = accessor.getDestination();
        if (Objects.isNull(destination) || NotificationTopic.valueOfTopic(destination) != getTopic()) {
            return;
        }
        checkFinancialTopic(UserUtils.currentUser(accessor).getId(), destination);
    }

    void checkFinancialTopic(Long currentUserId, String destination) {
        Long userIdInTopic = getTopic().getIdForTopic(destination);
        if (!currentUserId.equals(userIdInTopic)) {
            throw new BadCredentialsException("Cannot subscribe to topic of another user");
        }
    }

    @Override
    public NotificationTopic getTopic() {
        return NotificationTopic.FINANCIAL;
    }
}
