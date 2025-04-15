package org.playdomino.services.auth.token.provider;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.InvalidRefreshTokenException;
import org.playdomino.exceptions.auth.UserDoesNotExistsException;
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
public class RefreshTokenUserTokenService implements UserTokenServiceProvider {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public JwtResponse getToken(UserToken login) {
        if (!jwtService.isValidRefresh(login.getRefreshToken())) {
            throw new InvalidRefreshTokenException(messagesComponent.getMessage(AuthExceptionConstants.USER_INVALID_REFRESH_TOKEN));
        }
        User user = userRepository.findUserByUsername(jwtService.idRefresh(login.getRefreshToken())).orElseThrow(() -> new UserDoesNotExistsException(messagesComponent.getMessage(AuthExceptionConstants.USER_DOES_NOT_EXISTS)));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtResponse
                .builder()
                .accessToken(jwtService.generateAccessToken(authentication))
                .refreshToken(jwtService.generateRefreshToken(authentication))
                .build();
    }

    @Override
    public AuthProvider getAuthProvider() {
        return AuthProvider.REFRESH_TOKEN;
    }
}
