package org.playdomino.models.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoGameDTO {
    private Long id;

    private GameStatus status;

    private int currentPlayer;

    private Long betAmountCents;

    private UserDTO host;

    private List<DominoGamePlayerDTO> players;
    private List<DominoGameMoveDTO> moves;
    private List<DominoTile> pile;

    private int passCount;
    private String inviteCode;
    private ZonedDateTime createdAt;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;

    public static DominoGameDTO of(
            final DominoGame game,
            final List<DominoGamePlayer> players,
            final List<DominoGameMove> moves
    ) {
        final User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return DominoGameDTO
                .builder()
                .id(game.getId())
                .status(game.getStatus())
                .currentPlayer(game.getCurrentPlayer())
                .betAmountCents(game.getBetAmountCents())
                .host(UserDTO.of(game.getHost()))
                .players(Optional.ofNullable(players).orElseGet(ArrayList::new).stream().map(it -> DominoGamePlayerDTO.of(it, loggedUser)).toList())
                .moves(Optional.ofNullable(moves).orElseGet(ArrayList::new).stream().map(DominoGameMoveDTO::of).toList())
                .inviteCode(game.getInviteCode())
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .endedAt(game.getEndedAt())
                .build();
    }
}
