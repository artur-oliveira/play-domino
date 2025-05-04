package org.playdomino.services.game.validation.addplayer;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(3)
@RequiredArgsConstructor
public class CheckAlreadyJoinedBeforeAddPlayerService implements BeforeAddPlayerService {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        final DominoGame game = playerToGame.getGame();
        final User user = playerToGame.getUser();
        dominoGamePlayerRepository.findDominoGamePlayerByGameIdAndUserId(game.getId(), user.getId()).ifPresent((player) -> {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.USER_ALREADY_JOINED_GAME, messagesComponent.getMessage(DominoGameExceptionConstants.USER_ALREADY_JOINED_GAME));
        });
    }
}
