package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Animates;
import ru.cyriller.model.Genders;
import ru.cyriller.model.WordTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CyrNounTest {

    /** Правила для «стол» (ед. ч.: род. дат. вин. твор. предл.; мн. ч.: им. род. дат. вин. твор. предл.). */
    private static CyrRule[] stolRules() {
        return new CyrRule[]{
                new CyrRule("ла1"),  // стола
                new CyrRule("у"),    // столу
                new CyrRule(""),     // стол
                new CyrRule("ом"),   // столом
                new CyrRule("е"),    // столе
                new CyrRule("ы"),    // столы
                new CyrRule("ов"),   // столов
                new CyrRule("лам1"), // столам
                new CyrRule("ы"),    // столы
                new CyrRule("ами"),  // столами
                new CyrRule("ах")    // столах
        };
    }

    @Test
    void five_arg_constructor_collection_name_equals_name() {
        CyrNoun noun = new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED,
                WordTypes.TOPONYM, stolRules());
        assertEquals("стол", noun.name());
        assertEquals("стол", noun.collectionName());
        assertTrue(noun.exactMatch());
    }

    @Test
    void six_arg_constructor_different_collection_name() {
        CyrNoun noun = new CyrNoun("стол", "столешница", Genders.MASCULINE,
                Animates.INANIMATED, WordTypes.TOPONYM, stolRules());
        assertEquals("стол", noun.name());
        assertEquals("столешница", noun.collectionName());
        assertFalse(noun.exactMatch());
    }

    @Test
    void properties() {
        CyrNoun noun = new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED,
                WordTypes.TOPONYM, stolRules());
        assertEquals(Genders.MASCULINE, noun.gender());
        assertEquals(Animates.INANIMATED, noun.animate());
        assertEquals(WordTypes.TOPONYM, noun.wordType());
        assertFalse(noun.isAnimated());

        CyrNoun man = new CyrNoun("человек", Genders.MASCULINE, Animates.ANIMATED,
                WordTypes.NAME, stolRules());
        assertTrue(man.isAnimated());
    }

    @Test
    void decline_singular() {
        CyrNoun noun = new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED,
                WordTypes.TOPONYM, stolRules());
        CyrResult r = noun.decline();
        assertEquals("стол", r.nominative());
        assertEquals("стола", r.genitive());
        assertEquals("столу", r.dative());
        assertEquals("стол", r.accusative());
        assertEquals("столом", r.instrumental());
        assertEquals("столе", r.prepositional());
    }

    @Test
    void decline_plural() {
        CyrNoun noun = new CyrNoun("стол", Genders.MASCULINE, Animates.INANIMATED,
                WordTypes.TOPONYM, stolRules());
        CyrResult r = noun.declinePlural();
        assertEquals("столы", r.nominative());
        assertEquals("столов", r.genitive());
        assertEquals("столам", r.dative());
        assertEquals("столы", r.accusative());
        assertEquals("столами", r.instrumental());
        assertEquals("столах", r.prepositional());
    }
}
