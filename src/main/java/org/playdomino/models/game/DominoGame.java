package org.playdomino.models.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.models.auth.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "domino_game", uniqueConstraints = {
        @UniqueConstraint(name = "unique_domino_game_invite_code", columnNames = {"invite_code"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DominoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    @ManyToOne
    @JoinColumn(name = "host_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_host_user"))
    private User host;

    @Size(max = 4)
    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<DominoGamePlayer> players;

    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DominoGameRound> rounds;

    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DominoGameVote> votes;

    @Min(0)
    @Max(3)
    @Column(name = "current_player", nullable = false)
    private int currentPlayer;

    @Column(name = "bet_amount_cents", nullable = false)
    private Long betAmountCents;

    // Game Rules
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "game_start_condition", nullable = false, updatable = false)
    private GameStartCondition gameStartCondition = GameStartCondition.LAST_WINNER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "game_win_condition", nullable = false, updatable = false)
    private GameWinCondition gameWinCondition = GameWinCondition.ROUNDS;

    @Builder.Default
    @Min(10)
    @Column(name = "points_to_win", nullable = false, updatable = false)
    private Long pointsToWin = 100L;

    @Builder.Default
    @Min(1)
    @Column(name = "rounds_to_win", nullable = false, updatable = false)
    private Long roundsToWin = 5L;

    @Builder.Default
    @Column(name = "allow_close_game", nullable = false, updatable = false)
    private boolean allowCloseGame = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_player_id", foreignKey = @ForeignKey(name = "fk_domino_game_winner_player"))
    private DominoGamePlayer winner;

    @Builder.Default
    @Column(name = "visible", nullable = false)
    private boolean visible = true;

    @Builder.Default
    @Column(name = "allow_bots", nullable = false)
    private boolean allowBots = false;

    @Column(name = "invite_code", unique = true, nullable = false, updatable = false)
    private String inviteCode;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "started_at")
    private ZonedDateTime startedAt;

    @Column(name = "ended_at")
    private ZonedDateTime endedAt;

    @Transient
    @JsonIgnore
    public boolean notWaitingForPlayers() {
        return getStatus() != GameStatus.WAITING_FOR_PLAYERS;
    }

    @Transient
    @JsonIgnore
    public boolean cannotAcceptNewPlayers() {
        return getPlayers().size() == 4;
    }

    @JsonIgnore
    @Transient
    public boolean isPlayerTurn(DominoGamePlayer player) {
        return Objects.equals(getPlayers().get(getCurrentPlayer()).getId(), player.getId());
    }

    @Transient
    public DominoGamePlayer getPlayer(User user) {
        return getPlayers().stream().filter(it -> Objects.equals(user.getId(), it.getUser().getId())).findFirst().orElse(null);
    }
}
