package org.playdomino.services.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.playdomino.interfaces.ThrowableFunction;
import org.playdomino.models.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Getter
@Setter
@Log4j2
@RequiredArgsConstructor
public final class AccessTokenServiceImpl implements AccessTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    public String issueToken(Authentication authentication) {
        User user = Optional
                .ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(it -> (User) it)
                .orElseThrow(() -> new RuntimeException("The user cannot be null"));
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .id(user.getUsername())
                .subject(user.getDisplayName())
                .issuer("api@playdomino.org")
                .issuedAt(now)
                .expiresAt(now.plus(24, ChronoUnit.HOURS))
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    Jwt claims(String token) {
        return jwtDecoder.decode(token);
    }

    @Override
    public String id(String token) {
        return claims(token).getId();
    }

    public boolean validateClaims(ThrowableFunction<String, Boolean> supplier, String token) {
        try {
            return supplier.apply(token);
        } catch (Exception e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isValid(String token) {
        return validateClaims((t) -> {
            id(t);
            return true;
        }, token);
    }
}
