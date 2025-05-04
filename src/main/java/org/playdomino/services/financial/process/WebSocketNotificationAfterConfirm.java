package org.playdomino.services.financial.process;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.dto.WalletTransactionDTO;
import org.playdomino.models.ws.NotificationTopic;
import org.playdomino.models.ws.WebSocketNotification;
import org.playdomino.services.ws.WebSocketNotificationService;

@RequiredArgsConstructor
public class WebSocketNotificationAfterConfirm {

    private final MessagesComponent messagesComponent;
    private final WebSocketNotificationService webSocketNotificationService;

    protected void sendNotification(WalletTransaction walletTransaction, String messageType) {
        webSocketNotificationService.sendNotification(WebSocketNotification
                .builder()
                .topic(NotificationTopic.FINANCIAL)
                .data(WalletTransactionDTO.of(walletTransaction))
                .message(messagesComponent.getMessage(messageType))
                .messageType(messageType)
                .messageId(walletTransaction.getId().toString())
                .notifyId(walletTransaction.getWallet().getUser().getId())
                .build());
    }

}
