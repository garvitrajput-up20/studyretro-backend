package com.studyretro.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataValidator {

    @Value("${validation.email}")
    private String EMAIL_PATTERN;

    @Value("${validation.name}")
    private String NAME_PATTERN;

    @Value("${validation.phone}")
    private String PHONE_PATTERN;

    @Value("${validation.password}")
    private String PASSWORD_PATTERN;

    public boolean validateEmail(String email) {
        return validatePattern(email, EMAIL_PATTERN);
    }
    public boolean validateName(String name) {
        return !validatePattern(name, NAME_PATTERN);
    }

    public boolean validatePhone(String phone) {
        return validatePattern(phone, PHONE_PATTERN);
    }

    public boolean validatePassword(String password) {
        return validatePattern(password, PASSWORD_PATTERN);
    }

    private boolean validatePattern(String value, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(value);
        return matcher.matches();
    }
}
