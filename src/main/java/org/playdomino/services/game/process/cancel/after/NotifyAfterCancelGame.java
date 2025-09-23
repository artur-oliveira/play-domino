package org.playdomino.services.game.process.cancel.after;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.services.game.DominoWebSocketMessageTypes;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order
public class NotifyAfterCancelGame extends WebSocketNotificationGame implements AfterCancelGameService {

    @Override
    public void process(DominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getStatus(), GameStatus.CANCELLED)) {
            return;
        }
        sendNotification(DominoWebSocketMessageTypes.CANCEL_GAME, dominoGame.getId().toString(), dominoGame);
    }
}
