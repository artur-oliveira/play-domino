package org.playdomino.exceptions.auth;

import lombok.Getter;
import org.playdomino.exceptions.DominoException;

@Getter
public final class UserDoesNotExistsException extends DominoException {

    public UserDoesNotExistsException(String message) {
        super(AuthExceptionConstants.USER_DOES_NOT_EXISTS, message);
    }
}

