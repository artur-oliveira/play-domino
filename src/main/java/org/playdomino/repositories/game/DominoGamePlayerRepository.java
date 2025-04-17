package org.playdomino.repositories.game;

import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DominoGamePlayerRepository extends JpaRepository<DominoGamePlayer, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select dgp from DominoGamePlayer dgp left join fetch dgp.hand where dgp.game = :game")
    List<DominoGamePlayer> findAllByGameOrderById(@Param("game") DominoGame game);

    @Transactional(readOnly = true)
    Optional<DominoGamePlayer> findDominoGamePlayerByGameAndUser(DominoGame game, User user);
}
