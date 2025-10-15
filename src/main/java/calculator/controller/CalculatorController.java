package calculator.controller;

import calculator.model.Delimiters;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private static OutputView outputView;
    private static InputView inputView;
    private static Delimiters delimiters;

    public CalculatorController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
        delimiters = new Delimiters();
    }

    public void run() {
        outputView.showInputMessage();
        String rawExpression = inputView.readRawExpression();
    }
}
