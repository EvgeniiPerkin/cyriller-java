package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;
import ru.cyriller.model.WordTypes;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты {@link CyrNumber}.
 *
 * <p>Ожидаемые строки вычислены вручную по таблицам {@link CyrNumberStrings}.</p>
 */
class CyrNumberTest {

    private final CyrNumber n = new CyrNumber();

    // ------------------------------------------------------------------
    // Целые числа: 29 значений × 6 падежей (муж. род, неодушевлённые)
    // ------------------------------------------------------------------

    @Test
    void nominative() {
        assertEquals("ноль", n.toString(0, Cases.NOMINATIVE));
        assertEquals("один", n.toString(1, Cases.NOMINATIVE));
        assertEquals("два", n.toString(2, Cases.NOMINATIVE));
        assertEquals("три", n.toString(3, Cases.NOMINATIVE));
        assertEquals("четыре", n.toString(4, Cases.NOMINATIVE));
        assertEquals("пять", n.toString(5, Cases.NOMINATIVE));
        assertEquals("шесть", n.toString(6, Cases.NOMINATIVE));
        assertEquals("одиннадцать", n.toString(11, Cases.NOMINATIVE));
        assertEquals("двенадцать", n.toString(12, Cases.NOMINATIVE));
        assertEquals("тринадцать", n.toString(13, Cases.NOMINATIVE));
        assertEquals("двадцать один", n.toString(21, Cases.NOMINATIVE));
        assertEquals("двадцать два", n.toString(22, Cases.NOMINATIVE));
        assertEquals("двадцать три", n.toString(23, Cases.NOMINATIVE));
        assertEquals("двадцать пять", n.toString(25, Cases.NOMINATIVE));
        assertEquals("тридцать", n.toString(30, Cases.NOMINATIVE));
        assertEquals("сорок", n.toString(40, Cases.NOMINATIVE));
        assertEquals("пятьдесят", n.toString(50, Cases.NOMINATIVE));
        assertEquals("восемьдесят", n.toString(80, Cases.NOMINATIVE));
        assertEquals("девяносто", n.toString(90, Cases.NOMINATIVE));
        assertEquals("сто", n.toString(100, Cases.NOMINATIVE));
        assertEquals("двести", n.toString(200, Cases.NOMINATIVE));
        assertEquals("пятьсот", n.toString(500, Cases.NOMINATIVE));
        assertEquals("одна тысяча", n.toString(1000, Cases.NOMINATIVE));
        assertEquals("две тысячи", n.toString(2000, Cases.NOMINATIVE));
        assertEquals("пять тысяч", n.toString(5000, Cases.NOMINATIVE));
        assertEquals("десять тысяч", n.toString(10000, Cases.NOMINATIVE));
        assertEquals("сто тысяч", n.toString(100000, Cases.NOMINATIVE));
        assertEquals("один миллион", n.toString(1000000, Cases.NOMINATIVE));
        assertEquals("девять миллиардов девятьсот девяносто девять миллионов "
                + "девятьсот девяносто девять тысяч девятьсот девяносто девять",
                n.toString(9999999999L, Cases.NOMINATIVE));
    }

