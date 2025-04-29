
package org.playdomino.services.auth.token;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserException;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.token.provider.TokenProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of UserTokenService that manages authentication tokens using various providers.
 */
@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {
    private static final String INVALID_AUTH_MESSAGE = AuthExceptionConstants.USER_INVALID_AUTHENTICATION;

    private final List<TokenProvider> providers;
    private final MessagesComponent messagesComponent;

    /**
     * Finds an appropriate token service provider for the given user token.
     *
     * @param userToken the user token containing authentication details
     * @return matching UserTokenServiceProvider
     * @throws UserException if no suitable provider is found
     */
    @Override
    public TokenProvider getProviderFor(UserToken userToken) {
        return providers.stream()
                .filter(provider -> provider.accepts(userToken))
                .findFirst()
                .orElseThrow(this::createInvalidAuthenticationException);
    }

    @Override
    public JwtResponse authenticateAndGenerateTokens(UserToken login) {
        return getProviderFor(login).authenticateAndGenerateTokens(login);
    }

    private UserException createInvalidAuthenticationException() {
        return new UserException(
                INVALID_AUTH_MESSAGE,
                messagesComponent.getMessage(INVALID_AUTH_MESSAGE)
        );
    }
}