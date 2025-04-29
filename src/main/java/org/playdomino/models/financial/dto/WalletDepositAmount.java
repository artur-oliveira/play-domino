package org.playdomino.models.financial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.financial.Wallet;
import org.playdomino.models.financial.WalletTransaction;
import org.playdomino.models.financial.WalletTransactionType;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class WalletDepositAmount {
    @Min(value = 500, message = "{validation.wallet.deposit-amount}")
    private Long amountCents;

}
