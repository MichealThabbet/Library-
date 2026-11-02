package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class NumberFormatter {
    private static final UnlocalizedNumberFormatter BASE = new UnlocalizedNumberFormatter();
    static final long DEFAULT_THRESHOLD = 3;

    public enum DecimalSeparatorDisplay {
        AUTO,
        ALWAYS
    }

    public enum GroupingStrategy {
        OFF,
        MIN2,
        AUTO,
        ON_ALIGNED,
        THOUSANDS
    }

    public enum RoundingPriority {
        RELAXED,
        STRICT
    }

    public enum SignDisplay {
        AUTO,
        ALWAYS,
        NEVER,
        ACCOUNTING,
        ACCOUNTING_ALWAYS,
        EXCEPT_ZERO,
        ACCOUNTING_EXCEPT_ZERO,
        NEGATIVE,
        ACCOUNTING_NEGATIVE
    }

    public enum TrailingZeroDisplay {
        AUTO,
        HIDE_IF_WHOLE
    }

    public enum UnitWidth {
        NARROW,
        SHORT,
        FULL_NAME,
        ISO_CODE,
        FORMAL,
        VARIANT,
        HIDDEN
    }

    private NumberFormatter() {
    }

    public static UnlocalizedNumberFormatter with() {
        return BASE;
    }

    public static LocalizedNumberFormatter withLocale(Locale locale) {
        return BASE.locale(locale);
    }

    public static LocalizedNumberFormatter withLocale(ULocale uLocale) {
        return BASE.locale(uLocale);
    }

    public static UnlocalizedNumberFormatter forSkeleton(String str) {
        return NumberSkeletonImpl.getOrCreate(str);
    }

    @Deprecated
    public static UnlocalizedNumberFormatter fromDecimalFormat(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols, DecimalFormatProperties decimalFormatProperties2) {
        return NumberPropertyMapper.create(decimalFormatProperties, decimalFormatSymbols, decimalFormatProperties2);
    }
}
