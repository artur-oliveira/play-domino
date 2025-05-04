package org.playdomino.components.utils;

import java.util.Random;

public final class RandomUtils {

    private static final char[] ALPHA_NUMERIC = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRST".toCharArray();
    private static final char[] ALPHAUPPER_NUMERIC = "1234567890ABCDEFGHIJKLMNOPQRST".toCharArray();

    private static String getRandomString(char[] chars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getAlphaNumericRandomString(int length) {
        return getRandomString(ALPHA_NUMERIC, length);
    }

    public static String getAlphaUpperNumericRandomString(int length) {
        return getRandomString(ALPHAUPPER_NUMERIC, length);
    }
}
