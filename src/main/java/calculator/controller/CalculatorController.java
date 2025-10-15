package calculator.controller;

import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private static OutputView outputView;
    private static InputView inputView;

    public CalculatorController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        outputView.showInputMessage();
        String rawExpression = inputView.readRawExpression();
    }
}
