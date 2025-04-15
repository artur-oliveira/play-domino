package org.playdomino.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    boolean isValidAccess(String token);

    boolean isValidRefresh(String token);

    Jws<Claims> claims(String token);

    Jws<Claims> claimsRefresh(String token);

    default String id(String jwt) {
        return claims(jwt).getPayload().getId();
    }

    default String idRefresh(String jwt) {
        return claimsRefresh(jwt).getPayload().getId();
    }
}
