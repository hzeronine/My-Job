package com.example.myjob;

import java.util.Random;

public class RandomStringGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder();

        // Generate random letters
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(randomChar);
        }

        // Add underscore
        sb.append("_");

        // Generate random numbers
        for (int i = 0; i < 2; i++) {
            char randomDigit = NUMBERS.charAt(random.nextInt(NUMBERS.length()));
            sb.append(randomDigit);
        }

        return sb.toString();
    }
}
