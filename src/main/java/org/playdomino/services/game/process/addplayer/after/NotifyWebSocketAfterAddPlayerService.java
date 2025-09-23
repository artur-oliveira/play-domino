package org.playdomino.services.game.process.addplayer.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.services.game.DominoWebSocketMessageTypes;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.WebSocketNotificationPublicGame;
import org.playdomino.services.game.process.addplayer.AfterAddPlayerService;
import org.playdomino.services.game.process.create.AfterCreateGameService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotifyWebSocketAfterAddPlayerService extends WebSocketNotificationGame implements AfterAddPlayerService {

    @Override
    public void process(DominoGame dominoGame) {
        sendNotification(DominoWebSocketMessageTypes.USER_JOINED, dominoGame.getId().toString(), dominoGame);
    }
}
