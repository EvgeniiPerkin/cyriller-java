package ru.cyriller;

import ru.cyriller.model.Animates;
import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

/**
 * Таблицы слов числительных по падежам, родам и одушевлённости.
 *
 * <p>Поля — {@code final} (ссылки неизменяемы после конструктора), но сами массивы
 * могут мутироваться на месте (гендерная подстановка {@code numbers[0..1]}).</p>
 */
public class CyrNumberStrings {

    /** Сотни: [сто, двести, …, девятьсот] (9 элементов, индекс = значение/100 - 1). */
    public final String[] hundreds;
    /** Десятки: [десять, двадцать, …, девяносто] (9 элементов, индекс = значение/10 - 1). */
    public final String[] tens;
    /** Единицы и 11..19: [один, два, …, девятнадцать] (19 элементов, индекс = значение - 1). */
    public final String[] numbers;
    /** Тысяча: [one, two, five] (3 элемента, для {@code Case(value, one, two, five)}). */
    public final String[] thousand;
    /** Миллион: [one, two, five]. */
    public final String[] million;
    /** Миллиард: [one, two, five]. */
    public final String[] billion;
    /** Ноль. */
    public final String zero;

    /** Целая часть: [one, two, five]. */
    public final String[] integer;
    /** Десятые (10⁻¹): [one, two, five]. */
    public final String[] decimalTen;
    /** Сотые (10⁻²): [one, two, five]. */
    public final String[] decimalHundred;
    /** Тысячные (10⁻³): [one, two, five]. */
    public final String[] decimalThousand;
    /** Миллионные (10⁻⁶): [one, two, five]. */
    public final String[] decimalMillion;
    /** Миллиардные (10⁻⁹): [one, two, five]. */
    public final String[] decimalBillion;

    /**
     * Конструктор по умолчанию: Nominative, Masculine, Inanimated.
     */
    public CyrNumberStrings() {
        this(Cases.NOMINATIVE, Genders.MASCULINE, Animates.INANIMATED);
    }

