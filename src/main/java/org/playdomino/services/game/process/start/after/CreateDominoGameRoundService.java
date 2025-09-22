package org.playdomino.services.game.process.start.after;

import jakarta.transaction.Transactional;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.models.game.DominoTile;
import org.playdomino.repositories.game.DominoGameRoundRepository;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.playdomino.services.game.process.start.BeforeStartGameService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class CreateDominoGameRoundService implements AfterStartGameService {
    private final DominoGameRoundRepository dominoGameRoundRepository;

    public CreateDominoGameRoundService(DominoGameRoundRepository dominoGameRoundRepository) {
        this.dominoGameRoundRepository = dominoGameRoundRepository;
    }

    @Override
    @Transactional
    public void process(final DominoGame dominoGame) {
        DominoGameRound gameRound = new DominoGameRound();

        gameRound.setGame(dominoGame);
        gameRound.setPile(DominoTile.randomOrder(dominoGame.getPlayers().size()));
        gameRound.setStartedAt(ZonedDateTime.now());

        dominoGameRoundRepository.saveAndFlush(gameRound);

        dominoGame.setRounds(new ArrayList<>(List.of(gameRound)));
    }
}
