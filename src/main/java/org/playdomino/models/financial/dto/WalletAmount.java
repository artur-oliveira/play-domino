package org.playdomino.models.financial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.playdomino.models.auth.User;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.WalletTransactionType;
import org.springframework.security.core.context.SecurityContextHolder;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class WalletAmount {
    private Wallet wallet;
    private String description;
    private Long amountCents;

    @JsonIgnore
    public WalletTransaction walletTransaction(WalletTransactionType type) {
        return WalletTransaction
                .builder()
                .wallet(wallet)
                .type(type)
                .description(description)
                .amountCents(type.incoming() ? amountCents : -amountCents)
                .createdBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build();
    }
}
