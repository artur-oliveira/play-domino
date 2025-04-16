package org.playdomino.repositories.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DominoGameVoteRepository extends JpaRepository<DominoGameVote, Long> {

    Optional<DominoGameVote> findDominoGameVoteByGameAndPlayerAndVoteType(DominoGame game, DominoGamePlayer player, VoteType voteType);

}
