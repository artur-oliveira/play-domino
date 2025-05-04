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
    private Long maximumBetCents;
    private Long minimumBetCents;
    private String displayAvailableCents;
    private String displayLockedCents;
    private String displayPendingDepositCents;
    private String displayPendingWithdrawCents;
    private Integer feePercent;

    public static WalletDTO of(Wallet wallet) {
        return WalletDTO
                .builder()
                .minimumDepositCents(wallet.getMinimumDepositCents())
                .minimumWithdrawCents(wallet.getMinimumWithdrawCents())
                .maximumDepositCents(wallet.getMaximumDepositCents())
                .maximumWithdrawCents(wallet.getMaximumWithdrawCents())
                .feePercent(wallet.getFeePercent())
                .minimumBetCents(wallet.getMinimumBetCents())
                .maximumBetCents(wallet.getMaximumBetCents())
                .availableCents(wallet.getAvailableCents())
                .displayAvailableCents(CurrencyUtils.toCurrency(wallet.getAvailableCents(), wallet.getUser().getCountry().getJavaLocale()))
                .displayLockedCents(CurrencyUtils.toCurrency(wallet.getLockedCents(), wallet.getUser().getCountry().getJavaLocale()))
                .displayPendingDepositCents(CurrencyUtils.toCurrency(wallet.getPendingDepositCents(), wallet.getUser().getCountry().getJavaLocale()))
                .displayPendingWithdrawCents(CurrencyUtils.toCurrency(wallet.getPendingWithdrawCents(), wallet.getUser().getCountry().getJavaLocale()))
                .lockedCents(wallet.getLockedCents())
                .pendingWithdrawCents(wallet.getPendingWithdrawCents())
                .pendingDepositCents(wallet.getPendingDepositCents())
                .build();
    }
}
