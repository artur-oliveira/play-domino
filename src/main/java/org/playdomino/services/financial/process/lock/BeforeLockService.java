package org.playdomino.services.financial.process.lock;

import org.playdomino.models.financial.dto.WalletAmount;

public interface BeforeLockService {
    void process(WalletAmount walletAmount);
}
