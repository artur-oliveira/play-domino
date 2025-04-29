package org.playdomino.services.auth.token.provider;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserException;
import org.playdomino.models.auth.AuthProvider;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenProvider implements TokenProvider {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public JwtResponse authenticateAndGenerateTokens(UserToken login) {
        validateRefreshToken(login.getRefreshToken());
        User user = findUserByRefreshToken(login.getRefreshToken());
        Authentication authentication = authenticateUser(user);
        return generateTokenResponse(authentication);
    }

    @Override
    public AuthProvider getAuthProvider() {
        return AuthProvider.REFRESH_TOKEN;
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtService.isValidRefresh(refreshToken)) {
            throw createUserException(
                    AuthExceptionConstants.USER_INVALID_REFRESH_TOKEN
            );
        }
    }

    private User findUserByRefreshToken(String refreshToken) {
        return userRepository.findUserByUsername(jwtService.idRefresh(refreshToken))
                .orElseThrow(() -> createUserException(AuthExceptionConstants.USER_DOES_NOT_EXISTS));
    }

    private Authentication authenticateUser(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private JwtResponse generateTokenResponse(Authentication authentication) {
        return JwtResponse.builder()
                .accessToken(jwtService.generateAccessToken(authentication))
                .refreshToken(jwtService.generateRefreshToken(authentication))
                .build();
    }

    private UserException createUserException(String errorCode) {
        return new UserException(errorCode, messagesComponent.getMessage(errorCode));
    }
}