package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.range.PrefixInfixSuffixLengthHelper;
import com.ibm.icu.util.ICUException;
import java.text.Format;

/* loaded from: classes2.dex */
public class SimpleModifier implements Modifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ARG_NUM_LIMIT = 256;
    private final String compiledPattern;
    private final Format.Field field;
    private final Modifier.Parameters parameters;
    private final boolean strong;

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean containsField(Format.Field field) {
        return false;
    }

    public SimpleModifier(String str, Format.Field field, boolean z) {
        this(str, field, z, null);
    }

    public SimpleModifier(String str, Format.Field field, boolean z, Modifier.Parameters parameters) {
        this.compiledPattern = str;
        this.field = field;
        this.strong = z;
        this.parameters = parameters;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        return SimpleFormatterImpl.formatPrefixSuffix(this.compiledPattern, this.field, i, i2, formattedStringBuilder);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getPrefixLength() {
        return SimpleFormatterImpl.getPrefixLength(this.compiledPattern);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getCodePointCount() {
        return SimpleFormatterImpl.getLength(this.compiledPattern, true);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean isStrong() {
        return this.strong;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public Modifier.Parameters getParameters() {
        return this.parameters;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof SimpleModifier)) {
            return false;
        }
        SimpleModifier simpleModifier = (SimpleModifier) modifier;
        Modifier.Parameters parameters = this.parameters;
        if (parameters == null || simpleModifier.parameters == null || parameters.obj != simpleModifier.parameters.obj) {
            return this.compiledPattern.equals(simpleModifier.compiledPattern) && this.field == simpleModifier.field && this.strong == simpleModifier.strong;
        }
        return true;
    }

    public static void formatTwoArgPattern(String str, FormattedStringBuilder formattedStringBuilder, int i, PrefixInfixSuffixLengthHelper prefixInfixSuffixLengthHelper, Format.Field field) {
        int i2;
        int i3;
        int i4;
        if (SimpleFormatterImpl.getArgumentLimit(str) != 2) {
            throw new ICUException();
        }
        char cCharAt = str.charAt(1);
        int i5 = 2;
        int iCharAt = 0;
        if (cCharAt < 256) {
            i3 = 0;
            i2 = 0;
        } else {
            int i6 = cCharAt - 256;
            int i7 = 2 + i6;
            formattedStringBuilder.insert(i + 0, str, 2, i7, field);
            i5 = i7 + 1;
            i2 = i6;
            i3 = i6 + 0;
        }
        char cCharAt2 = str.charAt(i5);
        int i8 = i5 + 1;
        if (cCharAt2 < 256) {
            i4 = 0;
        } else {
            i4 = cCharAt2 - 256;
            int i9 = i8 + i4;
            formattedStringBuilder.insert(i + i3, str, i8, i9, field);
            i3 += i4;
            i8 = i9 + 1;
        }
        if (i8 != str.length()) {
            iCharAt = str.charAt(i8) - 256;
            int i10 = i8 + 1;
            formattedStringBuilder.insert(i + i3, str, i10, i10 + iCharAt, field);
        }
        prefixInfixSuffixLengthHelper.lengthPrefix = i2;
        prefixInfixSuffixLengthHelper.lengthInfix = i4;
        prefixInfixSuffixLengthHelper.lengthSuffix = iCharAt;
    }
}
