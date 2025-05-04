package org.playdomino.models.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.auth.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class UserDTO {
    private String name;

    public static UserDTO of(User user) {
        return UserDTO
                .builder()
                .name(user.getDisplayName())
                .build();
    }
}
