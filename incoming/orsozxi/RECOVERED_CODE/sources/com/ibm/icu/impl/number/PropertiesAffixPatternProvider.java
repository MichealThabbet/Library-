package com.ibm.icu.impl.number;

import com.ibm.icu.impl.locale.LanguageTag;

/* loaded from: classes2.dex */
public class PropertiesAffixPatternProvider implements AffixPatternProvider {
    private final boolean isCurrencyPattern;
    private final String negPrefix;
    private final String negSuffix;
    private final String posPrefix;
    private final String posSuffix;

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean hasBody() {
        return true;
    }

    public static AffixPatternProvider forProperties(DecimalFormatProperties decimalFormatProperties) {
        if (decimalFormatProperties.getCurrencyPluralInfo() == null) {
            return new PropertiesAffixPatternProvider(decimalFormatProperties);
        }
        return new CurrencyPluralInfoAffixProvider(decimalFormatProperties.getCurrencyPluralInfo(), decimalFormatProperties);
    }

    PropertiesAffixPatternProvider(DecimalFormatProperties decimalFormatProperties) {
        String strEscape = AffixUtils.escape(decimalFormatProperties.getPositivePrefix());
        String strEscape2 = AffixUtils.escape(decimalFormatProperties.getPositiveSuffix());
        String strEscape3 = AffixUtils.escape(decimalFormatProperties.getNegativePrefix());
        String strEscape4 = AffixUtils.escape(decimalFormatProperties.getNegativeSuffix());
        String positivePrefixPattern = decimalFormatProperties.getPositivePrefixPattern();
        String positiveSuffixPattern = decimalFormatProperties.getPositiveSuffixPattern();
        String negativePrefixPattern = decimalFormatProperties.getNegativePrefixPattern();
        String negativeSuffixPattern = decimalFormatProperties.getNegativeSuffixPattern();
        if (strEscape != null) {
            this.posPrefix = strEscape;
        } else if (positivePrefixPattern != null) {
            this.posPrefix = positivePrefixPattern;
        } else {
            this.posPrefix = "";
        }
        if (strEscape2 != null) {
            this.posSuffix = strEscape2;
        } else if (positiveSuffixPattern != null) {
            this.posSuffix = positiveSuffixPattern;
        } else {
            this.posSuffix = "";
        }
        if (strEscape3 != null) {
            this.negPrefix = strEscape3;
        } else if (negativePrefixPattern != null) {
            this.negPrefix = negativePrefixPattern;
        } else {
            String str = LanguageTag.SEP;
            if (positivePrefixPattern != null) {
                str = LanguageTag.SEP + positivePrefixPattern;
            }
            this.negPrefix = str;
        }
        if (strEscape4 != null) {
            this.negSuffix = strEscape4;
        } else if (negativeSuffixPattern != null) {
            this.negSuffix = negativeSuffixPattern;
        } else {
            this.negSuffix = positiveSuffixPattern != null ? positiveSuffixPattern : "";
        }
        this.isCurrencyPattern = AffixUtils.hasCurrencySymbols(positivePrefixPattern) || AffixUtils.hasCurrencySymbols(positiveSuffixPattern) || AffixUtils.hasCurrencySymbols(negativePrefixPattern) || AffixUtils.hasCurrencySymbols(negativeSuffixPattern);
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public char charAt(int i, int i2) {
        return getString(i).charAt(i2);
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public int length(int i) {
        return getString(i).length();
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public String getString(int i) {
        boolean z = (i & 256) != 0;
        boolean z2 = (i & 512) != 0;
        if (z && z2) {
            return this.negPrefix;
        }
        if (z) {
            return this.posPrefix;
        }
        if (z2) {
            return this.negSuffix;
        }
        return this.posSuffix;
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean positiveHasPlusSign() {
        return AffixUtils.containsType(this.posPrefix, -2) || AffixUtils.containsType(this.posSuffix, -2);
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean hasNegativeSubpattern() {
        if (this.negSuffix == this.posSuffix && this.negPrefix.length() == this.posPrefix.length() + 1) {
            String str = this.negPrefix;
            String str2 = this.posPrefix;
            if (str.regionMatches(1, str2, 0, str2.length()) && this.negPrefix.charAt(0) == '-') {
                return false;
            }
        }
        return true;
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean negativeHasMinusSign() {
        return AffixUtils.containsType(this.negPrefix, -1) || AffixUtils.containsType(this.negSuffix, -1);
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean hasCurrencySign() {
        return this.isCurrencyPattern;
    }

    @Override // com.ibm.icu.impl.number.AffixPatternProvider
    public boolean containsSymbolType(int i) {
        return AffixUtils.containsType(this.posPrefix, i) || AffixUtils.containsType(this.posSuffix, i) || AffixUtils.containsType(this.negPrefix, i) || AffixUtils.containsType(this.negSuffix, i);
    }

    public String toString() {
        return super.toString() + " {" + this.posPrefix + "#" + this.posSuffix + ";" + this.negPrefix + "#" + this.negSuffix + "}";
    }
}
