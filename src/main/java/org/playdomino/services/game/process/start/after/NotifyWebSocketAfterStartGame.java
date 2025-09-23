package org.playdomino.services.game.process.start.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.services.game.DominoWebSocketMessageTypes;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotifyWebSocketAfterStartGame extends WebSocketNotificationGame implements AfterStartGameService {

    @Override
    public void process(DominoGame dominoGame) {
        sendNotification(DominoWebSocketMessageTypes.GAME_STARTED, dominoGame.getId().toString(), dominoGame);
    }
}
