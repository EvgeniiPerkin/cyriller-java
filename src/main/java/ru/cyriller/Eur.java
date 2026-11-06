package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Евро (целая часть — «евро», дробная — «цент»).
 */
public class Eur extends Currency {

    public Eur() {
        super("Евро (EUR) €", Genders.NEUTER, Genders.MASCULINE, 2);
    }

    @Override
    public String[] getIntegerName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE, GENITIVE, DATIVE, ACCUSATIVE, INSTRUMENTAL, PREPOSITIVE
                    -> new String[] { "евро", "евро", "евро" };
        };
    }

    @Override
    public String[] getDecimalName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE -> new String[] { "цент", "цента", "центов" };
            case GENITIVE -> new String[] { "цента", "центов", "центов" };
            case DATIVE -> new String[] { "центу", "центам", "центам" };
            case ACCUSATIVE -> new String[] { "цент", "цента", "центов" };
            case INSTRUMENTAL -> new String[] { "центом", "центами", "центами" };
            case PREPOSITIVE -> new String[] { "центе", "центах", "центах" };
        };
    }
}
