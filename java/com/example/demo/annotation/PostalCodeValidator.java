package com.example.demo.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext constraintValidatorContext) {

        // Wyrażenia regularne - dopasowywanie wyrażeń do wzorca
        final Pattern postalCodePattern = Pattern.compile("^\\d{2}-\\d{3}$");
        final Matcher matcher = postalCodePattern.matcher(postalCode);

        return matcher.find();
    }
}
