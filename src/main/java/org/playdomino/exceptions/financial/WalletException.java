package org.playdomino.exceptions.financial;

import org.playdomino.exceptions.DominoException;

public class WalletException extends DominoException {
    public WalletException(String code, String message) {
        super(code, message);
    }
}
