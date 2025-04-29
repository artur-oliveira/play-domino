package org.playdomino.services.game.validation.cancel;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;

public interface BeforeCancelGameService {
    void process(CancelDominoGame cancelDominoGame);
}
