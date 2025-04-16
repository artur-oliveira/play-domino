package org.playdomino.services.financial;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletAmount;

import java.math.BigDecimal;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet getCurrentUserWallet();

    boolean cannotPerformTransaction(User user, Long amountCents);

    void deposit(WalletAmount walletAmount);

    void withdraw(WalletAmount walletAmount);

    void confirmWithdraw(WalletAmount walletAmount);

    void cancelWithdraw(WalletAmount walletAmount);

    void lockForGame(WalletAmount walletAmount);

    void unlockFromGame(WalletAmount walletAmount);

    void prize(WalletAmount walletAmount);

    void chargeFee(WalletAmount walletAmount);
}
