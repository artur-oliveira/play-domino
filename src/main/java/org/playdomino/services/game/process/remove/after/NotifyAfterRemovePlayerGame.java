package org.playdomino.services.game.process.remove.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.RemovePlayerDominoGame;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.playdomino.services.game.process.remove.AfterRemovePlayerService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order
public class NotifyAfterRemovePlayerGame extends WebSocketNotificationGame implements AfterRemovePlayerService {

    private static final String USER_LEFT = "domino.game.user-left";

    public NotifyAfterRemovePlayerGame(
            MessagesComponent messagesComponent,
            WebSocketNotificationService webSocketNotificationService
    ) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Override
    public void process(RemovePlayerDominoGame removePlayerDominoGame) {
        sendNotification(USER_LEFT, removePlayerDominoGame.getDominoGame().getId().toString(), removePlayerDominoGame.getDominoGame());
    }
}
