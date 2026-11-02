package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.impl.duration.impl.DataRecord;
import com.ibm.icu.impl.duration.impl.Utils;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class PeriodFormatterData {
    private static final int FORM_DUAL = 2;
    private static final int FORM_HALF_SPELLED = 6;
    private static final int FORM_PAUCAL = 3;
    private static final int FORM_PLURAL = 0;
    private static final int FORM_SINGULAR = 1;
    private static final int FORM_SINGULAR_NO_OMIT = 5;
    private static final int FORM_SINGULAR_SPELLED = 4;
    public static boolean trace = false;
    final DataRecord dr;
    String localeName;

    public PeriodFormatterData(String str, DataRecord dataRecord) {
        this.dr = dataRecord;
        this.localeName = str;
        if (str == null) {
            throw new NullPointerException("localename is null");
        }
        if (dataRecord == null) {
            throw new NullPointerException("data record is null");
        }
    }

    public int pluralization() {
        return this.dr.pl;
    }

    public boolean allowZero() {
        return this.dr.allowZero;
    }

    public boolean weeksAloneOnly() {
        return this.dr.weeksAloneOnly;
    }

    public int useMilliseconds() {
        return this.dr.useMilliseconds;
    }

    public boolean appendPrefix(int i, int i2, StringBuffer stringBuffer) {
        String str;
        if (this.dr.scopeData == null) {
            return false;
        }
        DataRecord.ScopeData scopeData = this.dr.scopeData[(i * 3) + i2];
        if (scopeData == null || (str = scopeData.prefix) == null) {
            return false;
        }
        stringBuffer.append(str);
        return scopeData.requiresDigitPrefix;
    }

    public void appendSuffix(int i, int i2, StringBuffer stringBuffer) {
        String str;
        if (this.dr.scopeData != null) {
            DataRecord.ScopeData scopeData = this.dr.scopeData[(i * 3) + i2];
            if (scopeData == null || (str = scopeData.suffix) == null) {
                return;
            }
            if (trace) {
                System.out.println("appendSuffix '" + str + "'");
            }
            stringBuffer.append(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean appendUnit(com.ibm.icu.impl.duration.TimeUnit r17, int r18, int r19, int r20, boolean r21, boolean r22, boolean r23, boolean r24, boolean r25, java.lang.StringBuffer r26) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.impl.PeriodFormatterData.appendUnit(com.ibm.icu.impl.duration.TimeUnit, int, int, int, boolean, boolean, boolean, boolean, boolean, java.lang.StringBuffer):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:134:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int appendCount(com.ibm.icu.impl.duration.TimeUnit r14, boolean r15, boolean r16, int r17, int r18, boolean r19, java.lang.String r20, boolean r21, java.lang.StringBuffer r22) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.impl.PeriodFormatterData.appendCount(com.ibm.icu.impl.duration.TimeUnit, boolean, boolean, int, int, boolean, java.lang.String, boolean, java.lang.StringBuffer):int");
    }

    public void appendCountValue(int i, int i2, int i3, StringBuffer stringBuffer) {
        int i4 = i / 1000;
        if (i3 == 0) {
            appendInteger(i4, i2, 10, stringBuffer);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(TokenParser.SP);
        }
        appendDigits(i4, i2, 10, stringBuffer);
        int i5 = i % 1000;
        if (i3 == 1) {
            i5 /= 100;
        } else if (i3 == 2) {
            i5 /= 10;
        }
        stringBuffer.append(this.dr.decimalSep);
        appendDigits(i5, i3, i3, stringBuffer);
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(TokenParser.SP);
        }
    }

    public void appendInteger(int i, int i2, int i3, StringBuffer stringBuffer) {
        String str;
        if (this.dr.numberNames != null && i < this.dr.numberNames.length && (str = this.dr.numberNames[i]) != null) {
            stringBuffer.append(str);
            return;
        }
        if (this.dr.requiresDigitSeparator && stringBuffer.length() > 0) {
            stringBuffer.append(TokenParser.SP);
        }
        byte b = this.dr.numberSystem;
        if (b == 0) {
            appendDigits(i, i2, i3, stringBuffer);
        } else if (b == 1) {
            stringBuffer.append(Utils.chineseNumber(i, Utils.ChineseDigits.TRADITIONAL));
        } else if (b == 2) {
            stringBuffer.append(Utils.chineseNumber(i, Utils.ChineseDigits.SIMPLIFIED));
        } else if (b == 3) {
            stringBuffer.append(Utils.chineseNumber(i, Utils.ChineseDigits.KOREAN));
        }
        if (this.dr.requiresDigitSeparator) {
            stringBuffer.append(TokenParser.SP);
        }
    }

    public void appendDigits(long j, int i, int i2, StringBuffer stringBuffer) {
        char[] cArr = new char[i2];
        int i3 = i2;
        while (i3 > 0 && j > 0) {
            i3--;
            cArr[i3] = (char) (this.dr.zero + (j % 10));
            j /= 10;
        }
        int i4 = i2 - i;
        while (i3 > i4) {
            i3--;
            cArr[i3] = this.dr.zero;
        }
        stringBuffer.append(cArr, i3, i2 - i3);
    }

    public void appendSkippedUnit(StringBuffer stringBuffer) {
        if (this.dr.skippedUnitMarker != null) {
            stringBuffer.append(this.dr.skippedUnitMarker);
        }
    }

    public boolean appendUnitSeparator(TimeUnit timeUnit, boolean z, boolean z2, boolean z3, StringBuffer stringBuffer) {
        if ((z && this.dr.unitSep != null) || this.dr.shortUnitSep != null) {
            if (z && this.dr.unitSep != null) {
                int i = (z2 ? 2 : 0) + (z3 ? 1 : 0);
                stringBuffer.append(this.dr.unitSep[i]);
                return this.dr.unitSepRequiresDP != null && this.dr.unitSepRequiresDP[i];
            }
            stringBuffer.append(this.dr.shortUnitSep);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b3, code lost:
    
        if (r8.dr.fractionHandling != 2) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fa A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int computeForm(com.ibm.icu.impl.duration.TimeUnit r9, int r10, int r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.impl.PeriodFormatterData.computeForm(com.ibm.icu.impl.duration.TimeUnit, int, int, boolean):int");
    }
}