    @Test
    void genitive() {
        assertEquals("ноля", n.toString(0, Cases.GENITIVE));
        assertEquals("одного", n.toString(1, Cases.GENITIVE));
        assertEquals("двух", n.toString(2, Cases.GENITIVE));
        assertEquals("трёх", n.toString(3, Cases.GENITIVE));
        assertEquals("четырёх", n.toString(4, Cases.GENITIVE));
        assertEquals("пяти", n.toString(5, Cases.GENITIVE));
        assertEquals("шести", n.toString(6, Cases.GENITIVE));
        assertEquals("одиннадцати", n.toString(11, Cases.GENITIVE));
        assertEquals("двенадцати", n.toString(12, Cases.GENITIVE));
        assertEquals("тринадцати", n.toString(13, Cases.GENITIVE));
        assertEquals("двадцати одного", n.toString(21, Cases.GENITIVE));
        assertEquals("двадцати двух", n.toString(22, Cases.GENITIVE));
        assertEquals("двадцати трёх", n.toString(23, Cases.GENITIVE));
        assertEquals("двадцати пяти", n.toString(25, Cases.GENITIVE));
        assertEquals("тридцати", n.toString(30, Cases.GENITIVE));
        assertEquals("сорока", n.toString(40, Cases.GENITIVE));
        assertEquals("пятидесяти", n.toString(50, Cases.GENITIVE));
        assertEquals("восьмидесяти", n.toString(80, Cases.GENITIVE)); // A-1
        assertEquals("девяноста", n.toString(90, Cases.GENITIVE));
        assertEquals("ста", n.toString(100, Cases.GENITIVE));
        assertEquals("двухсот", n.toString(200, Cases.GENITIVE));
        assertEquals("пятисот", n.toString(500, Cases.GENITIVE));
        assertEquals("одной тысячи", n.toString(1000, Cases.GENITIVE));
        assertEquals("двух тысяч", n.toString(2000, Cases.GENITIVE));
        assertEquals("пяти тысяч", n.toString(5000, Cases.GENITIVE));
        assertEquals("десяти тысяч", n.toString(10000, Cases.GENITIVE));
        assertEquals("ста тысяч", n.toString(100000, Cases.GENITIVE));
        assertEquals("одного миллиона", n.toString(1000000, Cases.GENITIVE));
        assertEquals("девяти миллиардов девятисот девяноста девяти миллионов "
                + "девятисот девяноста девяти тысяч девятисот девяноста девяти",
                n.toString(9999999999L, Cases.GENITIVE));
    }

    @Test
    void dative() {
        assertEquals("нолю", n.toString(0, Cases.DATIVE)); // A-6
        assertEquals("одному", n.toString(1, Cases.DATIVE));
        assertEquals("двум", n.toString(2, Cases.DATIVE));
        assertEquals("трём", n.toString(3, Cases.DATIVE));
        assertEquals("четырём", n.toString(4, Cases.DATIVE));
        assertEquals("пяти", n.toString(5, Cases.DATIVE));
        assertEquals("шести", n.toString(6, Cases.DATIVE));
        assertEquals("одиннадцати", n.toString(11, Cases.DATIVE));
        assertEquals("двенадцати", n.toString(12, Cases.DATIVE));
        assertEquals("тринадцати", n.toString(13, Cases.DATIVE));
        assertEquals("двадцати одному", n.toString(21, Cases.DATIVE));
        assertEquals("двадцати двум", n.toString(22, Cases.DATIVE));
        assertEquals("двадцати трём", n.toString(23, Cases.DATIVE));
        assertEquals("двадцати пяти", n.toString(25, Cases.DATIVE));
        assertEquals("тридцати", n.toString(30, Cases.DATIVE));
        assertEquals("сорока", n.toString(40, Cases.DATIVE));
        assertEquals("пятидесяти", n.toString(50, Cases.DATIVE));
        assertEquals("восьмидесяти", n.toString(80, Cases.DATIVE)); // A-2
        assertEquals("девяноста", n.toString(90, Cases.DATIVE));
        assertEquals("ста", n.toString(100, Cases.DATIVE));
        assertEquals("двумстам", n.toString(200, Cases.DATIVE));
        assertEquals("пятистам", n.toString(500, Cases.DATIVE));
        assertEquals("одной тысяче", n.toString(1000, Cases.DATIVE));
        assertEquals("двум тысячам", n.toString(2000, Cases.DATIVE));
        assertEquals("пяти тысячам", n.toString(5000, Cases.DATIVE));
        assertEquals("десяти тысячам", n.toString(10000, Cases.DATIVE));
        assertEquals("ста тысячам", n.toString(100000, Cases.DATIVE));
        assertEquals("одному миллиону", n.toString(1000000, Cases.DATIVE));
        assertEquals("девяти миллиардам девятистам девяноста девяти миллионам "
                + "девятистам девяноста девяти тысячам девятистам девяноста девяти",
                n.toString(9999999999L, Cases.DATIVE));
    }

