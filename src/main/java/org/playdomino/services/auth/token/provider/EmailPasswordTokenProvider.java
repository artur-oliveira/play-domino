
package org.playdomino.services.auth.token.provider;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.AuthProvider;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service provider for handling email/password based authentication and token generation.
 */
@Service
@RequiredArgsConstructor
public class EmailPasswordTokenProvider implements TokenProvider {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Authenticates the user and generates access and refresh tokens.
     *
     * @param userToken the user credentials
     * @return JWT response containing access and refresh tokens
     */
    @Override
    @Transactional(readOnly = true)
    public JwtResponse authenticateAndGenerateTokens(UserToken userToken) {
        Authentication userAuthentication = authenticationManager.authenticate(userToken.getAuthentication());
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        return generateTokenResponse(userAuthentication);
    }

    @Override
    public AuthProvider getAuthProvider() {
        return AuthProvider.LOGIN_PASSWORD;
    }

    /**
     * Generates a JWT response containing access and refresh tokens.
     *
     * @param authentication an authenticated user
     * @return JWT response with tokens
     */
    private JwtResponse generateTokenResponse(Authentication authentication) {
        return JwtResponse.builder()
                .accessToken(jwtService.generateAccessToken(authentication))
                .refreshToken(jwtService.generateRefreshToken(authentication))
                .build();
    }
}