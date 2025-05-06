package org.playdomino.services.game.process;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.ws.WebSocketNotificationService;

@RequiredArgsConstructor
public class WebSocketNotificationGame {

    private final MessagesComponent messagesComponent;
    private final WebSocketNotificationService webSocketNotificationService;

    protected void sendNotification(String messageType, String messageId, DominoGame dominoGame) {
        webSocketNotificationService.sendNotification(WebSocketNotification
                .builder()
                .topic(NotificationTopic.GAME)
                .data(DominoGameDTO.of(
                        dominoGame,
                        dominoGame.getPlayers(),
                        dominoGame.getMoves(),
                        dominoGame.getVotes()
                ))
                .message(messagesComponent.getMessage(messageType))
                .messageType(messageType)
                .messageId(messageId)
                .notifyId(dominoGame.getId())
                .build());
    }

}
