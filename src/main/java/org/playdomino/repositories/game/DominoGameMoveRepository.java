package org.playdomino.repositories.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DominoGameMoveRepository extends JpaRepository<DominoGameMove, Long> {

    @Transactional(readOnly = true)
    List<DominoGameMove> findAllByGameOrderById(DominoGame game);

}
