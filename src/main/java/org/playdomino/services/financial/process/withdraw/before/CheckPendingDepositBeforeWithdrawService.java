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
@Order(4)
public class CheckPendingDepositBeforeWithdrawService implements BeforeWithdrawService {
    private final MessagesComponent messagesComponent;

    private void checkPendingDeposit(WalletAmount walletAmount) {
        if (walletAmount.getWallet().getPendingDepositCents() > 0) {
            throw new WalletException(WalletExceptionConstants.PENDING_WITHDRAW_EXISTS, messagesComponent.getMessage(WalletExceptionConstants.PENDING_WITHDRAW_EXISTS));
        }
    }

    @Transactional(readOnly = true)
    public void process(WalletAmount walletAmount) {
        checkPendingDeposit(walletAmount);
    }
}
