package org.playdomino.models.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.financial.Wallet;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class WalletDTO {
    private Long availableCents;
    private Long lockedCents;
    private Long pendingWithdrawCents;
    private Long pendingDepositCents;

    public static WalletDTO of(Wallet wallet) {
        return WalletDTO
                .builder()
                .availableCents(wallet.getAvailableCents())
                .lockedCents(wallet.getLockedCents())
                .pendingWithdrawCents(wallet.getPendingWithdrawCents())
                .pendingDepositCents(wallet.getPendingDepositCents())
                .build();
    }
}
