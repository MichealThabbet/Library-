package com.ibm.icu.text;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.util.Currency;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: classes2.dex */
public class CurrencyMetaInfo {

    @Deprecated
    protected static final CurrencyDigits defaultDigits = new CurrencyDigits(2, 0);
    private static final boolean hasData;
    private static final CurrencyMetaInfo impl;

    public static CurrencyMetaInfo getInstance() {
        return impl;
    }

    public static CurrencyMetaInfo getInstance(boolean z) {
        if (hasData) {
            return impl;
        }
        return null;
    }

    @Deprecated
    public static boolean hasData() {
        return hasData;
    }

    @Deprecated
    protected CurrencyMetaInfo() {
    }

    public static final class CurrencyFilter {
        private static final CurrencyFilter ALL = new CurrencyFilter(null, null, Long.MIN_VALUE, LongCompanionObject.MAX_VALUE, false);
        public final String currency;
        public final long from;
        public final String region;

        @Deprecated
        public final boolean tenderOnly;
        public final long to;

        private CurrencyFilter(String str, String str2, long j, long j2, boolean z) {
            this.region = str;
            this.currency = str2;
            this.from = j;
            this.to = j2;
            this.tenderOnly = z;
        }

        public static CurrencyFilter all() {
            return ALL;
        }

        public static CurrencyFilter now() {
            return ALL.withDate(new Date());
        }

        public static CurrencyFilter onRegion(String str) {
            return ALL.withRegion(str);
        }

        public static CurrencyFilter onCurrency(String str) {
            return ALL.withCurrency(str);
        }

        public static CurrencyFilter onDate(Date date) {
            return ALL.withDate(date);
        }

        public static CurrencyFilter onDateRange(Date date, Date date2) {
            return ALL.withDateRange(date, date2);
        }

        public static CurrencyFilter onDate(long j) {
            return ALL.withDate(j);
        }

        public static CurrencyFilter onDateRange(long j, long j2) {
            return ALL.withDateRange(j, j2);
        }

        public static CurrencyFilter onTender() {
            return ALL.withTender();
        }

        public CurrencyFilter withRegion(String str) {
            return new CurrencyFilter(str, this.currency, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withCurrency(String str) {
            return new CurrencyFilter(this.region, str, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withDate(Date date) {
            return new CurrencyFilter(this.region, this.currency, date.getTime(), date.getTime(), this.tenderOnly);
        }

        public CurrencyFilter withDateRange(Date date, Date date2) {
            return new CurrencyFilter(this.region, this.currency, date == null ? Long.MIN_VALUE : date.getTime(), date2 == null ? LongCompanionObject.MAX_VALUE : date2.getTime(), this.tenderOnly);
        }

        public CurrencyFilter withDate(long j) {
            return new CurrencyFilter(this.region, this.currency, j, j, this.tenderOnly);
        }

        public CurrencyFilter withDateRange(long j, long j2) {
            return new CurrencyFilter(this.region, this.currency, j, j2, this.tenderOnly);
        }

        public CurrencyFilter withTender() {
            return new CurrencyFilter(this.region, this.currency, this.from, this.to, true);
        }

        public boolean equals(Object obj) {
            return (obj instanceof CurrencyFilter) && equals((CurrencyFilter) obj);
        }

        public boolean equals(CurrencyFilter currencyFilter) {
            return Utility.sameObjects(this, currencyFilter) || (currencyFilter != null && equals(this.region, currencyFilter.region) && equals(this.currency, currencyFilter.currency) && this.from == currencyFilter.from && this.to == currencyFilter.to && this.tenderOnly == currencyFilter.tenderOnly);
        }

        public int hashCode() {
            String str = this.region;
            int iHashCode = str != null ? str.hashCode() : 0;
            String str2 = this.currency;
            if (str2 != null) {
                iHashCode = (iHashCode * 31) + str2.hashCode();
            }
            long j = this.from;
            int i = ((((iHashCode * 31) + ((int) j)) * 31) + ((int) (j >>> 32))) * 31;
            long j2 = this.to;
            return ((((i + ((int) j2)) * 31) + ((int) (j2 >>> 32))) * 31) + (this.tenderOnly ? 1 : 0);
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }

        private static boolean equals(String str, String str2) {
            return Utility.sameObjects(str, str2) || (str != null && str.equals(str2));
        }
    }

    public static final class CurrencyDigits {
        public final int fractionDigits;
        public final int roundingIncrement;

        public CurrencyDigits(int i, int i2) {
            this.fractionDigits = i;
            this.roundingIncrement = i2;
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }
    }

    public static final class CurrencyInfo {
        public final String code;
        public final long from;
        public final int priority;
        public final String region;
        private final boolean tender;
        public final long to;

        @Deprecated
        public CurrencyInfo(String str, String str2, long j, long j2, int i) {
            this(str, str2, j, j2, i, true);
        }

        @Deprecated
        public CurrencyInfo(String str, String str2, long j, long j2, int i, boolean z) {
            this.region = str;
            this.code = str2;
            this.from = j;
            this.to = j2;
            this.priority = i;
            this.tender = z;
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }

        public boolean isTender() {
            return this.tender;
        }
    }

    public List<CurrencyInfo> currencyInfo(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public List<String> currencies(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public List<String> regions(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public CurrencyDigits currencyDigits(String str) {
        return currencyDigits(str, Currency.CurrencyUsage.STANDARD);
    }

    public CurrencyDigits currencyDigits(String str, Currency.CurrencyUsage currencyUsage) {
        return defaultDigits;
    }

    static {
        CurrencyMetaInfo currencyMetaInfo;
        boolean z = false;
        try {
            currencyMetaInfo = (CurrencyMetaInfo) Class.forName("com.ibm.icu.impl.ICUCurrencyMetaInfo").newInstance();
            z = true;
        } catch (Throwable unused) {
            currencyMetaInfo = new CurrencyMetaInfo();
        }
        impl = currencyMetaInfo;
        hasData = z;
    }

    private static String dateString(long j) {
        if (j == LongCompanionObject.MAX_VALUE || j == Long.MIN_VALUE) {
            return null;
        }
        return Grego.timeToString(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String debugString(Object obj) {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        try {
            for (Field field : obj.getClass().getFields()) {
                Object obj2 = field.get(obj);
                if (obj2 != null) {
                    if (obj2 instanceof Date) {
                        strValueOf = dateString(((Date) obj2).getTime());
                    } else if (obj2 instanceof Long) {
                        strValueOf = dateString(((Long) obj2).longValue());
                    } else {
                        strValueOf = String.valueOf(obj2);
                    }
                    if (strValueOf != null) {
                        if (sb.length() > 0) {
                            sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
                        }
                        sb.append(field.getName());
                        sb.append("='");
                        sb.append(strValueOf);
                        sb.append("'");
                    }
                }
            }
        } catch (Throwable unused) {
        }
        sb.insert(0, obj.getClass().getSimpleName() + "(");
        sb.append(")");
        return sb.toString();
    }
}
