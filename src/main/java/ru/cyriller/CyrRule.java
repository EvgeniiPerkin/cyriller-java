package ru.cyriller;

import java.util.regex.Pattern;

/**
 * Правило склонения: суффикс ({@code end}) + количество отрезаемых букв ({@code cut}).
 *
 * <p>Особые случаи (воспроизведены как есть):</p>
 * <ul>
 *   <li>null/пустое правило → {@code end=""}, {@code cut=0} (слово не меняется);</li>
 *   <li>{@code end == "*"} → {@link #apply(String)} возвращает пустую строку;</li>
 *   <li>если {@code cut} больше длины слова — {@link StringIndexOutOfBoundsException}.</li>
 * </ul>
 */
public class CyrRule {

    private static final Pattern DIGIT = Pattern.compile("\\d");

    private final String end;
    private final int cut;

    public CyrRule(String rule) {
        if (rule == null || rule.isEmpty()) {
            this.end = "";
            this.cut = 0;
            return;
        }

        this.end = DIGIT.matcher(rule).replaceAll("");

        String temp = this.end.length() > 0 ? rule.replace(this.end, "") : rule;

        this.cut = temp.isEmpty() ? 0 : Integer.parseInt(temp);
    }

    /**
     * Применяет правило к слову: отрезает {@code cut} букв от конца и добавляет {@code end}.
     */
    public String apply(String name) {
        if (this.end.equals("*")) {
            return "";
        }
        return name.substring(0, name.length() - cut) + end;
    }
}
