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

}
