package org.playdomino.services.game.distribution;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameRound;

public interface FindFirstGamePlayer {
    int findFirstGamePlayer(DominoGame dominoGame);
}
