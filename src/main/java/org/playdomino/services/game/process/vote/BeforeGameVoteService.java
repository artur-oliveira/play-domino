package org.playdomino.services.game.process.vote;

import org.playdomino.models.game.DominoGame;

public interface BeforeGameVoteService {
    void process(DominoGame game);
}
