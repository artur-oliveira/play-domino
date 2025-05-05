package org.playdomino.services.game.process.create;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckBetGameBeforeAddPlayerService implements BeforeCreateGameService {

    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public void process(DominoGame game) {
        if (game.getHost().cannotBetOrPerformTransactions() && game.getBetAmountCents() > 0) {
            throw new DominoGameException(DominoGameExceptionConstants.NOT_HAVE_INFO_FOR_BET, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_HAVE_INFO_FOR_BET));
        }
    }
}
