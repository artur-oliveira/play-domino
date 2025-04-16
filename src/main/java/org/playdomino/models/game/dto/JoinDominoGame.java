package org.playdomino.models.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class JoinDominoGame {
    @JsonIgnore
    private Long gameId;
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,16}$",
            message = "{validation.dominogame.password}"
    )
    public String password;
}
