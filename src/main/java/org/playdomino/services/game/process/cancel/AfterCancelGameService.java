package org.playdomino.services.game.process.cancel;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;

public interface AfterCancelGameService {
    void process(DominoGame dominoGame);
}
