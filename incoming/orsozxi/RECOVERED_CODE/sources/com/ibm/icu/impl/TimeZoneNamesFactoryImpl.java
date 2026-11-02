package com.ibm.icu.impl;

import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
public class TimeZoneNamesFactoryImpl extends TimeZoneNames.Factory {
    @Override // com.ibm.icu.text.TimeZoneNames.Factory
    public TimeZoneNames getTimeZoneNames(ULocale uLocale) {
        return new TimeZoneNamesImpl(uLocale);
    }
}
