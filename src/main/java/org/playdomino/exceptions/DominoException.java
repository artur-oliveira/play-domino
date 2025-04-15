package org.playdomino.exceptions;

import lombok.Getter;

@Getter
public abstract class DominoException extends RuntimeException {
    private final String code;

    public DominoException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DominoException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DominoException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
