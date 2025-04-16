package org.playdomino.models.game.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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

    private List<DominoPlayerDTO> players;
    private List<DominoMoveDTO> moves;
    private List<DominoTile> pile;

    private int passCount;
    private String inviteCode;
    private ZonedDateTime createdAt;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;

    public static DominoGameDTO of(final DominoGame game) {
        final User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return DominoGameDTO
                .builder()
                .id(game.getId())
                .status(game.getStatus())
                .currentPlayer(game.getCurrentPlayer())
                .betAmountCents(game.getBetAmountCents())
                .host(UserDTO.of(game.getHost()))
                .players(Optional.ofNullable(game.getPlayers()).orElseGet(ArrayList::new).stream().map(it -> DominoPlayerDTO.of(it, loggedUser)).toList())
                .moves(Optional.ofNullable(game.getMoves()).orElseGet(ArrayList::new).stream().map(DominoMoveDTO::of).toList())
                .inviteCode(game.getInviteCode())
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .endedAt(game.getEndedAt())
                .build();
    }
}
