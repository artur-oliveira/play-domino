package org.playdomino.models.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.game.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class CreateDominoGame {
    @NotNull
    @Min(0)
    public Long betAmountCents;

    @NotNull
    @Builder.Default
    public Boolean visible = Boolean.TRUE;

    @NotNull
    @Builder.Default
    public Boolean allowBots = Boolean.FALSE;

    @NotNull
    @Builder.Default
    public GameStartCondition gameStartCondition = GameStartCondition.LAST_WINNER;

    @NotNull
    @Builder.Default
    public GameWinCondition gameWinCondition = GameWinCondition.ROUNDS;

    @Min(10)
    @Builder.Default
    private Long pointsToWin = 100L;

    @Builder.Default
    @NotNull
    @Min(1)
    private Long roundsToWin = 5L;

    @NotNull
    @Builder.Default
    private Boolean allowCloseGame = false;

    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,16}$",
            message = "{validation.dominogame.password}"
    )
    public String password;

    @JsonIgnore
    public DominoGame toGame(
            PasswordEncoder passwordEncoder
    ) {
        return DominoGame
                .builder()
                .betAmountCents(getBetAmountCents())
                .password(Optional.ofNullable(getPassword()).map(passwordEncoder::encode).orElse(null))
                .host(UserUtils.currentUser())
                .status(GameStatus.WAITING_FOR_PLAYERS)
                .visible(getVisible())
                .allowBots(getAllowBots())
                .allowCloseGame(getAllowCloseGame())
                .gameWinCondition(getGameWinCondition())
                .gameStartCondition(getGameStartCondition())
                .pointsToWin(getPointsToWin())
                .roundsToWin(getRoundsToWin())
                .players(new ArrayList<>())
                .inviteCode(UUID.randomUUID().toString())
                .build();
    }
}
