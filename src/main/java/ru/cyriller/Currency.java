package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Абстрактная валюта: имя, роды целой/дробной частей, число знаков дробной части
 * и падежные формы названий (целая и дробная части).
 *
 * <p>Каждый метод возвращает массив из 3 форм: {@code [ед.ч. (1), 2–5, мн.ч. (5+)]}. 
 * Выбор формы по числу делает {@code CyrNumber} (этап 9).</p>
 */
public abstract class Currency {

    /** Полное имя валюты (напр. «Российский рубль (руб) ₽»). */
    public final String name;
    /** Род целой части (напр. MASCULINE для рубля). */
    public final Genders integerGender;
    /** Род дробной части (напр. FEMININE для копейки). */
    public final Genders decimalGender;
    /** Число знаков дробной части (2 для рубля/доллара/евро, 1 для юаня). */
    public final int decimals;

    protected Currency(String name, Genders integerGender, Genders decimalGender, int decimals) {
        this.name = name;
        this.integerGender = integerGender;
        this.decimalGender = decimalGender;
        this.decimals = decimals;
    }

    /**
     * Падежные формы названия целой части: {@code [ед.ч. (1), 2–5, мн.ч. (5+)]}.
     *
     * @param cases падеж
     * @return массив из 3 форм
     */
    public abstract String[] getIntegerName(Cases cases);

    /**
     * Падежные формы названия дробной части: {@code [ед.ч. (1), 2–5, мн.ч. (5+)]}.
     *
     * @param cases падеж
     * @return массив из 3 форм
     */
    public abstract String[] getDecimalName(Cases cases);
}
