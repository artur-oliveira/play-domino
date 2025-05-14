package org.playdomino.services.game.process.exit.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.services.game.process.exit.BeforeExitGameService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckUserIsPlayerBeforeExitGameServiceImpl implements BeforeExitGameService {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Override
    public void process(final DominoGame dominoGame) {
        Optional<DominoGamePlayer> gamePlayer = dominoGamePlayerRepository
                .findDominoGamePlayerByGameIdAndUserId(dominoGame.getId(), UserUtils.currentUser().getId());
        gamePlayer.orElseThrow(() -> new DominoGameException(DominoGameExceptionConstants.NOT_A_PLAYER, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_A_PLAYER)));
    }
}
