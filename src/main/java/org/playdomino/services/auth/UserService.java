package org.playdomino.services.auth;

import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;

public interface UserService {

    User create(UserCreate user);

}
