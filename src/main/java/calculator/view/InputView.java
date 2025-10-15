package calculator.view;

import static camp.nextstep.edu.missionutils.Console.close;
import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {

    public String readRawExpression() {
        String rawExpression = readLine();
        close();
        return rawExpression;
    }

}
