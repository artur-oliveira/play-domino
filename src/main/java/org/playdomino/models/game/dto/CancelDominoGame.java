package org.playdomino.models.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.VoteType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class CancelDominoGame {
    @JsonIgnore
    private Long gameId;
    @JsonIgnore
    private DominoGame game;
    @NotNull
    private Boolean approve;

    @JsonIgnore
    public DominoGameVote toDominoGameVote(
            final DominoGame dominoGame,
            final DominoGamePlayer dominoPlayer
    ) {
        return DominoGameVote
                .builder()
                .voteType(VoteType.CANCEL_GAME)
                .approved(getApprove())
                .game(dominoGame)
                .player(dominoPlayer)
                .build();
    }
}
