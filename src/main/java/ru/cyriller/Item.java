package ru.cyriller;

import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Склонение существительного (ед. ч. + мн. ч.) для числительных: обёртка над {@link CyrNoun}.
 */
public class Item {

    protected final CyrResult singular;
    protected final CyrResult plural;
    protected final CyrNoun noun;

    public Item(CyrNoun noun) {
        this.noun = noun;
        this.singular = noun.decline();
        this.plural = noun.declinePlural();
    }

    /**
     * 3 формы {@code [ед. ч. (1), 2–5, мн. ч. (5+)]}
     * для падежа. Диспетч по роду: женский → {@link #getFeminine}, средний →
     * {@link #getNeuter}, иначе (мужской и {@link Genders#UNDEFINED}) →
     * {@link #getMasculine(Cases, long)}.
     */
    public String[] getName(Cases c, long value) {
        if (this.gender() == Genders.FEMININE) {
            return getFeminine(c);
        } else if (this.gender() == Genders.NEUTER) {
            return getNeuter(c);
        } else {
            return getMasculine(c, value);
        }
    }

    public Genders gender() {
        return noun.gender();
    }

    public Animates animate() {
        return noun.animate();
    }

    /**
     * Мужской род. Одушевлённые с {@code value < 20} — винительный = родительный
     * (во всех числах); иначе — винительный = именительный (ед. ч.).
     */
    protected String[] getMasculine(Cases c, long value) {
        if (this.animate() == Animates.ANIMATED && value < 20) {
            return switch (c) {
                case NOMINATIVE -> new String[]{singular.get(1), singular.get(2), plural.get(2)};
                case GENITIVE -> new String[]{singular.get(2), plural.get(2), plural.get(2)};
                case DATIVE -> new String[]{singular.get(3), plural.get(3), plural.get(3)};
                case ACCUSATIVE -> new String[]{singular.get(4), plural.get(4), plural.get(4)};
                case INSTRUMENTAL -> new String[]{singular.get(5), plural.get(5), plural.get(5)};
                case PREPOSITIVE -> new String[]{singular.get(6), plural.get(6), plural.get(6)};
            };
        } else {
            boolean isGod = noun.name().equalsIgnoreCase("год"); // C-7
            return switch (c) {
                case NOMINATIVE -> isGod
                        ? new String[]{singular.get(1), singular.get(2), "лет"}
                        : new String[]{singular.get(1), singular.get(2), plural.get(2)};
                case GENITIVE -> isGod
                        ? new String[]{singular.get(2), plural.get(2), "лет"}
                        : new String[]{singular.get(2), plural.get(2), plural.get(2)};
                case DATIVE -> new String[]{singular.get(3), plural.get(3), plural.get(3)};
                case ACCUSATIVE -> isGod
                        ? new String[]{singular.get(4), singular.get(4), "лет"}
                        : new String[]{singular.get(1), singular.get(2), plural.get(2)};
                case INSTRUMENTAL -> new String[]{singular.get(5), plural.get(5), plural.get(5)};
                case PREPOSITIVE -> new String[]{singular.get(6), plural.get(6), plural.get(6)};
            };
        }
    }

    /** Женский род: винительный ед. ч. = винительный, 2–5 = родительный ед. ч. */
    protected String[] getFeminine(Cases c) {
        return switch (c) {
            case NOMINATIVE -> new String[]{singular.get(1), singular.get(2), plural.get(2)};
            case GENITIVE -> new String[]{singular.get(2), plural.get(2), plural.get(2)};
            case DATIVE -> new String[]{singular.get(3), plural.get(3), plural.get(3)};
            case ACCUSATIVE -> new String[]{singular.get(4), singular.get(2), plural.get(4)};
            case INSTRUMENTAL -> new String[]{singular.get(5), plural.get(5), plural.get(5)};
            case PREPOSITIVE -> new String[]{singular.get(6), plural.get(6), plural.get(6)};
        };
    }

    /** Средний род: винительный = именительный (ед. ч.), 2–5 = родительный ед. ч. */
    protected String[] getNeuter(Cases c) {
        return switch (c) {
            case NOMINATIVE -> new String[]{singular.get(1), singular.get(2), plural.get(2)};
            case GENITIVE -> new String[]{singular.get(2), plural.get(2), plural.get(2)};
            case DATIVE -> new String[]{singular.get(3), plural.get(3), plural.get(3)};
            case ACCUSATIVE -> new String[]{singular.get(4), singular.get(2), plural.get(2)};
            case INSTRUMENTAL -> new String[]{singular.get(5), plural.get(5), plural.get(5)};
            case PREPOSITIVE -> new String[]{singular.get(6), plural.get(6), plural.get(6)};
        };
    }
}
