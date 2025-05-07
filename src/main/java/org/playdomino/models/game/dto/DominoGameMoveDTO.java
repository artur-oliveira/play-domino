package org.playdomino.models.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.game.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoGameMoveDTO {

    private Long id;
    private DominoGamePlayerDTO player;
    private DominoTile tilePlayed;
    private int turn;
    private MoveDirection moveDirection;
    private boolean passed;
    private boolean closedGame;
    private ZonedDateTime createdAt;

    public static DominoGameMoveDTO of(final DominoGameMove move) {
        return DominoGameMoveDTO
                .builder()
                .id(move.getId())
                .player(DominoGamePlayerDTO.of(move.getPlayer(), null))
                .tilePlayed(move.getTilePlayed())
                .moveDirection(move.getMoveDirection())
                .turn(move.getTurn())
                .passed(move.isPassed())
                .closedGame(move.isClosedGame())
                .build();
    }
}
