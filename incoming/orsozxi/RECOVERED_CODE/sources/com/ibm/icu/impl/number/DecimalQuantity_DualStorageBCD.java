package com.ibm.icu.impl.number;

import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.text.DateFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class DecimalQuantity_DualStorageBCD extends DecimalQuantity_AbstractBCD {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] bcdBytes;
    private long bcdLong = 0;
    private boolean usingBytes = false;

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public int maxRepresentableDigits() {
        return Integer.MAX_VALUE;
    }

    public DecimalQuantity_DualStorageBCD() {
        setBcdToZero();
        this.flags = (byte) 0;
    }

    public DecimalQuantity_DualStorageBCD(long j) {
        setToLong(j);
    }

    public DecimalQuantity_DualStorageBCD(int i) {
        setToInt(i);
    }

    public DecimalQuantity_DualStorageBCD(double d) {
        setToDouble(d);
    }

    public DecimalQuantity_DualStorageBCD(BigInteger bigInteger) {
        setToBigInteger(bigInteger);
    }

    public DecimalQuantity_DualStorageBCD(BigDecimal bigDecimal) {
        setToBigDecimal(bigDecimal);
    }

    public DecimalQuantity_DualStorageBCD(DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD) {
        copyFrom(decimalQuantity_DualStorageBCD);
    }

    public DecimalQuantity_DualStorageBCD(Number number) {
        if (number instanceof Long) {
            setToLong(number.longValue());
            return;
        }
        if (number instanceof Integer) {
            setToInt(number.intValue());
            return;
        }
        if (number instanceof Float) {
            setToDouble(number.doubleValue());
            return;
        }
        if (number instanceof Double) {
            setToDouble(number.doubleValue());
            return;
        }
        if (number instanceof BigInteger) {
            setToBigInteger((BigInteger) number);
            return;
        }
        if (number instanceof BigDecimal) {
            setToBigDecimal((BigDecimal) number);
        } else {
            if (number instanceof com.ibm.icu.math.BigDecimal) {
                setToBigDecimal(((com.ibm.icu.math.BigDecimal) number).toBigDecimal());
                return;
            }
            throw new IllegalArgumentException("Number is of an unsupported type: " + number.getClass().getName());
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity
    public DecimalQuantity createCopy() {
        return new DecimalQuantity_DualStorageBCD(this);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected byte getDigitPos(int i) {
        if (this.usingBytes) {
            if (i < 0 || i >= this.precision) {
                return (byte) 0;
            }
            return this.bcdBytes[i];
        }
        if (i < 0 || i >= 16) {
            return (byte) 0;
        }
        return (byte) ((this.bcdLong >>> (i * 4)) & 15);
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void setDigitPos(int i, byte b) {
        if (this.usingBytes) {
            ensureCapacity(i + 1);
            this.bcdBytes[i] = b;
        } else if (i >= 16) {
            switchStorage();
            ensureCapacity(i + 1);
            this.bcdBytes[i] = b;
        } else {
            int i2 = i * 4;
            this.bcdLong = (b << i2) | (this.bcdLong & (~(15 << i2)));
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void shiftLeft(int i) {
        if (!this.usingBytes && this.precision + i > 16) {
            switchStorage();
        }
        if (this.usingBytes) {
            ensureCapacity(this.precision + i);
            byte[] bArr = this.bcdBytes;
            System.arraycopy(bArr, 0, bArr, i, this.precision);
            Arrays.fill(this.bcdBytes, 0, i, (byte) 0);
        } else {
            this.bcdLong <<= i * 4;
        }
        this.scale -= i;
        this.precision += i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void shiftRight(int i) {
        if (this.usingBytes) {
            int i2 = 0;
            while (i2 < this.precision - i) {
                byte[] bArr = this.bcdBytes;
                bArr[i2] = bArr[i2 + i];
                i2++;
            }
            while (i2 < this.precision) {
                this.bcdBytes[i2] = 0;
                i2++;
            }
        } else {
            this.bcdLong >>>= i * 4;
        }
        this.scale += i;
        this.precision -= i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void popFromLeft(int i) {
        if (this.usingBytes) {
            int i2 = this.precision;
            while (true) {
                i2--;
                if (i2 < this.precision - i) {
                    break;
                } else {
                    this.bcdBytes[i2] = 0;
                }
            }
        } else {
            this.bcdLong &= (1 << ((this.precision - i) * 4)) - 1;
        }
        this.precision -= i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void setBcdToZero() {
        if (this.usingBytes) {
            this.bcdBytes = null;
            this.usingBytes = false;
        }
        this.bcdLong = 0L;
        this.scale = 0;
        this.precision = 0;
        this.isApproximate = false;
        this.origDouble = 0.0d;
        this.origDelta = 0;
        this.exponent = 0;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void readIntToBcd(int i) {
        long j = 0;
        int i2 = 16;
        while (i != 0) {
            j = (j >>> 4) + ((i % 10) << 60);
            i /= 10;
            i2--;
        }
        this.bcdLong = j >>> (i2 * 4);
        this.scale = 0;
        this.precision = 16 - i2;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void readLongToBcd(long j) {
        if (j >= 10000000000000000L) {
            ensureCapacity();
            int i = 0;
            while (j != 0) {
                this.bcdBytes[i] = (byte) (j % 10);
                j /= 10;
                i++;
            }
            this.scale = 0;
            this.precision = i;
            return;
        }
        long j2 = 0;
        int i2 = 16;
        while (j != 0) {
            j2 = (j2 >>> 4) + ((j % 10) << 60);
            j /= 10;
            i2--;
        }
        this.bcdLong = j2 >>> (i2 * 4);
        this.scale = 0;
        this.precision = 16 - i2;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void readBigIntegerToBcd(BigInteger bigInteger) {
        ensureCapacity();
        int i = 0;
        while (bigInteger.signum() != 0) {
            BigInteger[] bigIntegerArrDivideAndRemainder = bigInteger.divideAndRemainder(BigInteger.TEN);
            int i2 = i + 1;
            ensureCapacity(i2);
            this.bcdBytes[i] = bigIntegerArrDivideAndRemainder[1].byteValue();
            bigInteger = bigIntegerArrDivideAndRemainder[0];
            i = i2;
        }
        this.scale = 0;
        this.precision = i;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected BigDecimal bcdToBigDecimal() {
        BigDecimal bigDecimalScaleByPowerOfTen;
        if (this.usingBytes) {
            BigDecimal bigDecimal = new BigDecimal(toNumberString());
            return isNegative() ? bigDecimal.negate() : bigDecimal;
        }
        long digitPos = 0;
        for (int i = this.precision - 1; i >= 0; i--) {
            digitPos = (digitPos * 10) + getDigitPos(i);
        }
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(digitPos);
        if (bigDecimalValueOf.scale() + this.scale + this.exponent <= -2147483648L) {
            bigDecimalScaleByPowerOfTen = BigDecimal.ZERO;
        } else {
            bigDecimalScaleByPowerOfTen = bigDecimalValueOf.scaleByPowerOfTen(this.scale + this.exponent);
        }
        return isNegative() ? bigDecimalScaleByPowerOfTen.negate() : bigDecimalScaleByPowerOfTen;
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void compact() {
        if (this.usingBytes) {
            int i = 0;
            while (i < this.precision && this.bcdBytes[i] == 0) {
                i++;
            }
            if (i == this.precision) {
                setBcdToZero();
                return;
            }
            shiftRight(i);
            int i2 = this.precision - 1;
            while (i2 >= 0 && this.bcdBytes[i2] == 0) {
                i2--;
            }
            this.precision = i2 + 1;
            if (this.precision <= 16) {
                switchStorage();
                return;
            }
            return;
        }
        long j = this.bcdLong;
        if (j == 0) {
            setBcdToZero();
            return;
        }
        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j) / 4;
        this.bcdLong >>>= iNumberOfTrailingZeros * 4;
        this.scale += iNumberOfTrailingZeros;
        this.precision = 16 - (Long.numberOfLeadingZeros(this.bcdLong) / 4);
    }

    private void ensureCapacity() {
        ensureCapacity(40);
    }

    private void ensureCapacity(int i) {
        if (i == 0) {
            return;
        }
        int length = this.usingBytes ? this.bcdBytes.length : 0;
        if (!this.usingBytes) {
            this.bcdBytes = new byte[i];
        } else if (length < i) {
            byte[] bArr = new byte[i * 2];
            System.arraycopy(this.bcdBytes, 0, bArr, 0, length);
            this.bcdBytes = bArr;
        }
        this.usingBytes = true;
    }

    private void switchStorage() {
        if (this.usingBytes) {
            this.bcdLong = 0L;
            for (int i = this.precision - 1; i >= 0; i--) {
                long j = this.bcdLong << 4;
                this.bcdLong = j;
                this.bcdLong = j | this.bcdBytes[i];
            }
            this.bcdBytes = null;
            this.usingBytes = false;
            return;
        }
        ensureCapacity();
        for (int i2 = 0; i2 < this.precision; i2++) {
            byte[] bArr = this.bcdBytes;
            long j2 = this.bcdLong;
            bArr[i2] = (byte) (15 & j2);
            this.bcdLong = j2 >>> 4;
        }
    }

    @Override // com.ibm.icu.impl.number.DecimalQuantity_AbstractBCD
    protected void copyBcdFrom(DecimalQuantity decimalQuantity) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = (DecimalQuantity_DualStorageBCD) decimalQuantity;
        setBcdToZero();
        if (decimalQuantity_DualStorageBCD.usingBytes) {
            ensureCapacity(decimalQuantity_DualStorageBCD.precision);
            System.arraycopy(decimalQuantity_DualStorageBCD.bcdBytes, 0, this.bcdBytes, 0, decimalQuantity_DualStorageBCD.precision);
        } else {
            this.bcdLong = decimalQuantity_DualStorageBCD.bcdLong;
        }
    }

    @Deprecated
    public String checkHealth() {
        int i = 0;
        if (this.usingBytes) {
            if (this.bcdLong != 0) {
                return "Value in bcdLong but we are in byte mode";
            }
            if (this.precision == 0) {
                return "Zero precision but we are in byte mode";
            }
            if (this.precision > this.bcdBytes.length) {
                return "Precision exceeds length of byte array";
            }
            if (getDigitPos(this.precision - 1) == 0) {
                return "Most significant digit is zero in byte mode";
            }
            if (getDigitPos(0) == 0) {
                return "Least significant digit is zero in long mode";
            }
            while (i < this.precision) {
                if (getDigitPos(i) >= 10) {
                    return "Digit exceeding 10 in byte array";
                }
                if (getDigitPos(i) < 0) {
                    return "Digit below 0 in byte array";
                }
                i++;
            }
            for (int i2 = this.precision; i2 < this.bcdBytes.length; i2++) {
                if (getDigitPos(i2) != 0) {
                    return "Nonzero digits outside of range in byte array";
                }
            }
            return null;
        }
        if (this.bcdBytes != null) {
            int i3 = 0;
            while (true) {
                byte[] bArr = this.bcdBytes;
                if (i3 >= bArr.length) {
                    break;
                }
                if (bArr[i3] != 0) {
                    return "Nonzero digits in byte array but we are in long mode";
                }
                i3++;
            }
        }
        if (this.precision == 0 && this.bcdLong != 0) {
            return "Value in bcdLong even though precision is zero";
        }
        if (this.precision > 16) {
            return "Precision exceeds length of long";
        }
        if (this.precision != 0 && getDigitPos(this.precision - 1) == 0) {
            return "Most significant digit is zero in long mode";
        }
        if (this.precision != 0 && getDigitPos(0) == 0) {
            return "Least significant digit is zero in long mode";
        }
        while (i < this.precision) {
            if (getDigitPos(i) >= 10) {
                return "Digit exceeding 10 in long";
            }
            if (getDigitPos(i) < 0) {
                return "Digit below 0 in long (?!)";
            }
            i++;
        }
        for (int i4 = this.precision; i4 < 16; i4++) {
            if (getDigitPos(i4) != 0) {
                return "Nonzero digits outside of range in long";
            }
        }
        return null;
    }

    @Deprecated
    public boolean isUsingBytes() {
        return this.usingBytes;
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = Integer.valueOf(this.lReqPos);
        objArr[1] = Integer.valueOf(this.rReqPos);
        objArr[2] = this.usingBytes ? "bytes" : "long";
        objArr[3] = isNegative() ? LanguageTag.SEP : "";
        objArr[4] = toNumberString();
        return String.format("<DecimalQuantity %d:%d %s %s%s>", objArr);
    }

    private String toNumberString() {
        StringBuilder sb = new StringBuilder();
        if (this.usingBytes) {
            if (this.precision == 0) {
                sb.append('0');
            }
            for (int i = this.precision - 1; i >= 0; i--) {
                sb.append((int) this.bcdBytes[i]);
            }
        } else {
            sb.append(Long.toHexString(this.bcdLong));
        }
        sb.append(DateFormat.ABBR_WEEKDAY);
        sb.append(this.scale);
        return sb.toString();
    }
}
