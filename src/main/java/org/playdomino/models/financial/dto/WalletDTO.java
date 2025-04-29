package org.playdomino.models.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.components.utils.CurrencyUtils;
import org.playdomino.models.financial.Wallet;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.NumberFormat;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class WalletDTO {
    private Long availableCents;
    private Long lockedCents;
    private Long pendingWithdrawCents;
    private Long pendingDepositCents;
    private Long minimumDepositCents;
    private Long minimumWithdrawCents;
    private Long maximumDepositCents;
    private Long maximumWithdrawCents;
    private Integer feePercent;

    public static WalletDTO of(Wallet wallet) {
        return WalletDTO
                .builder()
                .minimumDepositCents(wallet.getMinimumDepositCents())
                .minimumWithdrawCents(wallet.getMinimumWithdrawCents())
                .maximumDepositCents(wallet.getMaximumDepositCents())
                .maximumWithdrawCents(wallet.getMaximumWithdrawCents())
                .feePercent(wallet.getFeePercent())
                .availableCents(wallet.getAvailableCents())
                .lockedCents(wallet.getLockedCents())
                .pendingWithdrawCents(wallet.getPendingWithdrawCents())
                .pendingDepositCents(wallet.getPendingDepositCents())
                .build();
    }

    public String getDisplayAvailableCents() {
        return CurrencyUtils.toCurrency(getAvailableCents() / 100.0);
    }

    public String getDisplayLockedCents() {
        return CurrencyUtils.toCurrency(getLockedCents() / 100.0);
    }

    public String getDisplayPendingDepositCents() {
        return CurrencyUtils.toCurrency(getPendingDepositCents() / 100.0);
    }

    public String getDisplayPendingWithdrawCents() {
        return CurrencyUtils.toCurrency(getPendingWithdrawCents() / 100.0);
    }
}
