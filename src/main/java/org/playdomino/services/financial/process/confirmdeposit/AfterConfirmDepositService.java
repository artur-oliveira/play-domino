package org.playdomino.services.financial.process.confirmdeposit;

import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletAmount;

public interface AfterConfirmDepositService {
    void process(WalletTransaction walletTransaction);
}
