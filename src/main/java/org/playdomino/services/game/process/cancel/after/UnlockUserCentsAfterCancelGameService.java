package org.playdomino.services.game.process.cancel.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.services.financial.WalletService;
import org.playdomino.services.game.process.addplayer.AfterAddPlayerService;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Order(0)
@RequiredArgsConstructor
public class UnlockUserCentsAfterCancelGameService implements AfterCancelGameService {
    private final WalletService walletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getStatus(), GameStatus.CANCELLED)) {
            return;
        }
        dominoGame.getPlayers().forEach(player -> walletService.unlockFromGame(WalletAmount
                .builder()
                .wallet(walletService.getUserWallet(player.getUser()))
                .amountCents(dominoGame.getBetAmountCents())
                .build()));
    }
}
