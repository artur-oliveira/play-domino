package org.playdomino.services.game.process.remove;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.dto.RemovePlayerDominoGame;

public interface AfterRemovePlayerService {
    void process(RemovePlayerDominoGame removePlayerDominoGame);
}
