package org.playdomino.services.game.process.addplayer.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.game.process.addplayer.BeforeAddPlayerService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(2)
@RequiredArgsConstructor
public class CheckUserBalanceBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final WalletService walletService;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        if (walletService.cannotPerformTransaction(playerToGame.getUser(), playerToGame.getGame().getBetAmountCents())) {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET));
        }
    }
}
