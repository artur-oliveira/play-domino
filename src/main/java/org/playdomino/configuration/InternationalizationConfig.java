package org.playdomino.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
public class InternationalizationConfig {
    @Bean
    public MessageSource exceptionMessagesSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/ExceptionMessages");
        source.setDefaultLocale(Locale.ENGLISH);
        source.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return source;
    }
}
