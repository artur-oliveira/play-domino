package org.playdomino.services.game.process.create.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.WebSocketNotificationPublicGame;
import org.playdomino.services.game.process.create.AfterCreateGameService;
import org.playdomino.services.game.process.vote.AfterGameVoteService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotifyWebSocketAfterCreatedService extends WebSocketNotificationPublicGame implements AfterCreateGameService {
    private static final String NEW_GAME = "domino.game.new";

    public NotifyWebSocketAfterCreatedService(
            MessagesComponent messagesComponent,
            WebSocketNotificationService webSocketNotificationService
    ) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Override
    public void process(DominoGame dominoGame) {
        if (dominoGame.isVisible() && Objects.isNull(dominoGame.getPassword())) {
            sendNotification(NEW_GAME, dominoGame.getId().toString(), dominoGame);
        }
    }
}
