package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Китайский юань (целая часть — «юань», дробная — «цзяо»).
 */
public class Yuan extends Currency {

    public Yuan() {
        super("Китайский юань (CNY) ¥", Genders.MASCULINE, Genders.MASCULINE, 1);
    }

    @Override
    public String[] getIntegerName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE -> new String[] { "юань", "юаня", "юаней" };
            case GENITIVE -> new String[] { "юаня", "юаней", "юаней" };
            case DATIVE -> new String[] { "юаню", "юаням", "юаням" };
            case ACCUSATIVE -> new String[] { "юань", "юаня", "юаней" };
            case INSTRUMENTAL -> new String[] { "юанем", "юанями", "юанями" };
            case PREPOSITIVE -> new String[] { "юане", "юанях", "юанях" };
        };
    }

    @Override
    public String[] getDecimalName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE, GENITIVE, DATIVE, ACCUSATIVE, INSTRUMENTAL, PREPOSITIVE
                    -> new String[] { "цзяо", "цзяо", "цзяо" };
        };
    }
}
