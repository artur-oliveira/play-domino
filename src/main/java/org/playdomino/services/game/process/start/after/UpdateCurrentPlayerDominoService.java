package org.playdomino.services.game.process.start.after;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.repositories.game.DominoGameRoundRepository;
import org.playdomino.services.game.distribution.FindFirstGamePlayer;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@AllArgsConstructor
public class UpdateCurrentPlayerDominoService implements AfterStartGameService {
    private final DominoGameRepository dominoGameRepository;
    private final FindFirstGamePlayer findFirstGamePlayer;

    @Override
    @Transactional
    public void process(DominoGame dominoGame) {

        dominoGame.setCurrentPlayer(findFirstGamePlayer.findFirstGamePlayer(dominoGame));
        dominoGameRepository.save(dominoGame);

    }
}
