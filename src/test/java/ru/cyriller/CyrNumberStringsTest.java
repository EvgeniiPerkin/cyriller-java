package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CyrNumberStringsTest {

    private static CyrNumberStrings s(Cases c, Genders g, Animates a) {
        return new CyrNumberStrings(c, g, a);
    }

    @Test
    void a1_genitive_tens7_fixed() {
        assertEquals("восьмидесяти", s(Cases.GENITIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
    }

    @Test
    void a2_dative_tens7_fixed() {
        assertEquals("восьмидесяти", s(Cases.DATIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
    }

    @Test
    void a3_accusative_decimalMillion1_feminine_fixed() {
        assertEquals("миллионных", s(Cases.ACCUSATIVE, Genders.FEMININE, Animates.INANIMATED).decimalMillion[1]);
    }

    @Test
    void a4_accusative_decimalMillion1_masculine_fixed() {
        assertEquals("миллионных", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).decimalMillion[1]);
    }

    @Test
    void a5_prepositive_numbers7_fixed() {
        assertEquals("восьми", s(Cases.PREPOSITIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
    }

    @Test
    void a6_dative_zero_fixed() {
        assertEquals("нолю", s(Cases.DATIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
    }

    // ---------- Спотовые: Tens[7] во всех падежах ----------

    @Test
    void tens7_all_cases() {
        assertEquals("восемьдесят", s(Cases.NOMINATIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
        assertEquals("восьмидесяти", s(Cases.GENITIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
        assertEquals("восьмидесяти", s(Cases.DATIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
        assertEquals("восемьдесят", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
        assertEquals("восьмьюдесятью", s(Cases.INSTRUMENTAL, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
        assertEquals("восьмидесяти", s(Cases.PREPOSITIVE, Genders.MASCULINE, Animates.INANIMATED).tens[7]);
    }

    // ---------- Спотовые: Zero во всех падежах ----------

    @Test
    void zero_all_cases() {
        assertEquals("ноль", s(Cases.NOMINATIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
        assertEquals("ноля", s(Cases.GENITIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
        assertEquals("нолю", s(Cases.DATIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
        assertEquals("ноль", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
        assertEquals("нолём", s(Cases.INSTRUMENTAL, Genders.MASCULINE, Animates.INANIMATED).zero);
        assertEquals("ноле", s(Cases.PREPOSITIVE, Genders.MASCULINE, Animates.INANIMATED).zero);
    }

    // ---------- Спотовые: Numbers[7] во всех падежах (муж. род) ----------

    @Test
    void numbers7_all_cases_masculine() {
        assertEquals("восемь", s(Cases.NOMINATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
        assertEquals("восьми", s(Cases.GENITIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
        assertEquals("восьми", s(Cases.DATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
        assertEquals("восемь", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
        assertEquals("восемью", s(Cases.INSTRUMENTAL, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
        assertEquals("восьми", s(Cases.PREPOSITIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[7]);
    }

    // ---------- Accusative: одушевлённый vs неодушевлённый ----------

    @Test
    void accusative_numbers_animated_vs_inanimated() {
        // Одушевлённый: numbers[0..3] в форме родительного
        assertEquals("одного", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED).numbers[0]);
        assertEquals("двух", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED).numbers[1]);
        assertEquals("трёх", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED).numbers[2]);
        assertEquals("четырёх", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED).numbers[3]);
        // Неодушевлённый: как в именительном
        assertEquals("один", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[0]);
        assertEquals("два", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[1]);
        assertEquals("три", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[2]);
        assertEquals("четыре", s(Cases.ACCUSATIVE, Genders.MASCULINE, Animates.INANIMATED).numbers[3]);
    }

    // ---------- Гендерная подстановка numbers[0..1] ----------

    @Test
    void feminine_numbers_override() {
        assertEquals("одна", s(Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED).numbers[0]);
        assertEquals("две", s(Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED).numbers[1]);
        assertEquals("одной", s(Cases.GENITIVE, Genders.FEMININE, Animates.INANIMATED).numbers[0]);
        assertEquals("двум", s(Cases.DATIVE, Genders.FEMININE, Animates.INANIMATED).numbers[1]);
        assertEquals("одну", s(Cases.ACCUSATIVE, Genders.FEMININE, Animates.INANIMATED).numbers[0]);
        assertEquals("две", s(Cases.ACCUSATIVE, Genders.FEMININE, Animates.INANIMATED).numbers[1]);
        assertEquals("одной", s(Cases.INSTRUMENTAL, Genders.FEMININE, Animates.INANIMATED).numbers[0]);
        assertEquals("двумя", s(Cases.INSTRUMENTAL, Genders.FEMININE, Animates.INANIMATED).numbers[1]);
        assertEquals("одной", s(Cases.PREPOSITIVE, Genders.FEMININE, Animates.INANIMATED).numbers[0]);
        assertEquals("двух", s(Cases.PREPOSITIVE, Genders.FEMININE, Animates.INANIMATED).numbers[1]);
    }

    @Test
    void feminine_accusative_animated_numbers1() {
        // Accusative жен. + одушевлённый: numbers[1] = "двух"
        assertEquals("двух", s(Cases.ACCUSATIVE, Genders.FEMININE, Animates.ANIMATED).numbers[1]);
    }

    @Test
    void neuter_numbers0_override() {
        assertEquals("одно", s(Cases.NOMINATIVE, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
        assertEquals("одного", s(Cases.GENITIVE, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
        assertEquals("одному", s(Cases.DATIVE, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
        assertEquals("одно", s(Cases.ACCUSATIVE, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
        assertEquals("одним", s(Cases.INSTRUMENTAL, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
        assertEquals("одном", s(Cases.PREPOSITIVE, Genders.NEUTER, Animates.INANIMATED).numbers[0]);
    }

    // ---------- Конструктор по умолчанию ----------

    @Test
    void noarg_constructor_defaults() {
        CyrNumberStrings d = new CyrNumberStrings();
        // Nominative, Masculine, Inanimated
        assertEquals("ноль", d.zero);
        assertEquals("один", d.numbers[0]);
        assertEquals("два", d.numbers[1]);
        assertEquals("сто", d.hundreds[0]);
        assertEquals("тысяча", d.thousand[0]);
    }

    // ---------- Полные таблицы (спотовая проверка целостности) ----------

    @Test
    void nominative_masculine_full() {
        CyrNumberStrings s = s(Cases.NOMINATIVE, Genders.MASCULINE, Animates.INANIMATED);
        assertArrayEquals(
                new String[] { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот" },
                s.hundreds);
        assertArrayEquals(
                new String[] { "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто" },
                s.tens);
        assertArrayEquals(
                new String[] { "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" },
                s.numbers);
        assertArrayEquals(new String[] { "тысяча", "тысячи", "тысяч" }, s.thousand);
        assertArrayEquals(new String[] { "миллион", "миллиона", "миллионов" }, s.million);
        assertArrayEquals(new String[] { "миллиард", "миллиарда", "миллиардов" }, s.billion);
        assertArrayEquals(new String[] { "целый", "целых", "целых" }, s.integer);
        assertArrayEquals(new String[] { "миллионный", "миллионных", "миллионных" }, s.decimalMillion);
    }

    @Test
    void genitive_feminine_full() {
        CyrNumberStrings s = s(Cases.GENITIVE, Genders.FEMININE, Animates.INANIMATED);
        assertArrayEquals(
                new String[] { "ста", "двухсот", "трёхсот", "четырёхсот", "пятисот", "шестисот", "семисот", "восьмисот", "девятисот" },
                s.hundreds);
        assertArrayEquals(
                new String[] { "одной", "двух", "трёх", "четырёх", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" },
                s.numbers);
        assertArrayEquals(new String[] { "целой", "целыми", "целыми" }, s.integer);
        assertArrayEquals(new String[] { "миллионной", "миллионных", "миллионных" }, s.decimalMillion);
    }

    @Test
    void instrumental_masculine_full() {
        CyrNumberStrings s = s(Cases.INSTRUMENTAL, Genders.MASCULINE, Animates.INANIMATED);
        assertArrayEquals(
                new String[] { "ста", "двумястами", "тремястами", "четырьмястами", "пятьюстами", "шестьюстами", "семьюстами", "восьмьюстами", "девятьюстами" },
                s.hundreds);
        assertArrayEquals(
                new String[] { "одним", "двумя", "тремя", "четырьмя", "пятью", "шестью", "семью", "восемью", "девятью", "десятью", "одиннадцатью", "двенадцатью", "тринадцатью", "четырнадцатью", "пятнадцатью", "шестнадцатью", "семнадцатью", "восемнадцатью", "девятнадцатью" },
                s.numbers);
        assertArrayEquals(new String[] { "тысячей", "тысячами", "тысячами" }, s.thousand);
        assertArrayEquals(new String[] { "целым", "целыми", "целыми" }, s.integer);
    }

    @Test
    void prepositive_masculine_full() {
        CyrNumberStrings s = s(Cases.PREPOSITIVE, Genders.MASCULINE, Animates.INANIMATED);
        assertArrayEquals(
                new String[] { "ста", "двухстах", "трёхстах", "четырёхстах", "пятистах", "шестистах", "семистах", "восьмистах", "девятистах" },
                s.hundreds);
        assertArrayEquals(
                new String[] { "одном", "двух", "трёх", "четырёх", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" },
                s.numbers);
        assertArrayEquals(new String[] { "тысяче", "тысячах", "тысячах" }, s.thousand);
        assertArrayEquals(new String[] { "целом", "целых", "целых" }, s.integer);
    }
}
