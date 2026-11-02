package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.UFieldPosition;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.FieldPosition;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public abstract class DecimalQuantity_AbstractBCD implements DecimalQuantity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final int INFINITY_FLAG = 2;
    protected static final int NAN_FLAG = 4;
    protected static final int NEGATIVE_FLAG = 1;
    private static final int SECTION_LOWER_EDGE = -1;
    private static final int SECTION_UPPER_EDGE = -2;
    protected byte flags;
    protected boolean isApproximate;
    protected int origDelta;
    protected double origDouble;
    protected int precision;
    protected int scale;
    private static final double[] DOUBLE_MULTIPLIERS = {1.0d, 10.0d, 100.0d, 1000.0d, 10000.0d, 100000.0d, 1000000.0d, 1.0E7d, 1.0E8d, 1.0E9d, 1.0E10d, 1.0E11d, 1.0E12d, 1.0E13d, 1.0E14d, 1.0E15d, 1.0E16d, 1.0E17d, 1.0E18d, 1.0E19d, 1.0E20d, 1.0E21d};
    static final byte[] INT64_BCD = {9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8};
    protected int lReqPos = 0;
    protected int rReqPos = 0;
    protected int exponent = 0;

    @Deprecated
    public boolean explicitExactDouble = false;

    private static int safeSubtract(int i, int i2) {
        int i3 = i - i2;
        if (i2 < 0 && i3 < i) {
            return Integer.MAX_VALUE;
        }
        if (i2 <= 0 || i3 <= i) {
            return i3;
        }
        return Integer.MIN_VALUE;
    }

    protected abstract BigDecimal bcdToBigDecimal();

    protected abstract void compact();

    protected abstract void copyBcdFrom(DecimalQuantity decimalQuantity);

    protected abstract byte getDigitPos(int i);

    protected abstract void popFromLeft(int i);

    protected abstract void readBigIntegerToBcd(BigInteger bigInteger);

    protected abstract void readIntToBcd(int i);

    protected abstract void readLongToBcd(long j);

    protected abstract void setBcdToZero();

    protected abstract void setDigitPos(int i, byte b);

    protected abstract void shiftLeft(int i);

    protected abstract void shiftRight(int i);

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void copyFrom(DecimalQuantity decimalQuantity) {
        copyBcdFrom(decimalQuantity);
        DecimalQuantity_AbstractBCD decimalQuantity_AbstractBCD = (DecimalQuantity_AbstractBCD) decimalQuantity;
        this.lReqPos = decimalQuantity_AbstractBCD.lReqPos;
        this.rReqPos = decimalQuantity_AbstractBCD.rReqPos;
        this.scale = decimalQuantity_AbstractBCD.scale;
        this.precision = decimalQuantity_AbstractBCD.precision;
        this.flags = decimalQuantity_AbstractBCD.flags;
        this.origDouble = decimalQuantity_AbstractBCD.origDouble;
        this.origDelta = decimalQuantity_AbstractBCD.origDelta;
        this.isApproximate = decimalQuantity_AbstractBCD.isApproximate;
        this.exponent = decimalQuantity_AbstractBCD.exponent;
    }

    public DecimalQuantity_AbstractBCD clear() {
        this.lReqPos = 0;
        this.rReqPos = 0;
        this.flags = (byte) 0;
        setBcdToZero();
        return this;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void setMinInteger(int i) {
        int i2 = this.lReqPos;
        if (i < i2) {
            i = i2;
        }
        this.lReqPos = i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void setMinFraction(int i) {
        this.rReqPos = -i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void applyMaxInteger(int i) throws ArithmeticException {
        if (this.precision == 0) {
            return;
        }
        if (i <= this.scale) {
            setBcdToZero();
            return;
        }
        int magnitude = getMagnitude();
        if (i <= magnitude) {
            popFromLeft((magnitude - i) + 1);
            compact();
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public long getPositionFingerprint() {
        return ((this.lReqPos << 16) ^ 0) ^ (this.rReqPos << 32);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void roundToIncrement(BigDecimal bigDecimal, MathContext mathContext) {
        BigDecimal bigDecimalRound = toBigDecimal().divide(bigDecimal, 0, mathContext.getRoundingMode()).multiply(bigDecimal).round(mathContext);
        if (bigDecimalRound.signum() == 0) {
            setBcdToZero();
        } else {
            setToBigDecimal(bigDecimalRound);
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void multiplyBy(BigDecimal bigDecimal) {
        if (isZeroish()) {
            return;
        }
        setToBigDecimal(toBigDecimal().multiply(bigDecimal));
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void negate() {
        this.flags = (byte) (this.flags ^ 1);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public int getMagnitude() throws ArithmeticException {
        if (this.precision == 0) {
            throw new ArithmeticException("Magnitude is not well-defined for zero");
        }
        return (this.scale + r0) - 1;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void adjustMagnitude(int i) {
        if (this.precision != 0) {
            this.scale = Utility.addExact(this.scale, i);
            this.origDelta = Utility.addExact(this.origDelta, i);
            Utility.addExact(this.scale, this.precision);
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public int getExponent() {
        return this.exponent;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void adjustExponent(int i) {
        this.exponent += i;
    }

    @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
    public boolean isHasIntegerValue() {
        return this.scale >= 0;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public StandardPlural getStandardPlural(PluralRules pluralRules) {
        if (pluralRules == null) {
            return StandardPlural.OTHER;
        }
        return StandardPlural.orOtherFromString(pluralRules.select(this));
    }

    /* renamed from: com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$PluralRules$Operand;

        static {
            int[] iArr = new int[PluralRules.Operand.values().length];
            $SwitchMap$com$ibm$icu$text$PluralRules$Operand = iArr;
            try {
                iArr[PluralRules.Operand.i.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.f.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.t.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.v.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.w.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.e.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$PluralRules$Operand[PluralRules.Operand.c.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
    public double getPluralOperand(PluralRules.Operand operand) {
        switch (AnonymousClass1.$SwitchMap$com$ibm$icu$text$PluralRules$Operand[operand.ordinal()]) {
            case 1:
                boolean zIsNegative = isNegative();
                long j = toLong(true);
                if (zIsNegative) {
                    j = -j;
                }
                return j;
            case 2:
                return toFractionLong(true);
            case 3:
                return toFractionLong(false);
            case 4:
                return fractionCount();
            case 5:
                return fractionCountWithoutTrailingZeros();
            case 6:
                return getExponent();
            case 7:
                return getExponent();
            default:
                return Math.abs(toDouble());
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void populateUFieldPosition(FieldPosition fieldPosition) {
        if (fieldPosition instanceof UFieldPosition) {
            ((UFieldPosition) fieldPosition).setFractionDigits((int) getPluralOperand(PluralRules.Operand.v), (long) getPluralOperand(PluralRules.Operand.f));
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public int getUpperDisplayMagnitude() {
        int i = this.scale + this.precision;
        int i2 = this.lReqPos;
        if (i2 > i) {
            i = i2;
        }
        return i - 1;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public int getLowerDisplayMagnitude() {
        int i = this.scale;
        int i2 = this.rReqPos;
        return i2 < i ? i2 : i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public byte getDigit(int i) {
        return getDigitPos(i - this.scale);
    }

    private int fractionCount() {
        return Math.max(0, (-getLowerDisplayMagnitude()) - this.exponent);
    }

    private int fractionCountWithoutTrailingZeros() {
        return Math.max((-this.scale) - this.exponent, 0);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public boolean isNegative() {
        return (this.flags & 1) != 0;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public Modifier.Signum signum() {
        boolean z = isZeroish() && !isInfinite();
        boolean zIsNegative = isNegative();
        if (z && zIsNegative) {
            return Modifier.Signum.NEG_ZERO;
        }
        if (z) {
            return Modifier.Signum.POS_ZERO;
        }
        if (zIsNegative) {
            return Modifier.Signum.NEG;
        }
        return Modifier.Signum.POS;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity, com.ibm.icu.text.PluralRules.IFixedDecimal
    public boolean isInfinite() {
        return (this.flags & 2) != 0;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity, com.ibm.icu.text.PluralRules.IFixedDecimal
    public boolean isNaN() {
        return (this.flags & 4) != 0;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public boolean isZeroish() {
        return this.precision == 0;
    }

    public void setToInt(int i) {
        setBcdToZero();
        this.flags = (byte) 0;
        if (i < 0) {
            this.flags = (byte) (0 | 1);
            i = -i;
        }
        if (i != 0) {
            _setToInt(i);
            compact();
        }
    }

    private void _setToInt(int i) {
        if (i == Integer.MIN_VALUE) {
            readLongToBcd(-i);
        } else {
            readIntToBcd(i);
        }
    }

    public void setToLong(long j) {
        setBcdToZero();
        this.flags = (byte) 0;
        if (j < 0) {
            this.flags = (byte) (0 | 1);
            j = -j;
        }
        if (j != 0) {
            _setToLong(j);
            compact();
        }
    }

    private void _setToLong(long j) {
        if (j == Long.MIN_VALUE) {
            readBigIntegerToBcd(BigInteger.valueOf(j).negate());
        } else if (j <= 2147483647L) {
            readIntToBcd((int) j);
        } else {
            readLongToBcd(j);
        }
    }

    public void setToBigInteger(BigInteger bigInteger) {
        setBcdToZero();
        this.flags = (byte) 0;
        if (bigInteger.signum() == -1) {
            this.flags = (byte) (this.flags | 1);
            bigInteger = bigInteger.negate();
        }
        if (bigInteger.signum() != 0) {
            _setToBigInteger(bigInteger);
            compact();
        }
    }

    private void _setToBigInteger(BigInteger bigInteger) {
        if (bigInteger.bitLength() < 32) {
            readIntToBcd(bigInteger.intValue());
        } else if (bigInteger.bitLength() < 64) {
            readLongToBcd(bigInteger.longValue());
        } else {
            readBigIntegerToBcd(bigInteger);
        }
    }

    public void setToDouble(double d) {
        setBcdToZero();
        this.flags = (byte) 0;
        if (Double.doubleToRawLongBits(d) < 0) {
            this.flags = (byte) (this.flags | 1);
            d = -d;
        }
        if (Double.isNaN(d)) {
            this.flags = (byte) (this.flags | 4);
            return;
        }
        if (Double.isInfinite(d)) {
            this.flags = (byte) (this.flags | 2);
        } else if (d != 0.0d) {
            _setToDoubleFast(d);
            compact();
        }
    }

    private void _setToDoubleFast(double d) {
        double d2;
        this.isApproximate = true;
        this.origDouble = d;
        this.origDelta = 0;
        int iDoubleToLongBits = ((int) ((Double.doubleToLongBits(d) & 9218868437227405312L) >> 52)) - 1023;
        if (iDoubleToLongBits <= 52) {
            long j = (long) d;
            if (j == d) {
                _setToLong(j);
                return;
            }
        }
        if (iDoubleToLongBits == -1023 || iDoubleToLongBits == 1024) {
            convertToAccurateDouble();
            return;
        }
        int i = (int) ((52 - iDoubleToLongBits) / 3.321928094887362d);
        if (i >= 0) {
            int i2 = i;
            while (i2 >= 22) {
                d *= 1.0E22d;
                i2 -= 22;
            }
            d2 = d * DOUBLE_MULTIPLIERS[i2];
        } else {
            int i3 = i;
            while (i3 <= -22) {
                d /= 1.0E22d;
                i3 += 22;
            }
            d2 = d / DOUBLE_MULTIPLIERS[-i3];
        }
        long jRound = Math.round(d2);
        if (jRound != 0) {
            _setToLong(jRound);
            this.scale -= i;
        }
    }

    private void convertToAccurateDouble() {
        double d = this.origDouble;
        int i = this.origDelta;
        setBcdToZero();
        String string = Double.toString(d);
        if (string.indexOf(69) != -1) {
            int iIndexOf = string.indexOf(69);
            _setToLong(Long.parseLong(string.charAt(0) + string.substring(2, iIndexOf)));
            this.scale = this.scale + (Integer.parseInt(string.substring(iIndexOf + 1)) - (iIndexOf - 1)) + 1;
        } else if (string.charAt(0) == '0') {
            _setToLong(Long.parseLong(string.substring(2)));
            this.scale += 2 - string.length();
        } else if (string.charAt(string.length() - 1) == '0') {
            _setToLong(Long.parseLong(string.substring(0, string.length() - 2)));
        } else {
            int iIndexOf2 = string.indexOf(46);
            _setToLong(Long.parseLong(string.substring(0, iIndexOf2) + string.substring(iIndexOf2 + 1)));
            this.scale = this.scale + (iIndexOf2 - string.length()) + 1;
        }
        this.scale += i;
        compact();
        this.explicitExactDouble = true;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void setToBigDecimal(BigDecimal bigDecimal) {
        setBcdToZero();
        this.flags = (byte) 0;
        if (bigDecimal.signum() == -1) {
            this.flags = (byte) (this.flags | 1);
            bigDecimal = bigDecimal.negate();
        }
        if (bigDecimal.signum() != 0) {
            _setToBigDecimal(bigDecimal);
            compact();
        }
    }

    private void _setToBigDecimal(BigDecimal bigDecimal) {
        int iScale = bigDecimal.scale();
        _setToBigInteger(bigDecimal.scaleByPowerOfTen(iScale).toBigInteger());
        this.scale -= iScale;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public long toLong(boolean z) {
        long digitPos = 0;
        int iMin = ((this.exponent + this.scale) + this.precision) - 1;
        if (z) {
            iMin = Math.min(iMin, 17);
        }
        while (iMin >= 0) {
            digitPos = (digitPos * 10) + getDigitPos((iMin - this.scale) - this.exponent);
            iMin--;
        }
        return isNegative() ? -digitPos : digitPos;
    }

    public long toFractionLong(boolean z) {
        int iMin = this.scale;
        if (z) {
            iMin = Math.min(iMin, this.rReqPos);
        }
        long digitPos = 0;
        for (int i = (-1) - this.exponent; i >= iMin && digitPos <= 1.0E17d; i--) {
            digitPos = (digitPos * 10) + getDigitPos(i - this.scale);
        }
        if (!z) {
            while (digitPos > 0 && digitPos % 10 == 0) {
                digitPos /= 10;
            }
        }
        return digitPos;
    }

    public boolean fitsInLong() throws ArithmeticException {
        if (isInfinite() || isNaN()) {
            return false;
        }
        if (isZeroish()) {
            return true;
        }
        if (this.exponent + this.scale < 0) {
            return false;
        }
        int magnitude = getMagnitude();
        if (magnitude < 18) {
            return true;
        }
        if (magnitude > 18) {
            return false;
        }
        for (int i = 0; i < this.precision; i++) {
            byte digit = getDigit(18 - i);
            byte[] bArr = INT64_BCD;
            if (digit < bArr[i]) {
                return true;
            }
            if (digit > bArr[i]) {
                return false;
            }
        }
        return isNegative();
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public double toDouble() {
        if (isNaN()) {
            return Double.NaN;
        }
        if (isInfinite()) {
            return isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        StringBuilder sb = new StringBuilder();
        toScientificString(sb);
        return Double.valueOf(sb.toString()).doubleValue();
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public BigDecimal toBigDecimal() {
        if (this.isApproximate) {
            convertToAccurateDouble();
        }
        return bcdToBigDecimal();
    }

    public void truncate() {
        int i = this.scale;
        if (i < 0) {
            shiftRight(-i);
            this.scale = 0;
            compact();
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void roundToNickel(int i, MathContext mathContext) {
        roundToMagnitude(i, mathContext, true);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void roundToMagnitude(int i, MathContext mathContext) {
        roundToMagnitude(i, mathContext, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0059 A[EDGE_INSN: B:32:0x0059->B:110:0x0122 BREAK  A[LOOP:1: B:34:0x0060->B:38:0x0069]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void roundToMagnitude(int r17, java.math.MathContext r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD.roundToMagnitude(int, java.math.MathContext, boolean):void");
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public void roundToInfinity() {
        if (this.isApproximate) {
            convertToAccurateDouble();
        }
    }

    @Deprecated
    public void appendDigit(byte b, int i, boolean z) {
        if (b == 0) {
            if (!z || this.precision == 0) {
                return;
            }
            this.scale += i + 1;
            return;
        }
        int i2 = this.scale;
        if (i2 > 0) {
            i += i2;
            if (z) {
                this.scale = 0;
            }
        }
        int i3 = i + 1;
        shiftLeft(i3);
        setDigitPos(0, b);
        if (z) {
            this.scale += i3;
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public String toPlainString() {
        StringBuilder sb = new StringBuilder();
        toPlainString(sb);
        return sb.toString();
    }

    public void toPlainString(StringBuilder sb) {
        if (isNegative()) {
            sb.append(Soundex.SILENT_MARKER);
        }
        int i = this.precision;
        if (i == 0) {
            sb.append('0');
            return;
        }
        int i2 = this.scale;
        int i3 = this.exponent;
        int i4 = ((i + i2) + i3) - 1;
        int i5 = i2 + i3;
        int i6 = this.lReqPos;
        if (i4 < i6 - 1) {
            i4 = i6 - 1;
        }
        int i7 = this.rReqPos;
        if (i5 > i7) {
            i5 = i7;
        }
        if (i4 < 0) {
            sb.append('0');
        }
        while (i4 >= 0) {
            sb.append((char) (getDigitPos((i4 - this.scale) - this.exponent) + 48));
            i4--;
        }
        if (i5 < 0) {
            sb.append('.');
        }
        while (i4 >= i5) {
            sb.append((char) (getDigitPos((i4 - this.scale) - this.exponent) + 48));
            i4--;
        }
    }

    public String toScientificString() {
        StringBuilder sb = new StringBuilder();
        toScientificString(sb);
        return sb.toString();
    }

    public void toScientificString(StringBuilder sb) {
        if (isNegative()) {
            sb.append(Soundex.SILENT_MARKER);
        }
        int i = this.precision;
        if (i == 0) {
            sb.append("0E+0");
            return;
        }
        int i2 = i - 1;
        sb.append((char) (getDigitPos(i2) + 48));
        int i3 = i2 - 1;
        if (i3 >= 0) {
            sb.append('.');
            while (i3 >= 0) {
                sb.append((char) (getDigitPos(i3) + 48));
                i3--;
            }
        }
        sb.append('E');
        int i4 = i2 + this.scale + this.exponent;
        if (i4 == Integer.MIN_VALUE) {
            sb.append("-2147483648");
            return;
        }
        if (i4 < 0) {
            i4 *= -1;
            sb.append(Soundex.SILENT_MARKER);
        } else {
            sb.append('+');
        }
        if (i4 == 0) {
            sb.append('0');
        }
        int length = sb.length();
        while (i4 > 0) {
            sb.insert(length, (char) ((i4 % 10) + 48));
            i4 /= 10;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DecimalQuantity_AbstractBCD)) {
            return false;
        }
        DecimalQuantity_AbstractBCD decimalQuantity_AbstractBCD = (DecimalQuantity_AbstractBCD) obj;
        if (!(this.scale == decimalQuantity_AbstractBCD.scale && this.precision == decimalQuantity_AbstractBCD.precision && this.flags == decimalQuantity_AbstractBCD.flags && this.lReqPos == decimalQuantity_AbstractBCD.lReqPos && this.rReqPos == decimalQuantity_AbstractBCD.rReqPos && this.isApproximate == decimalQuantity_AbstractBCD.isApproximate)) {
            return false;
        }
        if (this.precision == 0) {
            return true;
        }
        if (this.isApproximate) {
            return this.origDouble == decimalQuantity_AbstractBCD.origDouble && this.origDelta == decimalQuantity_AbstractBCD.origDelta;
        }
        for (int upperDisplayMagnitude = getUpperDisplayMagnitude(); upperDisplayMagnitude >= getLowerDisplayMagnitude(); upperDisplayMagnitude--) {
            if (getDigit(upperDisplayMagnitude) != decimalQuantity_AbstractBCD.getDigit(upperDisplayMagnitude)) {
                return false;
            }
        }
        return true;
    }
}
