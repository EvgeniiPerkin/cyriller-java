package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;
import ru.cyriller.model.WordTypes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    /** «стол»: м. р., неодуш. (ед.: стола/столу/стол/столом/столе; мн.: столы/столов/столам/столы/столами/столах). */
    private static Item stol() {
        return new Item(new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED, WordTypes.TOPONYM,
                new CyrRule[]{
                        new CyrRule("ла1"), new CyrRule("у"), new CyrRule(""),
                        new CyrRule("ом"), new CyrRule("е"),
                        new CyrRule("ы"), new CyrRule("ов"), new CyrRule("лам1"),
                        new CyrRule("ы"), new CyrRule("ами"), new CyrRule("ах")
                }));
    }

    /** «год»: м. р., неодуш. (ед.: года/году/год/годом/годе; мн.: годы/годов/годам/годы/годами/годах). */
    private static Item god() {
        return new Item(new CyrNoun("год", Genders.MASCULINE, Animates.INANIMATED, WordTypes.TOPONYM,
                new CyrRule[]{
                        new CyrRule("а"), new CyrRule("у"), new CyrRule(""),
                        new CyrRule("ом"), new CyrRule("е"),
                        new CyrRule("ы"), new CyrRule("ов"), new CyrRule("ам"),
                        new CyrRule("ы"), new CyrRule("ами"), new CyrRule("ах")
                }));
    }

    /** «машина»: ж. р. (ед.: машины/машине/машину/машиной/машине; мн.: машины/машин/машинам/машины/машинами/машинах). */
    private static Item mashina() {
        return new Item(new CyrNoun("машина", Genders.FEMININE, Animates.INANIMATED, WordTypes.TOPONYM,
                new CyrRule[]{
                        new CyrRule("ы1"), new CyrRule("е1"), new CyrRule("у1"),
                        new CyrRule("ой1"), new CyrRule("е1"),
                        new CyrRule("ы1"), new CyrRule("1"), new CyrRule("ам1"),
                        new CyrRule("ы1"), new CyrRule("ами1"), new CyrRule("ах1")
                }));
    }

    /** «окно»: ср. р. (ед.: окна/окну/окно/окном/окне; мн.: окна/окон/окнам/окна/окнами/окнах). */
    private static Item okno() {
        return new Item(new CyrNoun("окно", Genders.NEUTER, Animates.INANIMATED, WordTypes.TOPONYM,
                new CyrRule[]{
                        new CyrRule("а1"), new CyrRule("у1"), new CyrRule(""),
                        new CyrRule("ом1"), new CyrRule("е1"),
                        new CyrRule("а1"), new CyrRule("он2"), new CyrRule("ам1"),
                        new CyrRule("а1"), new CyrRule("ами1"), new CyrRule("ах1")
                }));
    }

    /** «человек»: м. р., одуш. (ед.: человека/человеку/человека/человеком/человеке; мн.: люди/людей/людям/людей/людьми/людях). */
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
    void accessors() {
        Item item = stol();
        assertEquals(Genders.MASCULINE, item.gender());
        assertEquals(Animates.INANIMATED, item.animate());

        Item man = chelovek();
        assertEquals(Genders.MASCULINE, man.gender());
        assertEquals(Animates.ANIMATED, man.animate());
    }

    @Test
    void masculine_inanimated_stol_1_2_5() {
        Item item = stol();
        // Неодуш. м. р.: значение (1/2/5) на формы не влияет.
        for (long value : new long[]{1, 2, 5}) {
            assertArrayEquals(new String[]{"стол", "стола", "столов"}, item.getName(Cases.NOMINATIVE, value));
            assertArrayEquals(new String[]{"стола", "столов", "столов"}, item.getName(Cases.GENITIVE, value));
            assertArrayEquals(new String[]{"столу", "столам", "столам"}, item.getName(Cases.DATIVE, value));
            assertArrayEquals(new String[]{"стол", "стола", "столов"}, item.getName(Cases.ACCUSATIVE, value));
            assertArrayEquals(new String[]{"столом", "столами", "столами"}, item.getName(Cases.INSTRUMENTAL, value));
            assertArrayEquals(new String[]{"столе", "столах", "столах"}, item.getName(Cases.PREPOSITIVE, value));
        }
    }

    @Test
    void god_special_case_C7() {
        Item item = god();
        // 1 год, 2 года, 5 лет
        assertArrayEquals(new String[]{"год", "года", "лет"}, item.getName(Cases.NOMINATIVE, 1));
        assertArrayEquals(new String[]{"года", "годов", "лет"}, item.getName(Cases.GENITIVE, 5));
        assertArrayEquals(new String[]{"году", "годам", "годам"}, item.getName(Cases.DATIVE, 1));
        assertArrayEquals(new String[]{"год", "год", "лет"}, item.getName(Cases.ACCUSATIVE, 1));
        assertArrayEquals(new String[]{"годом", "годами", "годами"}, item.getName(Cases.INSTRUMENTAL, 1));
        assertArrayEquals(new String[]{"годе", "годах", "годах"}, item.getName(Cases.PREPOSITIVE, 1));
    }

    @Test
    void feminine_mashina() {
        Item item = mashina();
        assertArrayEquals(new String[]{"машина", "машины", "машин"}, item.getName(Cases.NOMINATIVE, 1));
        assertArrayEquals(new String[]{"машины", "машин", "машин"}, item.getName(Cases.GENITIVE, 5));
        assertArrayEquals(new String[]{"машине", "машинам", "машинам"}, item.getName(Cases.DATIVE, 1));
        // Винительный: [вин. ед., род. ед., вин. мн.]
        assertArrayEquals(new String[]{"машину", "машины", "машины"}, item.getName(Cases.ACCUSATIVE, 1));
        assertArrayEquals(new String[]{"машиной", "машинами", "машинами"}, item.getName(Cases.INSTRUMENTAL, 1));
        assertArrayEquals(new String[]{"машине", "машинах", "машинах"}, item.getName(Cases.PREPOSITIVE, 1));
    }

    @Test
    void neuter_okno() {
        Item item = okno();
        assertArrayEquals(new String[]{"окно", "окна", "окон"}, item.getName(Cases.NOMINATIVE, 1));
        assertArrayEquals(new String[]{"окна", "окон", "окон"}, item.getName(Cases.GENITIVE, 5));
        assertArrayEquals(new String[]{"окну", "окнам", "окнам"}, item.getName(Cases.DATIVE, 1));
        // Винительный: [вин. ед., род. ед., род. мн.]
        assertArrayEquals(new String[]{"окно", "окна", "окон"}, item.getName(Cases.ACCUSATIVE, 1));
        assertArrayEquals(new String[]{"окном", "окнами", "окнами"}, item.getName(Cases.INSTRUMENTAL, 1));
        assertArrayEquals(new String[]{"окне", "окнах", "окнах"}, item.getName(Cases.PREPOSITIVE, 1));
    }

    @Test
    void masculine_animated_chelovek_value_1() {
        Item item = chelovek();
        // Одуш. + value < 20: винительный = родительный во всех числах.
        assertArrayEquals(new String[]{"человек", "человека", "людей"}, item.getName(Cases.NOMINATIVE, 1));
        assertArrayEquals(new String[]{"человека", "людей", "людей"}, item.getName(Cases.GENITIVE, 1));
        assertArrayEquals(new String[]{"человеку", "людям", "людям"}, item.getName(Cases.DATIVE, 1));
        assertArrayEquals(new String[]{"человека", "людей", "людей"}, item.getName(Cases.ACCUSATIVE, 1));
        assertArrayEquals(new String[]{"человеком", "людьми", "людьми"}, item.getName(Cases.INSTRUMENTAL, 1));
        assertArrayEquals(new String[]{"человеке", "людях", "людях"}, item.getName(Cases.PREPOSITIVE, 1));
    }

    @Test
    void masculine_animated_chelovek_value_21_falls_back_to_inanimated() {
        Item item = chelovek();
        // value >= 20: ветка неодуш. — винительный = именительный (ед. ч.).
        assertArrayEquals(new String[]{"человек", "человека", "людей"}, item.getName(Cases.NOMINATIVE, 21));
        assertArrayEquals(new String[]{"человек", "человека", "людей"}, item.getName(Cases.ACCUSATIVE, 21));
    }
}
