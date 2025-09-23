package org.playdomino.exceptions.game;

import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.DominoException;

public class DominoGameException extends DominoException {
    public DominoGameException(String code, String message) {
        super(code, message);
    }

    public DominoGameException(String code, MessagesComponent component) {
        super(code, component.getMessage(code));
    }
}