    @Test
    void accusative() {
        assertEquals("ноль", n.toString(0, Cases.ACCUSATIVE));
        assertEquals("один", n.toString(1, Cases.ACCUSATIVE));
        assertEquals("два", n.toString(2, Cases.ACCUSATIVE));
        assertEquals("три", n.toString(3, Cases.ACCUSATIVE));
        assertEquals("четыре", n.toString(4, Cases.ACCUSATIVE));
        assertEquals("пять", n.toString(5, Cases.ACCUSATIVE));
        assertEquals("шесть", n.toString(6, Cases.ACCUSATIVE));
        assertEquals("одиннадцать", n.toString(11, Cases.ACCUSATIVE));
        assertEquals("двенадцать", n.toString(12, Cases.ACCUSATIVE));
        assertEquals("тринадцать", n.toString(13, Cases.ACCUSATIVE));
        assertEquals("двадцать один", n.toString(21, Cases.ACCUSATIVE));
        assertEquals("двадцать два", n.toString(22, Cases.ACCUSATIVE));
        assertEquals("двадцать три", n.toString(23, Cases.ACCUSATIVE));
        assertEquals("двадцать пять", n.toString(25, Cases.ACCUSATIVE));
        assertEquals("тридцать", n.toString(30, Cases.ACCUSATIVE));
        assertEquals("сорок", n.toString(40, Cases.ACCUSATIVE));
        assertEquals("пятьдесят", n.toString(50, Cases.ACCUSATIVE));
        assertEquals("восемьдесят", n.toString(80, Cases.ACCUSATIVE));
        assertEquals("девяносто", n.toString(90, Cases.ACCUSATIVE));
        assertEquals("сто", n.toString(100, Cases.ACCUSATIVE));
        assertEquals("двести", n.toString(200, Cases.ACCUSATIVE));
        assertEquals("пятьсот", n.toString(500, Cases.ACCUSATIVE));
        assertEquals("одну тысячу", n.toString(1000, Cases.ACCUSATIVE));
        assertEquals("две тысячи", n.toString(2000, Cases.ACCUSATIVE));
        assertEquals("пять тысяч", n.toString(5000, Cases.ACCUSATIVE));
        assertEquals("десять тысяч", n.toString(10000, Cases.ACCUSATIVE));
        assertEquals("сто тысяч", n.toString(100000, Cases.ACCUSATIVE));
        assertEquals("один миллион", n.toString(1000000, Cases.ACCUSATIVE));
        assertEquals("девять миллиардов девятьсот девяносто девять миллионов "
                + "девятьсот девяносто девять тысяч девятьсот девяносто девять",
                n.toString(9999999999L, Cases.ACCUSATIVE));
    }

    @Test
    void instrumental() {
        assertEquals("нолём", n.toString(0, Cases.INSTRUMENTAL));
        assertEquals("одним", n.toString(1, Cases.INSTRUMENTAL));
        assertEquals("двумя", n.toString(2, Cases.INSTRUMENTAL));
        assertEquals("тремя", n.toString(3, Cases.INSTRUMENTAL));
        assertEquals("четырьмя", n.toString(4, Cases.INSTRUMENTAL));
        assertEquals("пятью", n.toString(5, Cases.INSTRUMENTAL));
        assertEquals("шестью", n.toString(6, Cases.INSTRUMENTAL));
        assertEquals("одиннадцатью", n.toString(11, Cases.INSTRUMENTAL));
        assertEquals("двенадцатью", n.toString(12, Cases.INSTRUMENTAL));
        assertEquals("тринадцатью", n.toString(13, Cases.INSTRUMENTAL));
        assertEquals("двадцатью одним", n.toString(21, Cases.INSTRUMENTAL));
        assertEquals("двадцатью двумя", n.toString(22, Cases.INSTRUMENTAL));
        assertEquals("двадцатью тремя", n.toString(23, Cases.INSTRUMENTAL));
        assertEquals("двадцатью пятью", n.toString(25, Cases.INSTRUMENTAL));
        assertEquals("тридцатью", n.toString(30, Cases.INSTRUMENTAL));
        assertEquals("сорока", n.toString(40, Cases.INSTRUMENTAL));
        assertEquals("пятьюдесятью", n.toString(50, Cases.INSTRUMENTAL));
        assertEquals("восьмьюдесятью", n.toString(80, Cases.INSTRUMENTAL));
        assertEquals("девяноста", n.toString(90, Cases.INSTRUMENTAL));
        assertEquals("ста", n.toString(100, Cases.INSTRUMENTAL));
        assertEquals("двумястами", n.toString(200, Cases.INSTRUMENTAL));
        assertEquals("пятьюстами", n.toString(500, Cases.INSTRUMENTAL));
        assertEquals("одной тысячей", n.toString(1000, Cases.INSTRUMENTAL));
        assertEquals("двумя тысячами", n.toString(2000, Cases.INSTRUMENTAL));
        assertEquals("пятью тысячами", n.toString(5000, Cases.INSTRUMENTAL));
        assertEquals("десятью тысячами", n.toString(10000, Cases.INSTRUMENTAL));
        assertEquals("ста тысячами", n.toString(100000, Cases.INSTRUMENTAL));
        assertEquals("одним миллионом", n.toString(1000000, Cases.INSTRUMENTAL));
        assertEquals("девятью миллиардами девятьюстами девяноста девятью миллионами "
                + "девятьюстами девяноста девятью тысячами девятьюстами девяноста девятью",
                n.toString(9999999999L, Cases.INSTRUMENTAL));
    }

