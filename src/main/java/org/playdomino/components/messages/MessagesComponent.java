package org.playdomino.components.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessagesComponent {
    private final MessageSource messageSource;

    public MessagesComponent(
            @Qualifier("exceptionMessagesSource") MessageSource messageSource
    ) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
