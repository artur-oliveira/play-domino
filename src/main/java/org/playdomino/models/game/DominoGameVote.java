package org.playdomino.models.game;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "domino_game_vote", uniqueConstraints = {
        @UniqueConstraint(name = "unique_vote_per_game_player_vote_type", columnNames = {"game_id", "player_id", "vote_type"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DominoGameVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_vote_game"))
    private DominoGame game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domino_game_vote_game_player"))
    private DominoGamePlayer player;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    @Column(name = "approved", nullable = false)
    private boolean approved;
}

