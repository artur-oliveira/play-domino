package org.playdomino.services.game.process.move.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.services.game.process.move.BeforeCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@RequiredArgsConstructor
public class ValidatePlayerCurrentTurnService implements BeforeCreateMoveService {

    private final MessagesComponent messagesComponent;

    User getCurrentUser() {
        return UserUtils.currentUser();
    }

    boolean isCurrentUserTurn(DominoGame game) {
        return game.getCurrentPlayer() == game.getPlayers().indexOf(game.getPlayer(getCurrentUser()));
    }

    @Override
    public void process(DominoGame game, CreateGameMove createGameMove) {
        if (!isCurrentUserTurn(game)) {
            throw new DominoGameException(DominoGameExceptionConstants.NOT_CURRENT_TURN, messagesComponent);
        }
    }
}
