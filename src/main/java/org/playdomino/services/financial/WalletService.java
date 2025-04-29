package org.playdomino.services.financial;

import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.dto.WalletAmount;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet getCurrentUserWallet();

    boolean cannotPerformTransaction(User user, Long amountCents);

    void initiateDeposit(WalletAmount walletAmount);

    void confirmDeposit(WalletAmount walletAmount);

    void initiateWithdraw(WalletAmount walletAmount);

    void confirmWithdraw(WalletAmount walletAmount);

    void cancelWithdraw(WalletAmount walletAmount);

    void lockForGame(WalletAmount walletAmount);

    void unlockFromGame(WalletAmount walletAmount);

    void prize(WalletAmount walletAmount);

}
