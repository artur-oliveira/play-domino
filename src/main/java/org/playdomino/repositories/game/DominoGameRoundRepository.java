package org.playdomino.repositories.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameRound;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DominoGameRoundRepository extends JpaRepository<DominoGameRound, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select dgr from DominoGameRound dgr left join fetch dgr.pile where dgr.game = :game order by dgr.id")
    List<DominoGameRound> findAllByGameOrderById(@Param("game") DominoGame game);

    DominoGameRound game(DominoGame game);
}
