package org.playdomino.services.game.process.addplayer.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.WebSocketNotificationPublicGame;
import org.playdomino.services.game.process.addplayer.AfterAddPlayerService;
import org.playdomino.services.game.process.create.AfterCreateGameService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotifyWebSocketAfterAddPlayerService extends WebSocketNotificationGame implements AfterAddPlayerService {
    private static final String USER_JOINED = "domino.game.user-joined";

    public NotifyWebSocketAfterAddPlayerService(
            MessagesComponent messagesComponent,
            WebSocketNotificationService webSocketNotificationService
    ) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Override
    public void process(DominoGame dominoGame) {
        sendNotification(USER_JOINED, dominoGame.getId().toString(), dominoGame);
    }
}
