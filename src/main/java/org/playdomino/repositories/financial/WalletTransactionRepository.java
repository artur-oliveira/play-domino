package org.playdomino.repositories.financial;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select wt from WalletTransaction wt join fetch wt.wallet join fetch wt.wallet.user join fetch wt.createdBy where wt.wallet = :wallet order by wt.id desc")
    Page<WalletTransaction> findWalletTransactionByWalletOrderByIdDesc(Wallet wallet, Pageable pageable);

}
