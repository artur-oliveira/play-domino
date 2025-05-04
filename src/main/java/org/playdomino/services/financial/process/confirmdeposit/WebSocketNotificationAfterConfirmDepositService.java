package org.playdomino.services.financial.process.confirmdeposit;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.financial.WalletException;
import org.playdomino.exceptions.financial.WalletExceptionConstants;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.financial.process.WebSocketNotificationAfterConfirm;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class WebSocketNotificationAfterConfirmDepositService extends WebSocketNotificationAfterConfirm implements AfterConfirmDepositService {
    private static final String CONFIRM_DEPOSIT_MESSAGE = "transaction.deposit.confirm";

    public WebSocketNotificationAfterConfirmDepositService(MessagesComponent messagesComponent, WebSocketNotificationService webSocketNotificationService) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Transactional(readOnly = true)
    public void process(WalletTransaction walletTransaction) {
        sendNotification(walletTransaction, CONFIRM_DEPOSIT_MESSAGE);
    }
}
