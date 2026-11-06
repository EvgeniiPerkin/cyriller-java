package ru.cyriller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumbersTest {

    @Test
    void values() {
        assertEquals(2, Numbers.values().length);
        assertEquals(Numbers.SINGULAR, Numbers.valueOf("SINGULAR"));
        assertEquals(Numbers.PLURAL, Numbers.valueOf("PLURAL"));
    }
}
