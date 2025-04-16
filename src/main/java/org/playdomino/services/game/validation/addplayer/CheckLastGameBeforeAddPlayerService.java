package org.playdomino.services.game.validation.addplayer;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckLastGameBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final DominoGameRepository dominoGameRepository;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        if (dominoGameRepository.existsDominoGameByStatusAndPlayerUser(GameStatus.unfinisheds(), (User) SecurityContextHolder.getContext().getAuthentication())) {
            throw new DominoGameException(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED, messagesComponent.getMessage(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED));
        }
    }
}
