package org.playdomino.exceptions.auth;

import org.playdomino.exceptions.DominoException;

public class InvalidAuthenticationException extends DominoException {
    public InvalidAuthenticationException(String message) {
        super(AuthExceptionConstants.USER_INVALID_AUTHENTICATION, message);
    }
}
