package org.playdomino.models.game.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.models.game.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class DominoGameRoundDTO {
    private Long id;
    private List<DominoGameMoveDTO> moves;
    private List<DominoTile> pile;
    private DominoTile lastLeftTile;
    private DominoTile lastRightTile;
    private Long roundPoints;
    private DominoGamePlayerDTO winner;
    private int passCount;
    private boolean isClosed;
    private ZonedDateTime createdAt;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;

    public static DominoGameRoundDTO of(DominoGameRound round) {
        return DominoGameRoundDTO
                .builder()
                .id(round.getId())
                .moves(round.getMoves().stream().map(DominoGameMoveDTO::of).toList())
                .pile(round.getPile())
                .lastLeftTile(round.getLastLeftTile())
                .lastRightTile(round.getLastRightTile())
                .roundPoints(round.getRoundPoints())
                .winner(Optional.ofNullable(round.getWinner()).map(it -> DominoGamePlayerDTO.of(it, null)).orElse(null))
                .passCount(round.getPassCount())
                .isClosed(round.isClosed())
                .createdAt(round.getCreatedAt())
                .startedAt(round.getStartedAt())
                .endedAt(round.getEndedAt())
                .build();
    }
}
