# Cyriller-Java

Морфологическая библиотека русского языка: склонение имён, фамилий, отчеств,
существительных и прописывание чисел словами. Порт C#-библиотеки **Cyriller**
на **Java 25** (Maven).

- **GroupId / ArtifactId:** `ru.cyriller:cyriller-java`
- **Версия:** `0.1.0-SNAPSHOT`
- **Пакет:** `ru.cyriller` (классы) + `ru.cyriller.model` (перечисления)
- **Зависимости:** нет (только JUnit 5 в `test`-scope)

---

## Содержание

1. [Установка и сборка](#установка-и-сборка)
2. [Базовые понятия](#базовые-понятия)
3. [Склонение ФИО — `CyrName`](#склонение-фио--cyrname)
4. [Числа словами — `CyrNumber`](#числа-словами--cyrnumber)
5. [Склонение существительных — `CyrNoun` и `CyrRule`](#склонение-существительных--cyrnoun-и-cyrrule)
6. [Предметы — `Item`](#предметы--item)
7. [Валюты — `Currency`](#валюты--currency)
8. [Результаты — `CyrResult` и `CyrNameResult`](#результаты--cyrresult-и-cyrnameresult)
9. [Перечисления (model)](#перечисления-model)
10. [Ограничения и примечания](#ограничения-и-примечания)

---

## Установка и сборка

```bash
cd cyriller-java
mvn test          # собрать и прогнать 150 тестов
mvn package       # собрать jar в target/
```

Добавление в свой Maven-проект (после `mvn install`):

```xml
<dependency>
    <groupId>ru.cyriller</groupId>
    <artifactId>cyriller-java</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

---

## Базовые понятия

| Понятие | Перечисление | Значения |
|---|---|---|
| Падеж | `Cases` | `NOMINATIVE`(1), `GENITIVE`(2), `DATIVE`(3), `ACCUSATIVE`(4), `INSTRUMENTAL`(5), `PREPOSITIVE`(6) |
| Род | `Genders` | `UNDEFINED`, `MASCULINE`, `FEMININE`, `NEUTER` |
| Одушевлённость | `Animates` | `ANIMATED`, `INANIMATED` |
| Тип слова | `WordTypes` | `ABBREVIATION`(1) … `COMPARATIVE_EJ`(16) |

> **Важно:** `Cases` — 1-based (поле `index`), `Genders` — 0-based (`ordinal()`).

---

## Склонение ФИО — `CyrName`

Склоняет фамилию, имя и отчество по всем падежам. Понимает составные фамилии
через дефис, тюркские отчества (`-оглы`, `-кызы`, `ибн`), сокращения и
автоматическое определение рода.

### Методы

```java
// Один падеж → CyrNameResult (фамилия / имя / отчество по отдельности)
CyrNameResult decline(String surname, String name, String patronymic,
                      Cases cases, Genders gender, boolean shorten);

// Все 6 падежей → CyrResult (каждый элемент = "Фамилия Имя Отчество")
CyrResult decline(String surname, String name, String patronymic,
                  Genders gender, boolean shorten);

// Вход — строка "Фамилия Имя Отчество" (или "Фамилия Имя")
CyrNameResult decline(String fullName, Cases cases, Genders gender, boolean shorten);
CyrResult decline(String fullName, Genders gender, boolean shorten);

// Разбор тюркского отчества на префикс / корень / суффикс
SplitPatronymicResult splitPatronymic(String patronymic);
```

Параметр `shorten = true` сокращает имя и отчество до инициалов (`И. И.`).
Параметр `gender = Genders.UNDEFINED` включает автоопределение рода по
отчеству (`…на` → женский, `…вич` → мужской).

### Пример: один падеж

```java
CyrName cyrName = new CyrName();

// Родительный падеж, мужской род
CyrNameResult r = cyrName.decline("Иванов", "Иван", "Иванович",
                                  Cases.GENITIVE, Genders.MASCULINE, false);
r.surname();     // "Иванова"
r.name();        // "Ивана"
r.patronymic();  // "Ивановича"
r.fullName();    // "Иванова Ивана Ивановича"
```

### Пример: все падежи сразу

```java
CyrResult all = cyrName.decline("Иванов", "Иван", "Иванович",
                                Genders.MASCULINE, false);
all.get(Cases.NOMINATIVE);     // "Иванов Иван Иванович"
all.get(Cases.GENITIVE);       // "Иванова Ивана Ивановича"
all.get(Cases.DATIVE);         // "Иванову Ивану Ивановичу"
all.get(Cases.ACCUSATIVE);     // "Иванова Ивана Ивановича"
all.get(Cases.INSTRUMENTAL);   // "Ивановым Иваном Ивановичем"
all.get(Cases.PREPOSITIVE);    // "Иванове Иване Ивановиче"
```

### Пример: вход одной строкой + сокращение

```java
// Женский род, сокращение до инициалов
CyrNameResult r = cyrName.decline("Иванова Наталья Петровна",
                                  Cases.GENITIVE, Genders.FEMININE, true);
r.fullName();  // "Ивановой Н. П."
```

### Пример: составная фамилия и тюркское отчество

```java
// Обе части фамилии склоняются
cyrName.decline("Иванов-Петров", "Иван", "Иванович",
                Cases.GENITIVE, Genders.MASCULINE, false).surname();
// → "Иванова-Петрова"

// Тюркское отчество не склоняется
cyrName.decline("Мамедов", "Салим", "Салим-оглы",
                Cases.GENITIVE, Genders.MASCULINE, false).patronymic();
// → "Салим-оглы"
```

### Разбор отчества

```java
CyrName.SplitPatronymicResult s = cyrName.splitPatronymic("Салим-оглы");
s.prefix();     // null
s.patronymic(); // "Салим"
s.suffix();     // "-оглы"
```

---

## Числа словами — `CyrNumber`

Превращает числа в русские слова: целые, дробные, деньги и количество
предметов. Поддерживает род, одушевлённость и все 6 падежей.

### Методы

```java
// Целые числа
String toString(long value, Cases cases);
String toString(long value, Cases cases, Genders gender, Animates animate);

// Дробные числа (BigDecimal)
String toString(BigDecimal value, Cases cases);
String toString(BigDecimal value, Cases cases, Genders gender, Animates animate);

// Деньги
String toString(BigDecimal value, Cases cases, Currency currency);

// Предметы
String toString(BigDecimal value, Cases cases, Item item);

// Все 6 падежей → CyrResult
CyrResult decline(long value);
CyrResult decline(BigDecimal value);
CyrResult decline(BigDecimal value, Currency currency);
CyrResult decline(BigDecimal value, Item item);

// Правило 1/2/5: выбор формы по последним цифрам
String caseForm(long value, String one, String two, String five);

// Граница: 9 999 999 999
static final long MAX_VALUE;
```

### Пример: целые числа

```java
CyrNumber n = new CyrNumber();

n.toString(1, Cases.NOMINATIVE);        // "один"
n.toString(21, Cases.NOMINATIVE);       // "двадцать один"
n.toString(1000, Cases.NOMINATIVE);     // "одна тысяча"
n.toString(1000000, Cases.NOMINATIVE);  // "один миллион"
n.toString(9999999999L, Cases.NOMINATIVE);
// "девять миллиардов девятьсот девяносто девять миллионов
//  девятьсот девяносто девять тысяч девятьсот девяносто девять"
```

### Пример: род и одушевлённость

```java
// Женский род
n.toString(1, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED); // "одна"
n.toString(2, Cases.NOMINATIVE, Genders.FEMININE, Animates.INANIMATED); // "две"

// Одушевлённый винительный (1/2 → форма родительного)
n.toString(1, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED); // "одного"
n.toString(2, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED); // "двух"
n.toString(5, Cases.ACCUSATIVE, Genders.MASCULINE, Animates.ANIMATED); // "пять"
```

### Пример: дробные числа

```java
n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE);
// "один целый и пять десятых"

n.toString(new BigDecimal("123.456"), Cases.NOMINATIVE);
// "сто двадцать три целых и четыреста пятьдесят шесть тысячных"

n.toString(new BigDecimal("1.5"), Cases.GENITIVE);
// "одного целого и пяти десятых"
```

### Пример: деньги

```java
Rur rur = new Rur();

n.toString(new BigDecimal("1"), Cases.NOMINATIVE, rur);
// "один рубль"

n.toString(new BigDecimal("1.5"), Cases.NOMINATIVE, rur);
// "один рубль и пятьдесят копеек"

n.toString(new BigDecimal("1.5"), Cases.GENITIVE, rur);
// "одного рубля и пятидесяти копеек"
```

### Пример: предметы

```java
Item stol = new Item(new CyrNoun("стол", Genders.MASCULINE,
        Animates.INANIMATED, WordTypes.TOPONYM, new CyrRule[]{
            new CyrRule("ла1"), new CyrRule("у"), new CyrRule(""),
            new CyrRule("ом"), new CyrRule("е"),
            new CyrRule("ы"), new CyrRule("ов"), new CyrRule("лам1"),
            new CyrRule("ы"), new CyrRule("ами"), new CyrRule("ах")
        }));

n.toString(new BigDecimal("1"), Cases.NOMINATIVE, stol);  // "один стол"
n.toString(new BigDecimal("2"), Cases.NOMINATIVE, stol);  // "два стола"
n.toString(new BigDecimal("5"), Cases.NOMINATIVE, stol);  // "пять столов"
n.toString(new BigDecimal("21"), Cases.NOMINATIVE, stol); // "двадцать один стол"
```

### Пример: все падежи числа

```java
CyrResult r = n.decline(21);
r.get(Cases.NOMINATIVE);     // "двадцать один"
r.get(Cases.GENITIVE);       // "двадцати одного"
r.get(Cases.DATIVE);         // "двадцати одному"
r.get(Cases.ACCUSATIVE);     // "двадцать один"
r.get(Cases.INSTRUMENTAL);   // "двадцатью одним"
r.get(Cases.PREPOSITIVE);    // "двадцати одном"
```

### Правило 1/2/5

```java
n.caseForm(1, "один", "два", "пять");  // "один"
n.caseForm(2, "один", "два", "пять");  // "два"
n.caseForm(3, "один", "два", "пять");  // "два"
n.caseForm(5, "один", "два", "пять");  // "пять"
n.caseForm(21, "один", "два", "пять"); // "один"
n.caseForm(22, "один", "два", "пять"); // "два"
n.caseForm(25, "один", "два", "пять"); // "пять"
```

---

## Склонение существительных — `CyrNoun` и `CyrRule`

`CyrNoun` склоняет существительное по правилам, заданным массивом `CyrRule`.
Правила задают суффиксы для каждого падежа (5 для ед. ч. + 6 для мн. ч.).

### `CyrRule`

```java
CyrRule(String rule)   // например "ла1"
String apply(String name)  // применяет правило к слову
```

Формат правила: **суффикс + число**. Число — сколько символов отбросить от
конца слова перед добавлением суффикса. Пример: `"ла1"` для слова `стол`
отбрасывает `л` и добавляет `ла` → `стола`.

### `CyrNoun`

```java
// 5 аргументов: collectionName = name
CyrNoun(String name, Genders gender, Animates animate,
        WordTypes wordType, CyrRule[] rules);

// 6 аргументов: отдельное название для множественного числа
CyrNoun(String name, String collectionName, Genders gender,
        Animates animate, WordTypes wordType, CyrRule[] rules);

// Склонение
CyrResult decline();         // единственное число (6 падежей)
CyrResult declinePlural();   // множественное число (6 падежей)
```

### Пример: «стол»

```java
CyrNoun stol = new CyrNoun("стол", Genders.MASCULINE,
        Animates.INANIMATED, WordTypes.TOPONYM, new CyrRule[]{
            // Ед. ч.: род. дат. вин. твор. предл.
            new CyrRule("ла1"),  // стола
            new CyrRule("у"),    // столу
            new CyrRule(""),     // стол
            new CyrRule("ом"),   // столом
            new CyrRule("е"),    // столе
            // Мн. ч.: им. род. дат. вин. твор. предл.
            new CyrRule("ы"),    // столы
            new CyrRule("ов"),   // столов
            new CyrRule("лам1"), // столам
            new CyrRule("ы"),    // столы
            new CyrRule("ами"),  // столами
            new CyrRule("ах")    // столах
        });

CyrResult sing = stol.decline();
sing.nominative();      // "стол"
sing.genitive();        // "стола"
sing.dative();          // "столу"
sing.accusative();      // "стол"
sing.instrumental();    // "столом"
sing.prepositional();   // "столе"

CyrResult plur = stol.declinePlural();
plur.nominative();      // "столы"
plur.genitive();        // "столов"
plur.dative();          // "столам"
plur.accusative();      // "столы"
plur.instrumental();    // "столами"
plur.prepositional();   // "столах"
```

### Пример: «человек» (одушевлённое, с особым мн. ч.)

```java
CyrNoun chelovek = new CyrNoun("человек", Genders.MASCULINE,
        Animates.ANIMATED, WordTypes.NAME, new CyrRule[]{
            new CyrRule("а"), new CyrRule("у"), new CyrRule("а"),
            new CyrRule("ом"), new CyrRule("е"),
            new CyrRule("люди7"), new CyrRule("людей7"), new CyrRule("людям7"),
            new CyrRule("людей7"), new CyrRule("людьми7"), new CyrRule("людях7")
        });
// Мн. ч.: люди / людей / людям / людей / людьми / людях
```

---

## Предметы — `Item`

`Item` оборачивает `CyrNoun` и предоставляет три формы для правила 1/2/5,
используемого `CyrNumber` при прописывании количества.

```java
Item(CyrNoun noun)
String[] getName(Cases cases, long value)  // [форма для 1, для 2-5, для 5+]
Genders gender()
Animates animate()
```

### Пример

```java
Item stol = new Item(new CyrNoun("стол", Genders.MASCULINE,
        Animates.INANIMATED, WordTypes.TOPONYM, /* правила */));

// Именительный: [стол, стола, столов]
stol.getName(Cases.NOMINATIVE, 1);
// Родительный: [стола, столов, столов]
stol.getName(Cases.GENITIVE, 5);
```

> **Особый случай «год»:** в мн. ч. хардкод `лет` (C-7):
> `1 год, 2 года, 5 лет`.

---

## Валюты — `Currency`

Абстрактный класс для определения валют. Конкретные реализации:
`Rur` (рубль), `Usd` (доллар), `Eur` (евро), `Yuan` (юань).

```java
// Абстрактные методы (реализуются в подклассах)
String[] getIntegerName(Cases cases);   // [1, 2-5, 5+] для целой части
String[] getDecimalName(Cases cases);   // [1, 2-5, 5+] для дробной части

// Доступные свойства
String name();          // например "Российский рубль (руб) ₽"
Genders integerGender();
Genders decimalGender();
int decimals();         // число знаков после запятой (2 для Rur/Usd/Eur, 1 для Yuan)
```

### Примеры валют

| Класс | Целая часть | Дробная часть | Знаков |
|---|---|---|---|
| `Rur` | рубль / рубля / рублей | копейка / копейки / копеек | 2 |
| `Usd` | доллар / доллара / долларов | цент / цента / центов | 2 |
| `Eur` | евро (инвариантно) | цент / цента / центов | 2 |
| `Yuan` | юань / юаня / юаней | цзяо (инвариантно) | 1 |

```java
Rur rur = new Rur();
rur.name();            // "Российский рубль (руб) ₽"
rur.decimals();        // 2
rur.getIntegerName(Cases.NOMINATIVE);  // ["рубль", "рубля", "рублей"]
```

---

## Результаты — `CyrResult` и `CyrNameResult`

### `CyrResult`

Контейнер для 6 падежей. Поддерживает поэлементное сложение через `plus()`.

```java
CyrResult();  // пустой
CyrResult(String nominative, String genitive, String dative,
          String accusative, String instrumental, String prepositional);

String get(Cases cases);
void set(Cases cases, String value);
CyrResult plus(CyrResult other);  // поэлементная конкатенация

// Аксессоры
String nominative();
String genitive();
String dative();
String accusative();
String instrumental();
String prepositional();
```

### `CyrNameResult`

Record с тремя полями и методом `fullName()`.

```java
String surname();
String name();
String patronymic();
String fullName();  // "Фамилия Имя Отчество" (пустые части пропускаются)
```

---

## Перечисления (model)

### `Cases` (1-based)

| Значение | index | Вопрос |
|---|---|---|
| `NOMINATIVE` | 1 | Кто? Что? (есть) |
| `GENITIVE` | 2 | Кого? Чего? (нет) |
| `DATIVE` | 3 | Кому? Чему? (дам) |
| `ACCUSATIVE` | 4 | Кого? Что? (вижу) |
| `INSTRUMENTAL` | 5 | Кем? Чем? (горжусь) |
| `PREPOSITIVE` | 6 | О ком? О чём? (думаю) |

```java
Cases c = Cases.fromIndex(3);  // DATIVE
```

### `Genders` (0-based)

`UNDEFINED`, `MASCULINE`, `FEMININE`, `NEUTER`

> `CyrName` использует только 0/1/2; `NEUTER` там невалиден.

### `Animates`

`ANIMATED`, `INANIMATED`

### `WordTypes` (1-based, 1..16)

`ABBREVIATION`, `NAME`, `SURNAME`, `PATRONYMIC`, `TOPONYM`, `ORGANIZATION`,
`TRADE_MARK`, `SUPERLATIVE`, `QUALITATIVE`, `PRONOMINAL`, `ORDINAL`,
`POSSESSIVE`, `FORM_EY`, `FORM_OY`, `COMPARATIVE`, `COMPARATIVE_EJ`

```java
WordTypes t = WordTypes.fromValue(3);  // SURNAME
```

### `Numbers`

`SINGULAR`, `PLURAL` (мёртвый код в C#, перенесён для совместимости)

---

## Ограничения и примечания

1. **Граница чисел:** `CyrNumber.MAX_VALUE = 9_999_999_999`. Числа выше
   вызывают `IllegalArgumentException`.

2. **Дробные числа:** используются `BigDecimal` (не `double`), чтобы избежать
   ошибок округления. Хвостовые нули отбрасываются (`1.50 == 1.5`).

3. **Округление денег:** дробная часть длиннее `decimals()` округляется
   (например, `1.555` ₽ → `56` копеек).

4. **Тюркские отчества:** не склоняются (возвращаются как есть).

5. **Составные фамилии:** обе части склоняются независимо.

6. **Сокращение:** `shorten = true` сокращает имя и отчество до инициалов
   (`И. И.`), фамилия не сокращается.

7. **Автоопределение рода:** `gender = Genders.UNDEFINED` определяет род по
   отчеству (`…на` → женский, `…вич` → мужской).

8. **Особые случаи:**
   - `год` → `1 год, 2 года, 5 лет` (хардкод `лет` в мн. ч.)
   - `евро` и `цзяо` инвариантны по падежам
   - Одушевлённые существительные: винительный = родительный для 1/2

9. **Порт C#:** библиотека является портом C#-библиотеки Cyriller. Все
   морфологические особенности (включая известные баги C#) воспроизведены
   1:1. См. `PORTING.md` в корне репозитория.

---

## Лицензия

**GNU Lesser General Public License v3.0 only (LGPL-3.0-only).**

Cyriller-Java является Java-портом библиотеки [Cyriller](https://github.com/miyconst/Cyriller), созданной Miyconst.

Проект распространяется на условиях LGPL-3.0-only. Полный текст лицензии находится в файле `LICENSE` в корне репозитория.

```text
Cyriller-Java is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, version 3 of the License.
```
