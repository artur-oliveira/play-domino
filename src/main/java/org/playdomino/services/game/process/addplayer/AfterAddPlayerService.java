package org.playdomino.services.game.process.addplayer;

import org.playdomino.models.game.dto.AddPlayerDominoGame;

public interface AfterAddPlayerService {
    void process(AddPlayerDominoGame addPlayerDominoGame);
}
