package com.ibm.icu.math;

import java.io.Serializable;
import java.math.BigInteger;
import org.apache.commons.codec.language.Soundex;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public class BigDecimal extends Number implements Serializable, Comparable<BigDecimal> {
    private static final int MaxArg = 999999999;
    private static final int MaxExp = 999999999;
    private static final int MinArg = -999999999;
    private static final int MinExp = -999999999;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    private static final byte isneg = -1;
    private static final byte ispos = 1;
    private static final byte iszero = 0;
    private static final long serialVersionUID = 8245355804974198832L;
    private int exp;
    private byte form;
    private byte ind;
    private byte[] mant;
    public static final BigDecimal ZERO = new BigDecimal(0L);
    public static final BigDecimal ONE = new BigDecimal(1L);
    public static final BigDecimal TEN = new BigDecimal(10);
    private static final MathContext plainMC = new MathContext(0, 0);
    private static byte[] bytecar = new byte[190];
    private static byte[] bytedig = diginit();

    public BigDecimal(java.math.BigDecimal bigDecimal) {
        this(bigDecimal.toString());
    }

    public BigDecimal(BigInteger bigInteger) {
        this(bigInteger.toString(10));
    }

    public BigDecimal(BigInteger bigInteger, int i) {
        this(bigInteger.toString(10));
        if (i < 0) {
            throw new NumberFormatException("Negative scale: " + i);
        }
        this.exp = -i;
    }

    public BigDecimal(char[] cArr) {
        this(cArr, 0, cArr.length);
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public BigDecimal(char[] r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 403
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.math.BigDecimal.<init>(char[], int, int):void");
    }

    public BigDecimal(double d) {
        this(new java.math.BigDecimal(d).toString());
    }

    public BigDecimal(int i) {
        this.form = (byte) 0;
        int i2 = 9;
        if (i <= 9 && i >= -9) {
            if (i == 0) {
                this.mant = ZERO.mant;
                this.ind = (byte) 0;
                return;
            }
            if (i == 1) {
                this.mant = ONE.mant;
                this.ind = (byte) 1;
                return;
            }
            if (i == -1) {
                this.mant = ONE.mant;
                this.ind = (byte) -1;
                return;
            }
            byte[] bArr = new byte[1];
            this.mant = bArr;
            if (i > 0) {
                bArr[0] = (byte) i;
                this.ind = (byte) 1;
                return;
            } else {
                bArr[0] = (byte) (-i);
                this.ind = (byte) -1;
                return;
            }
        }
        if (i > 0) {
            this.ind = (byte) 1;
            i = -i;
        } else {
            this.ind = (byte) -1;
        }
        int i3 = i;
        while (true) {
            i3 /= 10;
            if (i3 == 0) {
                break;
            } else {
                i2--;
            }
        }
        int i4 = 10 - i2;
        this.mant = new byte[i4];
        int i5 = i4 - 1;
        while (true) {
            this.mant[i5] = (byte) (-((byte) (i % 10)));
            i /= 10;
            if (i == 0) {
                return;
            } else {
                i5--;
            }
        }
    }

    public BigDecimal(long j) {
        this.form = (byte) 0;
        if (j > 0) {
            this.ind = (byte) 1;
            j = -j;
        } else if (j == 0) {
            this.ind = (byte) 0;
        } else {
            this.ind = (byte) -1;
        }
        int i = 18;
        long j2 = j;
        while (true) {
            j2 /= 10;
            if (j2 == 0) {
                break;
            } else {
                i--;
            }
        }
        int i2 = 19 - i;
        this.mant = new byte[i2];
        int i3 = i2 - 1;
        while (true) {
            this.mant[i3] = (byte) (-((byte) (j % 10)));
            j /= 10;
            if (j == 0) {
                return;
            } else {
                i3--;
            }
        }
    }

    public BigDecimal(String str) {
        this(str.toCharArray(), 0, str.length());
    }

    private BigDecimal() {
        this.form = (byte) 0;
    }

    public BigDecimal abs() {
        return abs(plainMC);
    }

    public BigDecimal abs(MathContext mathContext) {
        if (this.ind == -1) {
            return negate(mathContext);
        }
        return plus(mathContext);
    }

    public BigDecimal add(BigDecimal bigDecimal) {
        return add(bigDecimal, plainMC);
    }

    public BigDecimal add(BigDecimal bigDecimal, MathContext mathContext) {
        BigDecimal bigDecimalRound;
        byte[] bArr;
        int i;
        byte[] bArr2;
        int i2;
        int i3;
        byte b;
        BigDecimal bigDecimalRound2 = bigDecimal;
        if (mathContext.lostDigits) {
            checkdigits(bigDecimalRound2, mathContext.digits);
        }
        if (this.ind == 0 && mathContext.form != 0) {
            return bigDecimal.plus(mathContext);
        }
        if (bigDecimalRound2.ind == 0 && mathContext.form != 0) {
            return plus(mathContext);
        }
        int i4 = mathContext.digits;
        if (i4 > 0) {
            bigDecimalRound = this.mant.length > i4 ? clone(this).round(mathContext) : this;
            if (bigDecimalRound2.mant.length > i4) {
                bigDecimalRound2 = clone(bigDecimal).round(mathContext);
            }
        } else {
            bigDecimalRound = this;
        }
        BigDecimal bigDecimal2 = new BigDecimal();
        byte[] bArr3 = bigDecimalRound.mant;
        int length = bArr3.length;
        byte[] bArr4 = bigDecimalRound2.mant;
        int length2 = bArr4.length;
        int i5 = bigDecimalRound.exp;
        int i6 = bigDecimalRound2.exp;
        if (i5 == i6) {
            bigDecimal2.exp = i5;
        } else if (i5 > i6) {
            int i7 = (length + i5) - i6;
            if (i7 >= length2 + i4 + 1 && i4 > 0) {
                bigDecimal2.mant = bArr3;
                bigDecimal2.exp = i5;
                bigDecimal2.ind = bigDecimalRound.ind;
                if (length < i4) {
                    bigDecimal2.mant = extend(bigDecimalRound.mant, i4);
                    bigDecimal2.exp -= i4 - length;
                }
                return bigDecimal2.finish(mathContext, false);
            }
            int i8 = bigDecimalRound2.exp;
            bigDecimal2.exp = i8;
            int i9 = i4 + 1;
            if (i7 > i9 && i4 > 0) {
                int i10 = (i7 - i4) - 1;
                length2 -= i10;
                bigDecimal2.exp = i8 + i10;
                i7 = i9;
            }
            if (i7 > length) {
                length = i7;
            }
        } else {
            int i11 = (length2 + i6) - i5;
            if (i11 >= length + i4 + 1 && i4 > 0) {
                bigDecimal2.mant = bArr4;
                bigDecimal2.exp = i6;
                bigDecimal2.ind = bigDecimalRound2.ind;
                if (length2 < i4) {
                    bigDecimal2.mant = extend(bigDecimalRound2.mant, i4);
                    bigDecimal2.exp -= i4 - length2;
                }
                return bigDecimal2.finish(mathContext, false);
            }
            int i12 = bigDecimalRound.exp;
            bigDecimal2.exp = i12;
            int i13 = i4 + 1;
            if (i11 > i13 && i4 > 0) {
                int i14 = (i11 - i4) - 1;
                length -= i14;
                bigDecimal2.exp = i12 + i14;
                i11 = i13;
            }
            if (i11 > length2) {
                length2 = i11;
            }
        }
        byte b2 = bigDecimalRound.ind;
        if (b2 == 0) {
            bigDecimal2.ind = (byte) 1;
        } else {
            bigDecimal2.ind = b2;
        }
        if ((bigDecimalRound.ind == -1) == (bigDecimalRound2.ind == -1)) {
            bArr = bArr3;
            i = length;
            bArr2 = bArr4;
            i2 = length2;
            i3 = 1;
        } else if (bigDecimalRound2.ind != 0) {
            if ((length < length2) || (bigDecimalRound.ind == 0)) {
                bigDecimal2.ind = (byte) (-bigDecimal2.ind);
            } else {
                if (length <= length2) {
                    int length3 = bArr3.length - 1;
                    int length4 = bArr4.length - 1;
                    int i15 = 0;
                    int i16 = 0;
                    while (true) {
                        if (i15 <= length3) {
                            b = bArr3[i15];
                        } else if (i16 <= length4) {
                            b = 0;
                        } else if (mathContext.form != 0) {
                            return ZERO;
                        }
                        byte b3 = i16 <= length4 ? bArr4[i16] : (byte) 0;
                        if (b == b3) {
                            i15++;
                            i16++;
                        } else if (b < b3) {
                            bigDecimal2.ind = (byte) (-bigDecimal2.ind);
                        }
                    }
                }
                bArr = bArr3;
                i = length;
                bArr2 = bArr4;
                i2 = length2;
                i3 = -1;
            }
            bArr2 = bArr3;
            i2 = length;
            bArr = bArr4;
            i = length2;
            i3 = -1;
        } else {
            bArr = bArr3;
            i = length;
            bArr2 = bArr4;
            i2 = length2;
            i3 = -1;
        }
        bigDecimal2.mant = byteaddsub(bArr, i, bArr2, i2, i3, false);
        return bigDecimal2.finish(mathContext, false);
    }

    @Override // java.lang.Comparable
    public int compareTo(BigDecimal bigDecimal) {
        return compareTo(bigDecimal, plainMC);
    }

    public int compareTo(BigDecimal bigDecimal, MathContext mathContext) {
        if (mathContext.lostDigits) {
            checkdigits(bigDecimal, mathContext.digits);
        }
        if ((this.ind == bigDecimal.ind) & (this.exp == bigDecimal.exp)) {
            int length = this.mant.length;
            byte[] bArr = bigDecimal.mant;
            if (length < bArr.length) {
                return (byte) (-this.ind);
            }
            if (length > bArr.length) {
                return this.ind;
            }
            if ((length <= mathContext.digits) | (mathContext.digits == 0)) {
                int i = 0;
                while (length > 0) {
                    byte[] bArr2 = this.mant;
                    byte b = bArr2[i];
                    byte[] bArr3 = bigDecimal.mant;
                    if (b < bArr3[i]) {
                        return (byte) (-this.ind);
                    }
                    if (bArr2[i] > bArr3[i]) {
                        return this.ind;
                    }
                    length--;
                    i++;
                }
                return 0;
            }
        } else {
            byte b2 = this.ind;
            byte b3 = bigDecimal.ind;
            if (b2 < b3) {
                return -1;
            }
            if (b2 > b3) {
                return 1;
            }
        }
        BigDecimal bigDecimalClone = clone(bigDecimal);
        bigDecimalClone.ind = (byte) (-bigDecimalClone.ind);
        return add(bigDecimalClone, mathContext).ind;
    }

    public BigDecimal divide(BigDecimal bigDecimal) {
        return dodivide('D', bigDecimal, plainMC, -1);
    }

    public BigDecimal divide(BigDecimal bigDecimal, int i) {
        return dodivide('D', bigDecimal, new MathContext(0, 0, false, i), -1);
    }

    public BigDecimal divide(BigDecimal bigDecimal, int i, int i2) {
        if (i < 0) {
            throw new ArithmeticException("Negative scale: " + i);
        }
        return dodivide('D', bigDecimal, new MathContext(0, 0, false, i2), i);
    }

    public BigDecimal divide(BigDecimal bigDecimal, MathContext mathContext) {
        return dodivide('D', bigDecimal, mathContext, -1);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal) {
        return dodivide('I', bigDecimal, plainMC, 0);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal, MathContext mathContext) {
        return dodivide('I', bigDecimal, mathContext, 0);
    }

    public BigDecimal max(BigDecimal bigDecimal) {
        return max(bigDecimal, plainMC);
    }

    public BigDecimal max(BigDecimal bigDecimal, MathContext mathContext) {
        if (compareTo(bigDecimal, mathContext) >= 0) {
            return plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal min(BigDecimal bigDecimal) {
        return min(bigDecimal, plainMC);
    }

    public BigDecimal min(BigDecimal bigDecimal, MathContext mathContext) {
        if (compareTo(bigDecimal, mathContext) <= 0) {
            return plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return multiply(bigDecimal, plainMC);
    }

    public BigDecimal multiply(BigDecimal bigDecimal, MathContext mathContext) {
        int i;
        BigDecimal bigDecimalRound;
        byte[] bArr;
        BigDecimal bigDecimalRound2 = bigDecimal;
        if (mathContext.lostDigits) {
            checkdigits(bigDecimalRound2, mathContext.digits);
        }
        int i2 = mathContext.digits;
        if (i2 > 0) {
            bigDecimalRound = this.mant.length > i2 ? clone(this).round(mathContext) : this;
            if (bigDecimalRound2.mant.length > i2) {
                bigDecimalRound2 = clone(bigDecimal).round(mathContext);
            }
            i = 0;
        } else {
            int i3 = this.exp;
            i = i3 > 0 ? i3 + 0 : 0;
            int i4 = bigDecimalRound2.exp;
            if (i4 > 0) {
                i += i4;
            }
            bigDecimalRound = this;
        }
        byte[] bArr2 = bigDecimalRound.mant;
        int length = bArr2.length;
        byte[] bArr3 = bigDecimalRound2.mant;
        if (length < bArr3.length) {
            bArr = bArr3;
        } else {
            bArr = bArr2;
            bArr2 = bArr3;
        }
        int length2 = (bArr2.length + bArr.length) - 1;
        int i5 = bArr2[0] * bArr[0] > 9 ? length2 + 1 : length2;
        BigDecimal bigDecimal2 = new BigDecimal();
        int i6 = length2;
        byte[] bArrByteaddsub = new byte[i5];
        int length3 = bArr2.length;
        int i7 = 0;
        while (length3 > 0) {
            byte b = bArr2[i7];
            if (b != 0) {
                bArrByteaddsub = byteaddsub(bArrByteaddsub, bArrByteaddsub.length, bArr, i6, b, true);
            }
            i6--;
            length3--;
            i7++;
        }
        bigDecimal2.ind = (byte) (bigDecimalRound.ind * bigDecimalRound2.ind);
        bigDecimal2.exp = (bigDecimalRound.exp + bigDecimalRound2.exp) - i;
        if (i == 0) {
            bigDecimal2.mant = bArrByteaddsub;
        } else {
            bigDecimal2.mant = extend(bArrByteaddsub, bArrByteaddsub.length + i);
        }
        return bigDecimal2.finish(mathContext, false);
    }

    public BigDecimal negate() {
        return negate(plainMC);
    }

    public BigDecimal negate(MathContext mathContext) {
        if (mathContext.lostDigits) {
            checkdigits((BigDecimal) null, mathContext.digits);
        }
        BigDecimal bigDecimalClone = clone(this);
        bigDecimalClone.ind = (byte) (-bigDecimalClone.ind);
        return bigDecimalClone.finish(mathContext, false);
    }

    public BigDecimal plus() {
        return plus(plainMC);
    }

    public BigDecimal plus(MathContext mathContext) {
        if (mathContext.lostDigits) {
            checkdigits((BigDecimal) null, mathContext.digits);
        }
        return (mathContext.form == 0 && this.form == 0 && (this.mant.length <= mathContext.digits || mathContext.digits == 0)) ? this : clone(this).finish(mathContext, false);
    }

    public BigDecimal pow(BigDecimal bigDecimal) {
        return pow(bigDecimal, plainMC);
    }

    public BigDecimal pow(BigDecimal bigDecimal, MathContext mathContext) {
        BigDecimal bigDecimalRound;
        int length;
        if (mathContext.lostDigits) {
            checkdigits(bigDecimal, mathContext.digits);
        }
        int iIntcheck = bigDecimal.intcheck(-999999999, 999999999);
        int i = mathContext.digits;
        boolean z = false;
        if (i == 0) {
            if (bigDecimal.ind == -1) {
                throw new ArithmeticException("Negative power: " + bigDecimal.toString());
            }
            bigDecimalRound = this;
            length = 0;
        } else {
            if (bigDecimal.mant.length + bigDecimal.exp > i) {
                throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
            }
            bigDecimalRound = this.mant.length > i ? clone(this).round(mathContext) : this;
            length = i + bigDecimal.mant.length + bigDecimal.exp + 1;
        }
        MathContext mathContext2 = new MathContext(length, mathContext.form, false, mathContext.roundingMode);
        BigDecimal bigDecimalDivide = ONE;
        if (iIntcheck == 0) {
            return bigDecimalDivide;
        }
        if (iIntcheck < 0) {
            iIntcheck = -iIntcheck;
        }
        int i2 = 1;
        while (true) {
            iIntcheck += iIntcheck;
            if (iIntcheck < 0) {
                bigDecimalDivide = bigDecimalDivide.multiply(bigDecimalRound, mathContext2);
                z = true;
            }
            if (i2 == 31) {
                break;
            }
            if (z) {
                bigDecimalDivide = bigDecimalDivide.multiply(bigDecimalDivide, mathContext2);
            }
            i2++;
        }
        if (bigDecimal.ind < 0) {
            bigDecimalDivide = ONE.divide(bigDecimalDivide, mathContext2);
        }
        return bigDecimalDivide.finish(mathContext, true);
    }

    public BigDecimal remainder(BigDecimal bigDecimal) {
        return dodivide('R', bigDecimal, plainMC, -1);
    }

    public BigDecimal remainder(BigDecimal bigDecimal, MathContext mathContext) {
        return dodivide('R', bigDecimal, mathContext, -1);
    }

    public BigDecimal subtract(BigDecimal bigDecimal) {
        return subtract(bigDecimal, plainMC);
    }

    public BigDecimal subtract(BigDecimal bigDecimal, MathContext mathContext) {
        if (mathContext.lostDigits) {
            checkdigits(bigDecimal, mathContext.digits);
        }
        BigDecimal bigDecimalClone = clone(bigDecimal);
        bigDecimalClone.ind = (byte) (-bigDecimalClone.ind);
        return add(bigDecimalClone, mathContext);
    }

    public byte byteValueExact() {
        int iIntValueExact = intValueExact();
        if (!(iIntValueExact < -128) && !(iIntValueExact > 127)) {
            return (byte) iIntValueExact;
        }
        throw new ArithmeticException("Conversion overflow: " + toString());
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return Double.valueOf(toString()).doubleValue();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BigDecimal)) {
            return false;
        }
        BigDecimal bigDecimal = (BigDecimal) obj;
        if (this.ind != bigDecimal.ind) {
            return false;
        }
        if ((this.mant.length == bigDecimal.mant.length) & (this.exp == bigDecimal.exp) & (this.form == bigDecimal.form)) {
            int length = this.mant.length;
            int i = 0;
            while (length > 0) {
                if (this.mant[i] != bigDecimal.mant[i]) {
                    return false;
                }
                length--;
                i++;
            }
        } else {
            char[] cArrLayout = layout();
            char[] cArrLayout2 = bigDecimal.layout();
            if (cArrLayout.length != cArrLayout2.length) {
                return false;
            }
            int length2 = cArrLayout.length;
            int i2 = 0;
            while (length2 > 0) {
                if (cArrLayout[i2] != cArrLayout2[i2]) {
                    return false;
                }
                length2--;
                i2++;
            }
        }
        return true;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return Float.valueOf(toString()).floatValue();
    }

    public String format(int i, int i2) {
        return format(i, i2, -1, -1, 1, 4);
    }

    public String format(int i, int i2, int i3, int i4, int i5, int i6) {
        int length;
        if ((i < -1) | (i == 0)) {
            badarg("format", 1, String.valueOf(i));
        }
        if (i2 < -1) {
            badarg("format", 2, String.valueOf(i2));
        }
        if ((i3 < -1) | (i3 == 0)) {
            badarg("format", 3, String.valueOf(i3));
        }
        if (i4 < -1) {
            badarg("format", 4, String.valueOf(i3));
        }
        if (i5 != 1 && i5 != 2) {
            if (i5 == -1) {
                i5 = 1;
            } else {
                badarg("format", 5, String.valueOf(i5));
            }
        }
        if (i6 != 4) {
            if (i6 == -1) {
                i6 = 4;
            } else {
                try {
                    new MathContext(9, 1, false, i6);
                } catch (IllegalArgumentException unused) {
                    badarg("format", 6, String.valueOf(i6));
                }
            }
        }
        BigDecimal bigDecimalClone = clone(this);
        if (i4 == -1 || bigDecimalClone.ind == 0) {
            bigDecimalClone.form = (byte) 0;
        } else {
            int length2 = bigDecimalClone.exp + bigDecimalClone.mant.length;
            if (length2 > i4 || length2 < -5) {
                bigDecimalClone.form = (byte) i5;
            } else {
                bigDecimalClone.form = (byte) 0;
            }
        }
        if (i2 >= 0) {
            while (true) {
                byte b = bigDecimalClone.form;
                if (b == 0) {
                    length = -bigDecimalClone.exp;
                } else if (b == 1) {
                    length = bigDecimalClone.mant.length - 1;
                } else {
                    int length3 = ((bigDecimalClone.exp + bigDecimalClone.mant.length) - 1) % 3;
                    if (length3 < 0) {
                        length3 += 3;
                    }
                    int i7 = length3 + 1;
                    byte[] bArr = bigDecimalClone.mant;
                    length = i7 >= bArr.length ? 0 : bArr.length - i7;
                }
                if (length == i2) {
                    break;
                }
                if (length < i2) {
                    byte[] bArr2 = bigDecimalClone.mant;
                    bigDecimalClone.mant = extend(bArr2, (bArr2.length + i2) - length);
                    int i8 = bigDecimalClone.exp - (i2 - length);
                    bigDecimalClone.exp = i8;
                    if (i8 < -999999999) {
                        throw new ArithmeticException("Exponent Overflow: " + bigDecimalClone.exp);
                    }
                } else {
                    int i9 = length - i2;
                    byte[] bArr3 = bigDecimalClone.mant;
                    if (i9 > bArr3.length) {
                        bigDecimalClone.mant = ZERO.mant;
                        bigDecimalClone.ind = (byte) 0;
                        bigDecimalClone.exp = 0;
                    } else {
                        int length4 = bArr3.length - i9;
                        int i10 = bigDecimalClone.exp;
                        bigDecimalClone.round(length4, i6);
                        if (bigDecimalClone.exp - i10 == i9) {
                            break;
                        }
                    }
                }
            }
        }
        char[] cArrLayout = bigDecimalClone.layout();
        if (i > 0) {
            int length5 = cArrLayout.length;
            int i11 = 0;
            while (length5 > 0 && cArrLayout[i11] != '.' && cArrLayout[i11] != 'E') {
                length5--;
                i11++;
            }
            if (i11 > i) {
                badarg("format", 1, String.valueOf(i));
            }
            if (i11 < i) {
                char[] cArr = new char[(cArrLayout.length + i) - i11];
                int i12 = i - i11;
                int i13 = 0;
                while (i12 > 0) {
                    cArr[i13] = TokenParser.SP;
                    i12--;
                    i13++;
                }
                System.arraycopy(cArrLayout, 0, cArr, i13, cArrLayout.length);
                cArrLayout = cArr;
            }
        }
        if (i3 > 0) {
            int length6 = cArrLayout.length - 1;
            int length7 = cArrLayout.length - 1;
            while (length6 > 0 && cArrLayout[length7] != 'E') {
                length6--;
                length7--;
            }
            if (length7 == 0) {
                char[] cArr2 = new char[cArrLayout.length + i3 + 2];
                System.arraycopy(cArrLayout, 0, cArr2, 0, cArrLayout.length);
                int i14 = i3 + 2;
                int length8 = cArrLayout.length;
                while (i14 > 0) {
                    cArr2[length8] = TokenParser.SP;
                    i14--;
                    length8++;
                }
                cArrLayout = cArr2;
            } else {
                int length9 = (cArrLayout.length - length7) - 2;
                if (length9 > i3) {
                    badarg("format", 3, String.valueOf(i3));
                }
                if (length9 < i3) {
                    char[] cArr3 = new char[(cArrLayout.length + i3) - length9];
                    int i15 = length7 + 2;
                    System.arraycopy(cArrLayout, 0, cArr3, 0, i15);
                    int i16 = i3 - length9;
                    int i17 = i15;
                    while (i16 > 0) {
                        cArr3[i17] = '0';
                        i16--;
                        i17++;
                    }
                    System.arraycopy(cArrLayout, i15, cArr3, i17, length9);
                    cArrLayout = cArr3;
                }
            }
        }
        return new String(cArrLayout);
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // java.lang.Number
    public int intValue() {
        return toBigInteger().intValue();
    }

    public int intValueExact() {
        if (this.ind == 0) {
            return 0;
        }
        byte[] bArr = this.mant;
        int length = bArr.length - 1;
        int i = this.exp;
        if (i < 0) {
            length += i;
            if (!allzero(bArr, length + 1)) {
                throw new ArithmeticException("Decimal part non-zero: " + toString());
            }
            if (length < 0) {
                return 0;
            }
            i = 0;
        } else if (i + length > 9) {
            throw new ArithmeticException("Conversion overflow: " + toString());
        }
        int i2 = i + length;
        int i3 = 0;
        for (int i4 = 0; i4 <= i2; i4++) {
            i3 *= 10;
            if (i4 <= length) {
                i3 += this.mant[i4];
            }
        }
        if (i2 == 9) {
            int i5 = i3 / 1000000000;
            byte[] bArr2 = this.mant;
            if (i5 != bArr2[0]) {
                if (i3 == Integer.MIN_VALUE && this.ind == -1 && bArr2[0] == 2) {
                    return i3;
                }
                throw new ArithmeticException("Conversion overflow: " + toString());
            }
        }
        return this.ind == 1 ? i3 : -i3;
    }

    @Override // java.lang.Number
    public long longValue() {
        return toBigInteger().longValue();
    }

    public long longValueExact() {
        long j = 0;
        if (this.ind == 0) {
            return 0L;
        }
        byte[] bArr = this.mant;
        int length = bArr.length - 1;
        int i = this.exp;
        if (i < 0) {
            length += i;
            if (!allzero(this.mant, length < 0 ? 0 : length + 1)) {
                throw new ArithmeticException("Decimal part non-zero: " + toString());
            }
            if (length < 0) {
                return 0L;
            }
            i = 0;
        } else if (bArr.length + i > 18) {
            throw new ArithmeticException("Conversion overflow: " + toString());
        }
        int i2 = i + length;
        for (int i3 = 0; i3 <= i2; i3++) {
            j *= 10;
            if (i3 <= length) {
                j += this.mant[i3];
            }
        }
        if (i2 == 18) {
            long j2 = j / 1000000000000000000L;
            byte[] bArr2 = this.mant;
            if (j2 != bArr2[0]) {
                if (j == Long.MIN_VALUE && this.ind == -1 && bArr2[0] == 9) {
                    return j;
                }
                throw new ArithmeticException("Conversion overflow: " + toString());
            }
        }
        return this.ind == 1 ? j : -j;
    }

    public BigDecimal movePointLeft(int i) {
        BigDecimal bigDecimalClone = clone(this);
        bigDecimalClone.exp -= i;
        return bigDecimalClone.finish(plainMC, false);
    }

    public BigDecimal movePointRight(int i) {
        BigDecimal bigDecimalClone = clone(this);
        bigDecimalClone.exp += i;
        return bigDecimalClone.finish(plainMC, false);
    }

    public int scale() {
        int i = this.exp;
        if (i >= 0) {
            return 0;
        }
        return -i;
    }

    public BigDecimal setScale(int i) {
        return setScale(i, 7);
    }

    public BigDecimal setScale(int i, int i2) {
        int iScale = scale();
        if (iScale == i && this.form == 0) {
            return this;
        }
        BigDecimal bigDecimalClone = clone(this);
        if (iScale <= i) {
            int i3 = iScale == 0 ? bigDecimalClone.exp + i : i - iScale;
            byte[] bArr = bigDecimalClone.mant;
            bigDecimalClone.mant = extend(bArr, bArr.length + i3);
            bigDecimalClone.exp = -i;
        } else {
            if (i < 0) {
                throw new ArithmeticException("Negative scale: " + i);
            }
            bigDecimalClone = bigDecimalClone.round(bigDecimalClone.mant.length - (iScale - i), i2);
            if (bigDecimalClone.exp != (-i)) {
                byte[] bArr2 = bigDecimalClone.mant;
                bigDecimalClone.mant = extend(bArr2, bArr2.length + 1);
                bigDecimalClone.exp--;
            }
        }
        bigDecimalClone.form = (byte) 0;
        return bigDecimalClone;
    }

    public short shortValueExact() {
        int iIntValueExact = intValueExact();
        if (!(iIntValueExact < -32768) && !(iIntValueExact > 32767)) {
            return (short) iIntValueExact;
        }
        throw new ArithmeticException("Conversion overflow: " + toString());
    }

    public int signum() {
        return this.ind;
    }

    public java.math.BigDecimal toBigDecimal() {
        return new java.math.BigDecimal(unscaledValue(), scale());
    }

    public BigInteger toBigInteger() {
        BigDecimal bigDecimalClone;
        if ((this.exp >= 0) && (this.form == 0)) {
            bigDecimalClone = this;
        } else {
            int i = this.exp;
            if (i >= 0) {
                bigDecimalClone = clone(this);
                bigDecimalClone.form = (byte) 0;
            } else if ((-i) >= this.mant.length) {
                bigDecimalClone = ZERO;
            } else {
                bigDecimalClone = clone(this);
                byte[] bArr = bigDecimalClone.mant;
                int length = bArr.length + bigDecimalClone.exp;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 0, bArr2, 0, length);
                bigDecimalClone.mant = bArr2;
                bigDecimalClone.form = (byte) 0;
                bigDecimalClone.exp = 0;
            }
        }
        return new BigInteger(new String(bigDecimalClone.layout()));
    }

    public BigInteger toBigIntegerExact() {
        int i = this.exp;
        if (i < 0) {
            byte[] bArr = this.mant;
            if (!allzero(bArr, bArr.length + i)) {
                throw new ArithmeticException("Decimal part non-zero: " + toString());
            }
        }
        return toBigInteger();
    }

    public char[] toCharArray() {
        return layout();
    }

    public String toString() {
        return new String(layout());
    }

    public BigInteger unscaledValue() {
        BigDecimal bigDecimalClone;
        if (this.exp >= 0) {
            bigDecimalClone = this;
        } else {
            bigDecimalClone = clone(this);
            bigDecimalClone.exp = 0;
        }
        return bigDecimalClone.toBigInteger();
    }

    public static BigDecimal valueOf(double d) {
        return new BigDecimal(new Double(d).toString());
    }

    public static BigDecimal valueOf(long j) {
        return valueOf(j, 0);
    }

    public static BigDecimal valueOf(long j, int i) {
        BigDecimal bigDecimal;
        if (j == 0) {
            bigDecimal = ZERO;
        } else if (j == 1) {
            bigDecimal = ONE;
        } else if (j == 10) {
            bigDecimal = TEN;
        } else {
            bigDecimal = new BigDecimal(j);
        }
        if (i == 0) {
            return bigDecimal;
        }
        if (i < 0) {
            throw new NumberFormatException("Negative scale: " + i);
        }
        BigDecimal bigDecimalClone = clone(bigDecimal);
        bigDecimalClone.exp = -i;
        return bigDecimalClone;
    }

    private char[] layout() {
        byte[] bArr = this.mant;
        int length = bArr.length;
        char[] cArr = new char[length];
        int length2 = bArr.length;
        int i = 0;
        while (length2 > 0) {
            cArr[i] = (char) (this.mant[i] + 48);
            length2--;
            i++;
        }
        byte b = this.form;
        char c = Soundex.SILENT_MARKER;
        if (b != 0) {
            StringBuilder sb = new StringBuilder(length + 15);
            if (this.ind == -1) {
                sb.append(Soundex.SILENT_MARKER);
            }
            int i2 = (this.exp + length) - 1;
            if (this.form == 1) {
                sb.append(cArr[0]);
                if (length > 1) {
                    sb.append('.');
                    sb.append(cArr, 1, length - 1);
                }
            } else {
                int i3 = i2 % 3;
                if (i3 < 0) {
                    i3 += 3;
                }
                i2 -= i3;
                int i4 = i3 + 1;
                if (i4 >= length) {
                    sb.append(cArr, 0, length);
                    for (int i5 = i4 - length; i5 > 0; i5--) {
                        sb.append('0');
                    }
                } else {
                    sb.append(cArr, 0, i4);
                    sb.append('.');
                    sb.append(cArr, i4, length - i4);
                }
            }
            if (i2 != 0) {
                if (i2 < 0) {
                    i2 = -i2;
                } else {
                    c = '+';
                }
                sb.append('E');
                sb.append(c);
                sb.append(i2);
            }
            char[] cArr2 = new char[sb.length()];
            int length3 = sb.length();
            if (length3 != 0) {
                sb.getChars(0, length3, cArr2, 0);
            }
            return cArr2;
        }
        if (this.exp == 0) {
            if (this.ind >= 0) {
                return cArr;
            }
            char[] cArr3 = new char[length + 1];
            cArr3[0] = Soundex.SILENT_MARKER;
            System.arraycopy(cArr, 0, cArr3, 1, length);
            return cArr3;
        }
        int i6 = this.ind == -1 ? 1 : 0;
        int i7 = this.exp;
        int i8 = i7 + length;
        if (i8 < 1) {
            int i9 = i6 + 2;
            char[] cArr4 = new char[i9 - i7];
            if (i6 != 0) {
                cArr4[0] = Soundex.SILENT_MARKER;
            }
            cArr4[i6] = '0';
            cArr4[i6 + 1] = '.';
            int i10 = -i8;
            int i11 = i9;
            while (i10 > 0) {
                cArr4[i11] = '0';
                i10--;
                i11++;
            }
            System.arraycopy(cArr, 0, cArr4, i9 - i8, length);
            return cArr4;
        }
        if (i8 > length) {
            char[] cArr5 = new char[i6 + i8];
            if (i6 != 0) {
                cArr5[0] = Soundex.SILENT_MARKER;
            }
            System.arraycopy(cArr, 0, cArr5, i6, length);
            int i12 = i8 - length;
            int i13 = i6 + length;
            while (i12 > 0) {
                cArr5[i13] = '0';
                i12--;
                i13++;
            }
            return cArr5;
        }
        char[] cArr6 = new char[i6 + 1 + length];
        if (i6 != 0) {
            cArr6[0] = Soundex.SILENT_MARKER;
        }
        System.arraycopy(cArr, 0, cArr6, i6, i8);
        int i14 = i6 + i8;
        cArr6[i14] = '.';
        System.arraycopy(cArr, i8, cArr6, i14 + 1, length - i8);
        return cArr6;
    }

    private int intcheck(int i, int i2) {
        int iIntValueExact = intValueExact();
        if (!(iIntValueExact < i) && !(iIntValueExact > i2)) {
            return iIntValueExact;
        }
        throw new ArithmeticException("Conversion overflow: " + iIntValueExact);
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0108, code lost:
    
        r22 = r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.math.BigDecimal dodivide(char r26, com.ibm.icu.math.BigDecimal r27, com.ibm.icu.math.MathContext r28, int r29) {
        /*
            Method dump skipped, instructions count: 684
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.math.BigDecimal.dodivide(char, com.ibm.icu.math.BigDecimal, com.ibm.icu.math.MathContext, int):com.ibm.icu.math.BigDecimal");
    }

    private void bad(char[] cArr) {
        throw new NumberFormatException("Not a number: " + String.valueOf(cArr));
    }

    private void badarg(String str, int i, String str2) {
        throw new IllegalArgumentException("Bad argument " + i + " to " + str + ": " + str2);
    }

    private static final byte[] extend(byte[] bArr, int i) {
        if (bArr.length == i) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private static final byte[] byteaddsub(byte[] bArr, int i, byte[] bArr2, int i2, int i3, boolean z) {
        int i4;
        byte[] bArr3 = bArr;
        int length = bArr3.length;
        int length2 = bArr2.length;
        int i5 = i - 1;
        int i6 = i2 - 1;
        int i7 = i6 < i5 ? i5 : i6;
        byte[] bArr4 = (z && i7 + 1 == length) ? bArr3 : null;
        if (bArr4 == null) {
            bArr4 = new byte[i7 + 1];
        }
        int i8 = 0;
        boolean z2 = i3 == 1 || i3 == -1;
        int i9 = 0;
        for (int i10 = i7; i10 >= 0; i10--) {
            if (i5 >= 0) {
                if (i5 < length) {
                    i9 += bArr3[i5];
                }
                i5--;
            }
            if (i6 >= 0) {
                if (i6 < length2) {
                    if (!z2) {
                        i4 = bArr2[i6] * i3;
                    } else if (i3 > 0) {
                        i4 = bArr2[i6];
                    } else {
                        i9 -= bArr2[i6];
                    }
                    i9 += i4;
                }
                i6--;
            }
            if (i9 < 10 && i9 >= 0) {
                bArr4[i10] = (byte) i9;
                i9 = 0;
            } else {
                int i11 = i9 + 90;
                bArr4[i10] = bytedig[i11];
                i9 = bytecar[i11];
            }
        }
        if (i9 == 0) {
            return bArr4;
        }
        if (!z || i7 + 2 != bArr3.length) {
            bArr3 = null;
        }
        if (bArr3 == null) {
            bArr3 = new byte[i7 + 2];
        }
        bArr3[0] = (byte) i9;
        if (i7 < 10) {
            int i12 = i7 + 1;
            while (i12 > 0) {
                int i13 = i8 + 1;
                bArr3[i13] = bArr4[i8];
                i12--;
                i8 = i13;
            }
        } else {
            System.arraycopy(bArr4, 0, bArr3, 1, i7 + 1);
        }
        return bArr3;
    }

    private static final byte[] diginit() {
        byte[] bArr = new byte[190];
        for (int i = 0; i <= 189; i++) {
            int i2 = i - 90;
            if (i2 >= 0) {
                bArr[i] = (byte) (i2 % 10);
                bytecar[i] = (byte) (i2 / 10);
            } else {
                bArr[i] = (byte) ((i2 + 100) % 10);
                bytecar[i] = (byte) ((r2 / 10) - 10);
            }
        }
        return bArr;
    }

    private static final BigDecimal clone(BigDecimal bigDecimal) {
        BigDecimal bigDecimal2 = new BigDecimal();
        bigDecimal2.ind = bigDecimal.ind;
        bigDecimal2.exp = bigDecimal.exp;
        bigDecimal2.form = bigDecimal.form;
        bigDecimal2.mant = bigDecimal.mant;
        return bigDecimal2;
    }

    private void checkdigits(BigDecimal bigDecimal, int i) {
        if (i == 0) {
            return;
        }
        byte[] bArr = this.mant;
        if (bArr.length > i && !allzero(bArr, i)) {
            throw new ArithmeticException("Too many digits: " + toString());
        }
        if (bigDecimal == null) {
            return;
        }
        byte[] bArr2 = bigDecimal.mant;
        if (bArr2.length <= i || allzero(bArr2, i)) {
            return;
        }
        throw new ArithmeticException("Too many digits: " + bigDecimal.toString());
    }

    private BigDecimal round(MathContext mathContext) {
        return round(mathContext.digits, mathContext.roundingMode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v19, types: [int] */
    private BigDecimal round(int i, int i2) {
        byte b;
        boolean z;
        byte[] bArr = this.mant;
        int length = bArr.length - i;
        if (length <= 0) {
            return this;
        }
        this.exp += length;
        byte b2 = this.ind;
        if (i > 0) {
            byte[] bArr2 = new byte[i];
            this.mant = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, i);
            b = bArr[i];
            z = true;
        } else {
            this.mant = ZERO.mant;
            this.ind = (byte) 0;
            b = i == 0 ? bArr[0] : (byte) 0;
            z = false;
        }
        if (i2 != 4) {
            if (i2 == 7) {
                if (!allzero(bArr, i)) {
                    throw new ArithmeticException("Rounding necessary");
                }
            } else if (i2 == 5) {
                if (b <= 5 && (b != 5 || allzero(bArr, i + 1))) {
                }
            } else if (i2 == 6) {
                if (b <= 5) {
                    if (b == 5) {
                        if (allzero(bArr, i + 1)) {
                            byte[] bArr3 = this.mant;
                            if (bArr3[bArr3.length - 1] % 2 == 0) {
                            }
                        }
                    }
                }
            } else if (i2 != 1) {
                if (i2 == 0) {
                    if (allzero(bArr, i)) {
                    }
                } else if (i2 == 2) {
                    if (b2 <= 0 || allzero(bArr, i)) {
                    }
                } else if (i2 == 3) {
                    if (b2 >= 0 || allzero(bArr, i)) {
                    }
                } else {
                    throw new IllegalArgumentException("Bad round value: " + i2);
                }
            }
            b2 = 0;
        } else if (b < 5) {
            b2 = 0;
        }
        if (b2 != 0) {
            byte b3 = this.ind;
            if (b3 == 0) {
                this.mant = ONE.mant;
                this.ind = b2;
            } else {
                byte b4 = b3 == -1 ? -b2 : b2;
                byte[] bArr4 = this.mant;
                byte[] bArrByteaddsub = byteaddsub(bArr4, bArr4.length, ONE.mant, 1, b4, z);
                int length2 = bArrByteaddsub.length;
                byte[] bArr5 = this.mant;
                if (length2 > bArr5.length) {
                    this.exp++;
                    System.arraycopy(bArrByteaddsub, 0, bArr5, 0, bArr5.length);
                } else {
                    this.mant = bArrByteaddsub;
                }
            }
        }
        if (this.exp <= 999999999) {
            return this;
        }
        throw new ArithmeticException("Exponent Overflow: " + this.exp);
    }

    private static final boolean allzero(byte[] bArr, int i) {
        if (i < 0) {
            i = 0;
        }
        int length = bArr.length - 1;
        while (i <= length) {
            if (bArr[i] != 0) {
                return false;
            }
            i++;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x009b, code lost:
    
        if (r9 <= 999999999) goto L60;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.ibm.icu.math.BigDecimal finish(com.ibm.icu.math.MathContext r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.math.BigDecimal.finish(com.ibm.icu.math.MathContext, boolean):com.ibm.icu.math.BigDecimal");
    }
}
