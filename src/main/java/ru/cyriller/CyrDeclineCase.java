package ru.cyriller;

import ru.cyriller.model.Cases;

import java.util.List;

/**
 * Описание падежа: имя на RU/EN, описание, 1-based индекс и связь с {@link Cases}.
 */
public record CyrDeclineCase(int index, String russianName, String englishName,
                             String description, Cases value) {

    /** Именительный (Nominative), Кто? Что? (есть). */
    public static final CyrDeclineCase CASE_1 =
            new CyrDeclineCase(1, "Именительный", "Nominative", "Кто? Что? (есть)", Cases.NOMINATIVE);
    /** Родительный (Genitive), Кого? Чего? (нет). */
    public static final CyrDeclineCase CASE_2 =
            new CyrDeclineCase(2, "Родительный", "Genitive", "Кого? Чего? (нет)", Cases.GENITIVE);
    /** Дательный (Dative), Кому? Чему? (дам). */
    public static final CyrDeclineCase CASE_3 =
            new CyrDeclineCase(3, "Дательный", "Dative", "Кому? Чему? (дам)", Cases.DATIVE);
    /** Винительный (Accusative), Кого? Что? (вижу). */
    public static final CyrDeclineCase CASE_4 =
            new CyrDeclineCase(4, "Винительный", "Accusative", "Кого? Что? (вижу)", Cases.ACCUSATIVE);
    /** Творительный (Instrumental), Кем? Чем? (горжусь). */
    public static final CyrDeclineCase CASE_5 =
            new CyrDeclineCase(5, "Творительный", "Instrumental", "Кем? Чем? (горжусь)", Cases.INSTRUMENTAL);
    /** Предложный (Prepositional), О ком? О чём? (думаю). */
    public static final CyrDeclineCase CASE_6 =
            new CyrDeclineCase(6, "Предложный", "Prepositional", "О ком? О чем? (думаю)", Cases.PREPOSITIVE);

    // EN-алиасы
    public static final CyrDeclineCase NOMINATIVE = CASE_1;
    public static final CyrDeclineCase GENITIVE = CASE_2;
    public static final CyrDeclineCase DATIVE = CASE_3;
    public static final CyrDeclineCase ACCUSATIVE = CASE_4;
    public static final CyrDeclineCase INSTRUMENTAL = CASE_5;
    public static final CyrDeclineCase PREPOSITIVE = CASE_6;

    // RU-алиасы
    public static final CyrDeclineCase ИМЕНИТЕЛЬНЫЙ = CASE_1;
    public static final CyrDeclineCase РОДИТЕЛЬНЫЙ = CASE_2;
    public static final CyrDeclineCase ДАТЕЛЬНЫЙ = CASE_3;
    public static final CyrDeclineCase ВИНИТЕЛЬНЫЙ = CASE_4;
    public static final CyrDeclineCase ТВОРИТЕЛЬНЫЙ = CASE_5;
    public static final CyrDeclineCase ПРЕЛОЖНЫЙ = CASE_6;

    /** Все 6 падежей. */
    public static List<CyrDeclineCase> all() {
        return List.of(CASE_1, CASE_2, CASE_3, CASE_4, CASE_5, CASE_6);
    }
}
