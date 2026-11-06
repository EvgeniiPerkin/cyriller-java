package ru.cyriller;

import org.junit.jupiter.api.Test;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для {@link CyrName}.
 *
 * <p>Покрытие: 20+ имён × 6 падежей × роды,
 * составные фамилии, тюркские отчества, сокращения, edge cases.</p>
 */
class CyrNameTest {

    private final CyrName cyrName = new CyrName();

    // ==================== Базовые тесты: мужской род ====================

    @Test
    void testMaleBasic() {
        // Иванов — стандартная фамилия
        assertEquals("Иванов", cyrName.decline("Иванов", "Иван", "Иванович", Cases.NOMINATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Иванова", cyrName.decline("Иванов", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false).surname());
        assertEquals("Иванову", cyrName.decline("Иванов", "Иван", "Иванович", Cases.DATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Иванова", cyrName.decline("Иванов", "Иван", "Иванович", Cases.ACCUSATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Ивановым", cyrName.decline("Иванов", "Иван", "Иванович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).surname());
        assertEquals("Иванове", cyrName.decline("Иванов", "Иван", "Иванович", Cases.PREPOSITIVE, Genders.MASCULINE, false).surname());

        // Петров
        assertEquals("Петрова", cyrName.decline("Петров", "Пётр", "Петрович", Cases.GENITIVE, Genders.MASCULINE, false).surname());
        assertEquals("Петрову", cyrName.decline("Петров", "Пётр", "Петрович", Cases.DATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Петрова", cyrName.decline("Петров", "Пётр", "Петрович", Cases.ACCUSATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Петровым", cyrName.decline("Петров", "Пётр", "Петрович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).surname());
        assertEquals("Петрове", cyrName.decline("Петров", "Пётр", "Петрович", Cases.PREPOSITIVE, Genders.MASCULINE, false).surname());

        // Сидоров
        assertEquals("Сидорова", cyrName.decline("Сидоров", "Сидор", "Сидорович", Cases.GENITIVE, Genders.MASCULINE, false).surname());
        assertEquals("Сидорову", cyrName.decline("Сидоров", "Сидор", "Сидорович", Cases.DATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Сидорова", cyrName.decline("Сидоров", "Сидор", "Сидорович", Cases.ACCUSATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Сидоровым", cyrName.decline("Сидоров", "Сидор", "Сидорович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).surname());
        assertEquals("Сидорове", cyrName.decline("Сидоров", "Сидор", "Сидорович", Cases.PREPOSITIVE, Genders.MASCULINE, false).surname());
    }

    // ==================== Базовые тесты: женский род ====================

    @Test
    void testFemaleBasic() {
        // Иванова
        assertEquals("Иванова", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.NOMINATIVE, Genders.FEMININE, false).surname());
        assertEquals("Ивановой", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.GENITIVE, Genders.FEMININE, false).surname());
        assertEquals("Ивановой", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.DATIVE, Genders.FEMININE, false).surname());
        assertEquals("Иванову", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.ACCUSATIVE, Genders.FEMININE, false).surname());
        assertEquals("Ивановой", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.INSTRUMENTAL, Genders.FEMININE, false).surname());
        assertEquals("Ивановой", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.PREPOSITIVE, Genders.FEMININE, false).surname());

        // Петрова
        assertEquals("Петровой", cyrName.decline("Петрова", "Мария", "Петровна", Cases.GENITIVE, Genders.FEMININE, false).surname());
        assertEquals("Петровой", cyrName.decline("Петрова", "Мария", "Петровна", Cases.DATIVE, Genders.FEMININE, false).surname());
        assertEquals("Петрову", cyrName.decline("Петрова", "Мария", "Петровна", Cases.ACCUSATIVE, Genders.FEMININE, false).surname());
        assertEquals("Петровой", cyrName.decline("Петрова", "Мария", "Петровна", Cases.INSTRUMENTAL, Genders.FEMININE, false).surname());
        assertEquals("Петровой", cyrName.decline("Петрова", "Мария", "Петровна", Cases.PREPOSITIVE, Genders.FEMININE, false).surname());
    }

    // ==================== Имена ====================

    @Test
    void testMaleNames() {
        // Иван
        assertEquals("Ивана", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Ивану", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Ивана", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Иваном", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Иване", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());

        // Пётр
        assertEquals("Петра", cyrName.decline("Кузнецов", "Пётр", "Петрович", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Петру", cyrName.decline("Кузнецов", "Пётр", "Петрович", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Петра", cyrName.decline("Кузнецов", "Пётр", "Петрович", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Петром", cyrName.decline("Кузнецов", "Пётр", "Петрович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Петре", cyrName.decline("Кузнецов", "Пётр", "Петрович", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());
        
        // Илья
        assertEquals("Ильи", cyrName.decline("Артамонов", "Илья", "Евгеньевич", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Илье", cyrName.decline("Артамонов", "Илья", "Евгеньевич", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Илью", cyrName.decline("Артамонов", "Илья", "Евгеньевич", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Ильёй", cyrName.decline("Артамонов", "Илья", "Евгеньевич", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Илье", cyrName.decline("Артамонов", "Илья", "Евгеньевич", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());
        
        // Алексей
        assertEquals("Алексея", cyrName.decline("Васильев", "Алексей", "Алексеевич", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Алексею", cyrName.decline("Васильев", "Алексей", "Алексеевич", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Алексея", cyrName.decline("Васильев", "Алексей", "Алексеевич", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Алексеем", cyrName.decline("Васильев", "Алексей", "Алексеевич", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Алексее", cyrName.decline("Васильев", "Алексей", "Алексеевич", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());

        // Дмитрий
        assertEquals("Дмитрия", cyrName.decline("Соколов", "Дмитрий", "Дмитриевич", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Дмитрию", cyrName.decline("Соколов", "Дмитрий", "Дмитриевич", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Дмитрия", cyrName.decline("Соколов", "Дмитрий", "Дмитриевич", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Дмитрием", cyrName.decline("Соколов", "Дмитрий", "Дмитриевич", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Дмитрии", cyrName.decline("Соколов", "Дмитрий", "Дмитриевич", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());

        // Николай
        assertEquals("Николая", cyrName.decline("Михайлов", "Николай", "Николаевич", Cases.GENITIVE, Genders.MASCULINE, false).name());
        assertEquals("Николаю", cyrName.decline("Михайлов", "Николай", "Николаевич", Cases.DATIVE, Genders.MASCULINE, false).name());
        assertEquals("Николая", cyrName.decline("Михайлов", "Николай", "Николаевич", Cases.ACCUSATIVE, Genders.MASCULINE, false).name());
        assertEquals("Николаем", cyrName.decline("Михайлов", "Николай", "Николаевич", Cases.INSTRUMENTAL, Genders.MASCULINE, false).name());
        assertEquals("Николае", cyrName.decline("Михайлов", "Николай", "Николаевич", Cases.PREPOSITIVE, Genders.MASCULINE, false).name());
    }

    @Test
    void testFemaleNames() {
        // Анна
        assertEquals("Анны", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.GENITIVE, Genders.FEMININE, false).name());
        assertEquals("Анне", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.DATIVE, Genders.FEMININE, false).name());
        assertEquals("Анну", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.ACCUSATIVE, Genders.FEMININE, false).name());
        assertEquals("Анной", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.INSTRUMENTAL, Genders.FEMININE, false).name());
        assertEquals("Анне", cyrName.decline("Иванова", "Анна", "Ивановна", Cases.PREPOSITIVE, Genders.FEMININE, false).name());

        // Мария
        assertEquals("Марии", cyrName.decline("Петрова", "Мария", "Петровна", Cases.GENITIVE, Genders.FEMININE, false).name());
        assertEquals("Марии", cyrName.decline("Петрова", "Мария", "Петровна", Cases.DATIVE, Genders.FEMININE, false).name());
        assertEquals("Марию", cyrName.decline("Петрова", "Мария", "Петровна", Cases.ACCUSATIVE, Genders.FEMININE, false).name());
        assertEquals("Марией", cyrName.decline("Петрова", "Мария", "Петровна", Cases.INSTRUMENTAL, Genders.FEMININE, false).name());
        assertEquals("Марии", cyrName.decline("Петрова", "Мария", "Петровна", Cases.PREPOSITIVE, Genders.FEMININE, false).name());

        // Елена
        assertEquals("Елены", cyrName.decline("Сидорова", "Елена", "Сидоровна", Cases.GENITIVE, Genders.FEMININE, false).name());
        assertEquals("Елене", cyrName.decline("Сидорова", "Елена", "Сидоровна", Cases.DATIVE, Genders.FEMININE, false).name());
        assertEquals("Елену", cyrName.decline("Сидорова", "Елена", "Сидоровна", Cases.ACCUSATIVE, Genders.FEMININE, false).name());
        assertEquals("Еленой", cyrName.decline("Сидорова", "Елена", "Сидоровна", Cases.INSTRUMENTAL, Genders.FEMININE, false).name());
        assertEquals("Елене", cyrName.decline("Сидорова", "Елена", "Сидоровна", Cases.PREPOSITIVE, Genders.FEMININE, false).name());

        // Ольга
        assertEquals("Ольги", cyrName.decline("Кузнецова", "Ольга", "Кузнецовна", Cases.GENITIVE, Genders.FEMININE, false).name());
        assertEquals("Ольге", cyrName.decline("Кузнецова", "Ольга", "Кузнецовна", Cases.DATIVE, Genders.FEMININE, false).name());
        assertEquals("Ольгу", cyrName.decline("Кузнецова", "Ольга", "Кузнецовна", Cases.ACCUSATIVE, Genders.FEMININE, false).name());
        assertEquals("Ольгой", cyrName.decline("Кузнецова", "Ольга", "Кузнецовна", Cases.INSTRUMENTAL, Genders.FEMININE, false).name());
        assertEquals("Ольге", cyrName.decline("Кузнецова", "Ольга", "Кузнецовна", Cases.PREPOSITIVE, Genders.FEMININE, false).name());
    }

    // ==================== Отчества ====================

    @Test
    void testPatronymics() {
        // Иванович (мужское)
        assertEquals("Ивановича", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Ивановичу", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.DATIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Ивановича", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.ACCUSATIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Ивановичем", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).patronymic());
        assertEquals("Ивановиче", cyrName.decline("Смирнов", "Иван", "Иванович", Cases.PREPOSITIVE, Genders.MASCULINE, false).patronymic());

        // Петровна (женское)
        assertEquals("Петровны", cyrName.decline("Иванова", "Анна", "Петровна", Cases.GENITIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Петровне", cyrName.decline("Иванова", "Анна", "Петровна", Cases.DATIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Петровну", cyrName.decline("Иванова", "Анна", "Петровна", Cases.ACCUSATIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Петровной", cyrName.decline("Иванова", "Анна", "Петровна", Cases.INSTRUMENTAL, Genders.FEMININE, false).patronymic());
        assertEquals("Петровне", cyrName.decline("Иванова", "Анна", "Петровна", Cases.PREPOSITIVE, Genders.FEMININE, false).patronymic());

        // Сергеевич
        assertEquals("Сергеевича", cyrName.decline("Новиков", "Сергей", "Сергеевич", Cases.GENITIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Сергеевичу", cyrName.decline("Новиков", "Сергей", "Сергеевич", Cases.DATIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Сергеевича", cyrName.decline("Новиков", "Сергей", "Сергеевич", Cases.ACCUSATIVE, Genders.MASCULINE, false).patronymic());
        assertEquals("Сергеевичем", cyrName.decline("Новиков", "Сергей", "Сергеевич", Cases.INSTRUMENTAL, Genders.MASCULINE, false).patronymic());
        assertEquals("Сергеевиче", cyrName.decline("Новиков", "Сергей", "Сергеевич", Cases.PREPOSITIVE, Genders.MASCULINE, false).patronymic());

        // Александровна
        assertEquals("Александровны", cyrName.decline("Смирнова", "Екатерина", "Александровна", Cases.GENITIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Александровне", cyrName.decline("Смирнова", "Екатерина", "Александровна", Cases.DATIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Александровну", cyrName.decline("Смирнова", "Екатерина", "Александровна", Cases.ACCUSATIVE, Genders.FEMININE, false).patronymic());
        assertEquals("Александровной", cyrName.decline("Смирнова", "Екатерина", "Александровна", Cases.INSTRUMENTAL, Genders.FEMININE, false).patronymic());
        assertEquals("Александровне", cyrName.decline("Смирнова", "Екатерина", "Александровна", Cases.PREPOSITIVE, Genders.FEMININE, false).patronymic());
    }

    // ==================== Сложные фамилии ====================

    @Test
    void testCompoundSurnames() {
        // Иванов-Петров (обе части склоняются — как в C#)
        assertEquals("Иванова-Петрова", cyrName.decline("Иванов-Петров", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false).surname());
        assertEquals("Иванову-Петрову", cyrName.decline("Иванов-Петров", "Иван", "Иванович", Cases.DATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Иванова-Петрова", cyrName.decline("Иванов-Петров", "Иван", "Иванович", Cases.ACCUSATIVE, Genders.MASCULINE, false).surname());
        assertEquals("Ивановым-Петровым", cyrName.decline("Иванов-Петров", "Иван", "Иванович", Cases.INSTRUMENTAL, Genders.MASCULINE, false).surname());
        assertEquals("Иванове-Петрове", cyrName.decline("Иванов-Петров", "Иван", "Иванович", Cases.PREPOSITIVE, Genders.MASCULINE, false).surname());

        // Смирнова-Иванова (женская, обе части склоняются)
        assertEquals("Смирновой-Ивановой", cyrName.decline("Смирнова-Иванова", "Анна", "Ивановна", Cases.GENITIVE, Genders.FEMININE, false).surname());
        assertEquals("Смирновой-Ивановой", cyrName.decline("Смирнова-Иванова", "Анна", "Ивановна", Cases.DATIVE, Genders.FEMININE, false).surname());
        assertEquals("Смирнову-Иванову", cyrName.decline("Смирнова-Иванова", "Анна", "Ивановна", Cases.ACCUSATIVE, Genders.FEMININE, false).surname());
        assertEquals("Смирновой-Ивановой", cyrName.decline("Смирнова-Иванова", "Анна", "Ивановна", Cases.INSTRUMENTAL, Genders.FEMININE, false).surname());
        assertEquals("Смирновой-Ивановой", cyrName.decline("Смирнова-Иванова", "Анна", "Ивановна", Cases.PREPOSITIVE, Genders.FEMININE, false).surname());
    }

    // ==================== Тюркские отчества ====================

    @Test
    void testTurkicPatronymics() {
        // Салим-оглы
        CyrNameResult r = cyrName.decline("Мамедов", "Салим", "Салим-оглы", Cases.NOMINATIVE, Genders.MASCULINE, false);
        assertEquals("Салим-оглы", r.patronymic());

        // Салим-кызы
        r = cyrName.decline("Мамедова", "Айгюн", "Салим-кызы", Cases.NOMINATIVE, Genders.FEMININE, false);
        assertEquals("Салим-кызы", r.patronymic());
    }

    // ==================== Сокращения ====================

    @Test
    void testDeclineWithShorten() {
        // Сокращение имени
        CyrNameResult r = cyrName.decline("Иванов", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, true);
        assertEquals("И.", r.name());

        // Сокращение отчества
        r = cyrName.decline("Иванов", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, true);
        assertEquals("И.", r.patronymic());

        // Уже сокращённое имя
        r = cyrName.decline("Иванов", "И.", "И.", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("И.", r.name());
        assertEquals("И.", r.patronymic());
    }

    // ==================== Edge cases ====================

    @Test
    void testEdgeCases() {
        // Пустая фамилия
        CyrNameResult r = cyrName.decline("", "Иван", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("", r.surname());

        // Пустое имя
        r = cyrName.decline("Иванов", "", "Иванович", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("Иванова", r.surname());
        assertEquals("", r.name());

        // Пустое отчество
        r = cyrName.decline("Иванов", "Иван", "", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("Иванова", r.surname());
        assertEquals("Ивана", r.name());
        assertEquals("", r.patronymic());
    }

    // ==================== All cases ====================

    @Test
    void testAllCases() {
        // Проверка всех 6 падежей через allCases (CyrResult = fullName: фамилия + имя + отчество)
        CyrResult result = cyrName.decline("Иванов", "Иван", "Иванович", Genders.MASCULINE, false);
        assertNotNull(result);

        // NOMINATIVE
        assertEquals("Иванов Иван Иванович", result.get(Cases.NOMINATIVE));
        // GENITIVE
        assertEquals("Иванова Ивана Ивановича", result.get(Cases.GENITIVE));
        // DATIVE
        assertEquals("Иванову Ивану Ивановичу", result.get(Cases.DATIVE));
        // ACCUSATIVE
        assertEquals("Иванова Ивана Ивановича", result.get(Cases.ACCUSATIVE));
        // INSTRUMENTAL
        assertEquals("Ивановым Иваном Ивановичем", result.get(Cases.INSTRUMENTAL));
        // PREPOSITIVE
        assertEquals("Иванове Иване Ивановиче", result.get(Cases.PREPOSITIVE));
    }

    // ==================== ProperCase ====================

    @Test
    void testProperCase() {
        // Ввод в нижнем регистре → ProperCase
        CyrNameResult r = cyrName.decline("иванов", "иван", "иванович", Cases.NOMINATIVE, Genders.MASCULINE, false);
        assertEquals("Иванов", r.surname());
        assertEquals("Иван", r.name());
        assertEquals("Иванович", r.patronymic());
    }

    // ==================== Auto-detect gender ====================

    @Test
    void testAutoDetectGender() {
        // Отчество на "на" → женский род
        CyrNameResult r = cyrName.decline("Иванова", "Анна", "Ивановна", Cases.GENITIVE, Genders.UNDEFINED, false);
        assertEquals("Ивановой", r.surname());

        // Отчество на "вич" → мужской род
        r = cyrName.decline("Иванов", "Иван", "Иванович", Cases.GENITIVE, Genders.UNDEFINED, false);
        assertEquals("Иванова", r.surname());
    }

    // ==================== SplitPatronymic ====================

    @Test
    void testSplitPatronymic() {
        // Обычное отчество
        CyrName.SplitPatronymicResult r = cyrName.splitPatronymic("Иванович");
        assertNull(r.prefix());
        assertEquals("Иванович", r.patronymic());
        assertNull(r.suffix());

        // Тюркское отчество с префиксом
        r = cyrName.splitPatronymic("Ибн Салим");
        assertEquals("Ибн ", r.prefix());
        assertEquals("Салим", r.patronymic());
        assertNull(r.suffix());

        // Тюркское отчество с суффиксом
        r = cyrName.splitPatronymic("Салим-оглы");
        assertNull(r.prefix());
        assertEquals("Салим", r.patronymic());
        assertEquals("-оглы", r.suffix());

        // Пустое отчество
        r = cyrName.splitPatronymic("");
        assertNull(r.prefix());
        assertEquals("", r.patronymic());
        assertNull(r.suffix());
    }

    // ==================== SplitPatronymic — (15 кейсов) ====================

    @Test
    void testSplitPatronymicVba() {
        // Префиксные
        assertSplit("Ибн Салим", "Ибн ", "Салим", null);
        assertSplit("ибн-Салим", "ибн-", "Салим", null);
        assertSplit("о ибн-Салим", null, "о ибн-Салим", null);
        assertSplit("ибн-Салим Ука", "ибн-", "Салим Ука", null);

        // Суффиксные
        assertSplit("Салим Оглы", null, "Салим", " Оглы");
        assertSplit("Салим-Оглы", null, "Салим", "-Оглы");
        assertSplit("Салим-кызы", null, "Салим", "-кызы");
        assertSplit("Салим кызы", null, "Салим", " кызы");
        assertSplit("Салим-гызы", null, "Салим", "-гызы");
        assertSplit("Салим гызы", null, "Салим", " гызы");
        assertSplit("Абдибаит Уулу", null, "Абдибаит", " Уулу");
        assertSplit("Абдибаит-Уулу", null, "Абдибаит", "-Уулу");

        // Не совпадает
        assertSplit("Абдибаит Уулу Мулу", null, "Абдибаит Уулу Мулу", null);

        // Сложные
        assertSplit("Мулу Абдибаит Уулу", null, "Мулу Абдибаит", " Уулу");
        assertSplit("Ибн-Абдибаит Уулу", "Ибн-", "Абдибаит", " Уулу");
    }

    private void assertSplit(String input, String prefix, String patronymic, String suffix) {
        CyrName.SplitPatronymicResult r = cyrName.splitPatronymic(input);
        assertEquals(prefix, r.prefix(), "prefix for " + input);
        assertEquals(patronymic, r.patronymic(), "patronymic for " + input);
        assertEquals(suffix, r.suffix(), "suffix for " + input);
    }

    // ==================== Full name parser ====================

    @Test
    void testFullNameParser() {
        // Полный формат: "Фамилия Имя Отчество"
        CyrNameResult r = cyrName.decline("Иванов Иван Иванович", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("Иванова", r.surname());
        assertEquals("Ивана", r.name());
        assertEquals("Ивановича", r.patronymic());

        // Без отчества
        r = cyrName.decline("Иванов Иван", Cases.GENITIVE, Genders.MASCULINE, false);
        assertEquals("Иванова", r.surname());
        assertEquals("Ивана", r.name());
        assertEquals("", r.patronymic());
    }

    @Test
    void testFeminineFullNameDeclension() {
        // "Иванова Наталья Петровна" (F, shorten=false)
        assertFullName("Иванова Наталья Петровна", Genders.FEMININE, false,
                "Ивановой Натальи Петровны",   // Genitive
                "Ивановой Наталье Петровне",   // Dative
                "Иванову Наталью Петровну",    // Accusative
                "Ивановой Натальей Петровной", // Instrumental
                "Ивановой Наталье Петровне");  // Prepositional

        // "Сафаралиева Койкеб Кямил Кызы" (F, shorten=false)
        assertFullName("Сафаралиева Койкеб Кямил Кызы", Genders.FEMININE, false,
                "Сафаралиевой Койкеб Кямил Кызы",
                "Сафаралиевой Койкеб Кямил Кызы",
                "Сафаралиеву Койкеб Кямил Кызы",
                "Сафаралиевой Койкеб Кямил Кызы",
                "Сафаралиевой Койкеб Кямил Кызы");

        // "Сафаралиева Койкеб Кямил-Кызы" (F, shorten=false)
        assertFullName("Сафаралиева Койкеб Кямил-Кызы", Genders.FEMININE, false,
                "Сафаралиевой Койкеб Кямил-Кызы",
                "Сафаралиевой Койкеб Кямил-Кызы",
                "Сафаралиеву Койкеб Кямил-Кызы",
                "Сафаралиевой Койкеб Кямил-Кызы",
                "Сафаралиевой Койкеб Кямил-Кызы");

        // "Иванова Наталья Петровна" (F, shorten=true)
        assertFullName("Иванова Наталья Петровна", Genders.FEMININE, true,
                "Ивановой Н. П.",
                "Ивановой Н. П.",
                "Иванову Н. П.",
                "Ивановой Н. П.",
                "Ивановой Н. П.");

        // "Сафаралиева Койкеб Кямил Кызы" (F, shorten=true)
        assertFullName("Сафаралиева Койкеб Кямил Кызы", Genders.FEMININE, true,
                "Сафаралиевой К. К.",
                "Сафаралиевой К. К.",
                "Сафаралиеву К. К.",
                "Сафаралиевой К. К.",
                "Сафаралиевой К. К.");
    }

    @Test
    void testMasculineFullNameDeclension() {
        // "Иванов Иван Иванович" (M, shorten=false)
        assertFullName("Иванов Иван Иванович", Genders.MASCULINE, false,
                "Иванова Ивана Ивановича",
                "Иванову Ивану Ивановичу",
                "Иванова Ивана Ивановича",
                "Ивановым Иваном Ивановичем",
                "Иванове Иване Ивановиче");

        // "Карим Куржов Салим Оглы" (M, shorten=false)
        assertFullName("Карим Куржов Салим Оглы", Genders.MASCULINE, false,
                "Карима Куржова Салим Оглы",
                "Кариму Куржову Салим Оглы",
                "Карима Куржова Салим Оглы",
                "Каримом Куржовом Салим Оглы",
                "Кариме Куржове Салим Оглы");

        // "Карим Куржов Салим-Оглы" (M, shorten=false)
        assertFullName("Карим Куржов Салим-Оглы", Genders.MASCULINE, false,
                "Карима Куржова Салим-Оглы",
                "Кариму Куржову Салим-Оглы",
                "Карима Куржова Салим-Оглы",
                "Каримом Куржовом Салим-Оглы",
                "Кариме Куржове Салим-Оглы");

        // "Иванов Иван Иванович" (M, shorten=true)
        assertFullName("Иванов Иван Иванович", Genders.MASCULINE, true,
                "Иванова И. И.",
                "Иванову И. И.",
                "Иванова И. И.",
                "Ивановым И. И.",
                "Иванове И. И.");

        // "Карим Куржов Салим Оглы" (M, shorten=true)
        assertFullName("Карим Куржов Салим Оглы", Genders.MASCULINE, true,
                "Карима К. С.",
                "Кариму К. С.",
                "Карима К. С.",
                "Каримом К. С.",
                "Кариме К. С.");

        // "Илон МакФерсон" (M, shorten=false) — 2 слова
        assertFullName("Илон МакФерсон", Genders.MASCULINE, false,
                "Илона МакФерсона",
                "Илону МакФерсону",
                "Илона МакФерсона",
                "Илоном МакФерсоном",
                "Илоне МакФерсоне");

        // "Ахмед Гафуров ибн Мухаммад" (M, shorten=false) — тюркский префикс
        assertFullName("Ахмед Гафуров ибн Мухаммад", Genders.MASCULINE, false,
                "Ахмеда Гафурова ибн Мухаммада",
                "Ахмеду Гафурову ибн Мухаммаду",
                "Ахмеда Гафурова ибн Мухаммада",
                "Ахмедом Гафуровом ибн Мухаммадом",
                "Ахмеде Гафурове ибн Мухаммаде");
    }

    private void assertFullName(String fullName, Genders gender, boolean shorten,
                                String genitive, String dative, String accusative,
                                String instrumental, String prepositional) {
        CyrNameResult r;
        r = cyrName.decline(fullName, Cases.GENITIVE, gender, shorten);
        assertEquals(genitive, r.fullName(), "Genitive for " + fullName);
        r = cyrName.decline(fullName, Cases.DATIVE, gender, shorten);
        assertEquals(dative, r.fullName(), "Dative for " + fullName);
        r = cyrName.decline(fullName, Cases.ACCUSATIVE, gender, shorten);
        assertEquals(accusative, r.fullName(), "Accusative for " + fullName);
        r = cyrName.decline(fullName, Cases.INSTRUMENTAL, gender, shorten);
        assertEquals(instrumental, r.fullName(), "Instrumental for " + fullName);
        r = cyrName.decline(fullName, Cases.PREPOSITIVE, gender, shorten);
        assertEquals(prepositional, r.fullName(), "Prepositional for " + fullName);
    }

    // ==================== VBA: TestNameDeclension, TestSurnameDeclension, TestPatronymicDeclension ====================

    @Test
    void testNameDeclension() {
        // "Иван" (masculine)
        assertPart("", "Иван", "", Cases.ACCUSATIVE, Genders.MASCULINE, "Ивана");
        assertPart("", "Иван", "", Cases.DATIVE, Genders.MASCULINE, "Ивану");
        assertPart("", "Иван", "", Cases.GENITIVE, Genders.MASCULINE, "Ивана");
        assertPart("", "Иван", "", Cases.INSTRUMENTAL, Genders.MASCULINE, "Иваном");
        assertPart("", "Иван", "", Cases.PREPOSITIVE, Genders.MASCULINE, "Иване");

        // "Наталья" (feminine)
        assertPart("", "Наталья", "", Cases.ACCUSATIVE, Genders.FEMININE, "Наталью");
        assertPart("", "Наталья", "", Cases.DATIVE, Genders.FEMININE, "Наталье");
        assertPart("", "Наталья", "", Cases.GENITIVE, Genders.FEMININE, "Натальи");
        assertPart("", "Наталья", "", Cases.INSTRUMENTAL, Genders.FEMININE, "Натальей");
        assertPart("", "Наталья", "", Cases.PREPOSITIVE, Genders.FEMININE, "Наталье");
    }

    @Test
    void testSurnameDeclension() {
        // "Иванов" (masculine)
        assertPart("Иванов", "", "", Cases.ACCUSATIVE, Genders.MASCULINE, "Иванова");
        assertPart("Иванов", "", "", Cases.DATIVE, Genders.MASCULINE, "Иванову");
        assertPart("Иванов", "", "", Cases.GENITIVE, Genders.MASCULINE, "Иванова");
        assertPart("Иванов", "", "", Cases.INSTRUMENTAL, Genders.MASCULINE, "Ивановым");
        assertPart("Иванов", "", "", Cases.PREPOSITIVE, Genders.MASCULINE, "Иванове");

        // "Петрова" (feminine)
        assertPart("Петрова", "", "", Cases.ACCUSATIVE, Genders.FEMININE, "Петрову");
        assertPart("Петрова", "", "", Cases.DATIVE, Genders.FEMININE, "Петровой");
        assertPart("Петрова", "", "", Cases.GENITIVE, Genders.FEMININE, "Петровой");
        assertPart("Петрова", "", "", Cases.INSTRUMENTAL, Genders.FEMININE, "Петровой");
        assertPart("Петрова", "", "", Cases.PREPOSITIVE, Genders.FEMININE, "Петровой");
    }

    @Test
    void testPatronymicDeclension() {
        // "Иванович" (masculine)
        assertPart("", "", "Иванович", Cases.ACCUSATIVE, Genders.MASCULINE, "Ивановича");
        assertPart("", "", "Иванович", Cases.DATIVE, Genders.MASCULINE, "Ивановичу");
        assertPart("", "", "Иванович", Cases.GENITIVE, Genders.MASCULINE, "Ивановича");
        assertPart("", "", "Иванович", Cases.INSTRUMENTAL, Genders.MASCULINE, "Ивановичем");
        assertPart("", "", "Иванович", Cases.PREPOSITIVE, Genders.MASCULINE, "Ивановиче");

        // "Ивановна" (feminine)
        assertPart("", "", "Ивановна", Cases.ACCUSATIVE, Genders.FEMININE, "Ивановну");
        assertPart("", "", "Ивановна", Cases.DATIVE, Genders.FEMININE, "Ивановне");
        assertPart("", "", "Ивановна", Cases.GENITIVE, Genders.FEMININE, "Ивановны");
        assertPart("", "", "Ивановна", Cases.INSTRUMENTAL, Genders.FEMININE, "Ивановной");
        assertPart("", "", "Ивановна", Cases.PREPOSITIVE, Genders.FEMININE, "Ивановне");
    }

    private void assertPart(String surname, String name, String patronymic,
                            Cases c, Genders g, String expected) {
        CyrNameResult r = cyrName.decline(surname, name, patronymic, c, g, false);
        String actual = c == Cases.NOMINATIVE ? r.fullName() :
                (surname.isEmpty() ? (name.isEmpty() ? r.patronymic() : r.name()) : r.surname());
        assertEquals(expected, actual, c + " for " + surname + " " + name + " " + patronymic);
    }

    @Test
    void testIsShorten() {
        assertTrue(cyrName.isShorten(""));
        assertFalse(cyrName.isShorten("Петровна"));
        assertTrue(cyrName.isShorten(""));
    }

    @Test
    void testShorten() {
        assertNull(cyrName.shorten("     "));
        assertNull(cyrName.shorten(""));
        assertEquals("П.", cyrName.shorten("Перкин"));
    }

    @Test
    void testSubstringRight() {
        assertEquals("н", cyrName.substringRight("Перкин", 1));
        assertEquals("Перкни", cyrName.substringRight("Перкни", 7));
    }

    @Test
    void testSetEndS() {
        assertEquals("ПеркиСон", cyrName.setEnd("Перкин", 1, "Сон"));
        assertEquals("ПеркСон", cyrName.setEnd("Перкин", 2, "Сон"));
        assertEquals("ПерСон", cyrName.setEnd("Перкин", 3, "Сон"));
    }

    @Test
    void testSetEnd() {
        assertEquals("ПерСон", cyrName.setEnd("Перкин", "Сон"));
        assertEquals("ПеСонч", cyrName.setEnd("Перкин", "Сонч"));
        assertEquals("ПСончи", cyrName.setEnd("Перкин", "Сончи"));
    }

    @Test
    void testProperCaseHelper() {
        assertEquals("Перкин", cyrName.properCase("перкин"));
        assertEquals("Евгений", cyrName.properCase("евгений"));
        assertEquals("Владимирович", cyrName.properCase("владимирович"));
    }
}
