package org.playdomino.models.game.dto;

import jakarta.persistence.*;
import lombok.*;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoPlayer;
import org.playdomino.models.game.DominoTile;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoPlayerDTO {
    private Long id;
    private UserDTO user;
    private List<DominoTile> hand;
    private boolean passedLastTurn;
    private boolean isHost;

    public static DominoPlayerDTO of(
            final DominoPlayer player, final User loggedUser
    ) {
        return DominoPlayerDTO
                .builder()
                .id(player.getId())
                .user(UserDTO.of(player.getUser()))
                .hand(Objects.nonNull(loggedUser) && Objects.equals(loggedUser.getId(), player.getUser().getId()) ? player.getHand() : Collections.emptyList())
                .passedLastTurn(player.isPassedLastTurn())
                .isHost(player.isHost())
                .build();
    }
}
