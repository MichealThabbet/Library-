package com.ibm.icu.number;

import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.NumberFormatterSettings;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.math.RoundingMode;

/* loaded from: classes2.dex */
public abstract class NumberFormatterSettings<T extends NumberFormatterSettings<?>> {
    static final int KEY_DECIMAL = 12;
    static final int KEY_GROUPING = 6;
    static final int KEY_INTEGER = 8;
    static final int KEY_LOCALE = 1;
    static final int KEY_MACROS = 0;
    static final int KEY_MAX = 18;
    static final int KEY_NOTATION = 2;
    static final int KEY_PADDER = 7;
    static final int KEY_PER_UNIT = 15;
    static final int KEY_PRECISION = 4;
    static final int KEY_ROUNDING_MODE = 5;
    static final int KEY_SCALE = 13;
    static final int KEY_SIGN = 11;
    static final int KEY_SYMBOLS = 9;
    static final int KEY_THRESHOLD = 14;
    static final int KEY_UNIT = 3;
    static final int KEY_UNIT_DISPLAY_CASE = 17;
    static final int KEY_UNIT_WIDTH = 10;
    static final int KEY_USAGE = 16;
    private final int key;
    private final NumberFormatterSettings<?> parent;
    private volatile MacroProps resolvedMacros;
    private final Object value;

    abstract T create(int i, Object obj);

    NumberFormatterSettings(NumberFormatterSettings<?> numberFormatterSettings, int i, Object obj) {
        this.parent = numberFormatterSettings;
        this.key = i;
        this.value = obj;
    }

    public T notation(Notation notation) {
        return (T) create(2, notation);
    }

    public T unit(MeasureUnit measureUnit) {
        return (T) create(3, measureUnit);
    }

    public T perUnit(MeasureUnit measureUnit) {
        return (T) create(15, measureUnit);
    }

    public T precision(Precision precision) {
        return (T) create(4, precision);
    }

    public T roundingMode(RoundingMode roundingMode) {
        return (T) create(5, roundingMode);
    }

    public T grouping(NumberFormatter.GroupingStrategy groupingStrategy) {
        return (T) create(6, groupingStrategy);
    }

    public T integerWidth(IntegerWidth integerWidth) {
        return (T) create(8, integerWidth);
    }

    public T symbols(DecimalFormatSymbols decimalFormatSymbols) {
        return (T) create(9, (DecimalFormatSymbols) decimalFormatSymbols.clone());
    }

    public T symbols(NumberingSystem numberingSystem) {
        return (T) create(9, numberingSystem);
    }

    public T unitWidth(NumberFormatter.UnitWidth unitWidth) {
        return (T) create(10, unitWidth);
    }

    public T sign(NumberFormatter.SignDisplay signDisplay) {
        return (T) create(11, signDisplay);
    }

    public T decimal(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay) {
        return (T) create(12, decimalSeparatorDisplay);
    }

    public T scale(Scale scale) {
        return (T) create(13, scale);
    }

    public T usage(String str) {
        if (str != null && str.isEmpty()) {
            return (T) create(16, null);
        }
        return (T) create(16, str);
    }

    @Deprecated
    public T unitDisplayCase(String str) {
        return (T) create(17, str);
    }

    @Deprecated
    public T macros(MacroProps macroProps) {
        return (T) create(0, macroProps);
    }

    @Deprecated
    public T padding(Padder padder) {
        return (T) create(7, padder);
    }

    @Deprecated
    public T threshold(Long l) {
        return (T) create(14, l);
    }

    public String toSkeleton() {
        return NumberSkeletonImpl.generate(resolve());
    }

    MacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        MacroProps macroProps = new MacroProps();
        NumberFormatterSettings numberFormatterSettings = this;
        long j = 0;
        while (numberFormatterSettings != null) {
            int i = numberFormatterSettings.key;
            long j2 = 1 << i;
            if (0 != (j & j2)) {
                numberFormatterSettings = numberFormatterSettings.parent;
            } else {
                j |= j2;
                switch (i) {
                    case 0:
                        macroProps.fallback((MacroProps) numberFormatterSettings.value);
                        break;
                    case 1:
                        macroProps.loc = (ULocale) numberFormatterSettings.value;
                        break;
                    case 2:
                        macroProps.notation = (Notation) numberFormatterSettings.value;
                        break;
                    case 3:
                        macroProps.unit = (MeasureUnit) numberFormatterSettings.value;
                        break;
                    case 4:
                        macroProps.precision = (Precision) numberFormatterSettings.value;
                        break;
                    case 5:
                        macroProps.roundingMode = (RoundingMode) numberFormatterSettings.value;
                        break;
                    case 6:
                        macroProps.grouping = numberFormatterSettings.value;
                        break;
                    case 7:
                        macroProps.padder = (Padder) numberFormatterSettings.value;
                        break;
                    case 8:
                        macroProps.integerWidth = (IntegerWidth) numberFormatterSettings.value;
                        break;
                    case 9:
                        macroProps.symbols = numberFormatterSettings.value;
                        break;
                    case 10:
                        macroProps.unitWidth = (NumberFormatter.UnitWidth) numberFormatterSettings.value;
                        break;
                    case 11:
                        macroProps.sign = (NumberFormatter.SignDisplay) numberFormatterSettings.value;
                        break;
                    case 12:
                        macroProps.decimal = (NumberFormatter.DecimalSeparatorDisplay) numberFormatterSettings.value;
                        break;
                    case 13:
                        macroProps.scale = (Scale) numberFormatterSettings.value;
                        break;
                    case 14:
                        macroProps.threshold = (Long) numberFormatterSettings.value;
                        break;
                    case 15:
                        macroProps.perUnit = (MeasureUnit) numberFormatterSettings.value;
                        break;
                    case 16:
                        macroProps.usage = (String) numberFormatterSettings.value;
                        break;
                    case 17:
                        macroProps.unitDisplayCase = (String) numberFormatterSettings.value;
                        break;
                    default:
                        throw new AssertionError("Unknown key: " + numberFormatterSettings.key);
                }
                numberFormatterSettings = numberFormatterSettings.parent;
            }
        }
        this.resolvedMacros = macroProps;
        return macroProps;
    }

    public int hashCode() {
        return resolve().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof NumberFormatterSettings)) {
            return resolve().equals(((NumberFormatterSettings) obj).resolve());
        }
        return false;
    }
}
