package org.playdomino.services.game.validation.start;

import org.playdomino.models.game.DominoGame;

public interface BeforeStartGameService {
    void process(DominoGame game);
}
