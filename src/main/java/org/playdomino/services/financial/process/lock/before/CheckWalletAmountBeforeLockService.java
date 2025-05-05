package org.playdomino.services.financial.process.lock.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.services.financial.process.lock.BeforeLockService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckWalletAmountBeforeLockService implements BeforeLockService {

    private final MessagesComponent messagesComponent;

    @Override
    public void process(WalletAmount walletAmount) {
        if (walletAmount.getWallet().cannotPerformTransaction(walletAmount.getAmountCents())) {
            throw new WalletException(WalletExceptionConstants.WALLET_INSUFFICIENT_BALANCE, messagesComponent.getMessage(WalletExceptionConstants.WALLET_INSUFFICIENT_BALANCE));
        }
    }
}
