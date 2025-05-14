package org.playdomino.services.game.process.exit.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.exception.ExceptionFactory;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.GameStatus;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.services.game.process.exit.BeforeExitGameService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckGameStatusBeforeExitGameServiceImpl implements BeforeExitGameService {

    private final ExceptionFactory exceptionFactory;

    @Override
    public void process(final DominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getStatus(), GameStatus.WAITING_FOR_PLAYERS)) {
            throw exceptionFactory.newException(DominoGameExceptionConstants.INVALID_STATUS_FOR_EXIT, DominoGameException.class);
        }
    }
}
