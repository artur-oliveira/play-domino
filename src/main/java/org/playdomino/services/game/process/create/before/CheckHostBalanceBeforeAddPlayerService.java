package org.playdomino.services.game.process.create.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.game.process.create.BeforeCreateGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckHostBalanceBeforeAddPlayerService implements BeforeCreateGameService {

    private final MessagesComponent messagesComponent;
    private final WalletService walletService;

    @Transactional(readOnly = true)
    public void process(DominoGame game) {
        if (walletService.cannotPerformTransaction(game.getHost(), game.getBetAmountCents())) {
            throw new DominoGameException(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_AVAILABLE_FOR_BET));
        }
    }
}
