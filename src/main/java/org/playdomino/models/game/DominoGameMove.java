package org.playdomino.models.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "domino_game_move")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DominoGameMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_move_game"))
    private DominoGame game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_move_player"))
    private DominoGamePlayer player;

    @Enumerated(EnumType.STRING)
    @Column(name = "tile_played")
    private DominoTile tilePlayed;

    @Column(name = "turn", nullable = false)
    private int turn;

    @Enumerated(EnumType.STRING)
    @Column(name = "move_direction", nullable = false)
    private MoveDirection moveDirection;

    @Column(name = "passed")
    private boolean passed;

    @Column(name = "closed_game", nullable = false)
    private boolean closedGame;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}

