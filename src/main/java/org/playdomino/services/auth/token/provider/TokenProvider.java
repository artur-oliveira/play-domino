package org.playdomino.services.auth.token.provider;

import org.playdomino.models.auth.AuthProvider;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;

import java.util.Objects;

public interface TokenProvider {

    JwtResponse authenticateAndGenerateTokens(UserToken login);

    AuthProvider getAuthProvider();

    default boolean accepts(UserToken login) {
        return Objects.equals(login.getAuthProvider(), getAuthProvider());
    }
}
