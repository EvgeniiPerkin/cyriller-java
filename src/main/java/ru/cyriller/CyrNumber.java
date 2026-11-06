package ru.cyriller;

import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Числительные прописью: целые и дробные числа, деньги, количества предметов.
 */
public class CyrNumber {

    /** Максимально большое число для написания прописью. */
    public static final long MAX_VALUE = 9999999999L;

    /** Порог для дробных путей — целая часть больше MAX_VALUE ⇔ value >= 10^10. */
    private static final BigDecimal MAX_VALUE_PLUS_ONE = BigDecimal.valueOf(MAX_VALUE + 1);

    // ------------------------------------------------------------------
    // Decline
    // ------------------------------------------------------------------

    /**
     * Склоняет число прописью в мужском роде для неодушевлённых предметов.
     */
    public CyrResult decline(long value) {
        return decline(value, Genders.MASCULINE, Animates.INANIMATED);
    }

    /**
     * Склоняет число прописью в указанном роде и одушевлённости.
     */
    public CyrResult decline(long value, Genders gender, Animates animate) {
        return new CyrResult(
                toString(value, Cases.NOMINATIVE, gender, animate),
                toString(value, Cases.GENITIVE, gender, animate),
                toString(value, Cases.DATIVE, gender, animate),
                toString(value, Cases.ACCUSATIVE, gender, animate),
                toString(value, Cases.INSTRUMENTAL, gender, animate),
                toString(value, Cases.PREPOSITIVE, gender, animate));
    }

    /**
     * Склоняет дробное число прописью в мужском роде для неодушевлённых предметов.
     */
    public CyrResult decline(BigDecimal value) {
        return decline(value, Genders.MASCULINE, Animates.INANIMATED);
    }

    /**
     * Склоняет дробное число прописью в указанном роде и одушевлённости.
     */
    public CyrResult decline(BigDecimal value, Genders gender, Animates animate) {
        return new CyrResult(
                toString(value, Cases.NOMINATIVE, gender, animate),
                toString(value, Cases.GENITIVE, gender, animate),
                toString(value, Cases.DATIVE, gender, animate),
                toString(value, Cases.ACCUSATIVE, gender, animate),
                toString(value, Cases.INSTRUMENTAL, gender, animate),
                toString(value, Cases.PREPOSITIVE, gender, animate));
    }

    /**
     * Склоняет денежную сумму в указанной валюте.
     */
    public CyrResult decline(BigDecimal value, Currency currency) {
        return new CyrResult(
                toString(value, Cases.NOMINATIVE, currency),
                toString(value, Cases.GENITIVE, currency),
                toString(value, Cases.DATIVE, currency),
                toString(value, Cases.ACCUSATIVE, currency),
                toString(value, Cases.INSTRUMENTAL, currency),
                toString(value, Cases.PREPOSITIVE, currency));
    }

    /**
     * Склоняет количество указанных единиц.
     */
    public CyrResult decline(BigDecimal value, Item item) {
        return new CyrResult(
                toString(value, Cases.NOMINATIVE, item),
                toString(value, Cases.GENITIVE, item),
                toString(value, Cases.DATIVE, item),
                toString(value, Cases.ACCUSATIVE, item),
                toString(value, Cases.INSTRUMENTAL, item),
                toString(value, Cases.PREPOSITIVE, item));
    }

    // ------------------------------------------------------------------
    // ToString
    // ------------------------------------------------------------------

    /**
     * Склоняет число прописью в указанный падеж мужского рода для неодушевлённых предметов.
     */
    public String toString(long value, Cases c) {
        return toString(value, c, Genders.MASCULINE, Animates.INANIMATED);
    }

    /**
     * Склоняет дробное число прописью в указанный падеж мужского рода
     * для неодушевлённых предметов.
     */
    public String toString(BigDecimal value, Cases c) {
        return toString(value, c, Genders.MASCULINE, Animates.INANIMATED);
    }

    /**
     * Склоняет дробное число прописью в указанный падеж, род и одушевлённость.
     */
    public String toString(BigDecimal value, Cases c, Genders gender, Animates animate) {
        checkDecimalValue(value);
        String str = value.toPlainString();
        String[] parts = str.split("\\.");
        long i = Long.parseLong(parts[0]);
        StringBuilder sb = new StringBuilder();
        CyrNumberStrings s = new CyrNumberStrings(c, gender, animate);

        sb.append(toString(i, c, gender, animate));

        if (parts.length > 1) {
            parts[1] = trimTrailingZeros(parts[1]);
        }

        if (parts.length > 1 && !parts[1].isEmpty()) {
            String[] decimals;
            long d = Long.parseLong(parts[1]);
            long dr = Long.parseLong(new StringBuilder(parts[1]).reverse().toString());

            if (dr < 10) {
                decimals = s.decimalTen;
            } else if (dr < 100) {
                decimals = s.decimalHundred;
            } else if (dr < 1000) {
                decimals = s.decimalThousand;
            } else if (dr < 1000000) {
                decimals = s.decimalMillion;
            } else {
                decimals = s.decimalBillion;
            }

            sb.append(" ");
            sb.append(caseForm(i, s.integer[0], s.integer[1], s.integer[2]));
            sb.append(" и ");
            sb.append(toString(d, c, gender, animate));
            sb.append(" ");
            sb.append(caseForm(d, decimals[0], decimals[1], decimals[2]));
        }

        return sb.toString();
    }

