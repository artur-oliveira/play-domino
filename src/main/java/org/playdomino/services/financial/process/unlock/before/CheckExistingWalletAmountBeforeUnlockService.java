package org.playdomino.services.financial.process.unlock.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.services.financial.process.unlock.BeforeUnlockService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckExistingWalletAmountBeforeUnlockService implements BeforeUnlockService {

    private final MessagesComponent messagesComponent;

    @Override
    public void process(WalletAmount walletAmount) {
        if (!Objects.equals(walletAmount.getWallet().getLockedCents(), walletAmount.getAmountCents())) {
            throw new WalletException(WalletExceptionConstants.WALLET_INSUFFICIENT_LOCKED, messagesComponent.getMessage(WalletExceptionConstants.WALLET_INSUFFICIENT_LOCKED));
        }
    }
}
