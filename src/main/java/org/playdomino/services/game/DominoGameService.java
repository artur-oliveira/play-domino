package org.playdomino.services.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.JoinDominoGame;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DominoGameService {

    DominoGame findDominoGameById(Long gameId);

    DominoGame create(CreateDominoGame game);

    DominoGame join(JoinDominoGame joinDominoGame);

    DominoGame start(Long gameId);

    DominoGame cancel(CancelDominoGame cancelDominoGame);

    Optional<DominoGame> findCurrentDominoGame();

    List<DominoGame> findAllPublicGames(Integer integer, Integer integer1);
}
