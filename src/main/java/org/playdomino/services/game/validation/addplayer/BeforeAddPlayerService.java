package org.playdomino.services.game.validation.addplayer;

import org.playdomino.models.game.dto.AddPlayerDominoGame;

public interface BeforeAddPlayerService {
    void process(AddPlayerDominoGame addPlayerDominoGame);
}
