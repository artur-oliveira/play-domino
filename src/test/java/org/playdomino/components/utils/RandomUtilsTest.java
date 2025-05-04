package org.playdomino.components.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilsTest {

    @Test
    @DisplayName("Test RandomUtils: Should return a valid string of length")
    void testRandomUtilsRandomString() {
        assertEquals(10, RandomUtils.getAlphaNumericRandomString(10).length());
    }

}