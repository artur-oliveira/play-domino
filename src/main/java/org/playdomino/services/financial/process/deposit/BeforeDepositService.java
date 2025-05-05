package org.playdomino.services.financial.process.deposit;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeDepositService {
    void process(WalletAmount walletAmount);
}
