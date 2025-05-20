package org.playdomino.models.game.dto;

import lombok.*;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class RemovePlayerDominoGame {
    private DominoGame dominoGame;
    private DominoGamePlayer dominoGamePlayer;
}
