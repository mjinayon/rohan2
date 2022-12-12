package com.javacadets.rohan.helpers;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String coDomain = "yondu.com";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + Pattern.quote(coDomain) + "$";
    public static boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }
}
