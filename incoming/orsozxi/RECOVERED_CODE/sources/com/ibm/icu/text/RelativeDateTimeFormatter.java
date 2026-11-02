package com.ibm.icu.text;

import com.facebook.GraphRequest;
import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.InvalidObjectException;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.text.Format;
import java.util.EnumMap;
import java.util.Locale;
import java.util.MissingResourceException;
import net.fortuna.ical4j.model.property.RequestStatus;

/* loaded from: classes2.dex */
public final class RelativeDateTimeFormatter {
    private final BreakIterator breakIterator;
    private final DisplayContext capitalizationContext;
    private final String combinedDateAndTime;
    private final DateFormatSymbols dateFormatSymbols;
    private final ULocale locale;
    private final NumberFormat numberFormat;
    private final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap;
    private final PluralRules pluralRules;
    private final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
    private final Style style;
    private int[] styleToDateFormatSymbolsWidth = {1, 3, 2};
    private static final Style[] fallbackCache = new Style[3];
    private static final Cache cache = new Cache(null);

    public enum AbsoluteUnit {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        DAY,
        WEEK,
        MONTH,
        YEAR,
        NOW,
        QUARTER,
        HOUR,
        MINUTE
    }

    public enum Direction {
        LAST_2,
        LAST,
        THIS,
        NEXT,
        NEXT_2,
        PLAIN
    }

    public enum RelativeDateTimeUnit {
        YEAR,
        QUARTER,
        MONTH,
        WEEK,
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    public enum RelativeUnit {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS,
        QUARTERS
    }

    public enum Style {
        LONG,
        SHORT,
        NARROW;

        private static final int INDEX_COUNT = 3;
    }

    public static class Field extends Format.Field {
        public static final Field LITERAL = new Field("literal");
        public static final Field NUMERIC = new Field("numeric");
        private static final long serialVersionUID = -5327685528663492325L;

