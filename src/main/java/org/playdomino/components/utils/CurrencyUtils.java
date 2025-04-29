package org.playdomino.components.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.text.NumberFormat;

public final class CurrencyUtils {
    public static String toCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(LocaleContextHolder.getLocale()).format(amount);
    }
}
