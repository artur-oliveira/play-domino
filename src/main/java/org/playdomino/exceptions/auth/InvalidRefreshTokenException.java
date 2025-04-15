package org.playdomino.exceptions.auth;

import lombok.Getter;
import org.playdomino.exceptions.DominoException;

@Getter
public final class InvalidRefreshTokenException extends DominoException {

    public InvalidRefreshTokenException(String message) {
        super(AuthExceptionConstants.USER_INVALID_REFRESH_TOKEN, message);
    }
}

