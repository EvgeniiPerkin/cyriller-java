package ru.cyriller;

import ru.cyriller.model.Cases;
import ru.cyriller.model.Genders;

import java.util.regex.Pattern;
import java.util.function.IntFunction;

/**
 * Склонение полных имён (фамилия, имя, отчество) по падежам.
 *
 * <p>Порт C# {@code CyrName} (2273 строки). Реализует склонение фамилий, имён
 * и отчеств в 6 падежах русского языка, включая обработку составных фамилий
 * (через дефис) и тюркских отчеств (Салим-оглы, Ибн-Сина).</p>
 *
 * <h2>Публичный API</h2>
 * <ul>
 *   <li>{@link #decline(String, String, String, Cases, Genders, boolean)} — одно ФИО, один падеж → {@link CyrNameResult}</li>
 *   <li>{@link #decline(String, Cases, Genders, boolean)} — полное имя строкой, один падеж → {@link CyrNameResult}</li>
 *   <li>{@link #decline(String, String, String, Genders, boolean)} — одно ФИО, все падежи → {@link CyrResult}</li>
 *   <li>{@link #decline(String, Genders, boolean)} — полное имя строкой, все падежи → {@link CyrResult}</li>
 *   <li>{@link #splitPatronymic(String)} — разбор тюркского отчества</li>
 * </ul>
 * 
 * <h2>Возможные баги</h2>
 * <ul>
 *   <li><b>C-6:</b> {@code DeclineSurname} — проверка {@code surname.Length <= 1}
 *       до switch, но {@code DeclineSurnameGenitive} и др. не имеют этой защиты.</li>
 *   <li><b>D-1:</b> {@code DeclineNameGenitive} — {@code SubstringRight(name, 3).ToLower()}
 *       для 3-буквенного суффикса, но {@code SubstringRight(name, 2)} и
 *       {@code SubstringRight(name, 1)} — без {@code ToLower()}. Несогласованность
 *       регистров.</li>
 *   <li><b>D-2:</b> {@code DeclinePatronymicInstrumental} — проверка
 *       {@code patronymic.ToLower() == "ильич"} для выбора суффикса "ом"/"ем".</li>
 *   <li><b>D-3:</b> {@code DeclineSurnameGenitive} — в мужском роде для суффикса
 *       "ий"/"ый"/"ой" проверяется {@code surname.Length > 4}, но в женском роде
 *       для "ая"/"яя" — нет.</li>
 *   <li><b>D-4:</b> {@code DeclineSurnameInstrumental} — в мужском роде для
 *       "ин"/"ын"/"ен"/"эн"/"ов"/"ев"/"ёв"/"ун" исключаются конкретные фамилии
 *       (дарвин, франклин, чаплин, грин) через {@code ToLower()}.</li>
 * </ul>
 * 
 */
public class CyrName {

    // ==================== Регулярные выражения ====================

