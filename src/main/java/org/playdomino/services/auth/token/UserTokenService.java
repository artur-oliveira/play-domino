package org.playdomino.services.auth.token;

import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;
import org.playdomino.services.auth.token.provider.TokenProvider;

public interface UserTokenService {

    TokenProvider getProviderFor(UserToken userToken);

    JwtResponse authenticateAndGenerateTokens(UserToken login);
}
