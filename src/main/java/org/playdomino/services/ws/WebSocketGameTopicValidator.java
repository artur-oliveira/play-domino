package org.playdomino.services.ws;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WebSocketGameTopicValidator implements WebSocketTopicValidator {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Override
    public void validate(final StompHeaderAccessor accessor) {
        final String destination = accessor.getDestination();
        if (Objects.isNull(destination) || NotificationTopic.valueOfTopic(destination) != getTopic()) {
            return;
        }
        checkGameTopic(UserUtils.currentUser(accessor).getId(), destination);
    }

    void checkGameTopic(Long currentUserId, String destination) {
        Long gameId = getTopic().getIdForTopic(destination);
        dominoGamePlayerRepository.findDominoGamePlayerByGameIdAndUserId(gameId, currentUserId).orElseThrow(() -> new DominoGameException(DominoGameExceptionConstants.NOT_A_PLAYER, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_A_PLAYER)));
    }

    @Override
    public NotificationTopic getTopic() {
        return NotificationTopic.GAME;
    }
}
