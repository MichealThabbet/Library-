package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodFormatterFactory;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;

/* loaded from: classes2.dex */
class BasicPeriodFormatter implements PeriodFormatter {
    private BasicPeriodFormatterFactory.Customizations customs;
    private PeriodFormatterData data;
    private BasicPeriodFormatterFactory factory;
    private String localeName;

    BasicPeriodFormatter(BasicPeriodFormatterFactory basicPeriodFormatterFactory, String str, PeriodFormatterData periodFormatterData, BasicPeriodFormatterFactory.Customizations customizations) {
        this.factory = basicPeriodFormatterFactory;
        this.localeName = str;
        this.data = periodFormatterData;
        this.customs = customizations;
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatter
    public String format(Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return format(period.timeLimit, period.inFuture, period.counts);
    }

    @Override // com.ibm.icu.impl.duration.PeriodFormatter
    public PeriodFormatter withLocale(String str) {
        if (this.localeName.equals(str)) {
            return this;
        }
        return new BasicPeriodFormatter(this.factory, str, this.factory.getData(str), this.customs);
    }

    private String format(int i, boolean z, int[] iArr) {
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i3;
        int i4;
        boolean zAppendUnitSeparator;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i2 = 1;
            if (i5 >= iArr.length) {
                break;
            }
            if (iArr[i5] > 0) {
                i6 |= 1 << i5;
            }
            i5++;
        }
        if (!this.data.allowZero()) {
            int i7 = 0;
            int i8 = 1;
            while (i7 < iArr.length) {
                if ((i6 & i8) != 0 && iArr[i7] == 1) {
                    i6 &= ~i8;
                }
                i7++;
                i8 <<= 1;
            }
            if (i6 == 0) {
                return null;
            }
        }
        if (this.data.useMilliseconds() == 0 || ((1 << TimeUnit.MILLISECOND.ordinal) & i6) == 0) {
            z2 = false;
        } else {
            byte b = TimeUnit.SECOND.ordinal;
            byte b2 = TimeUnit.MILLISECOND.ordinal;
            int i9 = 1 << b;
            int i10 = 1 << b2;
            int iUseMilliseconds = this.data.useMilliseconds();
            if (iUseMilliseconds != 1) {
                if (iUseMilliseconds == 2 && (i9 & i6) != 0) {
                    iArr[b] = iArr[b] + ((iArr[b2] - 1) / 1000);
                }
                z2 = false;
            } else {
                if ((i6 & i9) == 0) {
                    i6 |= i9;
                    iArr[b] = 1;
                }
                iArr[b] = iArr[b] + ((iArr[b2] - 1) / 1000);
            }
            i6 &= ~i10;
            z2 = true;
        }
        int length = iArr.length - 1;
        int i11 = 0;
        while (i11 < iArr.length && ((1 << i11) & i6) == 0) {
            i11++;
        }
        while (length > i11 && ((1 << length) & i6) == 0) {
            length--;
        }
        int i12 = i11;
        while (true) {
            if (i12 > length) {
                z3 = true;
                break;
            }
            if (((1 << i12) & i6) != 0 && iArr[i12] > 1) {
                z3 = false;
                break;
            }
            i12++;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i13 = (!this.customs.displayLimit || z3) ? 0 : i;
        int i14 = (!this.customs.displayDirection || z3) ? 0 : z ? 2 : 1;
        boolean zAppendPrefix = this.data.appendPrefix(i13, i14, stringBuffer);
        boolean z6 = i11 != length;
        boolean z7 = this.customs.separatorVariant != 0;
        int i15 = i11;
        boolean z8 = zAppendPrefix;
        boolean z9 = false;
        boolean z10 = true;
        while (i15 <= length) {
            if (z9) {
                this.data.appendSkippedUnit(stringBuffer);
                i3 = i15;
                z5 = true;
                z4 = false;
            } else {
                z4 = z9;
                z5 = z10;
                i3 = i15;
            }
            while (true) {
                i4 = i3 + 1;
                if (i4 >= length || ((i2 << i4) & i6) != 0) {
                    break;
                }
                i3 = i4;
                z4 = true;
            }
            TimeUnit timeUnit = TimeUnit.units[i15];
            int i16 = i15;
            int i17 = i13;
            StringBuffer stringBuffer2 = stringBuffer;
            boolean zAppendUnit = z4 | this.data.appendUnit(timeUnit, iArr[i15] - 1, i15 == length ? z2 ? (byte) 5 : this.customs.countVariant : (byte) 0, this.customs.unitVariant, z7, z8, z6, i15 == length, z5, stringBuffer2);
            if (this.customs.separatorVariant == 0 || i4 > length) {
                zAppendUnitSeparator = false;
            } else {
                zAppendUnitSeparator = this.data.appendUnitSeparator(timeUnit, this.customs.separatorVariant == 2, i16 == i11, i4 == length, stringBuffer2);
            }
            stringBuffer = stringBuffer2;
            i15 = i4;
            z9 = zAppendUnit;
            i13 = i17;
            z10 = false;
            z8 = zAppendUnitSeparator;
            i2 = 1;
        }
        StringBuffer stringBuffer3 = stringBuffer;
        this.data.appendSuffix(i13, i14, stringBuffer3);
        return stringBuffer3.toString();
    }
}
