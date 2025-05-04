package org.playdomino.components.messages;

import org.playdomino.components.auth.UserUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessagesComponent {
    private final MessageSource exceptionMessageSource;
    private final MessageSource messagesSource;

    public MessagesComponent(
            @Qualifier("exceptionMessagesSource") MessageSource exceptionMessagesSource,
            @Qualifier("messagesSource") MessageSource messagesSource
    ) {
        this.exceptionMessageSource = exceptionMessagesSource;
        this.messagesSource = messagesSource;
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] args) {
        try {
            return exceptionMessageSource.getMessage(key, args, UserUtils.currentUser().getCountry().getJavaLocale());
        } catch (NoSuchMessageException e) {
            return messagesSource.getMessage(key, args, UserUtils.currentUser().getCountry().getJavaLocale());
        }
    }
}
