package org.playdomino.services.game.process.cancel.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order
public class NotifyAfterCancelGame extends WebSocketNotificationGame implements AfterCancelGameService {

    private static final String CANCEL_GAME = "domino.game.cancel";

    public NotifyAfterCancelGame(
            MessagesComponent messagesComponent,
            WebSocketNotificationService webSocketNotificationService
    ) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Override
    public void process(DominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getStatus(), GameStatus.CANCELLED)) {
            return;
        }
        sendNotification(CANCEL_GAME, dominoGame.getId().toString(), dominoGame);
    }
}
