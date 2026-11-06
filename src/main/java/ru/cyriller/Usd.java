package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Американский доллар (целая часть — «доллар», дробная — «цент»).
 */
public class Usd extends Currency {

    public Usd() {
        super("Американский доллар (USD) $", Genders.MASCULINE, Genders.MASCULINE, 2);
    }

    @Override
    public String[] getIntegerName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE -> new String[] { "доллар", "доллара", "долларов" };
            case GENITIVE -> new String[] { "доллара", "долларов", "долларов" };
            case DATIVE -> new String[] { "доллару", "долларам", "долларам" };
            case ACCUSATIVE -> new String[] { "доллар", "доллара", "долларов" };
            case INSTRUMENTAL -> new String[] { "долларов", "долларами", "долларами" };
            case PREPOSITIVE -> new String[] { "долларе", "долларах", "долларах" };
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
