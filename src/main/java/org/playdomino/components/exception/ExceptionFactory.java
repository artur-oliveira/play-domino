package org.playdomino.components.exception;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ExceptionFactory {

    private final MessagesComponent messagesComponent;

    @SneakyThrows
    public DominoGameException newException(final String code, Class<? extends DominoGameException> exception) {
        return exception.getDeclaredConstructor(String.class, String.class).newInstance(code, messagesComponent.getMessage(code));
    }
}
