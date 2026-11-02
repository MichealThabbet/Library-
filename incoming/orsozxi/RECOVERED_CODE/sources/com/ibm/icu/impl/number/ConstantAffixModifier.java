package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.number.Modifier;
import java.text.Format;

/* loaded from: classes2.dex */
public class ConstantAffixModifier implements Modifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final ConstantAffixModifier EMPTY = new ConstantAffixModifier();
    private final Format.Field field;
    private final String prefix;
    private final boolean strong;
    private final String suffix;

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean containsField(Format.Field field) {
        return false;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public Modifier.Parameters getParameters() {
        return null;
    }

    public ConstantAffixModifier(String str, String str2, Format.Field field, boolean z) {
        this.prefix = str == null ? "" : str;
        this.suffix = str2 == null ? "" : str2;
        this.field = field;
        this.strong = z;
    }

    public ConstantAffixModifier() {
        this.prefix = "";
        this.suffix = "";
        this.field = null;
        this.strong = false;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        return formattedStringBuilder.insert(i2, this.suffix, this.field) + formattedStringBuilder.insert(i, this.prefix, this.field);
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getPrefixLength() {
        return this.prefix.length();
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public int getCodePointCount() {
        String str = this.prefix;
        int iCodePointCount = str.codePointCount(0, str.length());
        String str2 = this.suffix;
        return iCodePointCount + str2.codePointCount(0, str2.length());
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean isStrong() {
        return this.strong;
    }

    @Override // com.ibm.icu.impl.number.Modifier
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!(modifier instanceof ConstantAffixModifier)) {
            return false;
        }
        ConstantAffixModifier constantAffixModifier = (ConstantAffixModifier) modifier;
        return this.prefix.equals(constantAffixModifier.prefix) && this.suffix.equals(constantAffixModifier.suffix) && this.field == constantAffixModifier.field && this.strong == constantAffixModifier.strong;
    }

    public String toString() {
        return String.format("<ConstantAffixModifier prefix:'%s' suffix:'%s'>", this.prefix, this.suffix);
    }
}
