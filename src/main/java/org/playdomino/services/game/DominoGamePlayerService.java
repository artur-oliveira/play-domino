package org.playdomino.services.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;

public interface DominoGamePlayerService {

    DominoGamePlayer getPlayerInGame(DominoGame game);

}
