package com.app.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AppUtil {

    public static String incrementNumbersInString(String input) {

        StringBuilder result = new StringBuilder();
        StringBuilder number = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    result.append(incrementNumber(number.toString()));
                    number.setLength(0);
                }
                result.append(c);
            }
        }

        if (number.length() > 0) {
            result.append(incrementNumber(number.toString()));
        }
        return result.toString();
    }

    private static String incrementNumber(String number) {
        long num = Long.parseLong(number);
        num++;
        return Long.toString(num);

    }
}
