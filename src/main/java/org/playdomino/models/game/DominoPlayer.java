package org.playdomino.models.game;

import jakarta.persistence.*;
import lombok.*;
import org.playdomino.models.auth.User;

import java.util.List;

@Entity
@Table(name = "domino_game_player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DominoPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_player_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_player_game"))
    private DominoGame game;

    @ElementCollection
    @CollectionTable(
            name = "domino_game_player_hand",
            joinColumns = @JoinColumn(name = "domino_player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_player_hand_player_id")),
            uniqueConstraints = {@UniqueConstraint(name = "unique_domino_game_player_hand", columnNames = {"domino_player_id", "tile"})}
    )
    @Column(name = "tile", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DominoTile> hand;

    @Column(name = "passed_last_turn", nullable = false)
    private boolean passedLastTurn;
}

