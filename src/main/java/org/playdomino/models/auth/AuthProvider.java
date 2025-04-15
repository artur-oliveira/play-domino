package org.playdomino.models.auth;

import org.playdomino.models.auth.dto.UserToken;

import java.util.Arrays;
import java.util.Objects;

public enum AuthProvider {
    LOGIN_PASSWORD {
        @Override
        public boolean accepts(UserToken login) {
            return Objects.nonNull(login.getPassword()) && (Objects.nonNull(login.getUsername()) || Objects.nonNull(login.getEmail()));
        }
    },
    REFRESH_TOKEN {
        @Override
        public boolean accepts(UserToken login) {
            return Objects.nonNull(login.getRefreshToken());
        }
    };

    public static AuthProvider valueOf(UserToken userToken) {
        return Arrays.stream(values()).filter(it -> it.accepts(userToken)).findFirst().orElse(null);
    }

    public abstract boolean accepts(UserToken login);
}
