package com.example.myjob;

import java.util.Calendar;
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
    public static String LastFour(String inputString) {
        if (inputString.length() >= 4) {
            String lastFour = inputString.substring(inputString.length() - 4);
            return lastFour;
        } else {
            // Xử lý khi chuỗi đầu vào có ít hơn 4 ký tự
            System.out.println("Chuỗi đầu vào quá ngắn");
        }

        return inputString;
    }
    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Lưu ý: Tháng bắt đầu từ 0
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String dateTime = String.format("%02d/%02d/%04d %02d:%02d", day, month, year, hour, minute);
        return dateTime;
    }
}
