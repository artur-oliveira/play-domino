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
    private Long id;
    private String name;

    public static UserDTO of(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .name(user.getDisplayName())
                .build();
    }
}
