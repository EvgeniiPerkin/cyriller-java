package ru.cyriller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WordTypesTest {

    @Test
    void values_match_csharp() {
        assertEquals(1, WordTypes.ABBREVIATION.value);
        assertEquals(2, WordTypes.NAME.value);
        assertEquals(3, WordTypes.SURNAME.value);
        assertEquals(4, WordTypes.PATRONYMIC.value);
        assertEquals(5, WordTypes.TOPONYM.value);
        assertEquals(6, WordTypes.ORGANIZATION.value);
        assertEquals(7, WordTypes.TRADE_MARK.value);
        assertEquals(8, WordTypes.SUPERLATIVE.value);
        assertEquals(9, WordTypes.QUALITATIVE.value);
        assertEquals(10, WordTypes.PRONOMINAL.value);
        assertEquals(11, WordTypes.ORDINAL.value);
        assertEquals(12, WordTypes.POSSESSIVE.value);
        assertEquals(13, WordTypes.FORM_EY.value);
        assertEquals(14, WordTypes.FORM_OY.value);
        assertEquals(15, WordTypes.COMPARATIVE.value);
        assertEquals(16, WordTypes.COMPARATIVE_EJ.value);
    }

    @Test
    void from_value() {
        assertEquals(WordTypes.ABBREVIATION, WordTypes.fromValue(1));
        assertEquals(WordTypes.NAME, WordTypes.fromValue(2));
        assertEquals(WordTypes.COMPARATIVE_EJ, WordTypes.fromValue(16));
    }

    @Test
    void from_value_out_of_range_throws() {
        assertThrows(IllegalArgumentException.class, () -> WordTypes.fromValue(0));
        assertThrows(IllegalArgumentException.class, () -> WordTypes.fromValue(17));
        assertThrows(IllegalArgumentException.class, () -> WordTypes.fromValue(-1));
    }
}
