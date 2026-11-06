package ru.cyriller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimatesTest {

    @Test
    void values() {
        assertEquals(2, Animates.values().length);
        assertEquals(Animates.ANIMATED, Animates.valueOf("ANIMATED"));
        assertEquals(Animates.INANIMATED, Animates.valueOf("INANIMATED"));
    }
}
