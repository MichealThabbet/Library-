package com.ibm.icu.number;

import com.ibm.icu.impl.number.range.RangeMacroProps;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.number.NumberRangeFormatterSettings;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
public abstract class NumberRangeFormatterSettings<T extends NumberRangeFormatterSettings<?>> {
    static final int KEY_COLLAPSE = 5;
    static final int KEY_FORMATTER_1 = 2;
    static final int KEY_FORMATTER_2 = 3;
    static final int KEY_IDENTITY_FALLBACK = 6;
    static final int KEY_LOCALE = 1;
    static final int KEY_MACROS = 0;
    static final int KEY_MAX = 7;
    static final int KEY_SAME_FORMATTERS = 4;
    private final int key;
    private final NumberRangeFormatterSettings<?> parent;
    private volatile RangeMacroProps resolvedMacros;
    private final Object value;

    abstract T create(int i, Object obj);

    NumberRangeFormatterSettings(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int i, Object obj) {
        this.parent = numberRangeFormatterSettings;
        this.key = i;
        this.value = obj;
    }

    public T numberFormatterBoth(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return (T) create(4, true).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterFirst(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return (T) create(4, false).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterSecond(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return (T) create(4, false).create(3, unlocalizedNumberFormatter);
    }

    public T collapse(NumberRangeFormatter.RangeCollapse rangeCollapse) {
        return (T) create(5, rangeCollapse);
    }

    public T identityFallback(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback) {
        return (T) create(6, rangeIdentityFallback);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    RangeMacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        RangeMacroProps rangeMacroProps = new RangeMacroProps();
        NumberRangeFormatterSettings numberRangeFormatterSettings = this;
        long j = 0;
        while (numberRangeFormatterSettings != null) {
            int i = numberRangeFormatterSettings.key;
            long j2 = 1 << i;
            if (0 != (j & j2)) {
                numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
            } else {
                j |= j2;
                switch (i) {
                    case 0:
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 1:
                        rangeMacroProps.loc = (ULocale) numberRangeFormatterSettings.value;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 2:
                        rangeMacroProps.formatter1 = (UnlocalizedNumberFormatter) numberRangeFormatterSettings.value;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 3:
                        rangeMacroProps.formatter2 = (UnlocalizedNumberFormatter) numberRangeFormatterSettings.value;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 4:
                        rangeMacroProps.sameFormatters = ((Boolean) numberRangeFormatterSettings.value).booleanValue() ? 1 : 0;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 5:
                        rangeMacroProps.collapse = (NumberRangeFormatter.RangeCollapse) numberRangeFormatterSettings.value;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    case 6:
                        rangeMacroProps.identityFallback = (NumberRangeFormatter.RangeIdentityFallback) numberRangeFormatterSettings.value;
                        numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
                        break;
                    default:
                        throw new AssertionError("Unknown key: " + numberRangeFormatterSettings.key);
                }
            }
        }
        if (rangeMacroProps.formatter1 != null) {
            rangeMacroProps.formatter1.resolve().loc = rangeMacroProps.loc;
        }
        if (rangeMacroProps.formatter2 != null) {
            rangeMacroProps.formatter2.resolve().loc = rangeMacroProps.loc;
        }
        this.resolvedMacros = rangeMacroProps;
        return rangeMacroProps;
    }

    public int hashCode() {
        return resolve().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof NumberRangeFormatterSettings)) {
            return resolve().equals(((NumberRangeFormatterSettings) obj).resolve());
        }
        return false;
    }
}