        private Field(String str) {
            super(str);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (getName().equals(LITERAL.getName())) {
                return LITERAL;
            }
            if (getName().equals(NUMERIC.getName())) {
                return NUMERIC;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    public static class FormattedRelativeDateTime implements FormattedValue {
        private final FormattedStringBuilder string;

        /* synthetic */ FormattedRelativeDateTime(FormattedStringBuilder formattedStringBuilder, AnonymousClass1 anonymousClass1) {
            this(formattedStringBuilder);
        }

        private FormattedRelativeDateTime(FormattedStringBuilder formattedStringBuilder) {
            this.string = formattedStringBuilder;
        }

        @Override // com.ibm.icu.text.FormattedValue, java.lang.CharSequence
        public String toString() {
            return this.string.toString();
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.string.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return this.string.charAt(i);
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            return this.string.subString(i, i2);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public <A extends Appendable> A appendTo(A a) {
            return (A) Utility.appendTo(this.string, a);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
            return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, Field.NUMERIC);
        }

        @Override // com.ibm.icu.text.FormattedValue
        public AttributedCharacterIterator toCharacterIterator() {
            return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, Field.NUMERIC);
        }
    }

    public static RelativeDateTimeFormatter getInstance() {
        return getInstance(ULocale.getDefault(), null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale) {
        return getInstance(uLocale, null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat) {
        return getInstance(uLocale, numberFormat, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat, Style style, DisplayContext displayContext) {
        NumberFormat numberFormat2;
        RelativeDateTimeFormatterData relativeDateTimeFormatterData = cache.get(uLocale);
        if (numberFormat == null) {
            numberFormat2 = NumberFormat.getInstance(uLocale);
        } else {
            numberFormat2 = (NumberFormat) numberFormat.clone();
        }
        return new RelativeDateTimeFormatter(relativeDateTimeFormatterData.qualitativeUnitMap, relativeDateTimeFormatterData.relUnitPatternMap, SimpleFormatterImpl.compileToStringMinMaxArguments(relativeDateTimeFormatterData.dateTimePattern, new StringBuilder(), 2, 2), PluralRules.forLocale(uLocale), numberFormat2, style, displayContext, displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE ? BreakIterator.getSentenceInstance(uLocale) : null, uLocale);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale, NumberFormat numberFormat) {
        return getInstance(ULocale.forLocale(locale), numberFormat);
    }

    public String format(double d, Direction direction, RelativeUnit relativeUnit) {
        return adjustForContext(formatImpl(d, direction, relativeUnit).toString());
    }

    public FormattedRelativeDateTime formatToValue(double d, Direction direction, RelativeUnit relativeUnit) {
        checkNoAdjustForContext();
        return new FormattedRelativeDateTime(formatImpl(d, direction, relativeUnit), null);
    }

    private FormattedStringBuilder formatImpl(double d, Direction direction, RelativeUnit relativeUnit) {
        String strSelect;
        if (direction != Direction.LAST && direction != Direction.NEXT) {
            throw new IllegalArgumentException("direction must be NEXT or LAST");
        }
        int i = direction == Direction.NEXT ? 1 : 0;
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        NumberFormat numberFormat = this.numberFormat;
        if (numberFormat instanceof DecimalFormat) {
            DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
            ((DecimalFormat) this.numberFormat).toNumberFormatter().formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
            strSelect = this.pluralRules.select(decimalQuantity_DualStorageBCD);
        } else {
            formattedStringBuilder.append(numberFormat.format(d), (Object) null);
            strSelect = this.pluralRules.select(d);
        }
        SimpleFormatterImpl.formatPrefixSuffix(getRelativeUnitPluralPattern(this.style, relativeUnit, i, StandardPlural.orOtherFromString(strSelect)), Field.LITERAL, 0, formattedStringBuilder.length(), formattedStringBuilder);
        return formattedStringBuilder;
    }

    public String formatNumeric(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        return adjustForContext(formatNumericImpl(d, relativeDateTimeUnit).toString());
    }

    public FormattedRelativeDateTime formatNumericToValue(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        checkNoAdjustForContext();
        return new FormattedRelativeDateTime(formatNumericImpl(d, relativeDateTimeUnit), null);
    }

    private FormattedStringBuilder formatNumericImpl(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        RelativeUnit relativeUnit = RelativeUnit.SECONDS;
        switch (AnonymousClass1.$SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[relativeDateTimeUnit.ordinal()]) {
            case 1:
                relativeUnit = RelativeUnit.YEARS;
                break;
            case 2:
                relativeUnit = RelativeUnit.QUARTERS;
                break;
            case 3:
                relativeUnit = RelativeUnit.MONTHS;
                break;
            case 4:
                relativeUnit = RelativeUnit.WEEKS;
                break;
            case 5:
                relativeUnit = RelativeUnit.DAYS;
                break;
            case 6:
                relativeUnit = RelativeUnit.HOURS;
                break;
            case 7:
                relativeUnit = RelativeUnit.MINUTES;
                break;
            case 8:
                break;
            default:
                throw new UnsupportedOperationException("formatNumeric does not currently support RelativeUnit.SUNDAY..SATURDAY");
        }
        Direction direction = Direction.NEXT;
        if (Double.compare(d, 0.0d) < 0) {
            direction = Direction.LAST;
            d = -d;
        }
        return formatImpl(d, direction, relativeUnit);
    }

    public String format(Direction direction, AbsoluteUnit absoluteUnit) {
        String absoluteImpl = formatAbsoluteImpl(direction, absoluteUnit);
        if (absoluteImpl != null) {
            return adjustForContext(absoluteImpl);
        }
        return null;
    }

    public FormattedRelativeDateTime formatToValue(Direction direction, AbsoluteUnit absoluteUnit) {
        checkNoAdjustForContext();
        String absoluteImpl = formatAbsoluteImpl(direction, absoluteUnit);
        AnonymousClass1 anonymousClass1 = null;
        if (absoluteImpl == null) {
            return null;
        }
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        formattedStringBuilder.append(absoluteImpl, Field.LITERAL);
        return new FormattedRelativeDateTime(formattedStringBuilder, anonymousClass1);
    }

    private String formatAbsoluteImpl(Direction direction, AbsoluteUnit absoluteUnit) {
        if (absoluteUnit == AbsoluteUnit.NOW && direction != Direction.PLAIN) {
            throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
        }
        if (direction == Direction.PLAIN && AbsoluteUnit.SUNDAY.ordinal() <= absoluteUnit.ordinal() && absoluteUnit.ordinal() <= AbsoluteUnit.SATURDAY.ordinal()) {
            return this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()])[(absoluteUnit.ordinal() - AbsoluteUnit.SUNDAY.ordinal()) + 1];
        }
        return getAbsoluteUnitString(this.style, absoluteUnit, direction);
    }

    public String format(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        return adjustForContext(formatRelativeImpl(d, relativeDateTimeUnit).toString());
    }

    public FormattedRelativeDateTime formatToValue(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        FormattedStringBuilder formattedStringBuilder;
        checkNoAdjustForContext();
        CharSequence relativeImpl = formatRelativeImpl(d, relativeDateTimeUnit);
        if (relativeImpl instanceof FormattedStringBuilder) {
            formattedStringBuilder = (FormattedStringBuilder) relativeImpl;
        } else {
            FormattedStringBuilder formattedStringBuilder2 = new FormattedStringBuilder();
            formattedStringBuilder2.append(relativeImpl, Field.LITERAL);
            formattedStringBuilder = formattedStringBuilder2;
        }
        return new FormattedRelativeDateTime(formattedStringBuilder, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.CharSequence formatRelativeImpl(double r11, com.ibm.icu.text.RelativeDateTimeFormatter.RelativeDateTimeUnit r13) {
        /*
            r10 = this;
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.THIS
            r1 = 1
            r2 = 0
            r3 = -4611460838446019379(0xc000cccccccccccd, double:-2.1)
            int r5 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r5 <= 0) goto L45
            r3 = 4611911198408756429(0x4000cccccccccccd, double:2.1)
            int r5 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r5 >= 0) goto L45
            r3 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r3 = r3 * r11
            r5 = 0
            r7 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 >= 0) goto L24
            double r3 = r3 - r7
            goto L25
        L24:
            double r3 = r3 + r7
        L25:
            int r3 = (int) r3
            r4 = -200(0xffffffffffffff38, float:NaN)
            if (r3 == r4) goto L42
            r4 = -100
            if (r3 == r4) goto L3f
            if (r3 == 0) goto L46
            r4 = 100
            if (r3 == r4) goto L3c
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 == r4) goto L39
            goto L45
        L39:
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.NEXT_2
            goto L46
        L3c:
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.NEXT
            goto L46
        L3f:
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.LAST
            goto L46
        L42:
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.LAST_2
            goto L46
        L45:
            r2 = 1
        L46:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.NOW
            int[] r4 = com.ibm.icu.text.RelativeDateTimeFormatter.AnonymousClass1.$SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit
            int r5 = r13.ordinal()
            r4 = r4[r5]
            switch(r4) {
                case 1: goto L82;
                case 2: goto L7f;
                case 3: goto L7c;
                case 4: goto L79;
                case 5: goto L76;
                case 6: goto L73;
                case 7: goto L70;
                case 8: goto L69;
                case 9: goto L66;
                case 10: goto L63;
                case 11: goto L60;
                case 12: goto L5d;
                case 13: goto L5a;
                case 14: goto L57;
                case 15: goto L54;
                default: goto L53;
            }
        L53:
            goto L85
        L54:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY
            goto L84
        L57:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.FRIDAY
            goto L84
        L5a:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.THURSDAY
            goto L84
        L5d:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.WEDNESDAY
            goto L84
        L60:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.TUESDAY
            goto L84
        L63:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.MONDAY
            goto L84
        L66:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY
            goto L84
        L69:
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r4 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.THIS
            if (r0 != r4) goto L85
            com.ibm.icu.text.RelativeDateTimeFormatter$Direction r0 = com.ibm.icu.text.RelativeDateTimeFormatter.Direction.PLAIN
            goto L84
        L70:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.MINUTE
            goto L84
        L73:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.HOUR
            goto L84
        L76:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY
            goto L84
        L79:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.WEEK
            goto L84
        L7c:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.MONTH
            goto L84
        L7f:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.QUARTER
            goto L84
        L82:
            com.ibm.icu.text.RelativeDateTimeFormatter$AbsoluteUnit r3 = com.ibm.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.YEAR
        L84:
            r1 = r2
        L85:
            if (r1 != 0) goto L94
            java.lang.String r0 = r10.formatAbsoluteImpl(r0, r3)
            if (r0 == 0) goto L94
            int r1 = r0.length()
            if (r1 <= 0) goto L94
            return r0
        L94:
            com.ibm.icu.impl.FormattedStringBuilder r11 = r10.formatNumericImpl(r11, r13)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.RelativeDateTimeFormatter.formatRelativeImpl(double, com.ibm.icu.text.RelativeDateTimeFormatter$RelativeDateTimeUnit):java.lang.CharSequence");
    }

    private String getAbsoluteUnitString(Style style, AbsoluteUnit absoluteUnit, Direction direction) {
        EnumMap<Direction, String> enumMap;
        String str;
        do {
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap2 = this.qualitativeUnitMap.get(style);
            if (enumMap2 != null && (enumMap = enumMap2.get(absoluteUnit)) != null && (str = enumMap.get(direction)) != null) {
                return str;
            }
            style = fallbackCache[style.ordinal()];
        } while (style != null);
        return null;
    }

    public String combineDateAndTime(String str, String str2) {
        return SimpleFormatterImpl.formatCompiledPattern(this.combinedDateAndTime, str2, str);
    }

    public NumberFormat getNumberFormat() {
        NumberFormat numberFormat;
        synchronized (this.numberFormat) {
            numberFormat = (NumberFormat) this.numberFormat.clone();
        }
        return numberFormat;
    }

    public DisplayContext getCapitalizationContext() {
        return this.capitalizationContext;
    }

    public Style getFormatStyle() {
        return this.style;
    }

    private String adjustForContext(String str) {
        String titleCase;
        if (this.breakIterator == null || str.length() == 0 || !UCharacter.isLowerCase(UCharacter.codePointAt(str, 0))) {
            return str;
        }
        synchronized (this.breakIterator) {
            titleCase = UCharacter.toTitleCase(this.locale, str, this.breakIterator, 768);
        }
        return titleCase;
    }

    private void checkNoAdjustForContext() {
        if (this.breakIterator != null) {
            throw new UnsupportedOperationException("Capitalization context is not supported in formatV");
        }
    }

    private RelativeDateTimeFormatter(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String str, PluralRules pluralRules, NumberFormat numberFormat, Style style, DisplayContext displayContext, BreakIterator breakIterator, ULocale uLocale) {
        this.qualitativeUnitMap = enumMap;
        this.patternMap = enumMap2;
        this.combinedDateAndTime = str;
        this.pluralRules = pluralRules;
        this.numberFormat = numberFormat;
        this.style = style;
        if (displayContext.type() != DisplayContext.Type.CAPITALIZATION) {
            throw new IllegalArgumentException(displayContext.toString());
        }
        this.capitalizationContext = displayContext;
        this.breakIterator = breakIterator;
        this.locale = uLocale;
        this.dateFormatSymbols = new DateFormatSymbols(uLocale);
    }

    private String getRelativeUnitPluralPattern(Style style, RelativeUnit relativeUnit, int i, StandardPlural standardPlural) {
        String relativeUnitPattern;
        return (standardPlural == StandardPlural.OTHER || (relativeUnitPattern = getRelativeUnitPattern(style, relativeUnit, i, standardPlural)) == null) ? getRelativeUnitPattern(style, relativeUnit, i, StandardPlural.OTHER) : relativeUnitPattern;
    }

    private String getRelativeUnitPattern(Style style, RelativeUnit relativeUnit, int i, StandardPlural standardPlural) {
        String[][] strArr;
        int iOrdinal = standardPlural.ordinal();
        do {
            EnumMap<RelativeUnit, String[][]> enumMap = this.patternMap.get(style);
            if (enumMap != null && (strArr = enumMap.get(relativeUnit)) != null && strArr[i][iOrdinal] != null) {
                return strArr[i][iOrdinal];
            }
            style = fallbackCache[style.ordinal()];
        } while (style != null);
        return null;
    }

    private static class RelativeDateTimeFormatterData {
        public final String dateTimePattern;
        public final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap;

        public RelativeDateTimeFormatterData(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String str) {
            this.qualitativeUnitMap = enumMap;
            this.relUnitPatternMap = enumMap2;
            this.dateTimePattern = str;
        }
    }

    private static class Cache {
        private final CacheBase<String, RelativeDateTimeFormatterData, ULocale> cache;

        private Cache() {
            this.cache = new SoftCache<String, RelativeDateTimeFormatterData, ULocale>() { // from class: com.ibm.icu.text.RelativeDateTimeFormatter.Cache.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.ibm.icu.impl.CacheBase
                public RelativeDateTimeFormatterData createInstance(String str, ULocale uLocale) {
                    return new Loader(uLocale).load();
                }
            };
        }

        /* synthetic */ Cache(AnonymousClass1 anonymousClass1) {
            this();
        }

        public RelativeDateTimeFormatterData get(ULocale uLocale) {
            return this.cache.getInstance(uLocale.toString(), uLocale);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Direction keyToDirection(UResource.Key key) {
        if (key.contentEquals("-2")) {
            return Direction.LAST_2;
        }
        if (key.contentEquals("-1")) {
            return Direction.LAST;
        }
        if (key.contentEquals("0")) {
            return Direction.THIS;
        }
        if (key.contentEquals("1")) {
            return Direction.NEXT;
        }
        if (key.contentEquals(RequestStatus.SUCCESS)) {
            return Direction.NEXT_2;
        }
        return null;
    }

    private static final class RelDateTimeDataSink extends UResource.Sink {
        int pastFutureIndex;
        Style style;
        DateTimeUnit unit;
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap = new EnumMap<>(Style.class);
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> styleRelUnitPatterns = new EnumMap<>(Style.class);
        StringBuilder sb = new StringBuilder();

        private enum DateTimeUnit {
            SECOND(RelativeUnit.SECONDS, null),
            MINUTE(RelativeUnit.MINUTES, AbsoluteUnit.MINUTE),
            HOUR(RelativeUnit.HOURS, AbsoluteUnit.HOUR),
            DAY(RelativeUnit.DAYS, AbsoluteUnit.DAY),
            WEEK(RelativeUnit.WEEKS, AbsoluteUnit.WEEK),
            MONTH(RelativeUnit.MONTHS, AbsoluteUnit.MONTH),
            QUARTER(RelativeUnit.QUARTERS, AbsoluteUnit.QUARTER),
            YEAR(RelativeUnit.YEARS, AbsoluteUnit.YEAR),
            SUNDAY(null, AbsoluteUnit.SUNDAY),
            MONDAY(null, AbsoluteUnit.MONDAY),
            TUESDAY(null, AbsoluteUnit.TUESDAY),
            WEDNESDAY(null, AbsoluteUnit.WEDNESDAY),
            THURSDAY(null, AbsoluteUnit.THURSDAY),
            FRIDAY(null, AbsoluteUnit.FRIDAY),
            SATURDAY(null, AbsoluteUnit.SATURDAY);

            AbsoluteUnit absUnit;
            RelativeUnit relUnit;

            DateTimeUnit(RelativeUnit relativeUnit, AbsoluteUnit absoluteUnit) {
                this.relUnit = relativeUnit;
                this.absUnit = absoluteUnit;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final DateTimeUnit orNullFromString(CharSequence charSequence) {
                int length = charSequence.length();
                if (length == 3) {
                    if ("day".contentEquals(charSequence)) {
                        return DAY;
                    }
                    if ("sun".contentEquals(charSequence)) {
                        return SUNDAY;
                    }
                    if ("mon".contentEquals(charSequence)) {
                        return MONDAY;
                    }
                    if ("tue".contentEquals(charSequence)) {
                        return TUESDAY;
                    }
                    if ("wed".contentEquals(charSequence)) {
                        return WEDNESDAY;
                    }
                    if ("thu".contentEquals(charSequence)) {
                        return THURSDAY;
                    }
                    if ("fri".contentEquals(charSequence)) {
                        return FRIDAY;
                    }
                    if ("sat".contentEquals(charSequence)) {
                        return SATURDAY;
                    }
                    return null;
                }
                if (length == 4) {
                    if ("hour".contentEquals(charSequence)) {
                        return HOUR;
                    }
                    if ("week".contentEquals(charSequence)) {
                        return WEEK;
                    }
                    if ("year".contentEquals(charSequence)) {
                        return YEAR;
                    }
                    return null;
                }
                if (length == 5) {
                    if ("month".contentEquals(charSequence)) {
                        return MONTH;
                    }
                    return null;
                }
                if (length == 6) {
                    if ("minute".contentEquals(charSequence)) {
                        return MINUTE;
                    }
                    if ("second".contentEquals(charSequence)) {
                        return SECOND;
                    }
                    return null;
                }
                if (length == 7 && "quarter".contentEquals(charSequence)) {
                    return QUARTER;
                }
                return null;
            }
        }

        private Style styleFromKey(UResource.Key key) {
            if (key.endsWith("-short")) {
                return Style.SHORT;
            }
            if (key.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private Style styleFromAlias(UResource.Value value) {
            String aliasString = value.getAliasString();
            if (aliasString.endsWith("-short")) {
                return Style.SHORT;
            }
            if (aliasString.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private static int styleSuffixLength(Style style) {
            int i = AnonymousClass1.$SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$Style[style.ordinal()];
            if (i != 1) {
                return i != 2 ? 0 : 7;
            }
            return 6;
        }

        public void consumeTableRelative(UResource.Key key, UResource.Value value) {
            AbsoluteUnit absoluteUnit;
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 0) {
                    String string = value.getString();
                    EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap = this.qualitativeUnitMap.get(this.style);
                    if (this.unit.relUnit != RelativeUnit.SECONDS || !key.contentEquals("0")) {
                        Direction directionKeyToDirection = RelativeDateTimeFormatter.keyToDirection(key);
                        if (directionKeyToDirection != null && (absoluteUnit = this.unit.absUnit) != null) {
                            if (enumMap == null) {
                                enumMap = new EnumMap<>(AbsoluteUnit.class);
                                this.qualitativeUnitMap.put((EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>>) this.style, (Style) enumMap);
                            }
                            EnumMap<Direction, String> enumMap2 = enumMap.get(absoluteUnit);
                            if (enumMap2 == null) {
                                enumMap2 = new EnumMap<>(Direction.class);
                                enumMap.put((EnumMap<AbsoluteUnit, EnumMap<Direction, String>>) absoluteUnit, (AbsoluteUnit) enumMap2);
                            }
                            if (enumMap2.get(directionKeyToDirection) == null) {
                                enumMap2.put((EnumMap<Direction, String>) directionKeyToDirection, (Direction) value.getString());
                            }
                        }
                    } else {
                        EnumMap<Direction, String> enumMap3 = enumMap.get(AbsoluteUnit.NOW);
                        if (enumMap3 == null) {
                            enumMap3 = new EnumMap<>(Direction.class);
                            enumMap.put((EnumMap<AbsoluteUnit, EnumMap<Direction, String>>) AbsoluteUnit.NOW, (AbsoluteUnit) enumMap3);
                        }
                        if (enumMap3.get(Direction.PLAIN) == null) {
                            enumMap3.put((EnumMap<Direction, String>) Direction.PLAIN, (Direction) string);
                        }
                    }
                }
            }
        }

        public void consumeTableRelativeTime(UResource.Key key, UResource.Value value) {
            if (this.unit.relUnit == null) {
                return;
            }
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (key.contentEquals("past")) {
                    this.pastFutureIndex = 0;
                } else if (key.contentEquals("future")) {
                    this.pastFutureIndex = 1;
                }
                consumeTimeDetail(key, value);
            }
        }

        public void consumeTimeDetail(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            EnumMap<RelativeUnit, String[][]> enumMap = this.styleRelUnitPatterns.get(this.style);
            if (enumMap == null) {
                enumMap = new EnumMap<>(RelativeUnit.class);
                this.styleRelUnitPatterns.put((EnumMap<Style, EnumMap<RelativeUnit, String[][]>>) this.style, (Style) enumMap);
            }
            String[][] strArr = enumMap.get(this.unit.relUnit);
            if (strArr == null) {
                strArr = (String[][]) Array.newInstance((Class<?>) String.class, 2, StandardPlural.COUNT);
                enumMap.put((EnumMap<RelativeUnit, String[][]>) this.unit.relUnit, (RelativeUnit) strArr);
            }
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 0) {
                    int iIndexFromString = StandardPlural.indexFromString(key.toString());
                    int i2 = this.pastFutureIndex;
                    if (strArr[i2][iIndexFromString] == null) {
                        strArr[i2][iIndexFromString] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 0, 1);
                    }
                }
            }
        }

        private void handlePlainDirection(UResource.Key key, UResource.Value value) {
            AbsoluteUnit absoluteUnit = this.unit.absUnit;
            if (absoluteUnit == null) {
                return;
            }
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> enumMap = this.qualitativeUnitMap.get(this.style);
            if (enumMap == null) {
                enumMap = new EnumMap<>(AbsoluteUnit.class);
                this.qualitativeUnitMap.put((EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>>) this.style, (Style) enumMap);
            }
            EnumMap<Direction, String> enumMap2 = enumMap.get(absoluteUnit);
            if (enumMap2 == null) {
                enumMap2 = new EnumMap<>(Direction.class);
                enumMap.put((EnumMap<AbsoluteUnit, EnumMap<Direction, String>>) absoluteUnit, (AbsoluteUnit) enumMap2);
            }
            if (enumMap2.get(Direction.PLAIN) == null) {
                enumMap2.put((EnumMap<Direction, String>) Direction.PLAIN, (Direction) value.toString());
            }
        }

        public void consumeTimeUnit(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (key.contentEquals("dn") && value.getType() == 0) {
                    handlePlainDirection(key, value);
                }
                if (value.getType() == 2) {
                    if (key.contentEquals("relative")) {
                        consumeTableRelative(key, value);
                    } else if (key.contentEquals("relativeTime")) {
                        consumeTableRelativeTime(key, value);
                    }
                }
            }
        }

        private void handleAlias(UResource.Key key, UResource.Value value, boolean z) {
            Style styleStyleFromKey = styleFromKey(key);
            if (DateTimeUnit.orNullFromString(key.substring(0, key.length() - styleSuffixLength(styleStyleFromKey))) != null) {
                Style styleStyleFromAlias = styleFromAlias(value);
                if (styleStyleFromKey != styleStyleFromAlias) {
                    if (RelativeDateTimeFormatter.fallbackCache[styleStyleFromKey.ordinal()] == null) {
                        RelativeDateTimeFormatter.fallbackCache[styleStyleFromKey.ordinal()] = styleStyleFromAlias;
                        return;
                    }
                    if (RelativeDateTimeFormatter.fallbackCache[styleStyleFromKey.ordinal()] == styleStyleFromAlias) {
                        return;
                    }
                    throw new ICUException("Inconsistent style fallback for style " + styleStyleFromKey + " to " + styleStyleFromAlias);
                }
                throw new ICUException("Invalid style fallback from " + styleStyleFromKey + " to itself");
            }
        }

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                if (value.getType() == 3) {
                    handleAlias(key, value, z);
                } else {
                    this.style = styleFromKey(key);
                    DateTimeUnit dateTimeUnitOrNullFromString = DateTimeUnit.orNullFromString(key.substring(0, key.length() - styleSuffixLength(this.style)));
                    this.unit = dateTimeUnitOrNullFromString;
                    if (dateTimeUnitOrNullFromString != null) {
                        consumeTimeUnit(key, value);
                    }
                }
            }
        }

        RelDateTimeDataSink() {
        }
    }

    /* renamed from: com.ibm.icu.text.RelativeDateTimeFormatter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit;
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$Style;

        static {
            int[] iArr = new int[Style.values().length];
            $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$Style = iArr;
            try {
                iArr[Style.SHORT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$Style[Style.NARROW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[RelativeDateTimeUnit.values().length];
            $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit = iArr2;
            try {
                iArr2[RelativeDateTimeUnit.YEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.QUARTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.MONTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.WEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.DAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.HOUR.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.MINUTE.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.SECOND.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.SUNDAY.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.MONDAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.TUESDAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.WEDNESDAY.ordinal()] = 12;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.THURSDAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.FRIDAY.ordinal()] = 14;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[RelativeDateTimeUnit.SATURDAY.ordinal()] = 15;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private static class Loader {
        private final ULocale ulocale;

        public Loader(ULocale uLocale) {
            this.ulocale = uLocale;
        }

        private String getDateTimePattern(ICUResourceBundle iCUResourceBundle) throws MissingResourceException {
            String stringWithFallback = iCUResourceBundle.getStringWithFallback("calendar/default");
            if (stringWithFallback == null || stringWithFallback.equals("")) {
                stringWithFallback = "gregorian";
            }
            ICUResourceBundle iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback("calendar/" + stringWithFallback + "/DateTimePatterns");
            if (iCUResourceBundleFindWithFallback == null && stringWithFallback.equals("gregorian")) {
                iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
            }
            if (iCUResourceBundleFindWithFallback == null || iCUResourceBundleFindWithFallback.getSize() < 9) {
                return "{1} {0}";
            }
            if (iCUResourceBundleFindWithFallback.get(8).getType() == 8) {
                return iCUResourceBundleFindWithFallback.get(8).getString(0);
            }
            return iCUResourceBundleFindWithFallback.getString(8);
        }

        public RelativeDateTimeFormatterData load() throws MissingResourceException {
            Style style;
            RelDateTimeDataSink relDateTimeDataSink = new RelDateTimeDataSink();
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, this.ulocale);
            iCUResourceBundle.getAllItemsWithFallback(GraphRequest.FIELDS_PARAM, relDateTimeDataSink);
            for (Style style2 : Style.values()) {
                Style style3 = RelativeDateTimeFormatter.fallbackCache[style2.ordinal()];
                if (style3 != null && (style = RelativeDateTimeFormatter.fallbackCache[style3.ordinal()]) != null && RelativeDateTimeFormatter.fallbackCache[style.ordinal()] != null) {
                    throw new IllegalStateException("Style fallback too deep");
                }
            }
            return new RelativeDateTimeFormatterData(relDateTimeDataSink.qualitativeUnitMap, relDateTimeDataSink.styleRelUnitPatterns, getDateTimePattern(iCUResourceBundle));
        }
    }
}
