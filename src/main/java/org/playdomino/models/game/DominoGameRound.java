package org.playdomino.models.game;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(
        name = "domino_game_round"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DominoGameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_round_game"))
    private DominoGame game;

    @OneToMany(mappedBy = "gameRound", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DominoGameMove> moves;

    @ElementCollection
    @CollectionTable(
            name = "domino_game_round_pile",
            joinColumns = @JoinColumn(name = "domino_game_round_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_pile_game_round_id")),
            uniqueConstraints = {@UniqueConstraint(name = "unique_domino_game_pile", columnNames = {"domino_game_round_id", "tile"})}
    )
    @Column(name = "tile", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DominoTile> pile;

    @Column(name = "last_left_tile")
    @Enumerated(EnumType.STRING)
    private DominoTile lastLeftTile;

    @Column(name = "last_right_tile")
    @Enumerated(EnumType.STRING)
    private DominoTile lastRightTile;

    @Builder.Default
    @Column(name = "round_points", nullable = false)
    private Long roundPoints = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_player_id", foreignKey = @ForeignKey(name = "fk_domino_game_round_winner_player"))
    private DominoGamePlayer winner;

    @Column(name = "pass_count", nullable = false)
    private int passCount;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "started_at")
    private ZonedDateTime startedAt;

    @Column(name = "ended_at")
    private ZonedDateTime endedAt;

}
