package org.playdomino.services.financial.process.confirmwithdraw;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.services.financial.process.WebSocketNotificationAfterConfirm;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WebSocketNotificationAfterConfirmWithdrawService extends WebSocketNotificationAfterConfirm implements AfterConfirmWithdrawService {
    private static final String CONFIRM_WITHDRAW_DEPOSIT = "transaction.withdraw.confirm";

    public WebSocketNotificationAfterConfirmWithdrawService(MessagesComponent messagesComponent, WebSocketNotificationService webSocketNotificationService) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Transactional(readOnly = true)
    public void process(WalletTransaction walletTransaction) {
        sendNotification(walletTransaction, CONFIRM_WITHDRAW_DEPOSIT);
    }
}
