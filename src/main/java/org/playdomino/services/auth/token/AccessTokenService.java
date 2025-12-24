package org.playdomino.services.auth.token;

import org.springframework.security.core.Authentication;

public interface AccessTokenService {

    String issueToken(Authentication authentication);

    boolean isValid(String token);

    String id(String token);
}
