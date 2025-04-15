package org.playdomino.exceptions.auth;

import lombok.Getter;
import org.playdomino.exceptions.DominoException;

@Getter
public final class UserAlreadyExistsException extends DominoException {
    private final String existingData;

    public UserAlreadyExistsException(String message, String existingData) {
        super(AuthExceptionConstants.USER_EXISTS, message);
        this.existingData = existingData;
    }
}