    /**
     * Склоняет денежную сумму в указанный падеж и валюту.
     */
    public String toString(BigDecimal value, Cases c, Currency currency) {
        checkDecimalValue(value);
        String str = value.toPlainString();
        String[] parts = str.split("\\.");
        long i = Long.parseLong(parts[0]);
        StringBuilder sb = new StringBuilder();
        String[] iname = currency.getIntegerName(c);
        String[] dname = currency.getDecimalName(c);

        sb.append(toString(i, c, currency.integerGender, Animates.INANIMATED));
        sb.append(" ").append(caseForm(i, iname[0], iname[1], iname[2]));

        if (parts.length > 1) {
            parts[1] = trimTrailingZeros(parts[1]);
        }

        if (parts.length > 1 && !parts[1].isEmpty()) {
            String v = parts[1];

            while (v.length() < currency.decimals) {
                v = v + "0";
            }

            if (v.length() > currency.decimals) {
                v = v.substring(0, currency.decimals) + "." + v.substring(currency.decimals);
            }

            // C#: (long)Math.Round(decimal.Parse(v)) — округление «от нуля» (HALF_UP для v >= 0).
            long d = new BigDecimal(v).setScale(0, RoundingMode.HALF_UP).longValue();

            sb.append(" и ");
            sb.append(toString(d, c, currency.decimalGender, Animates.INANIMATED));
            sb.append(" ");
            sb.append(caseForm(d, dname[0], dname[1], dname[2]));
        }

        return sb.toString();
    }

    /**
     * Склоняет количество указанных единиц в указанный падеж.
     */
    public String toString(BigDecimal value, Cases c, Item item) {
        long i = value.longValue();
        StringBuilder sb = new StringBuilder();
        Genders gender = BigDecimal.valueOf(i).compareTo(value) < 0 ? Genders.FEMININE : item.gender();
        Animates animate = BigDecimal.valueOf(i).compareTo(value) == 0 && i < 20 ? item.animate() : Animates.INANIMATED;
        String[] name;

        sb.append(toString(value, c, gender, animate)).append(" ");

        if (BigDecimal.valueOf(i).compareTo(value) < 0) {
            name = item.getName(Cases.NOMINATIVE, i);
            sb.append(name[1]);
        } else {
            name = item.getName(c, i);
            sb.append(caseForm(i, name[0], name[1], name[2]));
        }

        return sb.toString();
    }

    /**
     * Склоняет число прописью в указанный падеж, род и одушевлённость.
     *
     * @throws IllegalArgumentException если значение больше максимально допустимого {@link #MAX_VALUE}
     */
    public String toString(long value, Cases c, Genders gender, Animates animate) {
        if (value > MAX_VALUE) {
            throw new IllegalArgumentException("The maximum value is " + MAX_VALUE + ".");
        }

        CyrNumberStrings s = new CyrNumberStrings(c, gender, animate);

        if (value == 0) {
            return s.zero;
        }

        StringBuilder r = new StringBuilder();
        long v;

        if (value < 0) {
            r.append("минус ");
            value = Math.abs(value);
        }

        v = value / 1000000000L;
        if (v > 0) {
            r.append(toString(v, c, Genders.MASCULINE, animate)).append(" ")
                    .append(caseForm(v, s.billion[0], s.billion[1], s.billion[2])).append(" ");
            value = value - 1000000000L * v;
        }

        v = value / 1000000L;
        if (v > 0) {
            r.append(toString(v, c, Genders.MASCULINE, animate)).append(" ")
                    .append(caseForm(v, s.million[0], s.million[1], s.million[2])).append(" ");
            value = value - 1000000L * v;
        }

        v = value / 1000L;
        if (v > 0) {
            r.append(toString(v, c, Genders.FEMININE, animate)).append(" ")
                    .append(caseForm(v, s.thousand[0], s.thousand[1], s.thousand[2])).append(" ");
            value = value - 1000L * v;
        }

        v = value / 100;
        if (v > 0) {
            r.append(s.hundreds[(int) v - 1]).append(" ");
            value = value - 100 * v;
        }

        if (value >= 20 || value == 10) {
            v = value / 10;
            r.append(s.tens[(int) v - 1]).append(" ");
            value = value - v * 10;
        }

        if (value > 0) {
            r.append(s.numbers[(int) value - 1]);
        }

        return r.toString().trim();
    }

    // ------------------------------------------------------------------
    // Вспомогательные
    // ------------------------------------------------------------------

    /**
     * Выбирает правильный вариант слова в зависимости от указанного числа (правило 1/2/5).
     *
     * @param value число для выбора правильного варианта слова
     * @param one   вариант для употребления с числительным один
     * @param two   вариант для употребления с числительными два, три, четыре
     * @param five  вариант для употребления с числительными больше четырех
     */
    public String caseForm(long value, String one, String two, String five) {
        long t = (value % 100 > 20) ? value % 10 : value % 20;

        // t ∈ [0, 19]; каст в int: в Java 25 switch по long — preview-фича
        // (primitive patterns, JEP 497), switch по int — стандарт.
        return switch ((int) t) {
            case 1 -> one;
            case 2, 3, 4 -> two;
            default -> five;
        };
    }

    private static void checkDecimalValue(BigDecimal value) {
        if (value.compareTo(MAX_VALUE_PLUS_ONE) >= 0) {
            throw new IllegalArgumentException("The maximum value is " + MAX_VALUE + ".");
        }
    }

    private static String trimTrailingZeros(String s) {
        int end = s.length();
        while (end > 0 && s.charAt(end - 1) == '0') {
            end--;
        }
        return s.substring(0, end);
    }
}
