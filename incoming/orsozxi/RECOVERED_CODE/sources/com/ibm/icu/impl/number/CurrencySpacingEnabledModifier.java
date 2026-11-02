package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class CurrencySpacingEnabledModifier extends ConstantMultiFieldModifier {
    static final short IN_CURRENCY = 0;
    static final short IN_NUMBER = 1;
    static final byte PREFIX = 0;
    static final byte SUFFIX = 1;
    private static final UnicodeSet UNISET_DIGIT = new UnicodeSet("[:digit:]").freeze();
    private static final UnicodeSet UNISET_NOTSZ = new UnicodeSet("[[:^S:]&[:^Z:]]").freeze();
    private final String afterPrefixInsert;
    private final UnicodeSet afterPrefixUnicodeSet;
    private final String beforeSuffixInsert;
    private final UnicodeSet beforeSuffixUnicodeSet;

    public CurrencySpacingEnabledModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2, boolean z, boolean z2, DecimalFormatSymbols decimalFormatSymbols) {
        super(formattedStringBuilder, formattedStringBuilder2, z, z2);
        if (formattedStringBuilder.length() > 0 && formattedStringBuilder.fieldAt(formattedStringBuilder.length() - 1) == NumberFormat.Field.CURRENCY) {
            if (getUnicodeSet(decimalFormatSymbols, (short) 0, (byte) 0).contains(formattedStringBuilder.getLastCodePoint())) {
                UnicodeSet unicodeSet = getUnicodeSet(decimalFormatSymbols, (short) 1, (byte) 0);
                this.afterPrefixUnicodeSet = unicodeSet;
                unicodeSet.freeze();
                this.afterPrefixInsert = getInsertString(decimalFormatSymbols, (byte) 0);
            } else {
                this.afterPrefixUnicodeSet = null;
                this.afterPrefixInsert = null;
            }
        } else {
            this.afterPrefixUnicodeSet = null;
            this.afterPrefixInsert = null;
        }
        if (formattedStringBuilder2.length() > 0 && formattedStringBuilder2.fieldAt(0) == NumberFormat.Field.CURRENCY) {
            if (getUnicodeSet(decimalFormatSymbols, (short) 0, (byte) 1).contains(formattedStringBuilder2.getFirstCodePoint())) {
                UnicodeSet unicodeSet2 = getUnicodeSet(decimalFormatSymbols, (short) 1, (byte) 1);
                this.beforeSuffixUnicodeSet = unicodeSet2;
                unicodeSet2.freeze();
                this.beforeSuffixInsert = getInsertString(decimalFormatSymbols, (byte) 1);
                return;
            }
            this.beforeSuffixUnicodeSet = null;
            this.beforeSuffixInsert = null;
            return;
        }
        this.beforeSuffixUnicodeSet = null;
        this.beforeSuffixInsert = null;
    }

    @Override // com.ibm.icu.impl.number.ConstantMultiFieldModifier, com.ibm.icu.impl.number.Modifier
    public int apply(FormattedStringBuilder formattedStringBuilder, int i, int i2) {
        UnicodeSet unicodeSet;
        UnicodeSet unicodeSet2;
        int i3 = i2 - i;
        int iInsert = 0;
        if (i3 > 0 && (unicodeSet2 = this.afterPrefixUnicodeSet) != null && unicodeSet2.contains(formattedStringBuilder.codePointAt(i))) {
            iInsert = 0 + formattedStringBuilder.insert(i, this.afterPrefixInsert, (Object) null);
        }
        if (i3 > 0 && (unicodeSet = this.beforeSuffixUnicodeSet) != null && unicodeSet.contains(formattedStringBuilder.codePointBefore(i2))) {
            iInsert += formattedStringBuilder.insert(i2 + iInsert, this.beforeSuffixInsert, (Object) null);
        }
        return iInsert + super.apply(formattedStringBuilder, i, i2 + iInsert);
    }

    public static int applyCurrencySpacing(FormattedStringBuilder formattedStringBuilder, int i, int i2, int i3, int i4, DecimalFormatSymbols decimalFormatSymbols) {
        int iApplyCurrencySpacingAffix = 0;
        boolean z = i2 > 0;
        boolean z2 = i4 > 0;
        boolean z3 = (i3 - i) - i2 > 0;
        if (z && z3) {
            iApplyCurrencySpacingAffix = 0 + applyCurrencySpacingAffix(formattedStringBuilder, i + i2, (byte) 0, decimalFormatSymbols);
        }
        return (z2 && z3) ? iApplyCurrencySpacingAffix + applyCurrencySpacingAffix(formattedStringBuilder, i3 + iApplyCurrencySpacingAffix, (byte) 1, decimalFormatSymbols) : iApplyCurrencySpacingAffix;
    }

    private static int applyCurrencySpacingAffix(FormattedStringBuilder formattedStringBuilder, int i, byte b, DecimalFormatSymbols decimalFormatSymbols) {
        Object objFieldAt;
        if (b == 0) {
            objFieldAt = formattedStringBuilder.fieldAt(i - 1);
        } else {
            objFieldAt = formattedStringBuilder.fieldAt(i);
        }
        if (objFieldAt != NumberFormat.Field.CURRENCY) {
            return 0;
        }
        if (!getUnicodeSet(decimalFormatSymbols, (short) 0, b).contains(b == 0 ? formattedStringBuilder.codePointBefore(i) : formattedStringBuilder.codePointAt(i))) {
            return 0;
        }
        if (getUnicodeSet(decimalFormatSymbols, (short) 1, b).contains(b == 0 ? formattedStringBuilder.codePointAt(i) : formattedStringBuilder.codePointBefore(i))) {
            return formattedStringBuilder.insert(i, getInsertString(decimalFormatSymbols, b), (Object) null);
        }
        return 0;
    }

    private static UnicodeSet getUnicodeSet(DecimalFormatSymbols decimalFormatSymbols, short s, byte b) {
        String patternForCurrencySpacing = decimalFormatSymbols.getPatternForCurrencySpacing(s == 0 ? 0 : 1, b == 1);
        if (patternForCurrencySpacing.equals("[:digit:]")) {
            return UNISET_DIGIT;
        }
        if (patternForCurrencySpacing.equals("[[:^S:]&[:^Z:]]")) {
            return UNISET_NOTSZ;
        }
        return new UnicodeSet(patternForCurrencySpacing);
    }

    private static String getInsertString(DecimalFormatSymbols decimalFormatSymbols, byte b) {
        return decimalFormatSymbols.getPatternForCurrencySpacing(2, b == 1);
    }
}