    /**
     * Заполняет таблицы для указанного падежа, рода и одушевлённости.
     *
     * @param cases   падеж
     * @param gender  род (влияет на {@code integer}/{@code decimal*} и на {@code numbers[0..1]})
     * @param animate одушевлённость (влияет на {@code numbers} в Accusative и на {@code numbers[1]} в Accusative жен.)
     */
    public CyrNumberStrings(Cases cases, Genders gender, Animates animate) {
        switch (cases) {
            case NOMINATIVE -> {
                this.hundreds = new String[] { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот" };
                this.tens = new String[] { "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто" };
                this.numbers = new String[] { "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" };
                this.thousand = new String[] { "тысяча", "тысячи", "тысяч" };
                this.million = new String[] { "миллион", "миллиона", "миллионов" };
                this.billion = new String[] { "миллиард", "миллиарда", "миллиардов" };
                this.zero = "ноль";

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целая", "целые", "целых" };
                    this.decimalTen = new String[] { "десятая", "десятые", "десятых" };
                    this.decimalHundred = new String[] { "сотая", "сотые", "сотых" };
                    this.decimalThousand = new String[] { "тысячная", "тысячные", "тысячных" };
                    this.decimalMillion = new String[] { "миллионная", "миллионные", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардная", "миллиардные", "миллиардных" };
                } else {
                    this.integer = new String[] { "целый", "целых", "целых" };
                    this.decimalTen = new String[] { "десятый", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотый", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячный", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионный", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардный", "миллиардных", "миллиардных" };
                }
            }
            case GENITIVE -> {
                this.hundreds = new String[] { "ста", "двухсот", "трёхсот", "четырёхсот", "пятисот", "шестисот", "семисот", "восьмисот", "девятисот" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "восьмидесяти", "девяноста" }; // A-1
                this.numbers = new String[] { "одного", "двух", "трёх", "четырёх", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                this.thousand = new String[] { "тысячи", "тысяч", "тысяч" };
                this.million = new String[] { "миллиона", "миллионов", "миллионов" };
                this.billion = new String[] { "миллиарда", "миллиардов", "миллиардов" };
                this.zero = "ноля";

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целой", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятой", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотой", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячной", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионной", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардных", "миллиардных" };
                } else {
                    this.integer = new String[] { "целого", "целых", "целых" };
                    this.decimalTen = new String[] { "десятого", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотого", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячного", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионного", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардного", "миллиардных", "миллиардных" };
                }
            }
            case DATIVE -> {
                this.hundreds = new String[] { "ста", "двумстам", "трёмстам", "четырёмстам", "пятистам", "шестистам", "семистам", "восьмистам", "девятистам" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "восьмидесяти", "девяноста" }; // A-2
                this.numbers = new String[] { "одному", "двум", "трём", "четырём", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                this.thousand = new String[] { "тысяче", "тысячам", "тысячам" };
                this.million = new String[] { "миллиону", "миллионам", "миллионам" };
                this.billion = new String[] { "миллиарду", "миллиардам", "миллиардам" };
                this.zero = "нолю"; // A-6

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целой", "целым", "целым" };
                    this.decimalTen = new String[] { "десятой", "десятым", "десятым" };
                    this.decimalHundred = new String[] { "сотой", "сотым", "сотым" };
                    this.decimalThousand = new String[] { "тысячной", "тысячным", "тысячным" };
                    this.decimalMillion = new String[] { "миллионной", "миллионным", "миллионным" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардным", "миллиардным" };
                } else {
                    this.integer = new String[] { "целому", "целым", "целым" };
                    this.decimalTen = new String[] { "десятому", "десятым", "десятым" };
                    this.decimalHundred = new String[] { "сотому", "сотым", "сотым" };
                    this.decimalThousand = new String[] { "тысячному", "тысячным", "тысячным" };
                    this.decimalMillion = new String[] { "миллионному", "миллионным", "миллионным" };
                    this.decimalBillion = new String[] { "миллиардному", "миллиардным", "миллиардным" };
                }
            }
            case ACCUSATIVE -> {
                this.hundreds = new String[] { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот" };
                this.tens = new String[] { "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто" };

                if (animate == Animates.ANIMATED) {
                    this.numbers = new String[] { "одного", "двух", "трёх", "четырёх", "пять", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" };
                } else {
                    this.numbers = new String[] { "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" };
                }

                this.thousand = new String[] { "тысячу", "тысячи", "тысяч" };
                this.million = new String[] { "миллион", "миллиона", "миллионов" };
                this.billion = new String[] { "миллиард", "миллиарда", "миллиардов" };
                this.zero = "ноль";

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целую", "целые", "целых" };
                    this.decimalTen = new String[] { "десятую", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотую", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячную", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионную", "миллионных", "миллионных" }; // A-3
                    this.decimalBillion = new String[] { "миллиардную", "миллиардных", "миллиардных" };
                } else {
                    this.integer = new String[] { "целого", "целых", "целых" };
                    this.decimalTen = new String[] { "десятый", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотый", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячный", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионный", "миллионных", "миллионных" }; // A-4
                    this.decimalBillion = new String[] { "миллиардный", "миллиардных", "миллиардных" };
                }
            }
            case INSTRUMENTAL -> {
                this.hundreds = new String[] { "ста", "двумястами", "тремястами", "четырьмястами", "пятьюстами", "шестьюстами", "семьюстами", "восьмьюстами", "девятьюстами" };
                this.tens = new String[] { "десятью", "двадцатью", "тридцатью", "сорока", "пятьюдесятью", "шестьюдесятью", "семьюдесятью", "восьмьюдесятью", "девяноста" };
                this.numbers = new String[] { "одним", "двумя", "тремя", "четырьмя", "пятью", "шестью", "семью", "восемью", "девятью", "десятью", "одиннадцатью", "двенадцатью", "тринадцатью", "четырнадцатью", "пятнадцатью", "шестнадцатью", "семнадцатью", "восемнадцатью", "девятнадцатью" };
                this.thousand = new String[] { "тысячей", "тысячами", "тысячами" };
                this.million = new String[] { "миллионом", "миллионами", "миллионами" };
                this.billion = new String[] { "миллиардом", "миллиардами", "миллиардами" };
                this.zero = "нолём";

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целой", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятой", "десятыми", "десятыми" };
                    this.decimalHundred = new String[] { "сотой", "сотыми", "сотыми" };
                    this.decimalThousand = new String[] { "тысячной", "тысячными", "тысячными" };
                    this.decimalMillion = new String[] { "миллионной", "миллионными", "миллионными" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардными", "миллиардными" };
                } else {
                    this.integer = new String[] { "целым", "целыми", "целыми" };
                    this.decimalTen = new String[] { "десятым", "десятыми", "десятыми" };
                    this.decimalHundred = new String[] { "сотым", "сотыми", "сотыми" };
                    this.decimalThousand = new String[] { "тысячным", "тысячными", "тысячными" };
                    this.decimalMillion = new String[] { "миллионным", "миллионными", "миллионными" };
                    this.decimalBillion = new String[] { "миллиардным", "миллиардными", "миллиардными" };
                }
            }
            case PREPOSITIVE -> {
                this.hundreds = new String[] { "ста", "двухстах", "трёхстах", "четырёхстах", "пятистах", "шестистах", "семистах", "восьмистах", "девятистах" };
                this.tens = new String[] { "десяти", "двадцати", "тридцати", "сорока", "пятидесяти", "шестидесяти", "семидесяти", "восьмидесяти", "девяноста" };
                this.numbers = new String[] { "одном", "двух", "трёх", "четырёх", "пяти", "шести", "семи", "восьми", "девяти", "десяти", "одиннадцати", "двенадцати", "тринадцати", "четырнадцати", "пятнадцати", "шестнадцати", "семнадцати", "восемнадцати", "девятнадцати" }; // A-5
                this.thousand = new String[] { "тысяче", "тысячах", "тысячах" };
                this.million = new String[] { "миллионе", "миллионах", "миллионах" };
                this.billion = new String[] { "миллиарде", "миллиардах", "миллиардах" };
                this.zero = "ноле";

                if (gender == Genders.FEMININE) {
                    this.integer = new String[] { "целой", "целых", "целых" };
                    this.decimalTen = new String[] { "десятой", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотой", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячной", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионной", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардной", "миллиардных", "миллиардных" };
                } else {
                    this.integer = new String[] { "целом", "целых", "целых" };
                    this.decimalTen = new String[] { "десятом", "десятых", "десятых" };
                    this.decimalHundred = new String[] { "сотом", "сотых", "сотых" };
                    this.decimalThousand = new String[] { "тысячном", "тысячных", "тысячных" };
                    this.decimalMillion = new String[] { "миллионном", "миллионных", "миллионных" };
                    this.decimalBillion = new String[] { "миллиардном", "миллиардных", "миллиардных" };
                }
            }
            default -> throw new IllegalStateException("unreachable case: " + cases);
        }

        // Гендерная подстановка numbers[0] и numbers[1].
        if (gender == Genders.FEMININE) {
            switch (cases) {
                case NOMINATIVE -> {
                    numbers[0] = "одна";
                    numbers[1] = "две";
                }
                case GENITIVE -> {
                    numbers[0] = "одной";
                    numbers[1] = "двух";
                }
                case DATIVE -> {
                    numbers[0] = "одной";
                    numbers[1] = "двум";
                }
                case ACCUSATIVE -> {
                    numbers[0] = "одну";
                    numbers[1] = (animate == Animates.ANIMATED) ? "двух" : "две";
                }
                case INSTRUMENTAL -> {
                    numbers[0] = "одной";
                    numbers[1] = "двумя";
                }
                case PREPOSITIVE -> {
                    numbers[0] = "одной";
                    numbers[1] = "двух";
                }
            }
        } else if (gender == Genders.NEUTER) {
            switch (cases) {
                case NOMINATIVE -> numbers[0] = "одно";
                case GENITIVE -> numbers[0] = "одного";
                case DATIVE -> numbers[0] = "одному";
                case ACCUSATIVE -> numbers[0] = "одно";
                case INSTRUMENTAL -> numbers[0] = "одним";
                case PREPOSITIVE -> numbers[0] = "одном";
            }
        }
    }
}
