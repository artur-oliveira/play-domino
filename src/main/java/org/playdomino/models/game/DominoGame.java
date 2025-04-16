package org.playdomino.models.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

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

    @Min(0)
    @Max(3)
    @Column(name = "current_player", nullable = false)
    private int currentPlayer;

    @Column(name = "bet_amount_cents", nullable = false)
    private Long betAmountCents;

    @Size(min = 1, max = 4)
    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DominoPlayer> players;

    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DominoMove> moves;

    @ElementCollection
    @CollectionTable(
            name = "domino_game_pile",
            joinColumns = @JoinColumn(name = "domino_game_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_pile_game_id")),
            uniqueConstraints = {@UniqueConstraint(name = "unique_domino_game_pile", columnNames = {"domino_game_id", "tile"})}
    )
    @Column(name = "tile", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DominoTile> pile;

    @Column(name = "pass_count", nullable = false)
    private int passCount;

    @Column(name = "invite_code", unique = true, nullable = false, updatable = false)
    private String inviteCode;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "started_at", nullable = false)
    private ZonedDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private ZonedDateTime endedAt;
}
