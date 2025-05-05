package org.playdomino.services.game.process.vote;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameVote;

public interface AfterGameVoteService {
    void process(DominoGame dominoGame, DominoGameVote gameVote);
}
