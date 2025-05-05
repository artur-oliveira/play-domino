package org.playdomino.services.game.process.vote.after;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.services.game.process.vote.AfterGameVoteService;
import org.playdomino.services.game.process.vote.WebSocketNotificationAfterVote;
import org.playdomino.services.ws.WebSocketNotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotifyWebSocketAfterVoteService extends WebSocketNotificationAfterVote implements AfterGameVoteService {
    public NotifyWebSocketAfterVoteService(
            MessagesComponent messagesComponent,
            WebSocketNotificationService webSocketNotificationService
    ) {
        super(messagesComponent, webSocketNotificationService);
    }

    @Override
    public void process(DominoGame dominoGame, DominoGameVote gameVote) {
        sendNotification(dominoGame, gameVote);
    }
}
