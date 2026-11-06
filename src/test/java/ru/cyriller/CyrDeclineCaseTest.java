package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Cases;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CyrDeclineCaseTest {

    @Test
    void all_returns_six_cases_in_order() {
        assertArrayEquals(
                new CyrDeclineCase[]{
                        CyrDeclineCase.CASE_1, CyrDeclineCase.CASE_2, CyrDeclineCase.CASE_3,
                        CyrDeclineCase.CASE_4, CyrDeclineCase.CASE_5, CyrDeclineCase.CASE_6},
                CyrDeclineCase.all().toArray(new CyrDeclineCase[0]));
        assertEquals(6, CyrDeclineCase.all().size());
    }

    @Test
    void case1_fields() {
        assertEquals(1, CyrDeclineCase.CASE_1.index());
        assertEquals("Именительный", CyrDeclineCase.CASE_1.russianName());
        assertEquals("Nominative", CyrDeclineCase.CASE_1.englishName());
        assertEquals("Кто? Что? (есть)", CyrDeclineCase.CASE_1.description());
        assertEquals(Cases.NOMINATIVE, CyrDeclineCase.CASE_1.value());
    }

    @Test
    void case2_fields() {
        assertEquals(2, CyrDeclineCase.CASE_2.index());
        assertEquals("Родительный", CyrDeclineCase.CASE_2.russianName());
        assertEquals("Genitive", CyrDeclineCase.CASE_2.englishName());
        assertEquals("Кого? Чего? (нет)", CyrDeclineCase.CASE_2.description());
        assertEquals(Cases.GENITIVE, CyrDeclineCase.CASE_2.value());
    }

    @Test
    void case3_fields() {
        assertEquals(3, CyrDeclineCase.CASE_3.index());
        assertEquals("Дательный", CyrDeclineCase.CASE_3.russianName());
        assertEquals("Dative", CyrDeclineCase.CASE_3.englishName());
        assertEquals("Кому? Чему? (дам)", CyrDeclineCase.CASE_3.description());
        assertEquals(Cases.DATIVE, CyrDeclineCase.CASE_3.value());
    }

    @Test
    void case4_fields() {
        assertEquals(4, CyrDeclineCase.CASE_4.index());
        assertEquals("Винительный", CyrDeclineCase.CASE_4.russianName());
        assertEquals("Accusative", CyrDeclineCase.CASE_4.englishName());
        assertEquals("Кого? Что? (вижу)", CyrDeclineCase.CASE_4.description());
        assertEquals(Cases.ACCUSATIVE, CyrDeclineCase.CASE_4.value());
    }

    @Test
    void case5_fields() {
        assertEquals(5, CyrDeclineCase.CASE_5.index());
        assertEquals("Творительный", CyrDeclineCase.CASE_5.russianName());
        assertEquals("Instrumental", CyrDeclineCase.CASE_5.englishName());
        assertEquals("Кем? Чем? (горжусь)", CyrDeclineCase.CASE_5.description());
        assertEquals(Cases.INSTRUMENTAL, CyrDeclineCase.CASE_5.value());
    }

    @Test
    void case6_fields() {
        assertEquals(6, CyrDeclineCase.CASE_6.index());
        assertEquals("Предложный", CyrDeclineCase.CASE_6.russianName());
        assertEquals("Prepositional", CyrDeclineCase.CASE_6.englishName());
        assertEquals("О ком? О чем? (думаю)", CyrDeclineCase.CASE_6.description());
        assertEquals(Cases.PREPOSITIVE, CyrDeclineCase.CASE_6.value());
    }

    @Test
    void en_aliases_point_to_same_objects() {
        assertSame(CyrDeclineCase.CASE_1, CyrDeclineCase.NOMINATIVE);
        assertSame(CyrDeclineCase.CASE_2, CyrDeclineCase.GENITIVE);
        assertSame(CyrDeclineCase.CASE_3, CyrDeclineCase.DATIVE);
        assertSame(CyrDeclineCase.CASE_4, CyrDeclineCase.ACCUSATIVE);
        assertSame(CyrDeclineCase.CASE_5, CyrDeclineCase.INSTRUMENTAL);
        assertSame(CyrDeclineCase.CASE_6, CyrDeclineCase.PREPOSITIVE);
    }

    @Test
    void ru_aliases_point_to_same_objects() {
        assertSame(CyrDeclineCase.CASE_1, CyrDeclineCase.ИМЕНИТЕЛЬНЫЙ);
        assertSame(CyrDeclineCase.CASE_2, CyrDeclineCase.РОДИТЕЛЬНЫЙ);
        assertSame(CyrDeclineCase.CASE_3, CyrDeclineCase.ДАТЕЛЬНЫЙ);
        assertSame(CyrDeclineCase.CASE_4, CyrDeclineCase.ВИНИТЕЛЬНЫЙ);
        assertSame(CyrDeclineCase.CASE_5, CyrDeclineCase.ТВОРИТЕЛЬНЫЙ);
        assertSame(CyrDeclineCase.CASE_6, CyrDeclineCase.ПРЕЛОЖНЫЙ);
    }

    @Test
    void record_equality_and_hashCode() {
        // record: одинаковые компоненты → equals
        assertEquals(new CyrDeclineCase(1, "Именительный", "Nominative", "Кто? Что? (есть)", Cases.NOMINATIVE),
                CyrDeclineCase.CASE_1);
        assertEquals(CyrDeclineCase.CASE_1.hashCode(),
                new CyrDeclineCase(1, "Именительный", "Nominative", "Кто? Что? (есть)", Cases.NOMINATIVE).hashCode());
    }
}
