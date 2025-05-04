package org.playdomino.components.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyUtils {

    public static String toCurrency(long amountCents, Locale locale) {
        double amount = new BigDecimal(amountCents).setScale(2, RoundingMode.UNNECESSARY).divide(new BigDecimal("100.00"), RoundingMode.HALF_EVEN).doubleValue();
        return NumberFormat.getCurrencyInstance(locale).format(amount).replace(" "," ");
    }
}
