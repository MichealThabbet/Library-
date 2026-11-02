package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class FormattedNumberRange implements FormattedValue {
    final NumberRangeFormatter.RangeIdentityResult identityResult;
    final DecimalQuantity quantity1;
    final DecimalQuantity quantity2;
    final FormattedStringBuilder string;

    FormattedNumberRange(FormattedStringBuilder formattedStringBuilder, DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        this.string = formattedStringBuilder;
        this.quantity1 = decimalQuantity;
        this.quantity2 = decimalQuantity2;
        this.identityResult = rangeIdentityResult;
    }

    @Override // com.ibm.icu.text.FormattedValue, java.lang.CharSequence
    public String toString() {
        return this.string.toString();
    }

    @Override // com.ibm.icu.text.FormattedValue
    public <A extends Appendable> A appendTo(A a) throws IOException {
        try {
            a.append(this.string);
            return a;
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.string.charAt(i);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.string.length();
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return this.string.subString(i, i2);
    }

    @Override // com.ibm.icu.text.FormattedValue
    public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
        return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, null);
    }

    @Override // com.ibm.icu.text.FormattedValue
    public AttributedCharacterIterator toCharacterIterator() {
        return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
    }

    public BigDecimal getFirstBigDecimal() {
        return this.quantity1.toBigDecimal();
    }

    public BigDecimal getSecondBigDecimal() {
        return this.quantity2.toBigDecimal();
    }

    public NumberRangeFormatter.RangeIdentityResult getIdentityResult() {
        return this.identityResult;
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.string.toCharArray()) ^ Arrays.hashCode(this.string.toFieldArray())) ^ this.quantity1.toBigDecimal().hashCode()) ^ this.quantity2.toBigDecimal().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof FormattedNumberRange)) {
            return false;
        }
        FormattedNumberRange formattedNumberRange = (FormattedNumberRange) obj;
        return this.string.contentEquals(formattedNumberRange.string) && this.quantity1.toBigDecimal().equals(formattedNumberRange.quantity1.toBigDecimal()) && this.quantity2.toBigDecimal().equals(formattedNumberRange.quantity2.toBigDecimal());
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFirstFixedDecimal() {
        return this.quantity1;
    }

    @Deprecated
    public PluralRules.IFixedDecimal getSecondFixedDecimal() {
        return this.quantity2;
    }
}
