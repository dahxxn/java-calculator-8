package calculator.controller;

import calculator.model.Delimiters;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private final OutputView outputView;
    private final InputView inputView;
    private final Delimiters delimiters;
    private final CalculatorService calculatorService;

    public CalculatorController(OutputView outputView, InputView inputView, CalculatorService calculatorService) {
        this.outputView = outputView;
        this.inputView = inputView;
        delimiters = new Delimiters();
        this.calculatorService = calculatorService;
    }

    public void run() {
        String rawExpression = readRawExpression();
        String allDelimiters = getDelimiters(rawExpression);

        int result = calculate(rawExpression, allDelimiters);
        outputView.showResult(result);
    }

    private String readRawExpression() {
        outputView.showInputMessage();
        return inputView.readRawExpression();
    }

    private String getDelimiters(String rawExpression) {
        String customDelimiter = calculatorService.extractCustomDelimiter(rawExpression);
        if (customDelimiter != null) {
            delimiters.addCustomDelimiter(customDelimiter);
        }

        return delimiters.getAllDelimiters();
    }

    private int calculate(String rawExpression, String allDelimiters) {
        String[] calculationParts = calculatorService.parsingCalculation(rawExpression, allDelimiters);

        int result = 0;
        for (String calculationPart : calculationParts) {
            int number = calculatorService.convertToNumber(calculationPart);
            result += number;
        }
        return result;
    }
}
