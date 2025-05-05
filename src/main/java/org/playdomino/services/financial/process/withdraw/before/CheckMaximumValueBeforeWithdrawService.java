package org.playdomino.services.financial.process.withdraw.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.services.financial.process.withdraw.BeforeWithdrawService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Order(2)
public class CheckMaximumValueBeforeWithdrawService implements BeforeWithdrawService {
    private final MessagesComponent messagesComponent;

    private void checkMaximumValue(WalletAmount walletAmount) {
        if (walletAmount.getAmountCents() > walletAmount.getWallet().getMaximumWithdrawCents()) {
            throw new WalletException(WalletExceptionConstants.WITHDRAW_MAXIMUM_VALUE, messagesComponent.getMessage(WalletExceptionConstants.WITHDRAW_MAXIMUM_VALUE));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        checkMaximumValue(walletAmount);
    }
}
