package ru.cyriller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CyrNameResultTest {

    // --- Конструкторы ---

    @Test
    void three_args_constructor() {
        CyrNameResult r = new CyrNameResult("Иванов", "Иван", "Иванович");
        assertEquals("Иванов", r.surname());
        assertEquals("Иван", r.name());
        assertEquals("Иванович", r.patronymic());
    }

    @Test
    void array_constructor_two_elements() {
        CyrNameResult r = new CyrNameResult(new String[]{"Иванов", "Иван"});
        assertEquals("Иванов", r.surname());
        assertEquals("Иван", r.name());
        assertNull(r.patronymic());
    }

    @Test
    void array_constructor_three_elements() {
        CyrNameResult r = new CyrNameResult(new String[]{"Иванов", "Иван", "Иванович"});
        assertEquals(new CyrNameResult("Иванов", "Иван", "Иванович"), r);
    }

    @Test
    void array_constructor_bad_length_throws() {
        assertThrows(IllegalArgumentException.class, () -> new CyrNameResult(new String[]{}));
        assertThrows(IllegalArgumentException.class, () -> new CyrNameResult(new String[]{"Иванов"}));
        assertThrows(IllegalArgumentException.class,
                () -> new CyrNameResult(new String[]{"Иванов", "Иван", "Иванович", "лишнее"}));
        assertThrows(IllegalArgumentException.class, () -> new CyrNameResult((String[]) null));
    }

    // --- fullName ---

    @Test
    void full_name_all_three_parts() {
        assertEquals("Иванов Иван Иванович",
                new CyrNameResult("Иванов", "Иван", "Иванович").fullName());
    }

    @Test
    void full_name_without_patronymic() {
        assertEquals("Иванов Иван",
                new CyrNameResult("Иванов", "Иван", null).fullName());
    }

    @Test
    void full_name_skips_blank_parts() {
        assertEquals("Иванов Иван",
                new CyrNameResult("Иванов", "Иван", "   ").fullName());
        assertEquals("Иван",
                new CyrNameResult(null, "Иван", null).fullName());
    }

    @Test
    void full_name_all_null_is_empty_string() {
        assertEquals("", new CyrNameResult(null, null, null).fullName());
    }

    @Test
    void to_string_is_full_name() {
        CyrNameResult r = new CyrNameResult("Иванов", "Иван", "Иванович");
        assertEquals(r.fullName(), r.toString());
    }

    // --- equals/hashCode (B-2) ---

    @Test
    void equals_by_value_and_hash_code() {
        CyrNameResult a = new CyrNameResult("Иванов", "Иван", "Иванович");
        CyrNameResult b = new CyrNameResult("Иванов", "Иван", "Иванович");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_null_fields() {
        assertEquals(new CyrNameResult("Иванов", "Иван", null),
                new CyrNameResult("Иванов", "Иван", null));
        assertEquals(new CyrNameResult(null, null, null), new CyrNameResult(null, null, null));
    }

    @Test
    void not_equal_when_any_field_differs() {
        CyrNameResult a = new CyrNameResult("Иванов", "Иван", "Иванович");
        assertNotEquals(a, new CyrNameResult("Петров", "Иван", "Иванович"));
        assertNotEquals(a, new CyrNameResult("Иванов", "Пётр", "Иванович"));
        assertNotEquals(a, new CyrNameResult("Иванов", "Иван", "Петрович"));
    }
}
