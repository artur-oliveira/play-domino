package org.playdomino.services.auth.user.validation.create;

import org.playdomino.models.auth.User;

public interface CreateUserValidation {
    void validate(User user);
}
