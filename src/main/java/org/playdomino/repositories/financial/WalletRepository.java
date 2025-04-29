package org.playdomino.repositories.financial;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select w from Wallet w join fetch w.user u where w.user = :user")
    Optional<Wallet> findWalletByUser(@Param("user") User user);

    boolean existsWalletByUser(User user);
}
