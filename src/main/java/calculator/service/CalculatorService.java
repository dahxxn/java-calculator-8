package calculator.service;

import java.util.regex.Pattern;

public class CalculatorService {
    private static final String CUSTOM_HEADER_STRING_REGEX = "^//(.)\\\\n(.*)$";
    private static final Pattern CUSTOM_HEADER_PATTERN = Pattern.compile(CUSTOM_HEADER_STRING_REGEX);

    public boolean hasCustomHeader(String rawExpression) {
        return CUSTOM_HEADER_PATTERN.matcher(rawExpression).matches();
    }

}
