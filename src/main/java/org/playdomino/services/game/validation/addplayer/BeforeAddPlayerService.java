package org.playdomino.services.game.validation.addplayer;

import org.playdomino.models.game.dto.AddPlayerToGame;

public interface BeforeAddPlayerService {
    void process(AddPlayerToGame addPlayerToGame);
}
