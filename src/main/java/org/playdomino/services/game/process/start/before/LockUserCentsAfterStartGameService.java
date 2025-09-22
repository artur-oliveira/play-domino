package org.playdomino.services.game.process.start.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.playdomino.services.game.process.start.BeforeStartGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LockUserCentsAfterStartGameService implements BeforeStartGameService {
    private final WalletService walletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame dominoGame) {
        if (dominoGame.getBetAmountCents() <= 0) {
            return;
        }
        for (DominoGamePlayer player : dominoGame.getPlayers()) {
            walletService.lockForGame(WalletAmount
                    .builder()
                    .wallet(walletService.getUserWallet(player.getUser()))
                    .amountCents(dominoGame.getBetAmountCents())
                    .build());
        }
    }
}
