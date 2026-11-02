package com.ibm.icu.text;

import com.ibm.icu.impl.DontCareFieldPosition;
import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.LongNameHandler;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.ListFormatter;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class MeasureFormat extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CURRENCY_FORMAT = 2;
    private static final int MEASURE_FORMAT = 0;
    static final int NUMBER_FORMATTER_CURRENCY = 2;
    static final int NUMBER_FORMATTER_INTEGER = 3;
    static final int NUMBER_FORMATTER_STANDARD = 1;
    private static final int TIME_UNIT_FORMAT = 1;
    private static final Map<MeasureUnit, Integer> hmsTo012;
    private static final Map<ULocale, String> localeIdToRangeFormat;
    private static final SimpleCache<ULocale, NumericFormatters> localeToNumericDurationFormatters = new SimpleCache<>();
    static final long serialVersionUID = -7182021401701778240L;
    private final transient FormatWidth formatWidth;
    private transient NumberFormatterCacheEntry formatter1;
    private transient NumberFormatterCacheEntry formatter2;
    private transient NumberFormatterCacheEntry formatter3;
    private final transient NumberFormat numberFormat;
    private final transient LocalizedNumberFormatter numberFormatter;
    private final transient NumericFormatters numericFormatters;
    private final transient PluralRules rules;

    static {
        HashMap map = new HashMap();
        hmsTo012 = map;
        map.put(MeasureUnit.HOUR, 0);
        hmsTo012.put(MeasureUnit.MINUTE, 1);
        hmsTo012.put(MeasureUnit.SECOND, 2);
        localeIdToRangeFormat = new ConcurrentHashMap();
    }

    public enum FormatWidth {
        WIDE(ListFormatter.Width.WIDE, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.FULL_NAME),
        SHORT(ListFormatter.Width.SHORT, NumberFormatter.UnitWidth.SHORT, NumberFormatter.UnitWidth.ISO_CODE),
        NARROW(ListFormatter.Width.NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        NUMERIC(ListFormatter.Width.NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        DEFAULT_CURRENCY(ListFormatter.Width.SHORT, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.SHORT);

        final NumberFormatter.UnitWidth currencyWidth;
        final ListFormatter.Width listWidth;
        final NumberFormatter.UnitWidth unitWidth;

        FormatWidth(ListFormatter.Width width, NumberFormatter.UnitWidth unitWidth, NumberFormatter.UnitWidth unitWidth2) {
            this.listWidth = width;
            this.unitWidth = unitWidth;
            this.currencyWidth = unitWidth2;
        }
    }

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth) {
        return getInstance(uLocale, formatWidth, NumberFormat.getInstance(uLocale));
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth) {
        return getInstance(ULocale.forLocale(locale), formatWidth);
    }

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return new MeasureFormat(uLocale, formatWidth, numberFormat, null, null);
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return getInstance(ULocale.forLocale(locale), formatWidth, numberFormat);
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) throws IOException {
        int length = stringBuffer.length();
        int i = 0;
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            Measure[] measureArr = new Measure[collection.size()];
            for (Object obj2 : collection) {
                if (!(obj2 instanceof Measure)) {
                    throw new IllegalArgumentException(obj.toString());
                }
                measureArr[i] = (Measure) obj2;
                i++;
            }
            formatMeasuresInternal(stringBuffer, fieldPosition, measureArr);
        } else if (obj instanceof Measure[]) {
            formatMeasuresInternal(stringBuffer, fieldPosition, (Measure[]) obj);
        } else if (obj instanceof Measure) {
            FormattedStringBuilder measure = formatMeasure((Measure) obj);
            FormattedValueStringBuilderImpl.nextFieldPosition(measure, fieldPosition);
            Utility.appendTo(measure, stringBuffer);
        } else {
            throw new IllegalArgumentException(obj.toString());
        }
        if (length > 0 && fieldPosition.getEndIndex() != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + length);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + length);
        }
        return stringBuffer;
    }

    @Override // java.text.Format
    public Measure parseObject(String str, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public final String formatMeasures(Measure... measureArr) {
        return formatMeasures(new StringBuilder(), DontCareFieldPosition.INSTANCE, measureArr).toString();
    }

    public StringBuilder formatMeasurePerUnit(Measure measure, MeasureUnit measureUnit, StringBuilder sb, FieldPosition fieldPosition) throws IOException {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(measure.getNumber());
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        getUnitFormatterFromCache(1, measure.getUnit(), measureUnit).formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
        DecimalFormat.fieldPositionHelper(decimalQuantity_DualStorageBCD, formattedStringBuilder, fieldPosition, sb.length());
        Utility.appendTo(formattedStringBuilder, sb);
        return sb;
    }

    public StringBuilder formatMeasures(StringBuilder sb, FieldPosition fieldPosition, Measure... measureArr) throws IOException {
        int length = sb.length();
        formatMeasuresInternal(sb, fieldPosition, measureArr);
        if (length > 0 && fieldPosition.getEndIndex() > 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + length);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + length);
        }
        return sb;
    }

    private void formatMeasuresInternal(Appendable appendable, FieldPosition fieldPosition, Measure... measureArr) throws IOException {
        Number[] hms;
        if (measureArr.length == 0) {
            return;
        }
        if (measureArr.length == 1) {
            FormattedStringBuilder measure = formatMeasure(measureArr[0]);
            FormattedValueStringBuilderImpl.nextFieldPosition(measure, fieldPosition);
            Utility.appendTo(measure, appendable);
            return;
        }
        if (this.formatWidth == FormatWidth.NUMERIC && (hms = toHMS(measureArr)) != null) {
            formatNumeric(hms, appendable);
            return;
        }
        ListFormatter listFormatter = ListFormatter.getInstance(getLocale(), ListFormatter.Type.UNITS, this.formatWidth.listWidth);
        if (fieldPosition != DontCareFieldPosition.INSTANCE) {
            formatMeasuresSlowTrack(listFormatter, appendable, fieldPosition, measureArr);
            return;
        }
        String[] strArr = new String[measureArr.length];
        for (int i = 0; i < measureArr.length; i++) {
            if (i == measureArr.length - 1) {
                strArr[i] = formatMeasure(measureArr[i]).toString();
            } else {
                strArr[i] = formatMeasureInteger(measureArr[i]).toString();
            }
        }
        listFormatter.formatImpl(Arrays.asList(strArr), false).appendTo(appendable);
    }

    public String getUnitDisplayName(MeasureUnit measureUnit) {
        return LongNameHandler.getUnitDisplayName(getLocale(), measureUnit, this.formatWidth.unitWidth);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeasureFormat)) {
            return false;
        }
        MeasureFormat measureFormat = (MeasureFormat) obj;
        return getWidth() == measureFormat.getWidth() && getLocale().equals(measureFormat.getLocale()) && getNumberFormatInternal().equals(measureFormat.getNumberFormatInternal());
    }

    public final int hashCode() {
        return (((getLocale().hashCode() * 31) + getNumberFormatInternal().hashCode()) * 31) + getWidth().hashCode();
    }

    public FormatWidth getWidth() {
        if (this.formatWidth == FormatWidth.DEFAULT_CURRENCY) {
            return FormatWidth.WIDE;
        }
        return this.formatWidth;
    }

    public final ULocale getLocale() {
        return getLocale(ULocale.VALID_LOCALE);
    }

    public NumberFormat getNumberFormat() {
        return (NumberFormat) this.numberFormat.clone();
    }

    NumberFormat getNumberFormatInternal() {
        return this.numberFormat;
    }

    public static MeasureFormat getCurrencyFormat(ULocale uLocale) {
        return new CurrencyFormat(uLocale);
    }

    public static MeasureFormat getCurrencyFormat(Locale locale) {
        return getCurrencyFormat(ULocale.forLocale(locale));
    }

    public static MeasureFormat getCurrencyFormat() {
        return getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    MeasureFormat withLocale(ULocale uLocale) {
        return getInstance(uLocale, getWidth());
    }

    MeasureFormat withNumberFormat(NumberFormat numberFormat) {
        return new MeasureFormat(getLocale(), this.formatWidth, numberFormat, this.rules, this.numericFormatters);
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth) {
        this(uLocale, formatWidth, null, null, null);
    }

    private MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, PluralRules pluralRules, NumericFormatters numericFormatters) {
        NumberFormat numberFormat2;
        this.formatter1 = null;
        this.formatter2 = null;
        this.formatter3 = null;
        setLocale(uLocale, uLocale);
        this.formatWidth = formatWidth;
        this.rules = pluralRules == null ? PluralRules.forLocale(uLocale) : pluralRules;
        if (numberFormat == null) {
            numberFormat2 = NumberFormat.getInstance(uLocale);
        } else {
            numberFormat2 = (NumberFormat) numberFormat.clone();
        }
        this.numberFormat = numberFormat2;
        if (numericFormatters == null && formatWidth == FormatWidth.NUMERIC && (numericFormatters = localeToNumericDurationFormatters.get(uLocale)) == null) {
            numericFormatters = loadNumericFormatters(uLocale);
            localeToNumericDurationFormatters.put(uLocale, numericFormatters);
        }
        this.numericFormatters = numericFormatters;
        if (!(numberFormat2 instanceof DecimalFormat)) {
            throw new IllegalArgumentException();
        }
        this.numberFormatter = ((DecimalFormat) numberFormat2).toNumberFormatter().unitWidth(formatWidth.unitWidth);
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, PluralRules pluralRules) {
        this(uLocale, formatWidth, numberFormat, pluralRules, null);
        if (formatWidth == FormatWidth.NUMERIC) {
            throw new IllegalArgumentException("The format width 'numeric' is not allowed by this constructor");
        }
    }

    static class NumericFormatters {
        private String hourMinute;
        private String hourMinuteSecond;
        private String minuteSecond;

        public NumericFormatters(String str, String str2, String str3) {
            this.hourMinute = str;
            this.minuteSecond = str2;
            this.hourMinuteSecond = str3;
        }

        public String getHourMinute() {
            return this.hourMinute;
        }

        public String getMinuteSecond() {
            return this.minuteSecond;
        }

        public String getHourMinuteSecond() {
            return this.hourMinuteSecond;
        }
    }

    private static NumericFormatters loadNumericFormatters(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_UNIT_BASE_NAME, uLocale);
        return new NumericFormatters(loadNumericDurationFormat(iCUResourceBundle, "hm"), loadNumericDurationFormat(iCUResourceBundle, DateFormat.MINUTE_SECOND), loadNumericDurationFormat(iCUResourceBundle, "hms"));
    }

    static class NumberFormatterCacheEntry {
        LocalizedNumberFormatter formatter;
        MeasureUnit perUnit;
        int type;
        MeasureUnit unit;

        NumberFormatterCacheEntry() {
        }
    }

    private synchronized LocalizedNumberFormatter getUnitFormatterFromCache(int i, MeasureUnit measureUnit, MeasureUnit measureUnit2) {
        LocalizedNumberFormatter localizedNumberFormatterPrecision;
        if (this.formatter1 != null) {
            if (this.formatter1.type == i && this.formatter1.unit == measureUnit && this.formatter1.perUnit == measureUnit2) {
                return this.formatter1.formatter;
            }
            if (this.formatter2 != null) {
                if (this.formatter2.type == i && this.formatter2.unit == measureUnit && this.formatter2.perUnit == measureUnit2) {
                    return this.formatter2.formatter;
                }
                if (this.formatter3 != null && this.formatter3.type == i && this.formatter3.unit == measureUnit && this.formatter3.perUnit == measureUnit2) {
                    return this.formatter3.formatter;
                }
            }
        }
        if (i == 1) {
            localizedNumberFormatterPrecision = getNumberFormatter().unit(measureUnit).perUnit(measureUnit2).unitWidth(this.formatWidth.unitWidth);
        } else if (i == 2) {
            localizedNumberFormatterPrecision = NumberFormatter.withLocale(getLocale()).unit(measureUnit).perUnit(measureUnit2).unitWidth(this.formatWidth.currencyWidth);
        } else {
            localizedNumberFormatterPrecision = getNumberFormatter().unit(measureUnit).perUnit(measureUnit2).unitWidth(this.formatWidth.unitWidth).precision(Precision.integer().withMode(RoundingUtils.mathContextUnlimited(RoundingMode.DOWN)));
        }
        this.formatter3 = this.formatter2;
        this.formatter2 = this.formatter1;
        NumberFormatterCacheEntry numberFormatterCacheEntry = new NumberFormatterCacheEntry();
        this.formatter1 = numberFormatterCacheEntry;
        numberFormatterCacheEntry.type = i;
        this.formatter1.unit = measureUnit;
        this.formatter1.perUnit = measureUnit2;
        this.formatter1.formatter = localizedNumberFormatterPrecision;
        return localizedNumberFormatterPrecision;
    }

    synchronized void clearCache() {
        this.formatter1 = null;
        this.formatter2 = null;
        this.formatter3 = null;
    }

    LocalizedNumberFormatter getNumberFormatter() {
        return this.numberFormatter;
    }

    private FormattedStringBuilder formatMeasure(Measure measure) {
        MeasureUnit unit = measure.getUnit();
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(measure.getNumber());
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        if (unit instanceof Currency) {
            getUnitFormatterFromCache(2, unit, null).formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
        } else {
            getUnitFormatterFromCache(1, unit, null).formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
        }
        return formattedStringBuilder;
    }

    private FormattedStringBuilder formatMeasureInteger(Measure measure) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(measure.getNumber());
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        getUnitFormatterFromCache(3, measure.getUnit(), null).formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
        return formattedStringBuilder;
    }

    private void formatMeasuresSlowTrack(ListFormatter listFormatter, Appendable appendable, FieldPosition fieldPosition, Measure... measureArr) throws IOException {
        FormattedStringBuilder measureInteger;
        String[] strArr = new String[measureArr.length];
        FieldPosition fieldPosition2 = new FieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
        int i = -1;
        for (int i2 = 0; i2 < measureArr.length; i2++) {
            if (i2 == measureArr.length - 1) {
                measureInteger = formatMeasure(measureArr[i2]);
            } else {
                measureInteger = formatMeasureInteger(measureArr[i2]);
            }
            if (i == -1) {
                FormattedValueStringBuilderImpl.nextFieldPosition(measureInteger, fieldPosition2);
                if (fieldPosition2.getEndIndex() != 0) {
                    i = i2;
                }
            }
            strArr[i2] = measureInteger.toString();
        }
        ListFormatter.FormattedListBuilder impl = listFormatter.formatImpl(Arrays.asList(strArr), true);
        int offset = impl.getOffset(i);
        if (offset != -1) {
            fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex() + offset);
            fieldPosition.setEndIndex(fieldPosition2.getEndIndex() + offset);
        }
        impl.appendTo(appendable);
    }

    private static String loadNumericDurationFormat(ICUResourceBundle iCUResourceBundle, String str) {
        return iCUResourceBundle.getWithFallback(String.format("durationUnits/%s", str)).getString().replace("h", DateFormat.HOUR24);
    }

    private static Number[] toHMS(Measure[] measureArr) {
        Integer num;
        int iIntValue;
        Number[] numberArr = new Number[3];
        int length = measureArr.length;
        int i = -1;
        int i2 = 0;
        while (i2 < length) {
            Measure measure = measureArr[i2];
            if (measure.getNumber().doubleValue() < 0.0d || (num = hmsTo012.get(measure.getUnit())) == null || (iIntValue = num.intValue()) <= i) {
                return null;
            }
            numberArr[iIntValue] = measure.getNumber();
            i2++;
            i = iIntValue;
        }
        return numberArr;
    }

    private void formatNumeric(Number[] numberArr, Appendable appendable) throws IOException {
        String minuteSecond;
        Number number;
        int i;
        char c = 0;
        if (numberArr[0] != null && numberArr[2] != null) {
            minuteSecond = this.numericFormatters.getHourMinuteSecond();
            if (numberArr[1] == null) {
                numberArr[1] = 0;
            }
            numberArr[1] = Double.valueOf(Math.floor(numberArr[1].doubleValue()));
            numberArr[0] = Double.valueOf(Math.floor(numberArr[0].doubleValue()));
        } else if (numberArr[0] != null && numberArr[1] != null) {
            minuteSecond = this.numericFormatters.getHourMinute();
            numberArr[0] = Double.valueOf(Math.floor(numberArr[0].doubleValue()));
        } else if (numberArr[1] != null && numberArr[2] != null) {
            minuteSecond = this.numericFormatters.getMinuteSecond();
            numberArr[1] = Double.valueOf(Math.floor(numberArr[1].doubleValue()));
        } else {
            throw new IllegalStateException();
        }
        LocalizedNumberFormatter localizedNumberFormatterIntegerWidth = this.numberFormatter.integerWidth(IntegerWidth.zeroFillTo(2));
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        int i2 = 0;
        boolean z = false;
        while (i2 < minuteSecond.length()) {
            char cCharAt = minuteSecond.charAt(i2);
            if (cCharAt == 'H') {
                number = numberArr[c];
            } else if (cCharAt == 'm') {
                number = numberArr[1];
            } else if (cCharAt != 's') {
                number = 0;
            } else {
                number = numberArr[2];
            }
            if (cCharAt == '\'') {
                i = i2 + 1;
                if (i >= minuteSecond.length() || minuteSecond.charAt(i) != cCharAt) {
                    z = !z;
                } else {
                    formattedStringBuilder.appendChar16(cCharAt, null);
                    i2 = i;
                }
            } else if (cCharAt != 'H' && cCharAt != 'm' && cCharAt != 's') {
                formattedStringBuilder.appendChar16(cCharAt, null);
            } else if (z) {
                formattedStringBuilder.appendChar16(cCharAt, null);
            } else {
                i = i2 + 1;
                if (i < minuteSecond.length() && minuteSecond.charAt(i) == cCharAt) {
                    formattedStringBuilder.append(localizedNumberFormatterIntegerWidth.format(number), (Object) null);
                    i2 = i;
                } else {
                    formattedStringBuilder.append(this.numberFormatter.format(number), (Object) null);
                }
            }
            i2++;
            c = 0;
        }
        try {
            appendable.append(formattedStringBuilder);
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    Object toTimeUnitProxy() {
        return new MeasureProxy(getLocale(), this.formatWidth, getNumberFormatInternal(), 1);
    }

    Object toCurrencyProxy() {
        return new MeasureProxy(getLocale(), this.formatWidth, getNumberFormatInternal(), 2);
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureProxy(getLocale(), this.formatWidth, getNumberFormatInternal(), 0);
    }

    static class MeasureProxy implements Externalizable {
        private static final long serialVersionUID = -6033308329886716770L;
        private FormatWidth formatWidth;
        private HashMap<Object, Object> keyValues;
        private ULocale locale;
        private NumberFormat numberFormat;
        private int subClass;

        public MeasureProxy(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, int i) {
            this.locale = uLocale;
            this.formatWidth = formatWidth;
            this.numberFormat = numberFormat;
            this.subClass = i;
            this.keyValues = new HashMap<>();
        }

        public MeasureProxy() {
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.locale.toLanguageTag());
            objectOutput.writeByte(this.formatWidth.ordinal());
            objectOutput.writeObject(this.numberFormat);
            objectOutput.writeByte(this.subClass);
            objectOutput.writeObject(this.keyValues);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.locale = ULocale.forLanguageTag(objectInput.readUTF());
            this.formatWidth = MeasureFormat.fromFormatWidthOrdinal(objectInput.readByte() & 255);
            NumberFormat numberFormat = (NumberFormat) objectInput.readObject();
            this.numberFormat = numberFormat;
            if (numberFormat == null) {
                throw new InvalidObjectException("Missing number format.");
            }
            this.subClass = objectInput.readByte() & 255;
            HashMap<Object, Object> map = (HashMap) objectInput.readObject();
            this.keyValues = map;
            if (map == null) {
                throw new InvalidObjectException("Missing optional values map.");
            }
        }

        private TimeUnitFormat createTimeUnitFormat() throws InvalidObjectException {
            int i;
            if (this.formatWidth == FormatWidth.WIDE) {
                i = 0;
            } else {
                if (this.formatWidth != FormatWidth.SHORT) {
                    throw new InvalidObjectException("Bad width: " + this.formatWidth);
                }
                i = 1;
            }
            TimeUnitFormat timeUnitFormat = new TimeUnitFormat(this.locale, i);
            timeUnitFormat.setNumberFormat(this.numberFormat);
            return timeUnitFormat;
        }

        private Object readResolve() throws ObjectStreamException {
            int i = this.subClass;
            if (i == 0) {
                return MeasureFormat.getInstance(this.locale, this.formatWidth, this.numberFormat);
            }
            if (i == 1) {
                return createTimeUnitFormat();
            }
            if (i == 2) {
                return MeasureFormat.getCurrencyFormat(this.locale);
            }
            throw new InvalidObjectException("Unknown subclass: " + this.subClass);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FormatWidth fromFormatWidthOrdinal(int i) {
        FormatWidth[] formatWidthArrValues = FormatWidth.values();
        if (i < 0 || i >= formatWidthArrValues.length) {
            return FormatWidth.SHORT;
        }
        return formatWidthArrValues[i];
    }

    @Deprecated
    public static String getRangeFormat(ULocale uLocale, FormatWidth formatWidth) throws MissingResourceException {
        String stringWithFallback;
        String str;
        if (uLocale.getLanguage().equals("fr")) {
            return getRangeFormat(ULocale.ROOT, formatWidth);
        }
        String strCompileToStringMinMaxArguments = localeIdToRangeFormat.get(uLocale);
        if (strCompileToStringMinMaxArguments == null) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, uLocale);
            ULocale uLocale2 = iCUResourceBundle.getULocale();
            if (!uLocale.equals(uLocale2) && (str = localeIdToRangeFormat.get(uLocale)) != null) {
                localeIdToRangeFormat.put(uLocale, str);
                return str;
            }
            try {
                stringWithFallback = iCUResourceBundle.getStringWithFallback("NumberElements/" + NumberingSystem.getInstance(uLocale).getName() + "/miscPatterns/range");
            } catch (MissingResourceException unused) {
                stringWithFallback = iCUResourceBundle.getStringWithFallback("NumberElements/latn/patterns/range");
            }
            strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(stringWithFallback, new StringBuilder(), 2, 2);
            localeIdToRangeFormat.put(uLocale, strCompileToStringMinMaxArguments);
            if (!uLocale.equals(uLocale2)) {
                localeIdToRangeFormat.put(uLocale2, strCompileToStringMinMaxArguments);
            }
        }
        return strCompileToStringMinMaxArguments;
    }
}
