package org.playdomino.services.game.process.remove.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.exception.ExceptionFactory;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.RemovePlayerDominoGame;
import org.playdomino.services.game.process.exit.BeforeExitGameService;
import org.playdomino.services.game.process.remove.BeforeRemovePlayerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckGameStatusBeforeRemovePlayerServiceImpl implements BeforeRemovePlayerService {

    private final ExceptionFactory exceptionFactory;

    @Override
    public void process(final RemovePlayerDominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getDominoGame().getStatus(), GameStatus.WAITING_FOR_PLAYERS)) {
            throw exceptionFactory.newException(DominoGameExceptionConstants.INVALID_STATUS_FOR_REMOVE, DominoGameException.class);
        }
    }
}
