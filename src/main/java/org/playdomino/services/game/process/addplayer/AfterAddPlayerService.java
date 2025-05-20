package org.playdomino.services.game.process.addplayer;

import org.playdomino.models.game.DominoGame;

public interface AfterAddPlayerService {
    void process(DominoGame dominoGame);
}
