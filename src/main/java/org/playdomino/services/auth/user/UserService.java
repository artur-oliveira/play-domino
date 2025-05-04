package org.playdomino.services.auth.user;

import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.models.auth.dto.UserUpdate;

public interface UserService {

    User create(UserCreate user);

    void update(UserUpdate user);
}
