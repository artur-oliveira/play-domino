package org.playdomino.models.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.playdomino.models.auth.dto.UserToken;

import static org.playdomino.models.auth.AuthProvider.LOGIN_PASSWORD;
import static org.playdomino.models.auth.AuthProvider.REFRESH_TOKEN;

class AuthProviderTest {

    @Test
    @DisplayName("Test AuthProvider: Should auth provider be LOGIN_PASSWORD")
    void testProviderLoginPassword() {
        Assertions.assertTrue(LOGIN_PASSWORD.accepts(UserToken.builder().email("test@test.com").password("Test123456@").build()));
    }

    @Test
    @DisplayName("Test AuthProvider: Should auth provider be LOGIN_PASSWORD")
    void testProviderRefreshToken() {
        Assertions.assertTrue(REFRESH_TOKEN.accepts(UserToken.builder().refreshToken("a_refresh_token").build()));
    }

}