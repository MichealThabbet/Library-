package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.RuleBasedTransliterator;
import java.io.IOException;

/* loaded from: classes2.dex */
class StringMatcher implements UnicodeMatcher, UnicodeReplacer {
    private final RuleBasedTransliterator.Data data;
    private int matchLimit;
    private int matchStart;
    private String pattern;
    private int segmentNumber;

    @Override // com.ibm.icu.text.UnicodeReplacer
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
    }

    public StringMatcher(String str, int i, RuleBasedTransliterator.Data data) {
        this.data = data;
        this.pattern = str;
        this.matchLimit = -1;
        this.matchStart = -1;
        this.segmentNumber = i;
    }

    public StringMatcher(String str, int i, int i2, int i3, RuleBasedTransliterator.Data data) {
        this(str.substring(i, i2), i3, data);
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public int matches(Replaceable replaceable, int[] iArr, int i, boolean z) {
        int[] iArr2 = {iArr[0]};
        if (i < iArr2[0]) {
            for (int length = this.pattern.length() - 1; length >= 0; length--) {
                char cCharAt = this.pattern.charAt(length);
                UnicodeMatcher unicodeMatcherLookupMatcher = this.data.lookupMatcher(cCharAt);
                if (unicodeMatcherLookupMatcher == null) {
                    if (iArr2[0] <= i || cCharAt != replaceable.charAt(iArr2[0])) {
                        return 0;
                    }
                    iArr2[0] = iArr2[0] - 1;
                } else {
                    int iMatches = unicodeMatcherLookupMatcher.matches(replaceable, iArr2, i, z);
                    if (iMatches != 2) {
                        return iMatches;
                    }
                }
            }
            if (this.matchStart < 0) {
                this.matchStart = iArr2[0] + 1;
                this.matchLimit = iArr[0] + 1;
            }
        } else {
            for (int i2 = 0; i2 < this.pattern.length(); i2++) {
                if (z && iArr2[0] == i) {
                    return 1;
                }
                char cCharAt2 = this.pattern.charAt(i2);
                UnicodeMatcher unicodeMatcherLookupMatcher2 = this.data.lookupMatcher(cCharAt2);
                if (unicodeMatcherLookupMatcher2 == null) {
                    if (iArr2[0] >= i || cCharAt2 != replaceable.charAt(iArr2[0])) {
                        return 0;
                    }
                    iArr2[0] = iArr2[0] + 1;
                } else {
                    int iMatches2 = unicodeMatcherLookupMatcher2.matches(replaceable, iArr2, i, z);
                    if (iMatches2 != 2) {
                        return iMatches2;
                    }
                }
            }
            this.matchStart = iArr[0];
            this.matchLimit = iArr2[0];
        }
        iArr[0] = iArr2[0];
        return 2;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public String toPattern(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.segmentNumber > 0) {
            stringBuffer.append('(');
        }
        for (int i = 0; i < this.pattern.length(); i++) {
            char cCharAt = this.pattern.charAt(i);
            UnicodeMatcher unicodeMatcherLookupMatcher = this.data.lookupMatcher(cCharAt);
            if (unicodeMatcherLookupMatcher == null) {
                Utility.appendToRule(stringBuffer, (int) cCharAt, false, z, stringBuffer2);
            } else {
                Utility.appendToRule(stringBuffer, unicodeMatcherLookupMatcher.toPattern(z), true, z, stringBuffer2);
            }
        }
        if (this.segmentNumber > 0) {
            stringBuffer.append(')');
        }
        Utility.appendToRule(stringBuffer, -1, true, z, stringBuffer2);
        return stringBuffer.toString();
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public boolean matchesIndexValue(int i) {
        if (this.pattern.length() == 0) {
            return true;
        }
        int iCharAt = UTF16.charAt(this.pattern, 0);
        UnicodeMatcher unicodeMatcherLookupMatcher = this.data.lookupMatcher(iCharAt);
        if (unicodeMatcherLookupMatcher == null) {
            return (iCharAt & 255) == i;
        }
        return unicodeMatcherLookupMatcher.matchesIndexValue(i);
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        int charCount = 0;
        while (charCount < this.pattern.length()) {
            int iCharAt = UTF16.charAt(this.pattern, charCount);
            UnicodeMatcher unicodeMatcherLookupMatcher = this.data.lookupMatcher(iCharAt);
            if (unicodeMatcherLookupMatcher == null) {
                unicodeSet.add(iCharAt);
            } else {
                unicodeMatcherLookupMatcher.addMatchSetTo(unicodeSet);
            }
            charCount += UTF16.getCharCount(iCharAt);
        }
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public int replace(Replaceable replaceable, int i, int i2, int[] iArr) {
        int i3;
        int i4;
        int i5 = this.matchStart;
        if (i5 < 0 || i5 == (i4 = this.matchLimit)) {
            i3 = 0;
        } else {
            replaceable.copy(i5, i4, i2);
            i3 = this.matchLimit - this.matchStart;
        }
        replaceable.replace(i, i2, "");
        return i3;
    }

    @Override // com.ibm.icu.text.UnicodeReplacer
    public String toReplacerPattern(boolean z) throws IOException {
        StringBuffer stringBuffer = new StringBuffer("$");
        Utility.appendNumber(stringBuffer, this.segmentNumber, 10, 1);
        return stringBuffer.toString();
    }

    public void resetMatch() {
        this.matchLimit = -1;
        this.matchStart = -1;
    }
}
