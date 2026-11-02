package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.Modifier;
import java.text.Format;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ConstantMultiFieldModifier implements Modifier {
    private final boolean overwrite;
    private final Modifier.Parameters parameters;
    protected final char[] prefixChars;
    protected final Object[] prefixFields;
    private final boolean strong;
    protected final char[] suffixChars;
    protected final Object[] suffixFields;

    public ConstantMultiFieldModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean z, boolean z2) {
        this(formattedStringBuilder, formattedStringBuilder2, z, z2, null);
    }

    public ConstantMultiFieldModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean z, boolean z2, Modifier.Parameters parameters) {
        this.prefixChars = formattedStringBuilder.toCharArray();
        this.suffixChars = formattedStringBuilder2.toCharArray();
        this.prefixFields = formattedStringBuilder.toFieldArray();
        this.suffixFields = formattedStringBuilder2.toFieldArray();
        this.overwrite = z;
        this.strong = z2;
        this.parameters = parameters;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        int iInsert = formattedStringBuilder.insert(i, this.prefixChars, this.prefixFields);
        if (this.overwrite) {
            iInsert += formattedStringBuilder.splice(i + iInsert, i2 + iInsert, "", 0, 0, null);
        }
        return iInsert + formattedStringBuilder.insert(i2 + iInsert, this.suffixChars, this.suffixFields);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getPrefixLength() {
        return this.prefixChars.length;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getCodePointCount() {
        char[] cArr = this.prefixChars;
        int iCodePointCount = Character.codePointCount(cArr, 0, cArr.length);
        char[] cArr2 = this.suffixChars;
        return iCodePointCount + Character.codePointCount(cArr2, 0, cArr2.length);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean isStrong() {
        return this.strong;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean containsField(Format.Field field) {
        int i = 0;
        while (true) {
            Object[] objArr = this.prefixFields;
            if (i >= objArr.length) {
                int i2 = 0;
                while (true) {
                    Object[] objArr2 = this.suffixFields;
                    if (i2 >= objArr2.length) {
                        return false;
                    }
                    if (objArr2[i2] == field) {
                        return true;
                    }
                    i2++;
                }
            } else {
                if (objArr[i] == field) {
                    return true;
                }
                i++;
            }
        }
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public Modifier.Parameters getParameters() {
        return this.parameters;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof ConstantMultiFieldModifier)) {
            return false;
        }
        ConstantMultiFieldModifier constantMultiFieldModifier = (ConstantMultiFieldModifier) modifier;
        Modifier.Parameters parameters = this.parameters;
        if (parameters == null || constantMultiFieldModifier.parameters == null || parameters.obj != constantMultiFieldModifier.parameters.obj) {
            return Arrays.equals(this.prefixChars, constantMultiFieldModifier.prefixChars) && Arrays.equals(this.prefixFields, constantMultiFieldModifier.prefixFields) && Arrays.equals(this.suffixChars, constantMultiFieldModifier.suffixChars) && Arrays.equals(this.suffixFields, constantMultiFieldModifier.suffixFields) && this.overwrite == constantMultiFieldModifier.overwrite && this.strong == constantMultiFieldModifier.strong;
        }
        return true;
    }

    public String toString() {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        apply(formattedStringBuilder, 0, 0);
        int prefixLength = getPrefixLength();
        return String.format("<ConstantMultiFieldModifier prefix:'%s' suffix:'%s'>", formattedStringBuilder.subSequence(0, prefixLength), formattedStringBuilder.subSequence(prefixLength, formattedStringBuilder.length()));
    }
}
