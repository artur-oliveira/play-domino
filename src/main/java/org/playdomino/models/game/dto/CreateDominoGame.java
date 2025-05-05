package org.playdomino.models.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoTile;
import org.playdomino.models.game.GameStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean visible = Boolean.TRUE;

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
                .visible(isVisible())
                .pile(new ArrayList<>(Arrays.asList(DominoTile.values())))
                .players(new ArrayList<>())
                .inviteCode(UUID.randomUUID().toString())
                .build();
    }
}
