package org.playdomino.services.auth.user.validation.update;

import org.playdomino.models.auth.dto.UserUpdate;

public interface UpdateUserValidation {
    void validate(UserUpdate userUpdate);
}
