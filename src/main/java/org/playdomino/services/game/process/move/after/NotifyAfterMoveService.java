package org.playdomino.services.game.process.move.after;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameMove;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.services.game.DominoWebSocketMessageTypes;
import org.playdomino.services.game.process.WebSocketNotificationGame;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.playdomino.services.game.process.move.AfterCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class NotifyAfterMoveService extends WebSocketNotificationGame implements AfterCreateMoveService {

    @Override
    public void process(DominoGame dominoGame, CreateGameMove createGameMove) {
        sendNotification(DominoWebSocketMessageTypes.GAME_MOVE, dominoGame.getId().toString(), dominoGame);
    }
}
