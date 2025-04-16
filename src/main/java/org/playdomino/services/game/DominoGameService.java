package org.playdomino.services.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.JoinDominoGame;

public interface DominoGameService {

    DominoGame findDominoGameById(Long gameId);

    DominoGame create(CreateDominoGame game);

    DominoGame join(JoinDominoGame joinDominoGame);

}
