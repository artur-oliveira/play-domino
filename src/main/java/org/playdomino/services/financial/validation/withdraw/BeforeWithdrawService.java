package org.playdomino.services.financial.validation.withdraw;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeWithdrawService {
    void process(WalletAmount walletAmount);
}
