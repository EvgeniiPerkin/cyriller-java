package ru.cyriller;

import ru.cyriller.model.Cases;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Результат склонения: 6 форм слова по падежам (case1..case6).
 */
public class CyrResult {

    protected String case1;
    protected String case2;
    protected String case3;
    protected String case4;
    protected String case5;
    protected String case6;

    /** Пустой результат (все формы null). */
    public CyrResult() {
    }

    /** Все 6 падежей = {@code word}. */
    public CyrResult(String word) {
        this.case1 = word;
        this.case2 = word;
        this.case3 = word;
        this.case4 = word;
        this.case5 = word;
        this.case6 = word;
    }

    /** Явные 6 форм. */
    public CyrResult(String case1, String case2, String case3,
                     String case4, String case5, String case6) {
        this.case1 = case1;
        this.case2 = case2;
        this.case3 = case3;
        this.case4 = case4;
        this.case5 = case5;
        this.case6 = case6;
    }

    /**
     * Массив из 6 форм (0-based, в порядке падежей 1..6).
     *
     * @throws IllegalArgumentException если массив null или длина != 6
     */
    public CyrResult(String[] cases) {
        if (cases == null || cases.length != 6) {
            throw new IllegalArgumentException("Declension result must have six parts.");
        }
        this.case1 = cases[0];
        this.case2 = cases[1];
        this.case3 = cases[2];
        this.case4 = cases[3];
        this.case5 = cases[4];
        this.case6 = cases[5];
    }

    /**
     * Поэлементное сложение строк через пробел.
     */
    public CyrResult plus(CyrResult other) {
        return new CyrResult(case1 + " " + other.case1,
                case2 + " " + other.case2,
                case3 + " " + other.case3,
                case4 + " " + other.case4,
                case5 + " " + other.case5,
                case6 + " " + other.case6);
    }

    /** Именительный, Кто? Что? (есть). */
    public String nominative() {
        return case1;
    }

    /** Родительный, Кого? Чего? (нет). */
    public String genitive() {
        return case2;
    }

    /** Дательный, Кому? Чему? (дам). */
    public String dative() {
        return case3;
    }

    /** Винительный, Кого? Что? (вижу). */
    public String accusative() {
        return case4;
    }

    /** Творительный, Кем? Чем? (горжусь). */
    public String instrumental() {
        return case5;
    }

    /** Предложный, О ком? О чём? (думаю). */
    public String prepositional() {
        return case6;
    }

    /**
     * Форма слова по падежу.
     */
    public String get(Cases c) {
        return switch (c) {
            case NOMINATIVE -> case1;
            case GENITIVE -> case2;
            case DATIVE -> case3;
            case ACCUSATIVE -> case4;
            case INSTRUMENTAL -> case5;
            case PREPOSITIVE -> case6;
        };
    }

    /**
     * Установка формы слова по падежу.
     */
    public void set(Cases c, String value) {
        switch (c) {
            case NOMINATIVE -> case1 = value;
            case GENITIVE -> case2 = value;
            case DATIVE -> case3 = value;
            case ACCUSATIVE -> case4 = value;
            case INSTRUMENTAL -> case5 = value;
            case PREPOSITIVE -> case6 = value;
        }
    }

    /**
     * Индексатор: форма по 1-based номеру падежа (1..6).
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона 1..6 
     */
    public String get(int index) {
        checkIndex(index);
        return get(Cases.fromIndex(index));
    }

    /**
     * Индексатор: установка формы по 1-based номеру падежа (1..6).
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона 1..6
     */
    public void set(int index, String value) {
        checkIndex(index);
        set(Cases.fromIndex(index), value);
    }

    /**
     * Валидация 1-based индекса.
     */
    protected void checkIndex(int index) {
        if (index < 1 || index > 6) {
            throw new IndexOutOfBoundsException("This is a one based index! (1..6): " + index);
        }
    }

    public void add(CyrResult result) {
        add(result, "-");
    }

    public void add(CyrResult result, String separator) {
        case1 += separator + result.case1;
        case2 += separator + result.case2;
        case3 += separator + result.case3;
        case4 += separator + result.case4;
        case5 += separator + result.case5;
        case6 += separator + result.case6;
    }

    public List<String> toList() {
        return Arrays.asList(case1, case2, case3, case4, case5, case6);
    }

    public String[] toArray() {
        return new String[]{case1, case2, case3, case4, case5, case6};
    }

    public Map<Cases, String> toDictionary() {
        Map<Cases, String> result = new LinkedHashMap<>();
        result.put(Cases.NOMINATIVE, case1);
        result.put(Cases.GENITIVE, case2);
        result.put(Cases.DATIVE, case3);
        result.put(Cases.ACCUSATIVE, case4);
        result.put(Cases.INSTRUMENTAL, case5);
        result.put(Cases.PREPOSITIVE, case6);
        return result;
    }

    public Map<String, String> toStringDictionary() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("Nominative", case1);
        result.put("Genitive", case2);
        result.put("Dative", case3);
        result.put("Accusative", case4);
        result.put("Instrumental", case5);
        result.put("Prepositional", case6);
        return result;
    }

    public Map<String, String> toRussianStringDictionary() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("Именительный", case1);
        result.put("Родительный", case2);
        result.put("Дательный", case3);
        result.put("Винительный", case4);
        result.put("Творительный", case5);
        result.put("Предложный", case6);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CyrResult that)) {
            return false;
        }
        return Objects.equals(case1, that.case1)
                && Objects.equals(case2, that.case2)
                && Objects.equals(case3, that.case3)
                && Objects.equals(case4, that.case4)
                && Objects.equals(case5, that.case5)
                && Objects.equals(case6, that.case6);
    }

    @Override
    public int hashCode() {
        return Objects.hash(case1, case2, case3, case4, case5, case6);
    }

    @Override
    public String toString() {
        return "CyrResult" + Arrays.toString(toArray());
    }
}