    /** Тюркские суффиксы отчеств. */
    private static final Pattern PATRONYMIC_SUFFIX_REGEX =
            Pattern.compile("([- ](((оглы)|(улы)|(уулу))|([кг]ызы)))$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    /** Тюркский префикс отчеств. */
    private static final Pattern PATRONYMIC_PREFIX_REGEX =
            Pattern.compile("^(ибн[- ])", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    // ==================== Публичный API: один падеж ====================

    /**
     * Склоняет ФИО в указанный падеж.
     *
     * @param surname    фамилия (именительный падеж)
     * @param name       имя (именительный падеж)
     * @param patronymic отчество (именительный падеж)
     * @param c          падеж
     * @param g          род (UNDEFINED = автоопределение)
     * @param shorten    сокращать ли имя/отчество до инициала
     * @return результат склонения
     */
    public CyrNameResult decline(String surname, String name, String patronymic,
                                 Cases c, Genders g, boolean shorten) {
        String[] values = decline(surname, name, patronymic, c.index, g.ordinal(), shorten);
        return new CyrNameResult(values);
    }

    /**
     * Склоняет полное имя (строкой) в указанный падеж.
     *
     * @param fullName полное имя (фамилия имя отчество)
     * @param c        падеж
     * @param g        род (UNDEFINED = автоопределение)
     * @param shorten  сокращать ли имя/отчество до инициала
     * @return результат склонения
     */
    public CyrNameResult decline(String fullName, Cases c, Genders g, boolean shorten) {
        String[] values = decline(fullName, c.index, g.ordinal(), shorten);
        return new CyrNameResult(values);
    }

    // ==================== Публичный API: все падежи ====================

    /**
     * Склоняет ФИО во все 6 падежей.
     *
     * @param surname    фамилия (именительный падеж)
     * @param name       имя (именительный падеж)
     * @param patronymic отчество (именительный падеж)
     * @param g          род (UNDEFINED = автоопределение)
     * @param shorten    сокращать ли имя/отчество до инициала
     * @return результат склонения во все падежи
     */
    public CyrResult decline(String surname, String name, String patronymic,
                             Genders g, boolean shorten) {
        return declinePerCaseAndCombine(caseIndex ->
                decline(surname, name, patronymic, caseIndex, g.ordinal(), shorten));
    }

    /**
     * Склоняет полное имя (строкой) во все 6 падежей.
     *
     * @param fullName полное имя (фамилия имя отчество)
     * @param g        род (UNDEFINED = автоопределение)
     * @param shorten  сокращать ли имя/отчество до инициала
     * @return результат склонения во все падежи
     */
    public CyrResult decline(String fullName, Genders g, boolean shorten) {
        return declinePerCaseAndCombine(caseIndex ->
                decline(fullName, caseIndex, g.ordinal(), shorten));
    }

    // ==================== Основная реализация ====================

    /**
     * Основная реализация склонения ФИО в указанный падеж.
     *
     * <p>Обработка: ProperCase, SplitPatronymic, составные фамилии (дефис),
     * автоопределение рода, склонение фамилии/имени/отчества.</p>
     *
     * @param surname    фамилия (именительный падеж)
     * @param name       имя (именительный падеж)
     * @param patronymic отчество (именительный падеж)
     * @param caseIndex  1-based номер падежа (1..6)
     * @param gender     0=авто, 1=мужской, 2=женский
     * @param shorten    сокращать ли имя/отчество до инициала
     * @return массив [фамилия, имя, отчество]
     */
    public String[] decline(String surname, String name, String patronymic,
                            int caseIndex, int gender, boolean shorten) {
        String temp = null;
        int caseNumber = caseIndex;
        String surnameResult = null;
        String nameResult = null;
        String patronymicResult = null;
        String patronymicAfter = null;
        String patronymicBefore = null;
        boolean declinePatronymic = true;
        boolean isFeminine = false;
        int index = 0;
        String surnameNew = null;
        String surnameOld = null;

        surnameResult = properCase(surname);
        nameResult = properCase(name);

        SplitPatronymicResult sp = splitPatronymic(patronymic);
        patronymicBefore = sp.prefix();
        patronymicResult = sp.patronymic();
        patronymicAfter = sp.suffix();
        declinePatronymic = patronymicAfter == null || patronymicAfter.isEmpty();
        patronymicResult = properCase(patronymicResult);

        if (caseNumber < 1 || caseNumber > 6) {
            caseNumber = 1;
        }

        if (gender < 0 || gender > 2) {
            gender = 0;
        }

        if (gender == 0) {
            gender = patronymicResult.endsWith("на") ? 2 : 1;
        }

        isFeminine = (gender == 2);

        // Составные фамилии (через дефис)
        surnameOld = surnameResult;
        surnameNew = "";
        index = surnameOld.indexOf("-");

        while (index > 0) {
            temp = properCase(surnameOld.substring(0, index));
            surnameNew = surnameNew + declineSurname(temp, caseNumber, isFeminine) + "-";
            surnameOld = surnameOld.substring(index + 1);
            index = surnameOld.indexOf("-");
        }

        temp = properCase(surnameOld);
        surnameNew = surnameNew + declineSurname(temp, caseNumber, isFeminine);
        surnameResult = surnameNew;

        switch (caseNumber) {
            case 1:
                if (shorten) {
                    nameResult = shorten(nameResult);
                    patronymicResult = shorten(patronymicResult);
                }
                break;
            case 2:
                nameResult = declineNameGenitive(nameResult, isFeminine, shorten);
                if (declinePatronymic || shorten) {
                    patronymicResult = declinePatronymicGenitive(patronymicResult, isFeminine, shorten);
                }
                break;
            case 3:
                nameResult = declineNameDative(nameResult, isFeminine, shorten);
                if (declinePatronymic || shorten) {
                    patronymicResult = declinePatronymicDative(patronymicResult, isFeminine, shorten);
                }
                break;
            case 4:
                nameResult = declineNameAccusative(nameResult, isFeminine, shorten);
                if (declinePatronymic || shorten) {
                    patronymicResult = declinePatronymicAccusative(patronymicResult, isFeminine, shorten);
                }
                break;
            case 5:
                nameResult = declineNameInstrumental(nameResult, isFeminine, shorten);
                if (declinePatronymic || shorten) {
                    patronymicResult = declinePatronymicInstrumental(patronymicResult, isFeminine, shorten);
                }
                break;
            case 6:
                nameResult = declineNamePrepositional(nameResult, isFeminine, shorten);
                if (declinePatronymic || shorten) {
                    patronymicResult = declinePatronymicPrepositional(patronymicResult, isFeminine, shorten);
                }
                break;
            default:
                break;
        }

        if (!shorten) {
            patronymicResult = (patronymicBefore != null ? patronymicBefore : "")
                    + patronymicResult
                    + (patronymicAfter != null ? patronymicAfter : "");
        }

        return new String[]{surnameResult, nameResult, patronymicResult};
    }

    /**
     * Разбирает полное имя (строкой) на фамилию, имя и отчество,
     * затем вызывает основную реализацию.
     *
     * <p>Правила разбора: если 3 части и средняя заканчивается на
     * "ич"/"вна"/"чна" — это отчество (порядок: имя фамилия отчество),
     * иначе — фамилия имя отчество. Если 2 части и вторая заканчивается
     * на "ич"/"вна"/"чна" — это отчество (порядок: имя отчество).</p>
     *
     * @param fullName полное имя
     * @param caseIndex 1-based номер падежа
     * @param gender   0=авто, 1=мужской, 2=женский
     * @param shorten  сокращать ли
     * @return массив [фамилия, имя, отчество]
     */
    public String[] decline(String fullName, int caseIndex, int gender, boolean shorten) {
        String surname = null;
        String name = null;
        String patronymic = null;
        String str1 = null;
        String str2 = null;
        String str3 = null;
        int spaceIndex = 0;

        spaceIndex = fullName.indexOf(" ");

        if (spaceIndex > 0) {
            str1 = fullName.substring(0, spaceIndex).trim();
            fullName = fullName.substring(spaceIndex).trim();

            spaceIndex = fullName.indexOf(" ");

            if (spaceIndex > 0) {
                str2 = fullName.substring(0, spaceIndex).trim();
                str3 = fullName.substring(spaceIndex).trim();
            } else {
                str2 = fullName.trim();
            }
        } else {
            str1 = fullName.trim();
        }

        if (str3 != null && !str3.isEmpty()) {
            if (str2.endsWith("ич") || str2.endsWith("вна") || str2.endsWith("чна")) {
                surname = str3;
                name = str1;
                patronymic = str2;
            } else {
                surname = str1;
                name = str2;
                patronymic = str3;
            }
        } else if (str2 != null && !str2.isEmpty()) {
            if (str2.endsWith("ич") || str2.endsWith("вна") || str2.endsWith("чна")) {
                name = str1;
                patronymic = str2;
            } else {
                surname = str1;
                name = str2;
            }
        } else {
            surname = str1;
        }

        return decline(surname, name, patronymic, caseIndex, gender, shorten);
    }

    // ==================== Склонение имён ====================

    /**
     * Родительный падеж имени.
     *
     * <p><b>D-1:</b> {@code substringRight(name, 3).toLowerCase()} для 3-буквенного
     * суффикса, но {@code substringRight(name, 2)} и {@code substringRight(name, 1)}
     * — без {@code toLowerCase()}. Несогласованность регистров.</p>
     */
    private String declineNameGenitive(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(name)) {
            return name;
        }

        if (shorten) {
            name = shorten(name);
        } else {
            temp = name;

            switch (substringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = setEnd(name, 2, "ьва");
                    break;
                default:
                    break;
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 3)) {
                    case "ётр":
                        name = setEnd(name, "етр");
                        name += "а";
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = setEnd(name, "я");
                        break;
                    case "ел":
                        name = setEnd(name, "ла");
                        break;
                    case "ец":
                        name = setEnd(name, "ца");
                        break;
                    case "га":
                    case "жа":
                    case "ка":
                    case "ха":
                    case "ча":
                    case "ща":
                        name = setEnd(name, "и");
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 1)) {
                    case "а":
                        name = setEnd(name, "ы");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "я":
                        name = setEnd(name, "и");
                        break;
                    case "ь":
                        name = setEnd(name, isFeminine ? "и" : "я");
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "а";
                        }
                        break;
                }
            }
        }

        return name;
    }

    /**
     * Дательный падеж имени.
     */
    private String declineNameDative(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(name)) {
            return name;
        }

        if (shorten) {
            name = shorten(name);
        } else {
            temp = name;

            switch (substringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = setEnd(name, 2, "ьву");
                    break;
                default:
                    break;
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 3)) {
                    case "ётр":
                        name = setEnd(name, "етр");
                        name += "у";
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = setEnd(name, "ю");
                        break;
                    case "ел":
                        name = setEnd(name, "лу");
                        break;
                    case "ец":
                        name = setEnd(name, "цу");
                        break;
                    case "ия":
                        name = setEnd(name, "ии");
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 1)) {
                    case "а":
                    case "я":
                        name = setEnd(name, "е");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "ь":
                        name = setEnd(name, isFeminine ? "и" : "ю");
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "у";
                        }
                        break;
                }
            }
        }

        return name;
    }

    /**
     * Винительный падеж имени.
     */
    private String declineNameAccusative(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(name)) {
            return name;
        }

        if (shorten) {
            name = shorten(name);
        } else {
            temp = name;

            switch (substringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = setEnd(name, 2, "ьва");
                    break;
                default:
                    break;
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 3)) {
                    case "ётр":
                        name = setEnd(name, "етр");
                        name += "а";
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = setEnd(name, "я");
                        break;
                    case "ел":
                        name = setEnd(name, "ла");
                        break;
                    case "ец":
                        name = setEnd(name, "ца");
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 1)) {
                    case "а":
                        name = setEnd(name, "у");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "я":
                        name = setEnd(name, "ю");
                        break;
                    case "ь":
                        if (!isFeminine) {
                            name = setEnd(name, "я");
                        }
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "а";
                        }
                        break;
                }
            }
        }

        return name;
    }

    /**
     * Творительный падеж имени.
     */
    private String declineNameInstrumental(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(name)) {
            return name;
        }

        if (shorten) {
            name = shorten(name);
        } else {
            temp = name;

            switch (substringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = setEnd(name, 2, "ьвом");
                    break;
                default:
                    break;
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 3)) {
                    case "ётр":
                        name = setEnd(name, "етр");
                        name += "ом";
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 2)) {
                    case "ей":
                    case "ий":
                    case "ай":
                        name = setEnd(name, 1, "ем");
                        break;
                    case "ел":
                        name = setEnd(name, 2, "лом");
                        break;
                    case "ец":
                        name = setEnd(name, 2, "цом");
                        break;
                    case "жа":
                    case "ца":
                    case "ча":
                    case "ша":
                    case "ща":
                        name = setEnd(name, 1, "ей"); // E-1: в C# было name = name = ...
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 1)) {
                    case "а":
                        name = setEnd(name, 1, "ой");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "я":
                        name = setEnd(name, 1, isFeminine ? "ей" : "ёй");
                        break;
                    case "ь":
                        name = setEnd(name, 1, isFeminine ? "ью" : "ем");
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "ом";
                        }
                        break;
                }
            }
        }

        return name;
    }

    /**
     * Предложный падеж имени.
     */
    private String declineNamePrepositional(String name, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(name)) {
            return name;
        }

        if (shorten) {
            name = shorten(name);
        } else {
            temp = name;

            switch (substringRight(name, 3).toLowerCase()) {
                case "лев":
                    name = setEnd(name, 2, "ьве");
                    break;
                default:
                    break;
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 3)) {
                    case "ётр":
                        name = setEnd(name, "етр");
                        name += "е";
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 2)) {
                    case "ей":
                    case "ай":
                        name = setEnd(name, "е");
                        break;
                    case "ий":
                        name = setEnd(name, "и");
                        break;
                    case "ел":
                        name = setEnd(name, "ле");
                        break;
                    case "ец":
                        name = setEnd(name, "це");
                        break;
                    case "ия":
                        name = setEnd(name, "ии");
                        break;
                    default:
                        break;
                }
            }

            if (name.equals(temp)) {
                switch (substringRight(name, 1)) {
                    case "а":
                    case "я":
                        name = setEnd(name, "е");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "ь":
                        name = setEnd(name, isFeminine ? "и" : "е");
                        break;
                    default:
                        if (!isFeminine) {
                            name = name + "е";
                        }
                        break;
                }
            }
        }

        return name;
    }

    // ==================== Склонение отчеств ====================

    /**
     * Родительный падеж отчества.
     */
    private String declinePatronymicGenitive(String patronymic, boolean isFeminine, boolean shorten) {
        if (isShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = shorten(patronymic);
        } else {
            switch (substringRight(patronymic, 1)) {
                case "а":
                    patronymic = setEnd(patronymic, "ы");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "я":
                    patronymic = setEnd(patronymic, "и");
                    break;
                case "ь":
                    patronymic = setEnd(patronymic, isFeminine ? "и" : "я");
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "а";
                    }
                    break;
            }
        }

        return patronymic;
    }

    /**
     * Дательный падеж отчества.
     */
    private String declinePatronymicDative(String patronymic, boolean isFeminine, boolean shorten) {
        if (isShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = shorten(patronymic);
        } else {
            switch (substringRight(patronymic, 1)) {
                case "а":
                case "я":
                    patronymic = setEnd(patronymic, "е");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "ь":
                    patronymic = setEnd(patronymic, isFeminine ? "и" : "ю");
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "у";
                    }
                    break;
            }
        }

        return patronymic;
    }

    /**
     * Винительный падеж отчества.
     */
    private String declinePatronymicAccusative(String patronymic, boolean isFeminine, boolean shorten) {
        if (isShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = shorten(patronymic);
        } else {
            switch (substringRight(patronymic, 1)) {
                case "а":
                    patronymic = setEnd(patronymic, "у");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "я":
                    patronymic = setEnd(patronymic, "ю");
                    break;
                case "ь":
                    if (!isFeminine) {
                        patronymic = setEnd(patronymic, "я");
                    }
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "а";
                    }
                    break;
            }
        }

        return patronymic;
    }

    /**
     * Творительный падеж отчества.
     *
     * <p><b>D-2:</b> проверка {@code patronymic.toLowerCase().equals("ильич")}
     * для выбора суффикса "ом"/"ем".</p>
     */
    private String declinePatronymicInstrumental(String patronymic, boolean isFeminine, boolean shorten) {
        String temp;

        if (isShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = shorten(patronymic);
        } else {
            temp = patronymic;

            switch (substringRight(patronymic, 2)) {
                case "ич":
                    // D-2: проверка на "ильич" для выбора суффикса
                    patronymic = patronymic + (patronymic.toLowerCase().equals("ильич") ? "ом" : "ем");
                    break;
                case "на":
                    patronymic = setEnd(patronymic, 2, "ной");
                    break;
                default:
                    break;
            }

            if (patronymic.equals(temp)) {
                switch (substringRight(patronymic, 1)) {
                    case "а":
                        patronymic = setEnd(patronymic, 1, "ой");
                        break;
                    case "е":
                    case "ё":
                    case "и":
                    case "о":
                    case "у":
                    case "э":
                    case "ю":
                        break;
                    case "я":
                        patronymic = setEnd(patronymic, 1, "ей");
                        break;
                    case "ь":
                        patronymic = setEnd(patronymic, 1, isFeminine ? "ью" : "ем");
                        break;
                    default:
                        if (!isFeminine) {
                            patronymic = patronymic + "ом";
                        }
                        break;
                }
            }
        }

        return patronymic;
    }

    /**
     * Предложный падеж отчества.
     */
    private String declinePatronymicPrepositional(String patronymic, boolean isFeminine, boolean shorten) {
        if (isShorten(patronymic)) {
            return patronymic;
        }

        if (shorten) {
            patronymic = shorten(patronymic);
        } else {
            switch (substringRight(patronymic, 1)) {
                case "а":
                case "я":
                    patronymic = setEnd(patronymic, "е");
                    break;
                case "е":
                case "ё":
                case "и":
                case "о":
                case "у":
                case "э":
                case "ю":
                    break;
                case "ь":
                    patronymic = setEnd(patronymic, isFeminine ? "и" : "е");
                    break;
                default:
                    if (!isFeminine) {
                        patronymic = patronymic + "е";
                    }
                    break;
            }
        }

        return patronymic;
    }

    // ==================== Склонение фамилий ====================

    /**
     * Родительный падеж фамилии.
     *
     * <p><b>D-3:</b> в мужском роде для суффикса "ий"/"ый"/"ой" проверяется
     * {@code surname.length() > 4}, но в женском роде для "ая"/"яя" — нет.</p>
     */
    private String declineSurnameGenitive(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = substringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "жий":
                case "ний":
                case "ций":
                case "чий":
                case "ший":
                case "щий":
                    surname = setEnd(surname, 2, "его");
                    break;
                case "лец":
                    surname = setEnd(surname, 2, "ьца");
                    break;
                case "нок":
                    surname = setEnd(surname, "нка");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ова":
                case "ева":
                case "ина":
                case "ына":
                    surname = setEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = setEnd(surname, 2, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = setEnd(surname, 1, "ой");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 2);

        switch (end) {
            case "га":
            case "жа":
            case "ка":
            case "ха":
            case "ча":
            case "ша":
            case "ща":
                surname = setEnd(surname, 1, "и");
                break;
            default:
                break;
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = setEnd(surname, 1, "ка");
                    break;
                case "ёк":
                case "ек":
                    surname = setEnd(surname, 2, "ька");
                    break;
                case "ец":
                    surname = setEnd(surname, 2, "ца");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, 2, "ого");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase().equals("соловей") || surname.toLowerCase().equals("воробей")) {
                        surname = setEnd(surname, 2, "ья");
                    } else {
                        surname = setEnd(surname, 2, "ея");
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ая":
                    surname = setEnd(surname, 2, "ой");
                    break;
                case "яя":
                    surname = setEnd(surname, 2, "ей");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "ы");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "и");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname = surname + "а";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "а";
                    }
                    break;
                case "ь":
                case "й":
                    surname = setEnd(surname, 1, "я");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "ы");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "и");
                    break;
                default:
                    break;
            }
        }

        return surname;
    }

    /**
     * Дательный падеж фамилии.
     */
    private String declineSurnameDative(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = substringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "жий":
                case "ний":
                case "ций":
                case "чий":
                case "ший":
                case "щий":
                    surname = setEnd(surname, 2, "ему");
                    break;
                case "лец":
                    surname = setEnd(surname, 2, "ьцу");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ова":
                case "ева":
                case "ина":
                case "ына":
                    surname = setEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = setEnd(surname, 2, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = setEnd(surname, 1, "ой");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 2);

        switch (end) {
            case "ия":
                surname = setEnd(surname, 1, "и");
                break;
            default:
                break;
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = setEnd(surname, 2, "ку");
                    break;
                case "ёк":
                case "ек":
                    surname = setEnd(surname, 2, "ьку");
                    break;
                case "ец":
                    surname = setEnd(surname, 2, "цу");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, 2, "ому");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase().equals("соловей") || surname.toLowerCase().equals("воробей")) {
                        surname = setEnd(surname, 2, "ью");
                    } else {
                        surname = setEnd(surname, 2, "ею");
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ая":
                    surname = setEnd(surname, 2, "ой");
                    break;
                case "яя":
                    surname = setEnd(surname, 2, "ей");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "е");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname = surname + "у";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "у";
                    }
                    break;
                case "ь":
                case "й":
                    surname = setEnd(surname, 1, "ю");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "е");
                    break;
                default:
                    break;
            }
        }

        return surname;
    }

    /**
     * Винительный падеж фамилии.
     */
    private String declineSurnameAccusative(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = substringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "жий":
                case "ний":
                case "ций":
                case "чий":
                case "ший":
                case "щий":
                    surname = setEnd(surname, 2, "его");
                    break;
                case "лец":
                    surname = setEnd(surname, 2, "ьца");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ова":
                case "ева":
                case "ина":
                case "ына":
                    surname = setEnd(surname, "у");
                    break;
                case "ска":
                case "цка":
                    surname = setEnd(surname, 1, "ую");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 2);

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = setEnd(surname, "ка");
                    break;
                case "ёк":
                case "ек":
                    surname = setEnd(surname, 2, "ька");
                    break;
                case "ец":
                    surname = setEnd(surname, "ца");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, 2, "ого");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase().equals("соловей") || surname.toLowerCase().equals("воробей")) {
                        surname = setEnd(surname, "ья");
                    } else {
                        surname = setEnd(surname, "ея");
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ая":
                    surname = setEnd(surname, "ую");
                    break;
                case "яя":
                    surname = setEnd(surname, "юю");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, "у");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, "ю");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname = surname + "а";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "а";
                    }
                    break;
                case "ь":
                case "й":
                    surname = setEnd(surname, "я");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, "у");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, "ю");
                    break;
                default:
                    break;
            }
        }

        return surname;
    }

    /**
     * Творительный падеж фамилии.
     *
     * <p><b>D-4:</b> в мужском роде для "ин"/"ын"/"ен"/"эн"/"ов"/"ев"/"ёв"/"ун"
     * исключаются конкретные фамилии (дарвин, франклин, чаплин, грин) через
     * {@code toLowerCase()}.</p>
     */
    private String declineSurnameInstrumental(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = substringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "лец":
                    surname = setEnd(surname, 2, "ьцом");
                    break;
                case "бец":
                    surname = setEnd(surname, 2, "цем");
                    break;
                case "кой":
                    surname = setEnd(surname, "им");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = setEnd(surname, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = setEnd(surname, 1, "ой");
                    break;
                case "еца":
                case "ица":
                case "аца":
                case "ьца":
                    surname = setEnd(surname, 1, "ей");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 2);

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = setEnd(surname, 2, "ком");
                    break;
                case "ёк":
                case "ек":
                    surname = setEnd(surname, 2, "ьком");
                    break;
                case "ец":
                    surname = setEnd(surname, 2, "цом");
                    break;
                case "ий":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, "им");
                    }
                    break;
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, "ым");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase().equals("соловей") || surname.toLowerCase().equals("воробей")) {
                        surname = setEnd(surname, 2, "ьем");
                    } else {
                        surname = setEnd(surname, 2, "еем");
                    }
                    break;
                case "оч":
                case "ич":
                case "иц":
                case "ьц":
                case "ьш":
                case "еш":
                case "ыш":
                case "яц":
                    surname = surname + "ем";
                    break;
                case "ин":
                case "ын":
                case "ен":
                case "эн":
                case "ов":
                case "ев":
                case "ёв":
                case "ун":
                    // D-4: исключение для конкретных фамилий
                    if (!surname.toLowerCase().equals("дарвин")
                            && !surname.toLowerCase().equals("франклин")
                            && !surname.toLowerCase().equals("чаплин")
                            && !surname.toLowerCase().equals("грин")) {
                        surname = surname + "ым";
                    }
                    break;
                case "жа":
                case "ца":
                case "ча":
                case "ша":
                case "ща":
                    surname = setEnd(surname, 1, "ей");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ая":
                    surname = setEnd(surname, "ой");
                    break;
                case "яя":
                    surname = setEnd(surname, "ей");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "ой");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "ей");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                    surname = surname + "ом";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "ом";
                    }
                    break;
                case "щ":
                    surname = surname + "ем";
                    break;
                case "ь":
                case "й":
                    surname = setEnd(surname, 1, "ем");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, 1, "ой");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, 1, "ей");
                    break;
                default:
                    break;
            }
        }

        return surname;
    }

    /**
     * Предложный падеж фамилии.
     */
    private String declineSurnamePrepositional(String surname, boolean isFeminine) {
        String temp = surname;
        String end;

        end = substringRight(surname, 3);

        if (!isFeminine) {
            switch (end) {
                case "жий":
                case "ний":
                case "ций":
                case "чий":
                case "ший":
                case "щий":
                    surname = setEnd(surname, "ем");
                    break;
                case "лец":
                    surname = setEnd(surname, 2, "ьце");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ова":
                case "ева":
                case "ина":
                case "ына":
                    surname = setEnd(surname, 1, "ой");
                    break;
                case "жая":
                case "цая":
                case "чая":
                case "шая":
                case "щая":
                    surname = setEnd(surname, "ей");
                    break;
                case "ска":
                case "цка":
                    surname = setEnd(surname, 1, "ой");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 2);

        switch (end) {
            case "ия":
                surname = setEnd(surname, "и");
                break;
            default:
                break;
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        if (!isFeminine) {
            switch (end) {
                case "ок":
                    surname = setEnd(surname, "ке");
                    break;
                case "ёк":
                case "ек":
                    surname = setEnd(surname, 2, "ьке");
                    break;
                case "ец":
                    surname = setEnd(surname, "це");
                    break;
                case "ий":
                case "ый":
                case "ой":
                    if (surname.length() > 4) {
                        surname = setEnd(surname, "ом");
                    }
                    break;
                case "ей":
                    if (surname.toLowerCase().equals("соловей") || surname.toLowerCase().equals("воробей")) {
                        surname = setEnd(surname, "ье");
                    } else {
                        surname = setEnd(surname, "ее");
                    }
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "ая":
                    surname = setEnd(surname, "ой");
                    break;
                case "яя":
                    surname = setEnd(surname, "ей");
                    break;
                default:
                    break;
            }
        }

        if (!surname.equals(temp)) {
            return surname;
        }

        end = substringRight(surname, 1);

        if (!isFeminine) {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, "е");
                    break;
                case "б":
                case "в":
                case "г":
                case "д":
                case "ж":
                case "з":
                case "к":
                case "л":
                case "м":
                case "н":
                case "п":
                case "р":
                case "с":
                case "т":
                case "ф":
                case "ц":
                case "ч":
                case "ш":
                case "щ":
                    surname = surname + "е";
                    break;
                case "х":
                    if (!surname.endsWith("их") && !surname.endsWith("ых")) {
                        surname = surname + "е";
                    }
                    break;
                case "ь":
                case "й":
                    surname = setEnd(surname, "е");
                    break;
                default:
                    break;
            }
        } else {
            switch (end) {
                case "а":
                    switch (surname.substring(surname.length() - 2, surname.length() - 1)) {
                        case "а":
                        case "е":
                        case "ё":
                        case "и":
                        case "о":
                        case "у":
                        case "э":
                        case "ы":
                        case "ю":
                        case "я":
                            break;
                        default:
                            surname = setEnd(surname, "е");
                            break;
                    }
                    break;
                case "я":
                    surname = setEnd(surname, "е");
                    break;
                default:
                    break;
            }
        }

        return surname;
    }

    /**
     * Склоняет фамилию в указанный падеж.
     *
     * <p><b>C-6:</b> проверка {@code surname.length() <= 1} до switch,
     * но {@code DeclineSurnameGenitive} и др. не имеют этой защиты.</p>
     *
     * @param surname   фамилия (именительный падеж)
     * @param caseIndex 1-based номер падежа (1..6)
     * @param isFeminine true для женских фамилий
     * @return результат склонения
     */
    private String declineSurname(String surname, int caseIndex, boolean isFeminine) {
        String result = surname;

        // C-6: проверка длины и диапазона падежа
        if (surname.length() <= 1 || caseIndex < 2 || caseIndex > 6) {
            return surname;
        }

        switch (caseIndex) {
            case 2:
                result = declineSurnameGenitive(surname, isFeminine);
                break;
            case 3:
                result = declineSurnameDative(surname, isFeminine);
                break;
            case 4:
                result = declineSurnameAccusative(surname, isFeminine);
                break;
            case 5:
                result = declineSurnameInstrumental(surname, isFeminine);
                break;
            case 6:
                result = declineSurnamePrepositional(surname, isFeminine);
                break;
            default:
                break;
        }

        return result;
    }

    // ==================== Разбор тюркских отчеств ====================

    /**
     * Результат разбора тюркского отчества.
     *
     * @param prefix     префиксная часть (например, "Ибн "), не склоняется
     * @param patronymic основная часть отчества, может склоняться
     * @param suffix     суффиксная часть (например, "-оглы"), не склоняется
     */
    public record SplitPatronymicResult(String prefix, String patronymic, String suffix) {}

    /**
     * Разбивает составное отчество на префикс, основную часть и суффикс.
     *
     * <p>Примеры: "Салим-оглы" → (null, "Салим", "-оглы"),
     * "Ибн Сина" → ("Ибн ", "Сина", null),
     * "Петрович" → (null, "Петрович", null).</p>
     *
     * @param fullPatronymic полное отчество
     * @return результат разбора
     */
    public SplitPatronymicResult splitPatronymic(String fullPatronymic) {
        if (fullPatronymic == null || fullPatronymic.isEmpty()) {
            return new SplitPatronymicResult(null, fullPatronymic, null);
        }

        String prefix = null;
        String suffix = null;
        String patronymic = fullPatronymic;

        var prefixMatch = PATRONYMIC_PREFIX_REGEX.matcher(fullPatronymic);
        var suffixMatch = PATRONYMIC_SUFFIX_REGEX.matcher(fullPatronymic);

        if (prefixMatch.find()) {
            prefix = prefixMatch.group();
            patronymic = PATRONYMIC_PREFIX_REGEX.matcher(patronymic).replaceFirst("");
        }

        if (suffixMatch.find()) {
            suffix = suffixMatch.group();
            patronymic = PATRONYMIC_SUFFIX_REGEX.matcher(patronymic).replaceFirst("");
        }

        return new SplitPatronymicResult(prefix, patronymic, suffix);
    }

    // ==================== Вспомогательные методы ====================

    /**
     * Приводит строку к правильному регистру: убирает BOM, trim, заглавная первая буква.
     */
    String properCase(String value) {
        if (value != null) {
            value = value.replace("\uFEFF", "").trim();
        }

        if (value == null || value.isEmpty()) {
            return "";
        }

        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    /**
     * Заменяет конец строки на {@code add} (длина {@code add} символов).
     */
    String setEnd(String value, String add) {
        return setEnd(value, add.length(), add);
    }

    /**
     * Заменяет последние {@code cut} символов строки на {@code add}.
     */
    String setEnd(String value, int cut, String add) {
        return value.substring(0, value.length() - cut) + add;
    }

    /**
     * Возвращает последние {@code cut} символов строки.
     */
    String substringRight(String value, int cut) {
        if (cut > value.length()) {
            cut = value.length();
        }
        return value.substring(value.length() - cut);
    }

    /**
     * Сокращает значение до инициала: "Петровна" → "П.", "Николай" → "Н.".
     * Возвращает null, если вход null/whitespace.
     */
    String shorten(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return String.valueOf(value.charAt(0)) + ".";
    }

    /**
     * Возвращает true, если строка пустая, null или заканчивается на точку.
     */
    boolean isShorten(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.charAt(value.length() - 1) == '.';
    }

    // ==================== Склонение по всем падежам ====================

    /**
     * Вызывает функцию для каждого падежа (1..6) и возвращает общий результат.
     *
     * @param perCase функция, принимающая 1-based номер падежа и возвращающая
     *                массив [фамилия, имя, отчество]
     * @return результат склонения во все падежи
     */
    private CyrResult declinePerCaseAndCombine(IntFunction<String[]> perCase) {
        String[] cases = new String[6];

        for (Cases c : Cases.values()) {
            int caseIndex = c.index;
            String[] values = perCase.apply(caseIndex);
            CyrNameResult caseResult = new CyrNameResult(values);
            cases[caseIndex - 1] = caseResult.toString();
        }

        return new CyrResult(cases);
    }
}
