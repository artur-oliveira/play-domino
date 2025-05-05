package org.playdomino.services.financial.process.confirmdeposit.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.services.financial.process.confirmdeposit.BeforeConfirmDepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckPendingValueBeforeConfirmDepositService implements BeforeConfirmDepositService {
    private final MessagesComponent messagesComponent;

    private void comparePendingAndConfirmValues(WalletAmount walletAmount) {
        if (!Objects.equals(walletAmount.getAmountCents(), walletAmount.getWallet().getPendingDepositCents())) {
            throw new WalletException(WalletExceptionConstants.INCORRECT_PENDING_DEPOSIT_AMOUNT, messagesComponent.getMessage(WalletExceptionConstants.INCORRECT_PENDING_DEPOSIT_AMOUNT));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        comparePendingAndConfirmValues(walletAmount);
    }
}
