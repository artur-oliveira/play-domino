package org.playdomino.services.financial;

import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {
    List<WalletTransaction> findAllTransactionsByWallet(Wallet wallet, Integer page, Integer size);
}
