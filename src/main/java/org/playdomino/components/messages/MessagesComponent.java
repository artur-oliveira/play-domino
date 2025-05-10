package org.playdomino.components.messages;

import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

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

    Locale resolveLocale() {
        return Optional.ofNullable(UserUtils.currentUser()).map(User::getCountry).map(Country::getJavaLocale).orElseGet(LocaleContextHolder::getLocale);
    }

    public String getExceptionMessage(String key) {
        return getExceptionMessage(key, null);
    }

    public String getExceptionMessage(String key, Object[] args) {
        return exceptionMessageSource.getMessage(key, args, resolveLocale());
    }

    public String getFaqMessage(String key) {
        return getFaqMessage(key, null);
    }

    public String getFaqMessage(String key, Object[] args) {
        return faqMessagesSource.getMessage(key, args, resolveLocale());
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] args) {
        try {
            return exceptionMessageSource.getMessage(key, args, resolveLocale());
        } catch (NoSuchMessageException e) {
            return messagesSource.getMessage(key, args, resolveLocale());
        }
    }
}