    @Test
    void prepositive() {
        assertEquals("ноле", n.toString(0, Cases.PREPOSITIVE));
        assertEquals("одном", n.toString(1, Cases.PREPOSITIVE));
        assertEquals("двух", n.toString(2, Cases.PREPOSITIVE));
        assertEquals("трёх", n.toString(3, Cases.PREPOSITIVE));
        assertEquals("четырёх", n.toString(4, Cases.PREPOSITIVE));
        assertEquals("пяти", n.toString(5, Cases.PREPOSITIVE));
        assertEquals("шести", n.toString(6, Cases.PREPOSITIVE));
        assertEquals("одиннадцати", n.toString(11, Cases.PREPOSITIVE));
        assertEquals("двенадцати", n.toString(12, Cases.PREPOSITIVE));
        assertEquals("тринадцати", n.toString(13, Cases.PREPOSITIVE));
        assertEquals("двадцати одном", n.toString(21, Cases.PREPOSITIVE));
        assertEquals("двадцати двух", n.toString(22, Cases.PREPOSITIVE));
        assertEquals("двадцати трёх", n.toString(23, Cases.PREPOSITIVE));
        assertEquals("двадцати пяти", n.toString(25, Cases.PREPOSITIVE));
        assertEquals("тридцати", n.toString(30, Cases.PREPOSITIVE));
        assertEquals("сорока", n.toString(40, Cases.PREPOSITIVE));
        assertEquals("пятидесяти", n.toString(50, Cases.PREPOSITIVE));
        assertEquals("восьмидесяти", n.toString(80, Cases.PREPOSITIVE));
        assertEquals("девяноста", n.toString(90, Cases.PREPOSITIVE));
        assertEquals("ста", n.toString(100, Cases.PREPOSITIVE));
        assertEquals("двухстах", n.toString(200, Cases.PREPOSITIVE));
        assertEquals("пятистах", n.toString(500, Cases.PREPOSITIVE));
        assertEquals("одной тысяче", n.toString(1000, Cases.PREPOSITIVE));
        assertEquals("двух тысячах", n.toString(2000, Cases.PREPOSITIVE));
        assertEquals("пяти тысячах", n.toString(5000, Cases.PREPOSITIVE));
        assertEquals("десяти тысячах", n.toString(10000, Cases.PREPOSITIVE));
        assertEquals("ста тысячах", n.toString(100000, Cases.PREPOSITIVE));
        assertEquals("одном миллионе", n.toString(1000000, Cases.PREPOSITIVE));
        assertEquals("девяти миллиардах девятистах девяноста девяти миллионах "
                + "девятистах девяноста девяти тысячах девятистах девяноста девяти",
                n.toString(9999999999L, Cases.PREPOSITIVE));
    }

    // ------------------------------------------------------------------
    // Род и одушевлённость
    // ------------------------------------------------------------------

