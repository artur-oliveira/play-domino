package org.playdomino.services.game.process.start;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.AddPlayerDominoGame;

public interface AfterStartGameService {
    void process(DominoGame dominoGame);
}
