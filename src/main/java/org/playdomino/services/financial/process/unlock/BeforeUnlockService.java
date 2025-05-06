package org.playdomino.services.financial.process.unlock;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeUnlockService {
    void process(WalletAmount walletAmount);
}
