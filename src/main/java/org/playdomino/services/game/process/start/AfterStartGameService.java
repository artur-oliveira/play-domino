package org.playdomino.services.game.process.start;

import org.playdomino.models.game.dto.AddPlayerDominoGame;

public interface AfterStartGameService {
    void process(AddPlayerDominoGame addPlayerDominoGame);
}
