package ru.cyriller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CyrRuleTest {

    @Test
    void null_rule_identity() {
        assertEquals("стол", new CyrRule(null).apply("стол"));
    }

    @Test
    void empty_rule_identity() {
        assertEquals("стол", new CyrRule("").apply("стол"));
    }

    @Test
    void rule_with_suffix_and_cut() {
        // "ла1": отрезать 1 букву, добавить "ла"
        assertEquals("стола", new CyrRule("ла1").apply("стол"));
        // "ами": ничего не отрезать, добавить "ами"
        assertEquals("столами", new CyrRule("ами").apply("стол"));
        // "лам1": отрезать 1 букву, добавить "лам"
        assertEquals("столам", new CyrRule("лам1").apply("стол"));
    }

    @Test
    void digits_only_rule_cuts_without_suffix() {
        // "2": отрезать 2 буквы, ничего не добавлять
        assertEquals("ст", new CyrRule("2").apply("стол"));
    }

    @Test
    void star_rule_returns_empty() {
        assertEquals("", new CyrRule("*").apply("стол"));
    }

    @Test
    void cut_longer_than_name_throws() {
        assertThrows(StringIndexOutOfBoundsException.class,
                () -> new CyrRule("ла5").apply("стол"));
    }
}
