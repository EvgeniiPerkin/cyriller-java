package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Cases;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CyrResultTest {

    private static final CyrResult R = new CyrResult("а", "б", "в", "г", "д", "е");

    // --- Конструкторы ---

    @Test
    void default_constructor_all_null() {
        CyrResult r = new CyrResult();
        assertNull(r.get(1));
        assertNull(r.get(6));
    }

    @Test
    void word_constructor_fills_all_six() {
        CyrResult r = new CyrResult("слово");
        for (int i = 1; i <= 6; i++) {
            assertEquals("слово", r.get(i));
        }
    }

    @Test
    void six_args_constructor() {
        assertArrayEquals(new String[]{"а", "б", "в", "г", "д", "е"}, R.toArray());
    }

    @Test
    void array_constructor() {
        CyrResult r = new CyrResult(new String[]{"а", "б", "в", "г", "д", "е"});
        assertArrayEquals(new String[]{"а", "б", "в", "г", "д", "е"}, r.toArray());
    }

    @Test
    void array_constructor_wrong_length_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new CyrResult(new String[]{"а", "б", "в"}));
        assertThrows(IllegalArgumentException.class, () -> new CyrResult((String[]) null));
    }

    // --- get/set по Cases ---

    @Test
    void get_by_cases() {
        assertEquals("а", R.get(Cases.NOMINATIVE));
        assertEquals("б", R.get(Cases.GENITIVE));
        assertEquals("в", R.get(Cases.DATIVE));
        assertEquals("г", R.get(Cases.ACCUSATIVE));
        assertEquals("д", R.get(Cases.INSTRUMENTAL));
        assertEquals("е", R.get(Cases.PREPOSITIVE));
    }

    @Test
    void set_by_cases() {
        CyrResult r = new CyrResult();
        r.set(Cases.NOMINATIVE, "а");
        r.set(Cases.GENITIVE, "б");
        r.set(Cases.DATIVE, "в");
        r.set(Cases.ACCUSATIVE, "г");
        r.set(Cases.INSTRUMENTAL, "д");
        r.set(Cases.PREPOSITIVE, "е");
        assertEquals(R, r);
    }

    // --- Индексатор (1-based) ---

    @Test
    void get_set_by_index() {
        assertEquals("а", R.get(1));
        assertEquals("е", R.get(6));
        CyrResult r = new CyrResult();
        r.set(1, "а");
        r.set(6, "е");
        assertEquals("а", r.get(1));
        assertEquals("е", r.get(6));
    }

    @Test
    void index_out_of_range_throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> R.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> R.get(7));
        assertThrows(IndexOutOfBoundsException.class, () -> R.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> R.set(0, "x"));
        assertThrows(IndexOutOfBoundsException.class, () -> R.set(7, "x"));
    }

    @Test
    void plus_concatenates_elementwise_with_space() {
        CyrResult a = new CyrResult("Иван", "Ивана", "Ивану", "Ивана", "Иваном", "Иване");
        CyrResult b = new CyrResult("Иванов", "Иванова", "Иванову", "Иванова", "Ивановым", "Иванове");
        CyrResult sum = a.plus(b);
        assertEquals("Иван Иванов", sum.get(1));
        assertEquals("Ивана Иванова", sum.get(2));
        assertEquals("Ивану Иванову", sum.get(3));
        assertEquals("Ивана Иванова", sum.get(4));
        assertEquals("Иваном Ивановым", sum.get(5));
        assertEquals("Иване Иванове", sum.get(6));
    }

    // --- add ---

    @Test
    void add_with_default_separator() {
        CyrResult r = new CyrResult("Иван");
        r.add(new CyrResult("Иванов"));
        assertEquals("Иван-Иванов", r.get(1));
    }

    @Test
    void add_with_custom_separator() {
        CyrResult r = new CyrResult("Иван");
        r.add(new CyrResult("Иванов"), " ");
        assertEquals("Иван Иванов", r.get(1));
    }

    // --- toList / toArray / словари ---

    @Test
    void to_list() {
        assertEquals(List.of("а", "б", "в", "г", "д", "е"), R.toList());
    }

    @Test
    void to_array() {
        assertArrayEquals(new String[]{"а", "б", "в", "г", "д", "е"}, R.toArray());
    }

    @Test
    void to_dictionary() {
        Map<Cases, String> d = R.toDictionary();
        assertEquals(6, d.size());
        assertEquals("а", d.get(Cases.NOMINATIVE));
        assertEquals("е", d.get(Cases.PREPOSITIVE));
    }

    @Test
    void to_string_dictionary() {
        Map<String, String> d = R.toStringDictionary();
        assertEquals(6, d.size());
        assertEquals("а", d.get("Nominative"));
        assertEquals("е", d.get("Prepositional"));
    }

    @Test
    void to_russian_string_dictionary() {
        Map<String, String> d = R.toRussianStringDictionary();
        assertEquals(6, d.size());
        assertEquals("а", d.get("Именительный"));
        assertEquals("е", d.get("Предложный"));
    }

    // --- именованные геттеры ---

    @Test
    void named_getters() {
        assertEquals("а", R.nominative());
        assertEquals("б", R.genitive());
        assertEquals("в", R.dative());
        assertEquals("г", R.accusative());
        assertEquals("д", R.instrumental());
        assertEquals("е", R.prepositional());
    }

    // --- equals/hashCode (B-1) ---

    @Test
    void equals_by_value() {
        CyrResult a = new CyrResult("а", "б", "в", "г", "д", "е");
        CyrResult b = new CyrResult("а", "б", "в", "г", "д", "е");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_not_equal_when_any_field_differs() {
        CyrResult a = new CyrResult("а", "б", "в", "г", "д", "е");
        CyrResult b = new CyrResult("а", "б", "в", "г", "д", "ж");
        assertNotEquals(a, b);
    }

    @Test
    void equals_null_fields() {
        assertEquals(new CyrResult(), new CyrResult());
        assertEquals(new CyrResult().hashCode(), new CyrResult().hashCode());
    }
}
