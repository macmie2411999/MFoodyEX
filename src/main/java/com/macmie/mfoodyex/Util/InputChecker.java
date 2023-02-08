package com.macmie.mfoodyex.Util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputChecker {
    // ----------- START MACMIE ADJUST -----------

    // Remove all Spaces in the String input
    public static String removeSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    // Check if the input String can be cast to int
    public static boolean isStringInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if the input String can be cast to float
    public static boolean isStringFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    // Check if the input String is not null or empty
    public static boolean isStringValid(String input) {
        return input != null && !input.isEmpty();
    }

    // Check if the input String is Email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    // Check if the input String has form "dd/MM/yy"
    public static boolean isValidDateFormatDDMMYY(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Check if the input String has form "dd/MM/yyyy"
    public static boolean isValidDateDDMMYYY(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Check if the input String has form "dd/MM/yyyy" or "dd/MM/yy"
    public static boolean isValidDateFormat(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format1.parse(date);
            return true;
        } catch (ParseException e) {
            try {
                format2.parse(date);
                return true;
            } catch (ParseException e1) {
                return false;
            }
        }
    }

    // ----------- END MACMIE ADJUST -----------
}
