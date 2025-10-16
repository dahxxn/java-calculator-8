package calculator.controller;

import calculator.model.Delimiters;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private static OutputView outputView;
    private static InputView inputView;
    private static Delimiters delimiters;
    private static CalculatorService calculatorService;

    public CalculatorController(OutputView outputView, InputView inputView, CalculatorService calculatorService) {
        this.outputView = outputView;
        this.inputView = inputView;
        delimiters = new Delimiters();
        this.calculatorService = calculatorService;
    }

    public void run() {
        outputView.showInputMessage();
        String rawExpression = inputView.readRawExpression();

        if (calculatorService.hasCustomHeader(rawExpression)) {
            String customDelimiter = calculatorService.extractCustomDelimiter(rawExpression);
            delimiters.addCustomDelimiter(customDelimiter);
        }

    }
}
