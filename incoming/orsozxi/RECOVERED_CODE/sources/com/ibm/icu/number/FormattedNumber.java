package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.MeasureUnit;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;

/* loaded from: classes2.dex */
public class FormattedNumber implements FormattedValue {
    final DecimalQuantity fq;
    final String gender;
    final MeasureUnit outputUnit;
    final FormattedStringBuilder string;

    FormattedNumber(FormattedStringBuilder formattedStringBuilder, DecimalQuantity decimalQuantity, MeasureUnit measureUnit, String str) {
        this.string = formattedStringBuilder;
        this.fq = decimalQuantity;
        this.outputUnit = measureUnit;
        this.gender = str;
    }

    @Override // com.ibm.icu.text.FormattedValue, java.lang.CharSequence
    public String toString() {
        return this.string.toString();
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.string.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.string.charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return this.string.subString(i, i2);
    }

    @Override // com.ibm.icu.text.FormattedValue
    public <A extends Appendable> A appendTo(A a) {
        return (A) Utility.appendTo(this.string, a);
    }

    @Override // com.ibm.icu.text.FormattedValue
    public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
        return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, null);
    }

    @Override // com.ibm.icu.text.FormattedValue
    public AttributedCharacterIterator toCharacterIterator() {
        return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
    }

    public BigDecimal toBigDecimal() {
        return this.fq.toBigDecimal();
    }

    public MeasureUnit getOutputUnit() {
        return this.outputUnit;
    }

    @Deprecated
    public String getGender() {
        String str = this.gender;
        return str == null ? "" : str;
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal() {
        return this.fq;
    }
}
