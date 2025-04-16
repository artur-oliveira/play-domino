package org.playdomino.models.game.dto;

import lombok.*;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoPlayer;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class AddPlayerToGame {
    private DominoGame game;
    private User user;
    private String password;

    public DominoPlayer toDominoPlayer() {
        return DominoPlayer
                .builder()
                .game(getGame())
                .user(getUser())
                .isHost(Objects.equals(getGame().getHost().getId(), getUser().getId()))
                .build();
    }
}
