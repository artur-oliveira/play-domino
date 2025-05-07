package org.playdomino.services.game.process.cancel.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.dto.WalletAmount;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.services.financial.WalletService;
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

    boolean canUnlockCents(Wallet wallet) {
        return wallet.getLockedCents() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame dominoGame) {
        if (!Objects.equals(dominoGame.getStatus(), GameStatus.CANCELLED)) {
            return;
        }
        dominoGame.getPlayers().forEach(player -> {
            final Wallet wallet = walletService.getUserWallet(player.getUser());
            if (!canUnlockCents(wallet)) {
                return;
            }
            walletService.unlockFromGame(WalletAmount
                    .builder()
                    .wallet(wallet)
                    .amountCents(dominoGame.getBetAmountCents())
                    .build());
        });
    }
}
