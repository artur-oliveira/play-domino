package org.playdomino.services.financial;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.repositories.financial.WalletTransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WalletTransaction> findAllTransactionsByWallet(Wallet wallet, Integer page, Integer size) {
        return walletTransactionRepository.findWalletTransactionByWalletOrderByIdDesc(
                wallet,
                PageRequest.of(
                        Math.max(Optional.ofNullable(page).map(it -> it - 1).orElse(0), 0),
                        Math.min(Optional.ofNullable(size).orElse(10), 50)
                )
        ).toList();
    }
}
