package org.playdomino.services.game.process.move.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.*;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.services.game.process.move.BeforeCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 350)
@RequiredArgsConstructor
public class ValidateMoveWouldCloseTheGameService implements BeforeCreateMoveService {

    private final MessagesComponent messagesComponent;

    void validateCloseGameIfAnotherTileCanBePlayed(
            DominoGame game,
            CreateGameMove move
    ) {
        if (move.wouldCloseTheGame(game) && !game.isAllowCloseGame()) {
            DominoGamePlayer player = game.getPlayer(UserUtils.currentUser());
            DominoGameRound round = game.getCurrentRound();
            boolean existsOtherMoveToNotCloseTheGame = player
                    .getHand()
                    .stream()
                    .filter(it -> !Objects.equals(it, move.getTile()) && (it.accepts(round.getNextRightTileNumber()) || it.accepts(round.getNextLeftTileNumber())))
                    .anyMatch(it -> !game.wouldCloseTheGame(
                            it, MoveDirection.LEFT
                    ) || !game.wouldCloseTheGame(
                            it, MoveDirection.RIGHT
                    ));

            if (existsOtherMoveToNotCloseTheGame) {
                throw new DominoGameException(DominoGameExceptionConstants.MOVE_WOULD_CLOSE_THE_GAME, messagesComponent);
            }
        }

    }

    @Override
    public void process(DominoGame game, CreateGameMove createGameMove) {
        validateCloseGameIfAnotherTileCanBePlayed(game, createGameMove);
    }
}
