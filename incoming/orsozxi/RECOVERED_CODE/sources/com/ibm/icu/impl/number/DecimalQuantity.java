package com.ibm.icu.impl.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.text.PluralRules;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.FieldPosition;

/* loaded from: classes2.dex */
public interface DecimalQuantity extends PluralRules.IFixedDecimal {
    void adjustExponent(int i);

    void adjustMagnitude(int i);

    void applyMaxInteger(int i);

    void copyFrom(DecimalQuantity decimalQuantity);

    DecimalQuantity createCopy();

    byte getDigit(int i);

    int getExponent();

    int getLowerDisplayMagnitude();

    int getMagnitude() throws ArithmeticException;

    long getPositionFingerprint();

    StandardPlural getStandardPlural(PluralRules pluralRules);

    int getUpperDisplayMagnitude();

    @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
    boolean isInfinite();

    @Override // com.ibm.icu.text.PluralRules.IFixedDecimal
    boolean isNaN();

    boolean isNegative();

    boolean isZeroish();

    int maxRepresentableDigits();

    void multiplyBy(BigDecimal bigDecimal);

    void negate();

    void populateUFieldPosition(FieldPosition fieldPosition);

    void roundToIncrement(BigDecimal bigDecimal, MathContext mathContext);

    void roundToInfinity();

    void roundToMagnitude(int i, MathContext mathContext);

    void roundToNickel(int i, MathContext mathContext);

    void setMinFraction(int i);

    void setMinInteger(int i);

    void setToBigDecimal(BigDecimal bigDecimal);

    Modifier.Signum signum();

    BigDecimal toBigDecimal();

    double toDouble();

    long toLong(boolean z);

    String toPlainString();
}
