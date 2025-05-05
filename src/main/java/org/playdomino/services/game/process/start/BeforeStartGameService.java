package org.playdomino.services.game.process.start;

import org.playdomino.models.game.DominoGame;

public interface BeforeStartGameService {
    void process(DominoGame game);
}
