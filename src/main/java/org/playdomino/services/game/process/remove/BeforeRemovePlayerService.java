package org.playdomino.services.game.process.remove;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;

public interface BeforeRemovePlayerService {
    void process(final DominoGame game, final DominoGamePlayer player);
}
