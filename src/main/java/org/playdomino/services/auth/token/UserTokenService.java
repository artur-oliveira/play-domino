package org.playdomino.services.auth.token;

import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.token.provider.UserTokenServiceProvider;

public interface UserTokenService {

    UserTokenServiceProvider getProviderFor(UserToken userToken);

    JwtResponse getToken(UserToken login);
}
