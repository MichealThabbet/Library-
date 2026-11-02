package org.apache.poi.ss.util;

import com.ibm.icu.text.DateFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.http.message.TokenParser;

/* loaded from: classes3.dex */
final class NormalisedDecimal {
    private static final BigDecimal BD_2_POW_24 = new BigDecimal(BigInteger.ONE.shiftLeft(24));
    private static final int C_2_POW_19 = 524288;
    private static final int EXPONENT_OFFSET = 14;
    private static final int FRAC_HALF = 8388608;
    private static final int LOG_BASE_10_OF_2_TIMES_2_POW_20 = 315653;
    private static final long MAX_REP_WHOLE_PART = 1000000000000000L;
    private final int _fractionalPart;
    private final int _relativeDecimalExponent;
    private final long _wholePart;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.apache.poi.ss.util.NormalisedDecimal create(java.math.BigInteger r2, int r3) {
        /*
            r0 = 49
            if (r3 > r0) goto Lb
            r0 = 46
            if (r3 >= r0) goto L9
            goto Lb
        L9:
            r0 = 0
            goto L19
        Lb:
            r0 = 15204352(0xe80000, float:2.1305835E-38)
            r1 = 315653(0x4d105, float:4.42324E-40)
            int r1 = r1 * r3
            int r0 = r0 - r1
            r1 = 524288(0x80000, float:7.34684E-40)
            int r0 = r0 + r1
            int r0 = r0 >> 20
            int r0 = -r0
        L19:
            org.apache.poi.ss.util.MutableFPNumber r1 = new org.apache.poi.ss.util.MutableFPNumber
            r1.<init>(r2, r3)
            if (r0 == 0) goto L24
            int r2 = -r0
            r1.multiplyByPowerOfTen(r2)
        L24:
            int r2 = r1.get64BitNormalisedExponent()
            switch(r2) {
                case 44: goto L60;
                case 45: goto L60;
                case 46: goto L59;
                case 47: goto L66;
                case 48: goto L66;
                case 49: goto L4b;
                case 50: goto L52;
                default: goto L2b;
            }
        L2b:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = "Bad binary exp "
            r3.append(r0)
            int r0 = r1.get64BitNormalisedExponent()
            r3.append(r0)
            java.lang.String r0 = "."
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L4b:
            boolean r2 = r1.isBelowMaxRep()
            if (r2 == 0) goto L52
            goto L66
        L52:
            r2 = -1
            r1.multiplyByPowerOfTen(r2)
            int r0 = r0 + 1
            goto L66
        L59:
            boolean r2 = r1.isAboveMinRep()
            if (r2 == 0) goto L60
            goto L66
        L60:
            r2 = 1
            r1.multiplyByPowerOfTen(r2)
            int r0 = r0 + (-1)
        L66:
            r1.normalise64bit()
            org.apache.poi.ss.util.NormalisedDecimal r2 = r1.createNormalisedDecimal(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.util.NormalisedDecimal.create(java.math.BigInteger, int):org.apache.poi.ss.util.NormalisedDecimal");
    }

    public NormalisedDecimal roundUnits() {
        long j = this._wholePart;
        if (this._fractionalPart >= 8388608) {
            j++;
        }
        int i = this._relativeDecimalExponent;
        if (j < MAX_REP_WHOLE_PART) {
            return new NormalisedDecimal(j, 0, i);
        }
        return new NormalisedDecimal(j / 10, 0, i + 1);
    }

    NormalisedDecimal(long j, int i, int i2) {
        this._wholePart = j;
        this._fractionalPart = i;
        this._relativeDecimalExponent = i2;
    }

    public ExpandedDouble normaliseBaseTwo() {
        MutableFPNumber mutableFPNumber = new MutableFPNumber(composeFrac(), 39);
        mutableFPNumber.multiplyByPowerOfTen(this._relativeDecimalExponent);
        mutableFPNumber.normalise64bit();
        return mutableFPNumber.createExpandedDouble();
    }

    BigInteger composeFrac() {
        long j = this._wholePart;
        int i = this._fractionalPart;
        return new BigInteger(new byte[]{(byte) (j >> 56), (byte) (j >> 48), (byte) (j >> 40), (byte) (j >> 32), (byte) (j >> 24), (byte) (j >> 16), (byte) (j >> 8), (byte) (j >> 0), (byte) (i >> 16), (byte) (i >> 8), (byte) (i >> 0)});
    }

    public String getSignificantDecimalDigits() {
        return Long.toString(this._wholePart);
    }

    public String getSignificantDecimalDigitsLastDigitRounded() {
        long j = this._wholePart + 5;
        StringBuilder sb = new StringBuilder(24);
        sb.append(j);
        sb.setCharAt(sb.length() - 1, '0');
        return sb.toString();
    }

    public int getDecimalExponent() {
        return this._relativeDecimalExponent + 14;
    }

    public int compareNormalised(NormalisedDecimal normalisedDecimal) {
        int i = this._relativeDecimalExponent - normalisedDecimal._relativeDecimalExponent;
        if (i != 0) {
            return i;
        }
        long j = this._wholePart;
        long j2 = normalisedDecimal._wholePart;
        if (j > j2) {
            return 1;
        }
        if (j < j2) {
            return -1;
        }
        return this._fractionalPart - normalisedDecimal._fractionalPart;
    }

    public BigDecimal getFractionalPart() {
        return new BigDecimal(this._fractionalPart).divide(BD_2_POW_24);
    }

    private String getFractionalDigits() {
        return this._fractionalPart == 0 ? "0" : getFractionalPart().toString().substring(2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" [");
        String strValueOf = String.valueOf(this._wholePart);
        sb.append(strValueOf.charAt(0));
        sb.append('.');
        sb.append(strValueOf.substring(1));
        sb.append(TokenParser.SP);
        sb.append(getFractionalDigits());
        sb.append(DateFormat.ABBR_WEEKDAY);
        sb.append(getDecimalExponent());
        sb.append("]");
        return sb.toString();
    }
}
