
package ru.cyriller;

import ru.cyriller.model.Animates;
import ru.cyriller.model.Genders;
import ru.cyriller.model.WordTypes;

/**
 * Существительное со 11 правилами склонения (5 единственного + 6 множественного числа).
 *
 * <p>Порядок правил: {@code rules[0..4]} — родительный, дательный, винительный,
 * творительный, предложный (ед. ч.); {@code rules[5..10]} — именительный, родительный,
 * дательный, винительный, творительный, предложный (мн. ч.). Именительный ед. ч.
 * — само слово ({@link #name()}), правилу не подвергается.</p>
 */
public class CyrNoun {

    private final Genders gender;
    private final Animates animate;
    private final WordTypes type;
    private final String name;
    private final String collectionName;
    private final CyrRule[] rules;

    /** {@code collectionName} = {@code name}. */
    public CyrNoun(String name, Genders gender, Animates animate, WordTypes type, CyrRule[] rules) {
        this(name, name, gender, animate, type, rules);
    }

    public CyrNoun(String name, String collectionName, Genders gender,
                   Animates animate, WordTypes type, CyrRule[] rules) {
        this.name = name;
        this.collectionName = collectionName;
        this.gender = gender;
        this.animate = animate;
        this.type = type;
        this.rules = rules;
    }

    public boolean isAnimated() {
        return animate == Animates.ANIMATED;
    }

    public String name() {
        return name;
    }

    public String collectionName() {
        return collectionName;
    }

    public boolean exactMatch() {
        return name.equals(collectionName);
    }

    public Genders gender() {
        return gender;
    }

    public Animates animate() {
        return animate;
    }

    public WordTypes wordType() {
        return type;
    }

    /**
     * Склонение в единственном числе: именительный = само слово,
     * остальные падежи — {@code rules[0..4]}.
     */
    public CyrResult decline() {
        return new CyrResult(name,
                rules[0].apply(name),
                rules[1].apply(name),
                rules[2].apply(name),
                rules[3].apply(name),
                rules[4].apply(name));
    }

    /**
     * Склонение во множественном числе: {@code rules[5..10]}.
     */
    public CyrResult declinePlural() {
        return new CyrResult(
                rules[5].apply(name),
                rules[6].apply(name),
                rules[7].apply(name),
                rules[8].apply(name),
                rules[9].apply(name),
                rules[10].apply(name));
    }
}
