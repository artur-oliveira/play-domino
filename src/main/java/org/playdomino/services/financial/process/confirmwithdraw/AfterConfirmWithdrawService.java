package org.playdomino.services.financial.process.confirmwithdraw;

import org.playdomino.models.financial.WalletTransaction;

public interface AfterConfirmWithdrawService {
    void process(WalletTransaction walletTransaction);
}