    @Test
    void feminine() {
        assertEquals("одна", n.toString(1, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("две", n.toString(2, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("одной", n.toString(1, Cases.GENITIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("одну", n.toString(1, Cases.ACCUSATIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("одной", n.toString(1, Cases.INSTRUMENTAL, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("одной", n.toString(1, Cases.PREPOSITIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("одна тысяча", n.toString(1000, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        assertEquals("две тысячи", n.toString(2000, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
    }

    @Test
    void neuter() {
        assertEquals("одно", n.toString(1, Cases.NOMINATIVE, Genders.NEUTER, Animates.INANIMATED));
        assertEquals("одного", n.toString(1, Cases.GENITIVE, Genders.NEUTER, Animates.INANIMATED));
        assertEquals("одному", n.toString(1, Cases.DATIVE, Genders.NEUTER, Animates.INANIMATED));
        assertEquals("одно", n.toString(1, Cases.ACCUSATIVE, Genders.NEUTER, Animates.INANIMATED));
        assertEquals("одним", n.toString(1, Cases.INSTRUMENTAL, Genders.NEUTER, Animates.INANIMATED));
        assertEquals("одном", n.toString(1, Cases.PREPOSITIVE, Genders.NEUTER, Animates.INANIMATED));
    }

    @Test
    void animatedAccusative() {
        assertEquals("одного", n.toString(1, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED));
        assertEquals("двух", n.toString(2, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED));
        assertEquals("пять", n.toString(5, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED));
        assertEquals("одну", n.toString(1, Cases.ACCUSATIVE, Genders.FEMININE, Animates.ANIMATED));
        assertEquals("двух", n.toString(2, Cases.ACCUSATIVE, Genders.FEMININE, Animates.ANIMATED));
    }

    @Test
    void negative() {
        assertEquals("минус пять", n.toString(-5, Cases.NOMINATIVE));
        assertEquals("минус один", n.toString(-1, Cases.NOMINATIVE));
    }

    // ------------------------------------------------------------------
    // caseForm: правило 1/2/5
    // ------------------------------------------------------------------

    @Test
    void caseFormRule() {
        assertEquals("один", n.caseForm(1, "один", "два", "пять"));
        assertEquals("два", n.caseForm(2, "один", "два", "пять"));
        assertEquals("два", n.caseForm(3, "один", "два", "пять"));
        assertEquals("два", n.caseForm(4, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(5, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(0, "один", "два", "пять"));
        // 11..19: t = value % 20 (11..19) → default → five
        // (в реальном потоке 11..19 обрабатываются массивом numbers, не caseForm)
        assertEquals("пять", n.caseForm(11, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(12, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(15, "один", "два", "пять"));
        assertEquals("один", n.caseForm(21, "один", "два", "пять"));
        assertEquals("два", n.caseForm(22, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(25, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(30, "один", "два", "пять"));
        assertEquals("один", n.caseForm(101, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(105, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(205, "один", "два", "пять"));
        assertEquals("пять", n.caseForm(211, "один", "два", "пять"));
    }

    // ------------------------------------------------------------------
    // Дробные числа
    // ------------------------------------------------------------------

    @Test
    void decimals() {
        assertEquals("один целый и пять десятых", n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE));
        // caseForm(0) → five → «целых» (как в C#)
        assertEquals("ноль целых и пять десятых", n.toString(new BigDecimal("0.5"), Cases.NOMINATIVE));
        assertEquals("сто двадцать три целых и четыреста пятьдесят шесть тысячных",
                n.toString(new BigDecimal("123.456"), Cases.NOMINATIVE));
        assertEquals("одного целого и пяти десятых", n.toString(new BigDecimal("1.5"), Cases.GENITIVE));
        assertEquals("одна целая и пять десятых",
                n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // хвостовые нули отбрасываются: 1.50 == 1.5
        assertEquals("один целый и пять десятых", n.toString(new BigDecimal("1.50"), Cases.NOMINATIVE));
        // сотые
        assertEquals("один целый и пять сотых", n.toString(new BigDecimal("1.05"), Cases.NOMINATIVE));
    }

    @Test
    void decimalsVba() {
        // dr=1 < 10 → DecimalTen
        assertEquals("ноль целых и одна десятая",
                n.toString(new BigDecimal("0.1"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=21 < 100 → DecimalHundred
        assertEquals("ноль целых и двенадцать сотых",
                n.toString(new BigDecimal("0.12"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=321 < 1000 → DecimalThousand
        assertEquals("ноль целых и сто двадцать три тысячные",
                n.toString(new BigDecimal("0.123"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=4321 < 1000000 → DecimalMillion
        assertEquals("ноль целых и одна тысяча двести тридцать четыре миллионные",
                n.toString(new BigDecimal("0.1234"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=54321 < 1000000 → DecimalMillion
        assertEquals("ноль целых и двенадцать тысяч триста сорок пять миллионных",
                n.toString(new BigDecimal("0.12345"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=654321 < 1000000 → DecimalMillion
        assertEquals("ноль целых и сто двадцать три тысячи четыреста пятьдесят шесть миллионных",
                n.toString(new BigDecimal("0.123456"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=7654321 >= 1000000 → DecimalBillion
        assertEquals("ноль целых и один миллион двести тридцать четыре тысячи пятьсот шестьдесят семь миллиардных",
                n.toString(new BigDecimal("0.1234567"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=987654321 >= 1000000 → DecimalBillion
        assertEquals("ноль целых и двенадцать миллионов триста сорок пять тысяч шестьсот восемьдесят девять миллиардных",
                n.toString(new BigDecimal("0.12345689"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
        // dr=1987654321 >= 1000000 → DecimalBillion
        assertEquals("ноль целых и сто двадцать три миллиона четыреста пятьдесят шесть тысяч восемьсот девяносто одна миллиардная",
                n.toString(new BigDecimal("0.123456891"), Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED));
    }

    // ------------------------------------------------------------------
    // Деньги
    // ------------------------------------------------------------------

    @Test
    void currency() {
        Rur rur = new Rur();
        assertEquals("один рубль", n.toString(new BigDecimal("1"), Cases.NOMINATIVE, rur));
        assertEquals("два рубля", n.toString(new BigDecimal("2"), Cases.NOMINATIVE, rur));
        assertEquals("пять рублей", n.toString(new BigDecimal("5"), Cases.NOMINATIVE, rur));
        assertEquals("один рубль и пятьдесят копеек", n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE, rur));
        assertEquals("ноль рублей и пятьдесят копеек", n.toString(new BigDecimal("0.5"), Cases.NOMINATIVE, rur));
        assertEquals("одного рубля и пятидесяти копеек", n.toString(new BigDecimal("1.5"), Cases.GENITIVE, rur));
        // дробная часть длиннее Decimals: 1.555 → 55.5 → округление → 56 копеек
        assertEquals("один рубль и пятьдесят шесть копеек", n.toString(new BigDecimal("1.555"), Cases.NOMINATIVE, rur));
    }

    @Test
    void currencyVba() {
        Rur rur = new Rur();
        assertEquals("один рубль", n.toString(new BigDecimal("1"), Cases.NOMINATIVE, rur));
        assertEquals("один рубль и десять копеек", n.toString(new BigDecimal("1.1"), Cases.NOMINATIVE, rur));
        assertEquals("один рубль и девятнадцать копеек", n.toString(new BigDecimal("1.19"), Cases.NOMINATIVE, rur));
        assertEquals("один рубль и двадцать копеек", n.toString(new BigDecimal("1.195"), Cases.NOMINATIVE, rur));
        assertEquals("девятьсот девяносто девять миллионов девятьсот девяносто девять тысяч девятьсот девяносто девять рублей",
                n.toString(new BigDecimal("999999999"), Cases.NOMINATIVE, rur));
    }

    // ------------------------------------------------------------------
    // Предметы
    // ------------------------------------------------------------------

    /** «стол»: м. р., неодуш. (как в ItemTest). */
    private static Item stol() {
        return new Item(new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED, WordTypes.TOPONYM,
                new CyrRule[]{
                        new CyrRule("ла1"), new CyrRule("у"), new CyrRule(""),
                        new CyrRule("ом"), new CyrRule("е"),
                        new CyrRule("ы"), new CyrRule("ов"), new CyrRule("лам1"),
                        new CyrRule("ы"), new CyrRule("ами"), new CyrRule("ах")
                }));
    }

    /** «человек»: м. р., одуш. (как в ItemTest). */
    private static Item chelovek() {
        return new Item(new CyrNoun("человек", Genders.MASCULINE, Animates.ANIMATED, WordTypes.NAME,
                new CyrRule[]{
                        new CyrRule("а"), new CyrRule("у"), new CyrRule("а"),
                        new CyrRule("ом"), new CyrRule("е"),
                        new CyrRule("люди7"), new CyrRule("людей7"), new CyrRule("людям7"),
                        new CyrRule("людей7"), new CyrRule("людьми7"), new CyrRule("людях7")
                }));
    }

    @Test
    void items() {
        Item stol = stol();
        Item chel = chelovek();

        assertEquals("один стол", n.toString(new BigDecimal("1"), Cases.NOMINATIVE, stol));
        assertEquals("два стола", n.toString(new BigDecimal("2"), Cases.NOMINATIVE, stol));
        assertEquals("пять столов", n.toString(new BigDecimal("5"), Cases.NOMINATIVE, stol));
        assertEquals("двадцать один стол", n.toString(new BigDecimal("21"), Cases.NOMINATIVE, stol));
        // дробное: женский род + родительный ед. ч. предмета
        assertEquals("одна целая и пять десятых стола", n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE, stol));
        // одушевлённые: value < 20 → animate
        assertEquals("пять людей", n.toString(new BigDecimal("5"), Cases.NOMINATIVE, chel));
        assertEquals("двадцать один человек", n.toString(new BigDecimal("21"), Cases.NOMINATIVE, chel));
        assertEquals("одна целая и пять десятых человека", n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE, chel));
    }

    // ------------------------------------------------------------------
    // Decline
    // ------------------------------------------------------------------

    @Test
    void declineLong() {
        CyrResult r = n.decline(21);
        assertEquals("двадцать один", r.get(Cases.NOMINATIVE));
        assertEquals("двадцати одного", r.get(Cases.GENITIVE));
        assertEquals("двадцати одному", r.get(Cases.DATIVE));
        assertEquals("двадцать один", r.get(Cases.ACCUSATIVE));
        assertEquals("двадцатью одним", r.get(Cases.INSTRUMENTAL));
        assertEquals("двадцати одном", r.get(Cases.PREPOSITIVE));
    }

    @Test
    void declineDecimal() {
        CyrResult r = n.decline(new BigDecimal("1.5"));
        assertEquals("один целый и пять десятых", r.get(Cases.NOMINATIVE));
        assertEquals("одного целого и пяти десятых", r.get(Cases.GENITIVE));
    }

    @Test
    void declineCurrency() {
        CyrResult r = n.decline(new BigDecimal("1.5"), new Rur());
        assertEquals("один рубль и пятьдесят копеек", r.get(Cases.NOMINATIVE));
        assertEquals("одного рубля и пятидесяти копеек", r.get(Cases.GENITIVE));
    }

    @Test
    void declineItem() {
        CyrResult r = n.decline(new BigDecimal("2"), stol());
        assertEquals("два стола", r.get(Cases.NOMINATIVE));
        assertEquals("двух столов", r.get(Cases.GENITIVE));
    }

    // ------------------------------------------------------------------
    // MaxValue
    // ------------------------------------------------------------------

    @Test
    void maxValueLong() {
        // MAX_VALUE проходит
        assertEquals("девять миллиардов девятьсот девяносто девять миллионов "
                + "девятьсот девяносто девять тысяч девятьсот девяносто девять",
                n.toString(CyrNumber.MAX_VALUE, Cases.NOMINATIVE));
        // MAX_VALUE + 1 → исключение
        assertThrows(IllegalArgumentException.class, () -> n.toString(CyrNumber.MAX_VALUE + 1, Cases.NOMINATIVE));
    }

    @Test
    void maxValueDecimalC3Fixed() {
        assertThrows(IllegalArgumentException.class,
                () -> n.toString(new BigDecimal("10000000000"), Cases.NOMINATIVE));
        assertThrows(IllegalArgumentException.class,
                () -> n.toString(new BigDecimal("10000000000.5"), Cases.NOMINATIVE));
        assertEquals("девять миллиардов девятьсот девяносто девять миллионов "
                + "девятьсот девяносто девять тысяч девятьсот девяносто девять целых и пять десятых",
                n.toString(new BigDecimal("9999999999.5"), Cases.NOMINATIVE));
    }
}
