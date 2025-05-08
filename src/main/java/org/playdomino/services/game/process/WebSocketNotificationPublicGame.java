package org.playdomino.services.game.process;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.ws.WebSocketNotificationService;

@RequiredArgsConstructor
public class WebSocketNotificationPublicGame {

    private final MessagesComponent messagesComponent;
    private final WebSocketNotificationService webSocketNotificationService;

    protected void sendNotification(String messageType, String messageId, DominoGame dominoGame) {
        webSocketNotificationService.sendNotification(WebSocketNotification
                .builder()
                .topic(NotificationTopic.PUBLIC)
                .data(DominoGameDTO.of(
                        dominoGame,
                        dominoGame.getPlayers()
                ))
                .message(messagesComponent.getMessage(messageType))
                .messageType(messageType)
                .messageId(messageId)
                .notifyId(dominoGame.getId())
                .build());
    }

}
