package org.playdomino.repositories.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DominoGameVoteRepository extends JpaRepository<DominoGameVote, Long> {

    @Transactional(readOnly = true)
    Optional<DominoGameVote> findDominoGameVoteByGameAndPlayerAndVoteType(DominoGame game, DominoGamePlayer player, VoteType voteType);

    @Transactional(readOnly = true)
    @Query(value = "select dgv from DominoGameVote dgv left join fetch dgv.player where dgv.game = :game ORDER BY dgv.id")
    List<DominoGameVote> findAllByGameOrderById(@Param("game") DominoGame game);
}
