package org.playdomino.repositories.game;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DominoGameRepository extends JpaRepository<DominoGame, Long> {
    @Transactional(readOnly = true)
    Optional<DominoGame> findFirstByHostOrderByIdDesc(User host);

    @Query(value = "select exists(select dg.id from DominoGame dg join dg.players dgp where dgp.user = :user AND dg.status in (:status_list))")
    @Transactional(readOnly = true)
    boolean existsDominoGameByStatusAndPlayerUser(@Param("status_list") List<GameStatus> statusList, @Param("user") User user);

    @Query(value = "select dg from DominoGame dg join fetch dg.players dgp where dgp.user = :user AND dg.status in (:status_list)")
    @Transactional(readOnly = true)
    Optional<DominoGame> findDominoGameByStatusAndPlayerUser(@Param("status_list") List<GameStatus> statusList, @Param("user") User user);

    @Query(value = "select dg.id from DominoGame dg where dg.status in (:status_list) and dg.visible = true and dg.password is null and dg.host != :user order by dg.id desc")
    @Transactional(readOnly = true)
    List<Long> findIdPublicUnfinishedGamesByHostNotEqual(@Param("user") User user, @Param("status_list") List<GameStatus> statusList, Pageable pageable);

    @Query(value = "select dg from DominoGame dg join fetch dg.players join fetch dg.host where dg.id in (:ids)")
    @Transactional(readOnly = true)
    List<DominoGame> findAllByIdIn(@Param("ids") Collection<Long> ids);

    @Transactional(readOnly = true)
    Optional<DominoGame> findByInviteCode(String inviteCode);
}
