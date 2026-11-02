package com.ibm.icu.impl.duration;

/* loaded from: classes2.dex */
public interface PeriodFormatter {
    String format(Period period);

    PeriodFormatter withLocale(String str);
}
