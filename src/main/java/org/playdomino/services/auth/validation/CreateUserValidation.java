package org.playdomino.services.auth.validation;

import org.playdomino.models.auth.User;

public interface CreateUserValidation {
    void validate(User user);
}
