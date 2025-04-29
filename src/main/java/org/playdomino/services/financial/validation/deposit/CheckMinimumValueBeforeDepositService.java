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
@Order(1)
public class CheckMinimumValueBeforeDepositService implements BeforeDepositService {
    private final MessagesComponent messagesComponent;


    private void checkMinimumValue(WalletAmount walletAmount) {
        if (walletAmount.getAmountCents() < walletAmount.getWallet().getMinimumDepositCents()) {
            throw new WalletException(WalletExceptionConstants.DEPOSIT_MINIMUM_VALUE, messagesComponent.getMessage(WalletExceptionConstants.DEPOSIT_MINIMUM_VALUE));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        checkMinimumValue(walletAmount);
    }
}
