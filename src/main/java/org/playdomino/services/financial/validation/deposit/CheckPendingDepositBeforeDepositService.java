package org.playdomino.services.financial.validation.deposit;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.dto.WalletAmount;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Order(3)
public class CheckPendingDepositBeforeDepositService implements BeforeDepositService {
    private final MessagesComponent messagesComponent;

    private void checkPendingDeposit(WalletAmount walletAmount) {
        if (walletAmount.getWallet().getPendingDepositCents() > 0) {
            throw new WalletException(WalletExceptionConstants.PENDING_DEPOSIT_EXISTS, messagesComponent.getMessage(WalletExceptionConstants.PENDING_DEPOSIT_EXISTS));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        checkPendingDeposit(walletAmount);
    }
}
