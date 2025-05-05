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
@Order(1)
public class CheckWalletBalanceBeforeWithdrawService implements BeforeWithdrawService {
    private final MessagesComponent messagesComponent;

    private void checkMaximumValue(WalletAmount walletAmount) {
        if (walletAmount.getWallet().cannotPerformTransaction(walletAmount.getAmountCents())) {
            throw new WalletException(WalletExceptionConstants.WALLET_INSUFFICIENT_BALANCE, messagesComponent.getMessage(WalletExceptionConstants.WALLET_INSUFFICIENT_BALANCE));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        checkMaximumValue(walletAmount);
    }
}
