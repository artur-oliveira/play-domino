package org.playdomino.models.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class CreateGameMove {
    @JsonIgnore
    private Long gameId;

    @NotNull
    private DominoTile tile;
    @NotNull
    private MoveDirection moveDirection;

    public DominoGameMove toCreateGameMove(DominoGame game) {
        return DominoGameMove
                .builder()
                .gameRound(game.getCurrentRound())
                .moveDirection(getMoveDirection())
                .tilePlayed(getTile())
                .player(game.getPlayer(UserUtils.currentUser()))
                .turn(game.getCurrentRound().getMoves().size() + 1)
                .closedGame(wouldCloseTheGame(game))
                .build();
    }

    public boolean wouldCloseTheGame(
            DominoGame game
    ) {
        return game.wouldCloseTheGame(getTile(), getMoveDirection());
    }

}
