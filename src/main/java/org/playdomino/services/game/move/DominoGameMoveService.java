package org.playdomino.services.game.move;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CreateGameMove;

public interface DominoGameMoveService {

    DominoGame addMove(CreateGameMove createGameMove);

}
