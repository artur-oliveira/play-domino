package org.playdomino.services.auth.token.provider;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.AuthProvider;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class EmailPasswordUserTokenService implements UserTokenServiceProvider {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public JwtResponse getToken(UserToken login) {
        Authentication authentication = authenticationManager.authenticate(login.getAuthentication());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtResponse
                .builder()
                .accessToken(jwtService.generateAccessToken(authentication))
                .refreshToken(jwtService.generateRefreshToken(authentication))
                .build();
    }

    @Override
    public AuthProvider getAuthProvider() {
        return AuthProvider.LOGIN_PASSWORD;
    }
}
