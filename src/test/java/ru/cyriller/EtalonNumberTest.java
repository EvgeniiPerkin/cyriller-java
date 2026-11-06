package ru.cyriller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Регрессионные тесты числительных.
 */
class EtalonNumberTest {

    private final CyrNumber cyrNumber = new CyrNumber();

    record NumberCase(long value, String[] cases) {
        @Override
        public String toString() {
            return Long.toString(value);
        }
    }

    static Stream<Arguments> numberData() {
        return Stream.of(
                Arguments.of(new NumberCase(0, new String[]{
                        "ноль", "ноля", "нолю", "ноль", "нолём", "ноле"})),
                Arguments.of(new NumberCase(1, new String[]{
                        "один", "одного", "одному", "один", "одним", "одном"})),
                Arguments.of(new NumberCase(2, new String[]{
                        "два", "двух", "двум", "два", "двумя", "двух"})),
                Arguments.of(new NumberCase(5, new String[]{
                        "пять", "пяти", "пяти", "пять", "пятью", "пяти"})),
                Arguments.of(new NumberCase(6, new String[]{
                        "шесть", "шести", "шести", "шесть", "шестью", "шести"})),
                Arguments.of(new NumberCase(21, new String[]{
                        "двадцать один", "двадцати одного", "двадцати одному",
                        "двадцать один", "двадцатью одним", "двадцати одном"})),
                Arguments.of(new NumberCase(34, new String[]{
                        "тридцать четыре", "тридцати четырёх", "тридцати четырём",
                        "тридцать четыре", "тридцатью четырьмя", "тридцати четырёх"})),
                Arguments.of(new NumberCase(100, new String[]{
                        "сто", "ста", "ста", "сто", "ста", "ста"})),
                Arguments.of(new NumberCase(256, new String[]{
                        "двести пятьдесят шесть", "двухсот пятидесяти шести",
                        "двумстам пятидесяти шести", "двести пятьдесят шесть",
                        "двумястами пятьюдесятью шестью", "двухстах пятидесяти шести"})),
                Arguments.of(new NumberCase(1000, new String[]{
                        "одна тысяча", "одной тысячи", "одной тысяче",
                        "одну тысячу", "одной тысячей", "одной тысяче"})),
                Arguments.of(new NumberCase(9999999999L, new String[]{
                        "девять миллиардов девятьсот девяносто девять миллионов девятьсот девяносто девять тысяч девятьсот девяносто девять",
                        "девяти миллиардов девятисот девяноста девяти миллионов девятисот девяноста девяти тысяч девятисот девяноста девяти",
                        "девяти миллиардам девятистам девяноста девяти миллионам девятистам девяноста девяти тысячам девятистам девяноста девяти",
                        "девять миллиардов девятьсот девяносто девять миллионов девятьсот девяносто девять тысяч девятьсот девяносто девять",
                        "девятью миллиардами девятьюстами девяноста девятью миллионами девятьюстами девяноста девятью тысячами девятьюстами девяноста девятью",
                        "девяти миллиардах девятистах девяноста девяти миллионах девятистах девяноста девяти тысячах девятистах девяноста девяти"}))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("numberData")
    void testEtalonNumber(NumberCase nc) {
        Cases[] cases = Cases.values();
        for (int i = 0; i < cases.length; i++) {
            String actual = cyrNumber.toString(nc.value(), cases[i], Genders.UNDEFINED, Animates.INANIMATED);
            assertEquals(nc.cases()[i], actual,
                    "число " + nc.value() + ", " + cases[i]);
        }
    }
}
