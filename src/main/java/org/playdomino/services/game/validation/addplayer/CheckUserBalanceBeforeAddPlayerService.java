package org.playdomino.services.game.validation.addplayer;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.AddPlayerToGame;
import org.playdomino.services.financial.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckUserBalanceBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final WalletService walletService;

    @Transactional(readOnly = true)
    public void process(AddPlayerToGame playerToGame) {
        if (walletService.cannotPerformTransaction(playerToGame.getUser(), playerToGame.getGame().getBetAmountCents())) {
            throw new DominoGameException(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET));
        }
    }
}
