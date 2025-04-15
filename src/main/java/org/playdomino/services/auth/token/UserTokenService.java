package org.playdomino.services.auth.token;

import org.playdomino.models.auth.dto.JwtResponse;
import org.playdomino.models.auth.dto.UserToken;

public interface UserTokenService {
    JwtResponse getToken(UserToken login);
}
