package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import calculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("커스텀 구분자가 있는 입력에서 커스텀 구분자를 추출한다")
    void 커스텀구분자_추출_성공() {
        // given
        String input = "//;\\n1;2;3";

        // when
        String delimiter = calculatorService.extractCustomDelimiter(input);

        // then
        assertThat(delimiter).isEqualTo(";");
    }

    @Test
    @DisplayName("커스텀 구분자가 없는 입력에서 null을 반환한다")
    void 커스텀구분자_없을때_null반환() {
        // given
        String input = "1,2:3";

        // when
        String delimiter = calculatorService.extractCustomDelimiter(input);

        // then
        assertThat(delimiter).isNull();
    }

    @Test
    @DisplayName("커스텀 구분자 양식에 구분자 영역이 비어있으면 예외가 발생한다")
    void 커스텀구분자_빈문자열_예외() {
        // given
        String emptyDelimiter = "";

        // when & then
        assertThatThrownBy(() -> calculatorService.validateCustomDelimiter(emptyDelimiter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Custom delimiter cannot be empty.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "abc", "abcd"})
    @DisplayName("커스텀 구분자가 2글자 이상이면 예외가 발생한다")
    void 커스텀구분자_2글자이상_예외(String longDelimiter) {
        // when & then
        assertThatThrownBy(() -> calculatorService.validateCustomDelimiter(longDelimiter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Custom delimiter length must not exceed 1 characters.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1"})
    @DisplayName("커스텀 구분자가 숫자면 예외가 발생한다")
    void 커스텀구분자_숫자_예외(String numericDelimiter) {
        // when & then
        assertThatThrownBy(() -> calculatorService.validateCustomDelimiter(numericDelimiter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Custom delimiter cannot be a numeric value.");
    }

    @Test
    @DisplayName("유효한 커스텀 구분자는 검증을 통과한다")
    void 커스텀구분자_검증_성공() {
        // given
        String validDelimiter = ";";

        // when & then (예외가 발생하지 않음)
        calculatorService.validateCustomDelimiter(validDelimiter);
    }

    @Test
    @DisplayName("커스텀 구분자가 있는 입력에서 계산 부분을 추출한다")
    void 계산부분_추출_커스텀구분자있을때() {
        // given
        String input = "//;\\n1;2;3";

        // when
        String calculation = calculatorService.extractCalculation(input);

        // then
        assertThat(calculation).isEqualTo("1;2;3");
    }

    @Test
    @DisplayName("커스텀 구분자가 없는 입력에서 전체를 계산 부분으로 반환한다")
    void 계산부분_추출_커스텀구분자없을때() {
        // given
        String input = "1,2:3";

        // when
        String calculation = calculatorService.extractCalculation(input);

        // then
        assertThat(calculation).isEqualTo("1,2:3");
    }

    @Test
    @DisplayName("구분자를 기준으로 문자열을 파싱한다")
    void 문자열_파싱_성공() {
        // given
        String input = "1,2:3";
        String delimiters = "[,:]";

        // when
        String[] result = calculatorService.parsingCalculation(input, delimiters);

        // then
        assertThat(result).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("연속된 구분자가 있으면 빈 문자열도 파싱된다")
    void 연속된구분자_파싱() {
        // given
        String input = "1,,3";
        String delimiters = "[,]";

        // when
        String[] result = calculatorService.parsingCalculation(input, delimiters);

        // then
        assertThat(result).containsExactly("1", "", "3");
    }

    @ParameterizedTest
    @CsvSource({
            "'1', 1",
            "'12', 12",
            "'999', 999",
            "'01', 1",
    })
    @DisplayName("유효한 숫자 문자열을 숫자로 변환한다")
    void 숫자변환_성공(String input, int expected) {
        // when
        int result = calculatorService.convertToNumber(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 문자열은 0으로 변환한다")
    void 빈문자열_0으로_변환() {
        // given
        String empty = "";

        // when
        int result = calculatorService.convertToNumber(empty);

        // then
        assertThat(result).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "1a", "-1", "1.5", " 1 "})
    @DisplayName("유효하지 않은 숫자 형식이면 예외가 발생한다")
    void 잘못된숫자형식_예외(String invalid) {
        // when & then
        assertThatThrownBy(() -> calculatorService.convertToNumber(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid number format: " + invalid);
    }

    @Test
    @DisplayName("커스텀 구분자가 있는 전체 입력을 올바르게 파싱한다")
    void 전체파싱_커스텀구분자_통합테스트() {
        // given
        String input = "//;\\n1;2;3";
        String customDelimiter = calculatorService.extractCustomDelimiter(input);
        String calculation = calculatorService.extractCalculation(input);
        String delimiters = "[,:" + customDelimiter + "]";

        // when
        String[] parts = calculatorService.parsingCalculation(calculation, delimiters);

        // then
        assertThat(parts).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("커스텀 구분자가 없는 전체 입력을 올바르게 파싱한다")
    void 전체파싱_일반_통합테스트() {
        // given
        String input = "1:2,3,4";
        String customDelimiter = calculatorService.extractCustomDelimiter(input);
        String calculation = calculatorService.extractCalculation(input);
        String delimiters = "[,:" + customDelimiter + "]";

        // when
        String[] parts = calculatorService.parsingCalculation(calculation, delimiters);

        // then
        assertThat(parts).containsExactly("1", "2", "3", "4");
    }
}