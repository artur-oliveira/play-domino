package org.playdomino.services.auth;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    String generateAccessToken(String refreshToken);

    boolean isValidAccess(String token);

    boolean isValidRefresh(String token);

    String subject(String jwt);
}
