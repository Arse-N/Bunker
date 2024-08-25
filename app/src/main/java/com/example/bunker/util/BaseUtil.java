package com.example.bunker.util;

import java.util.Random;

public class BaseUtil {

    public static String generateCode() {
        int length = 8;
        StringBuilder code = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            code.append(randomChar);
        }

        return code.toString();
    }
}
