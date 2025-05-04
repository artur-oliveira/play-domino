package org.playdomino.models.financial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.playdomino.models.auth.User;

@Entity
@Table(
        name = "wallet",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_wallet_user", columnNames = {"user_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Min(0)
    @Column(name = "available_cents", nullable = false)
    private Long availableCents = 0L;

    @Builder.Default
    @Min(0)
    @Column(name = "locked_cents", nullable = false)
    private Long lockedCents = 0L;

    @Builder.Default
    @Min(0)
    @Column(name = "pending_withdraw_cents", nullable = false)
    private Long pendingWithdrawCents = 0L;

    @Builder.Default
    @Min(0)
    @Column(name = "pending_deposit_cents", nullable = false)
    private Long pendingDepositCents = 0L;

    @Builder.Default
    @Min(0)
    @Column(name = "minimum_deposit_cents", nullable = false)
    private Long minimumDepositCents = 500L;

    @Builder.Default
    @Min(0)
    @Column(name = "minimum_withdraw_cents", nullable = false)
    private Long minimumWithdrawCents = 500L;

    @Builder.Default
    @Min(0)
    @Column(name = "maximum_deposit_cents", nullable = false)
    private Long maximumDepositCents = 1_000_000L;

    @Builder.Default
    @Min(0)
    @Column(name = "maximum_withdraw_cents", nullable = false)
    private Long maximumWithdrawCents = 1_000_000L;

    @Builder.Default
    @Min(0)
    @Max(100)
    @Column(name = "fee_percent", nullable = false)
    private Integer feePercent = 3;

    @Builder.Default
    @Min(200)
    @Column(name = "minimum_bet_cents", nullable = false)
    private Long minimumBetCents = 200L;

    @Builder.Default
    @Min(200)
    @Max(1_000_000)
    @Column(name = "maximum_bet_cents", nullable = false)
    private Long maximumBetCents = 1_000_000L;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_wallet_user"))
    private User user;

    public boolean cannotPerformTransaction(Long amountCents) {
        return getAvailableCents() < amountCents;
    }
}
