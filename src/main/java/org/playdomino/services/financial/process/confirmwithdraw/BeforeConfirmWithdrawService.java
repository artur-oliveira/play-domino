package org.playdomino.services.financial.process.confirmwithdraw;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeConfirmWithdrawService {
    void process(WalletAmount walletAmount);
}
