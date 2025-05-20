package org.playdomino.models.game.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoGameDTO {
    private Long id;
    private GameStatus status;
    private UserDTO host;
    private boolean currentHost;
    private List<DominoGamePlayerDTO> players;
    private List<DominoGameRoundDTO> rounds;
    private List<DominoGameVoteDTO> votes;
    private int currentPlayer;
    private Long betAmountCents;
    private GameStartCondition gameStartCondition;
    private GameWinCondition gameWinCondition;
    private Long pointsToWin;
    private Long roundsToWin;
    private boolean allowCloseGame;
    private DominoGamePlayerDTO winner;
    private boolean visible;
    private boolean allowBots;
    private String inviteCode;
    private boolean requiresPassword;
    private ZonedDateTime createdAt;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;

    public static DominoGameDTO of(
            final DominoGame game,
            final List<DominoGamePlayer> players,
            final List<DominoGameRound> rounds,
            final List<DominoGameVote> votes
    ) {
        final User loggedUser = UserUtils.currentUser();
        return DominoGameDTO
                .builder()
                .id(game.getId())
                .status(game.getStatus())
                .currentPlayer(game.getCurrentPlayer())
                .betAmountCents(game.getBetAmountCents())
                .host(UserDTO.of(game.getHost()))
                .currentHost(Objects.equals(game.getHost().getId(), loggedUser.getId()))
                .players(Optional.ofNullable(players).orElseGet(ArrayList::new).stream().map(it -> DominoGamePlayerDTO.of(it, loggedUser)).toList())
                .rounds(Optional.ofNullable(rounds).orElseGet(ArrayList::new).stream().map(DominoGameRoundDTO::of).toList())
                .votes(Optional.ofNullable(votes).orElseGet(ArrayList::new).stream().map(DominoGameVoteDTO::of).toList())
                .inviteCode(Objects.equals(game.getHost().getId(), loggedUser.getId()) ? game.getInviteCode() : null)
                .gameStartCondition(game.getGameStartCondition())
                .gameWinCondition(game.getGameWinCondition())
                .pointsToWin(game.getPointsToWin())
                .roundsToWin(game.getRoundsToWin())
                .allowCloseGame(game.isAllowCloseGame())
                .allowBots(game.isAllowBots())
                .winner(Optional.ofNullable(game.getWinner()).map(it -> DominoGamePlayerDTO.of(it, null)).orElse(null))
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .requiresPassword(Objects.nonNull(game.getPassword()))
                .endedAt(game.getEndedAt())
                .build();
    }
}
