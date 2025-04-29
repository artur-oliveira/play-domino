package org.playdomino.services.game.validation.addplayer;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(1)
@RequiredArgsConstructor
public class CheckGameStatusWaitingBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        final DominoGame game = playerToGame.getGame();
        if (game.notWaitingForPlayers()) {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.NOT_WAITING_FOR_PLAYERS, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_WAITING_FOR_PLAYERS));
        }
    }
}
