package org.playdomino.services.financial.process.confirmdeposit;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeConfirmDepositService {
    void process(WalletAmount walletAmount);
}
