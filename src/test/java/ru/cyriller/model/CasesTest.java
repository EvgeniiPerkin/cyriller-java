package ru.cyriller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CasesTest {

    @Test
    void fromIndex_roundTrip() {
        for (Cases c : Cases.values()) {
            assertEquals(c, Cases.fromIndex(c.index), "round-trip для " + c);
        }
    }

    @Test
    void index_values_are_1_based() {
        assertEquals(1, Cases.NOMINATIVE.index);
        assertEquals(2, Cases.GENITIVE.index);
        assertEquals(3, Cases.DATIVE.index);
        assertEquals(4, Cases.ACCUSATIVE.index);
        assertEquals(5, Cases.INSTRUMENTAL.index);
        assertEquals(6, Cases.PREPOSITIVE.index);
    }

    @Test
    void fromIndex_all_valid() {
        assertEquals(Cases.NOMINATIVE, Cases.fromIndex(1));
        assertEquals(Cases.GENITIVE, Cases.fromIndex(2));
        assertEquals(Cases.DATIVE, Cases.fromIndex(3));
        assertEquals(Cases.ACCUSATIVE, Cases.fromIndex(4));
        assertEquals(Cases.INSTRUMENTAL, Cases.fromIndex(5));
        assertEquals(Cases.PREPOSITIVE, Cases.fromIndex(6));
    }

    @Test
    void fromIndex_out_of_range_throws() {
        assertThrows(IllegalArgumentException.class, () -> Cases.fromIndex(0));
        assertThrows(IllegalArgumentException.class, () -> Cases.fromIndex(7));
        assertThrows(IllegalArgumentException.class, () -> Cases.fromIndex(-1));
        assertThrows(IllegalArgumentException.class, () -> Cases.fromIndex(100));
    }
}
