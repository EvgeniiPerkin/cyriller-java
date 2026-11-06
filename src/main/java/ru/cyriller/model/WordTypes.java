package ru.cyriller.model;

/**
 * Тип слова.
 */
public enum WordTypes {
    /** Аббревиатура */
    ABBREVIATION(1),
    /** Имя */
    NAME(2),
    /** Фамилия */
    SURNAME(3),
    /** Отчество */
    PATRONYMIC(4),
    /** Топоним */
    TOPONYM(5),
    /** Организация */
    ORGANIZATION(6),
    /** Торговая марка */
    TRADE_MARK(7),
    /** Превосходная степень */
    SUPERLATIVE(8),
    /** Качественное */
    QUALITATIVE(9),
    /** Местоименное */
    PRONOMINAL(10),
    /** Порядковое */
    ORDINAL(11),
    /** Притяжательное */
    POSSESSIVE(12),
    /** форма на -ею */
    FORM_EY(13),
    /** форма на -ою */
    FORM_OY(14),
    /** сравнительная степень на по- */
    COMPARATIVE(15),
    /** форма компаратива на -ей */
    COMPARATIVE_EJ(16);

    /** Значение (1..16). */
    public final int value;

    WordTypes(int value) {
        this.value = value;
    }

    /**
     * Возвращает тип слова по значению.
     *
     * @param value значение (1..16)
     * @throws IllegalArgumentException если значение вне диапазона 1..16
     */
    public static WordTypes fromValue(int value) {
        for (WordTypes t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("bad word type value: " + value);
    }
}
