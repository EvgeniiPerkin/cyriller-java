package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты {@link Currency} и подклассов {@link Rur}/{@link Usd}/{@link Eur}/{@link Yuan}.
 *
 * <p>Проверяется целая и дробная части, 6 падежей × 3 формы
 * и метаданные (имя, роды, число знаков). Выбор формы по числу (1/2/5/11/21/22/25)
 * делает {@code CyrNumber} — здесь тестируются сами формы.</p>
 */
class CurrencyTest {

    private static final Cases[] ALL = Cases.values();

    // ---------- Метаданные ----------

    @Test
    void rur_metadata() {
        Rur c = new Rur();
        assertEquals("Российский рубль (руб) ₽", c.name);
        assertEquals(Genders.MASCULINE, c.integerGender);
        assertEquals(Genders.FEMININE, c.decimalGender);
        assertEquals(2, c.decimals);
    }

    @Test
    void usd_metadata() {
        Usd c = new Usd();
        assertEquals("Американский доллар (USD) $", c.name);
        assertEquals(Genders.MASCULINE, c.integerGender);
        assertEquals(Genders.MASCULINE, c.decimalGender);
        assertEquals(2, c.decimals);
    }

    @Test
    void eur_metadata() {
        Eur c = new Eur();
        assertEquals("Евро (EUR) €", c.name);
        assertEquals(Genders.NEUTER, c.integerGender);
        assertEquals(Genders.MASCULINE, c.decimalGender);
        assertEquals(2, c.decimals);
    }

    @Test
    void yuan_metadata() {
        Yuan c = new Yuan();
        assertEquals("Китайский юань (CNY) ¥", c.name);
        assertEquals(Genders.MASCULINE, c.integerGender);
        assertEquals(Genders.MASCULINE, c.decimalGender);
        assertEquals(1, c.decimals);
    }

    // ---------- Rur ----------

    @Test
    void rur_integer() {
        Rur c = new Rur();
        assertArrayEquals(new String[] { "рубль", "рубля", "рублей" }, c.getIntegerName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "рубля", "рублей", "рублей" }, c.getIntegerName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "рублю", "рублям", "рублям" }, c.getIntegerName(Cases.DATIVE));
        assertArrayEquals(new String[] { "рубль", "рубля", "рублей" }, c.getIntegerName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "рублем", "рублями", "рублями" }, c.getIntegerName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "рубле", "рублях", "рублях" }, c.getIntegerName(Cases.PREPOSITIVE));
    }

    @Test
    void rur_decimal() {
        Rur c = new Rur();
        assertArrayEquals(new String[] { "копейка", "копейки", "копеек" }, c.getDecimalName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "копейки", "копеек", "копеек" }, c.getDecimalName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "копейке", "копейкам", "копейкам" }, c.getDecimalName(Cases.DATIVE));
        assertArrayEquals(new String[] { "копейку", "копейки", "копеек" }, c.getDecimalName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "копейкой", "копейками", "копейками" }, c.getDecimalName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "копейке", "копейках", "копейках" }, c.getDecimalName(Cases.PREPOSITIVE));
    }

    // ---------- Usd ----------

    @Test
    void usd_integer() {
        Usd c = new Usd();
        assertArrayEquals(new String[] { "доллар", "доллара", "долларов" }, c.getIntegerName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "доллара", "долларов", "долларов" }, c.getIntegerName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "доллару", "долларам", "долларам" }, c.getIntegerName(Cases.DATIVE));
        assertArrayEquals(new String[] { "доллар", "доллара", "долларов" }, c.getIntegerName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "долларов", "долларами", "долларами" }, c.getIntegerName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "долларе", "долларах", "долларах" }, c.getIntegerName(Cases.PREPOSITIVE));
    }

    @Test
    void usd_decimal() {
        Usd c = new Usd();
        assertArrayEquals(new String[] { "цент", "цента", "центов" }, c.getDecimalName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "цента", "центов", "центов" }, c.getDecimalName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "центу", "центам", "центам" }, c.getDecimalName(Cases.DATIVE));
        assertArrayEquals(new String[] { "цент", "цента", "центов" }, c.getDecimalName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "центом", "центами", "центами" }, c.getDecimalName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "центе", "центах", "центах" }, c.getDecimalName(Cases.PREPOSITIVE));
    }

    // ---------- Eur ----------

    @Test
    void eur_integer_invariant() {
        Eur c = new Eur();
        for (Cases cs : ALL) {
            assertArrayEquals(new String[] { "евро", "евро", "евро" }, c.getIntegerName(cs), cs.name());
        }
    }

    @Test
    void eur_decimal() {
        Eur c = new Eur();
        assertArrayEquals(new String[] { "цент", "цента", "центов" }, c.getDecimalName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "цента", "центов", "центов" }, c.getDecimalName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "центу", "центам", "центам" }, c.getDecimalName(Cases.DATIVE));
        assertArrayEquals(new String[] { "цент", "цента", "центов" }, c.getDecimalName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "центом", "центами", "центами" }, c.getDecimalName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "центе", "центах", "центах" }, c.getDecimalName(Cases.PREPOSITIVE));
    }

    // ---------- Yuan ----------

    @Test
    void yuan_integer() {
        Yuan c = new Yuan();
        assertArrayEquals(new String[] { "юань", "юаня", "юаней" }, c.getIntegerName(Cases.NOMINATIVE));
        assertArrayEquals(new String[] { "юаня", "юаней", "юаней" }, c.getIntegerName(Cases.GENITIVE));
        assertArrayEquals(new String[] { "юаню", "юаням", "юаням" }, c.getIntegerName(Cases.DATIVE));
        assertArrayEquals(new String[] { "юань", "юаня", "юаней" }, c.getIntegerName(Cases.ACCUSATIVE));
        assertArrayEquals(new String[] { "юанем", "юанями", "юанями" }, c.getIntegerName(Cases.INSTRUMENTAL));
        assertArrayEquals(new String[] { "юане", "юанях", "юанях" }, c.getIntegerName(Cases.PREPOSITIVE));
    }

    @Test
    void yuan_decimal_invariant() {
        Yuan c = new Yuan();
        for (Cases cs : ALL) {
            assertArrayEquals(new String[] { "цзяо", "цзяо", "цзяо" }, c.getDecimalName(cs), cs.name());
        }
    }

    // ---------- Общая инвариантность: все формы — массивы из 3 ----------

    @Test
    void all_forms_have_length_3() {
        Currency[] currencies = { new Rur(), new Usd(), new Eur(), new Yuan() };
        for (Currency c : currencies) {
            for (Cases cs : ALL) {
                assertEquals(3, c.getIntegerName(cs).length, c.getClass().getSimpleName() + " integer " + cs);
                assertEquals(3, c.getDecimalName(cs).length, c.getClass().getSimpleName() + " decimal " + cs);
            }
        }
    }
}
