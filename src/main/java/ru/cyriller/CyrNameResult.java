package ru.cyriller;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Результат склонения ФИО: фамилия, имя, отчество.
 */
public record CyrNameResult(String surname, String name, String patronymic) {

    /**
     * Из массива: 2 элемента (фамилия, имя) или 3 (фамилия, имя, отчество).
     *
     * @throws IllegalArgumentException если массив null или длина не 2 и не 3
     */
    public CyrNameResult(String[] values) {
        this(
                values == null || values.length < 1 ? null : values[0],
                values == null || values.length < 2 ? null : values[1],
                values == null || values.length < 3 ? null : values[2]);
        if (values == null || (values.length != 2 && values.length != 3)) {
            throw new IllegalArgumentException("Name must contain two or three parts.");
        }
    }

    /**
     * ФИО целиком: непустые части через пробел.
     * Пустые/whitespace-части пропускаются; если всех нет — пустая строка.
     */
    public String fullName() {
        return Arrays.stream(new String[]{surname, name, patronymic})
                .filter(v -> v != null && !v.isBlank())
                .collect(Collectors.joining(" "));
    }
    
    @Override
    public String toString() {
        return fullName();
    }
}
