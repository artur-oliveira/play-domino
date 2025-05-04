package org.playdomino.components.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyUtilsTest {

    @Test
    @DisplayName("Test CurrencyUtils: Should return the correct display value for BRL")
    void testCurrencyUtilsBRL() {
        assertEquals("R$ 0,00", CurrencyUtils.toCurrency(0, Locale.forLanguageTag("pt-BR")));
    }
}