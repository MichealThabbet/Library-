package com.ibm.icu.util;

import java.util.Date;

/* loaded from: classes2.dex */
public interface DateRule {
    Date firstAfter(Date date);

    Date firstBetween(Date date, Date date2);

    boolean isBetween(Date date, Date date2);

    boolean isOn(Date date);
}
