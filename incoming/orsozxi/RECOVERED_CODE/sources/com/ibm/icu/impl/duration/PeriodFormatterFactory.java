package com.ibm.icu.impl.duration;

/* loaded from: classes2.dex */
public interface PeriodFormatterFactory {
    PeriodFormatter getFormatter();

    PeriodFormatterFactory setCountVariant(int i);

    PeriodFormatterFactory setDisplayLimit(boolean z);

    PeriodFormatterFactory setDisplayPastFuture(boolean z);

    PeriodFormatterFactory setLocale(String str);

    PeriodFormatterFactory setSeparatorVariant(int i);

    PeriodFormatterFactory setUnitVariant(int i);
}
