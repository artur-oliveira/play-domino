package org.playdomino.services.game.process.cancel;

import org.playdomino.models.game.dto.CancelDominoGame;

public interface BeforeCancelGameService {
    void process(CancelDominoGame cancelDominoGame);
}
