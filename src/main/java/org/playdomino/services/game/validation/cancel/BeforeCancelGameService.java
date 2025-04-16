package org.playdomino.services.game.validation.cancel;

import org.playdomino.models.game.DominoGame;

public interface BeforeCancelGameService {
    void process(DominoGame game);
}
