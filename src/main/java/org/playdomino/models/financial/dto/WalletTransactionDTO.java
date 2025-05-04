package org.playdomino.models.financial.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.components.utils.CurrencyUtils;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.WalletTransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class WalletTransactionDTO {

    private Long id;
    private WalletTransactionType type;
    private boolean isIncoming;
    private Long amountCents;
    private String displayAmountCents;
    private String description;
    private ZonedDateTime createdAt;
    private Long createdById;
    private String createdByName;

    public static WalletTransactionDTO of(WalletTransaction walletTransaction) {
        return WalletTransactionDTO
                .builder()
                .id(walletTransaction.getId())
                .type(walletTransaction.getType())
                .isIncoming(walletTransaction.getType().incoming())
                .displayAmountCents(CurrencyUtils.toCurrency(walletTransaction.getAmountCents(), walletTransaction.getWallet().getUser().getCountry().getJavaLocale()))
                .amountCents(walletTransaction.getAmountCents())
                .description(walletTransaction.getDescription())
                .createdAt(walletTransaction.getCreatedAt())
                .createdById(walletTransaction.getCreatedBy().getId())
                .createdByName(walletTransaction.getCreatedBy().getDisplayName())
                .build();
    }
}

