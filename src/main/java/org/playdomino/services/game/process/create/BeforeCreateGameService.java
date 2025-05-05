package org.playdomino.services.game.process.create;

import org.playdomino.models.game.DominoGame;

public interface BeforeCreateGameService {
    void process(DominoGame game);
}
