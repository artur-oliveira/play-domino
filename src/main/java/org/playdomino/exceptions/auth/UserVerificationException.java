package org.playdomino.exceptions.auth;

import org.playdomino.exceptions.DominoException;

public class UserVerificationException extends DominoException {
    public UserVerificationException(String code, String message) {
        super(code, message);
    }
}
