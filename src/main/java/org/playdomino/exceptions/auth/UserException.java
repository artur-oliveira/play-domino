package org.playdomino.exceptions.auth;

import org.playdomino.exceptions.DominoException;

public class UserException extends DominoException {
    public UserException(String code, String message) {
        super(code, message);
    }
}
