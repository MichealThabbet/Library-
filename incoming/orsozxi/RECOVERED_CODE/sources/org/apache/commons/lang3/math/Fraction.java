package org.apache.commons.lang3.math;

import it.piapostolos.android.pinotes.R2;
import java.math.BigInteger;
import org.apache.commons.lang3.Validate;

/* loaded from: classes3.dex */
public final class Fraction extends Number implements Comparable<Fraction> {
    private static final long serialVersionUID = 65382027393090L;
    private final int denominator;
    private final int numerator;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
    private transient int hashCode = 0;
    private transient String toString = null;
    private transient String toProperString = null;

    private Fraction(int i, int i2) {
        this.numerator = i;
        this.denominator = i2;
    }

    public static Fraction getFraction(int i, int i2) {
        if (i2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i = -i;
            i2 = -i2;
        }
        return new Fraction(i, i2);
    }

    public static Fraction getFraction(int i, int i2, int i3) {
        if (i3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i3 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        }
        if (i2 < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        }
        long j = i < 0 ? (i * i3) - i2 : (i * i3) + i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        }
        return new Fraction((int) j, i3);
    }

    public static Fraction getReducedFraction(int i, int i2) {
        if (i2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i == 0) {
            return ZERO;
        }
        if (i2 == Integer.MIN_VALUE && (i & 1) == 0) {
            i /= 2;
            i2 /= 2;
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i = -i;
            i2 = -i2;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(i, i2);
        return new Fraction(i / iGreatestCommonDivisor, i2 / iGreatestCommonDivisor);
    }

    public static Fraction getFraction(double d) {
        int i;
        int i2;
        int i3 = d < 0.0d ? -1 : 1;
        double dAbs = Math.abs(d);
        if (dAbs > 2.147483647E9d || Double.isNaN(dAbs)) {
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        }
        int i4 = (int) dAbs;
        double d2 = dAbs - i4;
        int i5 = (int) d2;
        double d3 = 1.0d;
        double d4 = d2 - i5;
        double dAbs2 = Double.MAX_VALUE;
        int i6 = i3;
        int i7 = 1;
        int i8 = 0;
        int i9 = 0;
        int i10 = 1;
        int i11 = 1;
        while (true) {
            int i12 = (int) (d3 / d4);
            double d5 = dAbs2;
            double d6 = d3 - (i12 * d4);
            int i13 = (i5 * i7) + i8;
            int i14 = (i5 * i9) + i10;
            dAbs2 = Math.abs(d2 - (i13 / i14));
            i = i11 + 1;
            if (d5 <= dAbs2 || i14 > 10000 || i14 <= 0) {
                break;
            }
            i2 = 25;
            if (i >= 25) {
                break;
            }
            i11 = i;
            int i15 = i9;
            i9 = i14;
            i5 = i12;
            i8 = i7;
            i7 = i13;
            i10 = i15;
            d3 = d4;
            d4 = d6;
        }
        i2 = 25;
        if (i == i2) {
            throw new ArithmeticException("Unable to convert double to fraction");
        }
        return getReducedFraction((i7 + (i4 * i9)) * i6, i9);
    }

    public static Fraction getFraction(String str) throws NumberFormatException {
        Validate.isTrue(str != null, "The string must not be null", new Object[0]);
        if (str.indexOf(46) >= 0) {
            return getFraction(Double.parseDouble(str));
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf > 0) {
            int i = Integer.parseInt(str.substring(0, iIndexOf));
            String strSubstring = str.substring(iIndexOf + 1);
            int iIndexOf2 = strSubstring.indexOf(47);
            if (iIndexOf2 < 0) {
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            return getFraction(i, Integer.parseInt(strSubstring.substring(0, iIndexOf2)), Integer.parseInt(strSubstring.substring(iIndexOf2 + 1)));
        }
        int iIndexOf3 = str.indexOf(47);
        if (iIndexOf3 < 0) {
            return getFraction(Integer.parseInt(str), 1);
        }
        return getFraction(Integer.parseInt(str.substring(0, iIndexOf3)), Integer.parseInt(str.substring(iIndexOf3 + 1)));
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }

    public int getProperWhole() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.numerator / this.denominator;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.numerator / this.denominator;
    }

    public Fraction reduce() {
        int i = this.numerator;
        if (i == 0) {
            return equals(ZERO) ? this : ZERO;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(Math.abs(i), this.denominator);
        return iGreatestCommonDivisor == 1 ? this : getFraction(this.numerator / iGreatestCommonDivisor, this.denominator / iGreatestCommonDivisor);
    }

    public Fraction invert() {
        int i = this.numerator;
        if (i == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (i == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        }
        if (i < 0) {
            return new Fraction(-this.denominator, -this.numerator);
        }
        return new Fraction(this.denominator, this.numerator);
    }

    public Fraction negate() {
        if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: too large to negate");
        }
        return new Fraction(-this.numerator, this.denominator);
    }

    public Fraction abs() {
        return this.numerator >= 0 ? this : negate();
    }

    public Fraction pow(int i) {
        if (i == 1) {
            return this;
        }
        if (i == 0) {
            return ONE;
        }
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return invert().pow(2).pow(-(i / 2));
            }
            return invert().pow(-i);
        }
        Fraction fractionMultiplyBy = multiplyBy(this);
        if (i % 2 == 0) {
            return fractionMultiplyBy.pow(i / 2);
        }
        return fractionMultiplyBy.pow(i / 2).multiplyBy(this);
    }

    private static int greatestCommonDivisor(int i, int i2) {
        int i3;
        if (i == 0 || i2 == 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            return Math.abs(i) + Math.abs(i2);
        }
        if (Math.abs(i) == 1 || Math.abs(i2) == 1) {
            return 1;
        }
        if (i > 0) {
            i = -i;
        }
        if (i2 > 0) {
            i2 = -i2;
        }
        int i4 = 0;
        while (true) {
            i3 = i & 1;
            if (i3 != 0 || (i2 & 1) != 0 || i4 >= 31) {
                break;
            }
            i /= 2;
            i2 /= 2;
            i4++;
        }
        if (i4 == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int i5 = i3 == 1 ? i2 : -(i / 2);
        while (true) {
            if ((i5 & 1) == 0) {
                i5 /= 2;
            } else {
                if (i5 > 0) {
                    i = -i5;
                } else {
                    i2 = i5;
                }
                i5 = (i2 - i) / 2;
                if (i5 == 0) {
                    return (-i) * (1 << i4);
                }
            }
        }
    }

    private static int mulAndCheck(int i, int i2) {
        long j = i * i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int) j;
    }

    private static int mulPosAndCheck(int i, int i2) {
        long j = i * i2;
        if (j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: mulPos");
    }

    private static int addAndCheck(int i, int i2) {
        long j = i + i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int) j;
    }

    private static int subAndCheck(int i, int i2) {
        long j = i - i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int) j;
    }

    public Fraction add(Fraction fraction) {
        return addSub(fraction, true);
    }

    public Fraction subtract(Fraction fraction) {
        return addSub(fraction, false);
    }

    private Fraction addSub(Fraction fraction, boolean z) {
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        if (this.numerator == 0) {
            return z ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(this.denominator, fraction.denominator);
        if (iGreatestCommonDivisor == 1) {
            int iMulAndCheck = mulAndCheck(this.numerator, fraction.denominator);
            int iMulAndCheck2 = mulAndCheck(fraction.numerator, this.denominator);
            return new Fraction(z ? addAndCheck(iMulAndCheck, iMulAndCheck2) : subAndCheck(iMulAndCheck, iMulAndCheck2), mulPosAndCheck(this.denominator, fraction.denominator));
        }
        BigInteger bigIntegerMultiply = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / iGreatestCommonDivisor));
        BigInteger bigIntegerMultiply2 = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / iGreatestCommonDivisor));
        BigInteger bigIntegerAdd = z ? bigIntegerMultiply.add(bigIntegerMultiply2) : bigIntegerMultiply.subtract(bigIntegerMultiply2);
        int iIntValue = bigIntegerAdd.mod(BigInteger.valueOf(iGreatestCommonDivisor)).intValue();
        int iGreatestCommonDivisor2 = iIntValue == 0 ? iGreatestCommonDivisor : greatestCommonDivisor(iIntValue, iGreatestCommonDivisor);
        BigInteger bigIntegerDivide = bigIntegerAdd.divide(BigInteger.valueOf(iGreatestCommonDivisor2));
        if (bigIntegerDivide.bitLength() > 31) {
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
        return new Fraction(bigIntegerDivide.intValue(), mulPosAndCheck(this.denominator / iGreatestCommonDivisor, fraction.denominator / iGreatestCommonDivisor2));
    }

    public Fraction multiplyBy(Fraction fraction) {
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        int i = this.numerator;
        if (i == 0 || fraction.numerator == 0) {
            return ZERO;
        }
        int iGreatestCommonDivisor = greatestCommonDivisor(i, fraction.denominator);
        int iGreatestCommonDivisor2 = greatestCommonDivisor(fraction.numerator, this.denominator);
        return getReducedFraction(mulAndCheck(this.numerator / iGreatestCommonDivisor, fraction.numerator / iGreatestCommonDivisor2), mulPosAndCheck(this.denominator / iGreatestCommonDivisor2, fraction.denominator / iGreatestCommonDivisor));
    }

    public Fraction divideBy(Fraction fraction) {
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        if (fraction.numerator == 0) {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
        return multiplyBy(fraction.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction fraction = (Fraction) obj;
        return getNumerator() == fraction.getNumerator() && getDenominator() == fraction.getDenominator();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((getNumerator() + R2.attr.layout_constraintTop_toBottomOf) * 37) + getDenominator();
        }
        return this.hashCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(Fraction fraction) {
        if (this == fraction) {
            return 0;
        }
        if (this.numerator == fraction.numerator && this.denominator == fraction.denominator) {
            return 0;
        }
        return Long.compare(this.numerator * fraction.denominator, fraction.numerator * this.denominator);
    }

    public String toString() {
        if (this.toString == null) {
            this.toString = getNumerator() + "/" + getDenominator();
        }
        return this.toString;
    }

    public String toProperString() {
        if (this.toProperString == null) {
            int i = this.numerator;
            if (i == 0) {
                this.toProperString = "0";
            } else {
                int i2 = this.denominator;
                if (i == i2) {
                    this.toProperString = "1";
                } else if (i == i2 * (-1)) {
                    this.toProperString = "-1";
                } else {
                    if (i > 0) {
                        i = -i;
                    }
                    if (i < (-this.denominator)) {
                        int properNumerator = getProperNumerator();
                        if (properNumerator == 0) {
                            this.toProperString = Integer.toString(getProperWhole());
                        } else {
                            this.toProperString = getProperWhole() + " " + properNumerator + "/" + getDenominator();
                        }
                    } else {
                        this.toProperString = getNumerator() + "/" + getDenominator();
                    }
                }
            }
        }
        return this.toProperString;
    }
}
