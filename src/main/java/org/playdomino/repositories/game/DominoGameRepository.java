package org.playdomino.repositories.game;

import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DominoGameRepository extends JpaRepository<DominoGame, Long> {
    Optional<DominoGame> findDominoGameByHostOrderByIdDesc(User host);
}
