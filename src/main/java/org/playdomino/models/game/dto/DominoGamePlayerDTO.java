package org.playdomino.models.game.dto;

import lombok.*;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserDTO;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoTile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoGamePlayerDTO {
    private Long id;
    private UserDTO user;
    private List<DominoTile> hand;
    private boolean passedLastTurn;
    private boolean isHost;

    public static DominoGamePlayerDTO of(
            final DominoGamePlayer player, final User loggedUser
    ) {
        return DominoGamePlayerDTO
                .builder()
                .id(player.getId())
                .user(UserDTO.of(player.getUser()))
                .hand(Objects.nonNull(loggedUser) && Objects.equals(loggedUser.getId(), player.getUser().getId()) ? player.getHand() : Collections.emptyList())
                .passedLastTurn(player.isPassedLastTurn())
                .isHost(player.isHost())
                .build();
    }
}
