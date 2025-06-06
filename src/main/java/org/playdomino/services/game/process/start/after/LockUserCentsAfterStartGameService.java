package org.playdomino.services.game.process.start.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LockUserCentsAfterStartGameService implements AfterStartGameService {
    private final WalletService walletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(AddPlayerDominoGame addPlayerDominoGame) {
        if (addPlayerDominoGame.getGame().getBetAmountCents() < 0) {
            return;
        }
        walletService.lockForGame(WalletAmount
                .builder()
                .wallet(walletService.getUserWallet(addPlayerDominoGame.getUser()))
                .amountCents(addPlayerDominoGame.getGame().getBetAmountCents())
                .build());
    }
}
