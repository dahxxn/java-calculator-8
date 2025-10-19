package calculator.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorService {
    private static final String CUSTOM_HEADER_STRING_REGEX = "^//(.*)\\\\n(.*)$";
    private static final Pattern CUSTOM_HEADER_PATTERN = Pattern.compile(CUSTOM_HEADER_STRING_REGEX);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    private String checkCustomHeader(String rawExpression) {
        Matcher matcher = CUSTOM_HEADER_PATTERN.matcher(rawExpression);
        return matcher.matches() ? matcher.group(1) : null;
    }

    public String extractCustomDelimiter(String rawExpression) {
        String customDelimiter = checkCustomHeader(rawExpression);
        if (customDelimiter != null) {
            validateCustomDelimiter(customDelimiter);
        }

        return customDelimiter;
    }

    public void validateCustomDelimiter(String customDelimiter) {
        checkCustomDelimiterEmpty(customDelimiter);
        checkCustomDelimiterMultiple(customDelimiter);
        checkCustomDelimiterNumber(customDelimiter);
    }

    private void checkCustomDelimiterEmpty(String customDelimiter) {
        if (customDelimiter.isEmpty()) {
            throw new IllegalArgumentException("Custom delimiter cannot be empty.");
        }
    }

    private void checkCustomDelimiterMultiple(String customDelimiter) {
        if (customDelimiter.length() > 2) {
            throw new IllegalArgumentException("Custom delimiter length must not exceed 2 characters.");
        }
    }

    private void checkCustomDelimiterNumber(String customDelimiter) {
        if (isNumber(customDelimiter)) {
            throw new IllegalArgumentException("Custom delimiter cannot be a numeric value.");
        }
    }

    public String extractCalculation(String rawExpression) {
        Matcher matcher = CUSTOM_HEADER_PATTERN.matcher(rawExpression);
        return matcher.find() ? matcher.group(2) : rawExpression;
    }

    public String[] parsingCalculation(String rawExpression, String allDelimiters) {
        String calculation = extractCalculation(rawExpression);
        return calculation.split(allDelimiters);
    }

    public int convertToNumber(String calculationPart) {
        if (calculationPart.isEmpty()) {
            return 0;
        }
        if (!isNumber(calculationPart)) {
            throw new IllegalArgumentException("Invalid number format: " + calculationPart);
        }
        return Integer.parseInt(calculationPart);
    }

    private boolean isNumber(String valueToCheck) {
        return NUMBER_PATTERN.matcher(valueToCheck).matches();
    }

}
