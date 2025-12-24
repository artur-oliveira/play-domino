package org.playdomino.services.auth.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface RefreshTokenService {

    String issueToken(Authentication authentication);

    boolean isValid(String token);
}
