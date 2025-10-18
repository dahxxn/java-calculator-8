package calculator.controller;

import calculator.model.Delimiters;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private final OutputView outputView;
    private final InputView inputView;
    private static Delimiters delimiters;
    private final CalculatorService calculatorService;

    public CalculatorController(OutputView outputView, InputView inputView, CalculatorService calculatorService) {
        this.outputView = outputView;
        this.inputView = inputView;
        delimiters = new Delimiters();
        this.calculatorService = calculatorService;
    }

    public void run() {
        outputView.showInputMessage();
        String rawExpression = inputView.readRawExpression();

        String customDelimiter = calculatorService.extractCustomDelimiter(rawExpression);
        if (customDelimiter != null) {
            delimiters.addCustomDelimiter(customDelimiter);
        }

        String allDelimiters = delimiters.getAllDelimiters();
        String[] calculationParts = calculatorService.parsingCalculation(rawExpression, allDelimiters);

        int result = 0;
        for (String calculationPart : calculationParts) {
            calculatorService.validateCalculationPart(calculationPart);

            int number = Integer.parseInt(calculationPart);
            result += number;
        }

        outputView.showResult(result);

    }
}
