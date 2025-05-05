package org.playdomino.services.financial.process.withdraw;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeWithdrawService {
    void process(WalletAmount walletAmount);
}
