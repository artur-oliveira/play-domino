package org.playdomino.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.playdomino.configuration.properties.AuthProperties;
import org.playdomino.interfaces.ThrowableFunction;
import org.playdomino.models.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

@Service
@Getter
@Setter
@Log4j2
public final class JwtServiceImpl implements JwtService {

    private final ObjectMapper mapper;
    private final SecretKey secretKeyAccess;
    private final SecretKey secretKeyRefresh;

    @Autowired
    public JwtServiceImpl(
            ObjectMapper mapper,
            AuthProperties authProperties
    ) {
        this.mapper = mapper;
        this.secretKeyAccess = Keys.hmacShaKeyFor(Decoders.BASE64.decode(authProperties.getSecretAccess()));
        this.secretKeyRefresh = Keys.hmacShaKeyFor(Decoders.BASE64.decode(authProperties.getSecretRefresh()));
    }

    private String generateToken(
            String id,
            String subject,
            Date iat,
            Date exp,
            TokenType typ
    ) {
        return Jwts
                .builder()
                .issuer("server@playdomino.org")
                .id(id)
                .subject(subject)
                .issuedAt(iat)
                .expiration(exp)
                .claim("type", typ.name().toLowerCase())
                .signWith(typ.secretKeyFor(this))
                .compact();
    }

    private String generateToken(User user, TokenType tokenType, Function<ZonedDateTime, Date> expirationFunction) {
        ZonedDateTime now = ZonedDateTime.now();
        return generateToken(
                user.getEmail(),
                user.getDisplayName(),
                Date.from(now.toInstant()),
                expirationFunction.apply(now),
                tokenType
        );
    }

    @Override
    public String generateAccessToken(Authentication authentication) {
        return generateToken((User) authentication.getPrincipal(), TokenType.ACCESS, (now) -> Date.from(now.with(LocalTime.MAX).toInstant()));
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return generateToken((User) authentication.getPrincipal(), TokenType.REFRESH, (now) -> Date.from(now.plusDays(365).with(LocalTime.MAX).toInstant()));
    }


    @Override
    public String generateAccessToken(String refreshToken) {
        Jws<Claims> claimsJws = claims(refreshToken, TokenType.REFRESH.secretKeyFor(this));
        ZonedDateTime now = ZonedDateTime.now();
        return generateToken(
                claimsJws.getPayload().getId(),
                claimsJws.getPayload().getSubject(),
                Date.from(now.toInstant()),
                Date.from(now.with(LocalTime.MAX).toInstant()),
                TokenType.ACCESS
        );
    }

    public Jws<Claims> claims(String authToken, SecretKey secretKey) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(authToken);
    }

    public Jws<Claims> claims(String authToken) {
        return claims(authToken, getSecretKeyAccess());
    }

    public boolean validateClaims(ThrowableFunction<String, Boolean> supplier, String token) {
        try {
            return supplier.apply(token);
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isValidAccess(String authToken) {
        return validateClaims((token) -> {
            claims(token);
            return true;
        }, authToken);
    }

    @Override
    public boolean isValidRefresh(String authToken) {
        return validateClaims((token) -> {
            claims(token, getSecretKeyRefresh());
            return true;
        }, authToken);
    }

    @Override
    public String subject(String jwt) {
        return claims(jwt).getPayload().getSubject();
    }

    private enum TokenType {
        ACCESS {
            @Override
            SecretKey secretKeyFor(JwtServiceImpl impl) {
                return impl.getSecretKeyAccess();
            }
        },
        REFRESH {
            @Override
            SecretKey secretKeyFor(JwtServiceImpl impl) {
                return impl.getSecretKeyRefresh();
            }
        };

        abstract SecretKey secretKeyFor(JwtServiceImpl impl);
    }
}
