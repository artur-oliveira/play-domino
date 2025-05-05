package org.playdomino.services.game.process.addplayer.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.services.game.process.addplayer.BeforeAddPlayerService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(4)
@RequiredArgsConstructor
public class CheckLastGameBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final DominoGameRepository dominoGameRepository;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        if (dominoGameRepository.existsDominoGameByStatusAndPlayerUser(GameStatus.unfinisheds(), UserUtils.currentUser())) {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED, messagesComponent.getMessage(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED));
        }
    }
}
