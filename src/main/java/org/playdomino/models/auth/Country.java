package org.playdomino.models.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum Country {
    BRAZIL("Brasil") {
        @Override
        public String getLocale() {
            return "pt-BR";
        }

        @Override
        public Locale getJavaLocale() {
            return Locale.forLanguageTag(getLocale());
        }

        @Override
        public String getCurrency() {
            return "BRL";
        }
    };
    private final String countryName;

    public abstract Locale getJavaLocale();

    public abstract String getLocale();

    public abstract String getCurrency();
}

