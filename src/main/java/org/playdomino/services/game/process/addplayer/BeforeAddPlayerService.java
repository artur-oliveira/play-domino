package org.playdomino.services.game.process.addplayer;

import org.playdomino.models.game.dto.AddPlayerDominoGame;

public interface BeforeAddPlayerService {
    void process(AddPlayerDominoGame addPlayerDominoGame);
}
