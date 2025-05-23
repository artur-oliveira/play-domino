package org.playdomino.services.game.process.create.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.services.game.process.create.BeforeCreateGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LastGameFinishedBeforeCreateService implements BeforeCreateGameService {

    private final MessagesComponent messagesComponent;
    private final DominoGameRepository dominoGameRepository;

    @Transactional(readOnly = true)
    public void process(DominoGame currentGame) {
        DominoGame lastGame = dominoGameRepository.findFirstByHostOrderByIdDesc(currentGame.getHost()).orElse(null);
        if (Objects.nonNull(lastGame) && lastGame.getStatus().ongoing()) {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED, messagesComponent.getMessage(DominoGameExceptionConstants.LAST_GAME_NOT_FINISHED));
        }
    }
}
