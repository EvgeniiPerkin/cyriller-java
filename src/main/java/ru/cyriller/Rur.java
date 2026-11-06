package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Российский рубль (целая часть — «рубль», дробная — «копейка»).
 */
public class Rur extends Currency {

    public Rur() {
        super("Российский рубль (руб) ₽", Genders.MASCULINE, Genders.FEMININE, 2);
    }

    @Override
    public String[] getIntegerName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE -> new String[] { "рубль", "рубля", "рублей" };
            case GENITIVE -> new String[] { "рубля", "рублей", "рублей" };
            case DATIVE -> new String[] { "рублю", "рублям", "рублям" };
            case ACCUSATIVE -> new String[] { "рубль", "рубля", "рублей" };
            case INSTRUMENTAL -> new String[] { "рублем", "рублями", "рублями" };
            case PREPOSITIVE -> new String[] { "рубле", "рублях", "рублях" };
        };
    }

    @Override
    public String[] getDecimalName(Cases cases) {
        return switch (cases) {
            case NOMINATIVE -> new String[] { "копейка", "копейки", "копеек" };
            case GENITIVE -> new String[] { "копейки", "копеек", "копеек" };
            case DATIVE -> new String[] { "копейке", "копейкам", "копейкам" };
            case ACCUSATIVE -> new String[] { "копейку", "копейки", "копеек" };
            case INSTRUMENTAL -> new String[] { "копейкой", "копейками", "копейками" };
            case PREPOSITIVE -> new String[] { "копейке", "копейках", "копейках" };
        };
    }
}
