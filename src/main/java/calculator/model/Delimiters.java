package calculator.model;

import java.util.HashSet;
import java.util.Set;

public class Delimiters {
    private final Set<Character> delimiters;

    public Delimiters() {
        delimiters = new HashSet<>();
        delimiters.add(',');
        delimiters.add(':');
    }

    public void addCustomDelimiter(String delimiter) {
        delimiters.add(delimiter.charAt(0));
    }

    public String getAllDelimiters() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (Character character : delimiters) {
            stringBuilder.append(character);
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

}
