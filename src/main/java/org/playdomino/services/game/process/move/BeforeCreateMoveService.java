package org.playdomino.services.game.process.move;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CreateGameMove;

public interface BeforeCreateMoveService {
    void process(DominoGame game, CreateGameMove createGameMove);
}
