package org.playdomino.models.financial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_wallet_user"))
    private User user;

    public boolean cannotPerformTransaction(Long amountCents) {
        return getAvailableCents() < amountCents;
    }
}
