package ru.cyriller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GendersTest {

    @Test
    void ordinal_matches_csharp_values() {
        assertEquals(0, Genders.UNDEFINED.ordinal());
        assertEquals(1, Genders.MASCULINE.ordinal());
        assertEquals(2, Genders.FEMININE.ordinal());
        assertEquals(3, Genders.NEUTER.ordinal());
    }

    @Test
    void values_count() {
        assertEquals(4, Genders.values().length);
    }
}
