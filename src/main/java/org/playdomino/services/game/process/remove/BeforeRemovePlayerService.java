package org.playdomino.services.game.process.remove;

import org.playdomino.models.game.dto.RemovePlayerDominoGame;

public interface BeforeRemovePlayerService {
    void process(RemovePlayerDominoGame removePlayerDominoGame);
}
