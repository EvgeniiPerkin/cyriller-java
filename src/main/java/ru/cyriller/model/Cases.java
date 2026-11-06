package ru.cyriller.model;

/**
 * Падежи русского языка.
 */
public enum Cases {
    /** Именительный, Кто? Что? (есть) */
    NOMINATIVE(1),
    /** Родительный, Кого? Чего? (нет) */
    GENITIVE(2),
    /** Дательный, Кому? Чему? (дам) */
    DATIVE(3),
    /** Винительный, Кого? Что? (вижу) */
    ACCUSATIVE(4),
    /** Творительный, Кем? Чем? (горжусь) */
    INSTRUMENTAL(5),
    /** Предложный, О ком? О чём? (думаю) */
    PREPOSITIVE(6);

    /** 1-based индекс падежа. */
    public final int index;

    Cases(int index) {
        this.index = index;
    }

    /**
     * Возвращает падеж по 1-based индексу.
     *
     * @param index 1-based индекс (1..6)
     * @throws IllegalArgumentException если индекс вне диапазона 1..6
     */
    public static Cases fromIndex(int index) {
        for (Cases c : values()) {
            if (c.index == index) {
                return c;
            }
        }
        throw new IllegalArgumentException("bad case index: " + index);
    }
}
