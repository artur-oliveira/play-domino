package org.playdomino.services.game.process;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.game.dto.DominoGameDTOService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public abstract class WebSocketNotificationGame {

    private MessagesComponent messagesComponent;
    private WebSocketNotificationService webSocketNotificationService;
    private DominoGameDTOService dominoGameDTOService;

    protected void sendNotification(String messageType, String messageId, DominoGame dominoGame) {
        webSocketNotificationService.sendNotification(WebSocketNotification
                .builder()
                .topic(NotificationTopic.GAME)
                .data(dominoGameDTOService.getDominoGameDTO(dominoGame))
                .message(messagesComponent.getMessage(messageType))
                .messageType(messageType)
                .messageId(messageId)
                .notifyId(dominoGame.getId())
                .build());
    }

    @Autowired
    public void setMessagesComponent(MessagesComponent messagesComponent) {
        this.messagesComponent = messagesComponent;
    }

    @Autowired
    public void setWebSocketNotificationService(WebSocketNotificationService webSocketNotificationService) {
        this.webSocketNotificationService = webSocketNotificationService;
    }

    @Autowired
    public void setDominoGameDTOService(DominoGameDTOService dominoGameDTOService) {
        this.dominoGameDTOService = dominoGameDTOService;
    }
}
