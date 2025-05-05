package org.playdomino.components.messages;

import org.playdomino.components.auth.UserUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessagesComponent {
    private final MessageSource exceptionMessageSource;
    private final MessageSource messagesSource;
    private final MessageSource faqMessagesSource;

    public MessagesComponent(
            @Qualifier("exceptionMessagesSource") MessageSource exceptionMessagesSource,
            @Qualifier("messagesSource") MessageSource messagesSource,
            @Qualifier("faqMessagesSource") MessageSource faqMessagesSource
    ) {
        this.exceptionMessageSource = exceptionMessagesSource;
        this.messagesSource = messagesSource;
        this.faqMessagesSource = faqMessagesSource;
    }

    public String getExceptionMessage(String key) {
        return getExceptionMessage(key, null);
    }

    public String getExceptionMessage(String key, Object[] args) {
        return exceptionMessageSource.getMessage(key, args, UserUtils.currentUser().getCountry().getJavaLocale());
    }

    public String getFaqMessage(String key) {
        return getFaqMessage(key, null);
    }

    public String getFaqMessage(String key, Object[] args) {
        return faqMessagesSource.getMessage(key, args, UserUtils.currentUser().getCountry().getJavaLocale());
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
