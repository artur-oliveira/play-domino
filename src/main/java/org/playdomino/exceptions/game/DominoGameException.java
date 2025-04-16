package org.playdomino.exceptions.game;

import org.playdomino.exceptions.DominoException;

public class DominoGameException extends DominoException {
    public DominoGameException(String code, String message) {
        super(code, message);
    }
}
