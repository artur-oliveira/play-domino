package org.playdomino.services.game.process.move.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoTile;
import org.playdomino.models.game.MoveDirection;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.services.game.process.move.BeforeCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 300)
@RequiredArgsConstructor
public class ValidateTileDirectionService implements BeforeCreateMoveService {

    private final MessagesComponent messagesComponent;

    boolean hasTileOnAnyDirection(DominoGame game, CreateGameMove gameMove) {
        if (Objects.isNull(game.getCurrentRound().getNextLeftTileNumber()) && Objects.isNull(game.getCurrentRound().getNextRightTileNumber())) {
            return true;
        } else if (gameMove.getMoveDirection() == MoveDirection.LEFT) {
            return gameMove.getTile().accepts(game.getCurrentRound().getNextLeftTileNumber());
        }
        return gameMove.getTile().accepts(game.getCurrentRound().getNextRightTileNumber());
    }

    @Override
    public void process(DominoGame game, CreateGameMove createGameMove) {
        if (!hasTileOnAnyDirection(game, createGameMove)) {
            throw new DominoGameException(DominoGameExceptionConstants.MOVE_INVALID_DIRECTION, messagesComponent);
        }
    }
}
