package calculator.view;

public class OutputView {
    private final String INPUT_MESSAGE = "덧셈할 문자열을 입력해 주세요.";
    private final String RESULT_MESSAGE = "결과 : ";

    public void showInputMessage() {
        System.out.println(INPUT_MESSAGE);
    }

    public void showResult(int result) {
        System.out.println(RESULT_MESSAGE + result);
    }
}
