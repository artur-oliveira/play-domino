package org.playdomino.services.game.process.vote;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.ws.WebSocketNotificationService;

@RequiredArgsConstructor
public class WebSocketNotificationAfterVote {

    private final MessagesComponent messagesComponent;
    private final WebSocketNotificationService webSocketNotificationService;

    protected void sendNotification(DominoGame dominoGame, DominoGameVote dominoGameVote) {
        webSocketNotificationService.sendNotification(WebSocketNotification
                .builder()
                .topic(NotificationTopic.GAME)
                .data(DominoGameDTO.of(
                        dominoGame,
                        dominoGame.getPlayers(),
                        dominoGame.getMoves(),
                        dominoGame.getVotes()
                ))
                .message(messagesComponent.getMessage(dominoGameVote.getVoteType().propertyName()))
                .messageType(dominoGameVote.getVoteType().propertyName())
                .messageId(dominoGameVote.getId().toString())
                .notifyId(dominoGameVote.getGame().getId())
                .build());
    }

}
