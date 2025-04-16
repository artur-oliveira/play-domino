package org.playdomino.models.game.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.*;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoMoveDTO {

    private Long id;
    private DominoPlayerDTO player;
    private DominoTile tilePlayed;
    private int turn;
    private boolean passed;
    private boolean closedGame;
    private ZonedDateTime createdAt;

    public static DominoMoveDTO of(final DominoMove move) {
        return DominoMoveDTO
                .builder()
                .id(move.getId())
                .player(DominoPlayerDTO.of(move.getPlayer(), null))
                .tilePlayed(move.getTilePlayed())
                .turn(move.getTurn())
                .passed(move.isPassed())
                .closedGame(move.isClosedGame())
                .build();
    }
}
