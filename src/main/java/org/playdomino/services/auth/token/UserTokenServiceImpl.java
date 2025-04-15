package org.playdomino.services.auth.token;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserException;
import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.token.provider.UserTokenServiceProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {
    public final List<UserTokenServiceProvider> providers;
    private final MessagesComponent messagesComponent;

    @Override
    public JwtResponse getToken(UserToken login) {
        return providers.stream().filter(it -> it.accepts(login)).findFirst().orElseThrow(() -> new UserException(AuthExceptionConstants.USER_INVALID_AUTHENTICATION, messagesComponent.getMessage(AuthExceptionConstants.USER_INVALID_AUTHENTICATION))).getToken(login);
    }
}
