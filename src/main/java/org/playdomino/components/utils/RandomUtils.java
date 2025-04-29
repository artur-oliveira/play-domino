package org.playdomino.components.utils;

import java.util.Random;

public final class RandomUtils {
    public static String getAlphaNumericRandomString(int length) {
        char[] chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRST".toCharArray();

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
