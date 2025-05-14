package org.playdomino.services.game.process.exit;

import org.playdomino.models.game.DominoGame;

public interface BeforeExitGameService {
    void process(final DominoGame dominoGame);
}
