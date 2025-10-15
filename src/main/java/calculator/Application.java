package calculator;

import calculator.controller.CalculatorController;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();
        CalculatorService calculatorService = new CalculatorService();

        CalculatorController calculatorController = new CalculatorController(outputView, inputView, calculatorService);
        calculatorController.run();
    }
}
