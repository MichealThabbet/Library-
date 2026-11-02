package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;

/* loaded from: classes2.dex */
public class CodePointMatcher implements NumberParseMatcher {
    private final int cp;

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public static CodePointMatcher getInstance(int i) {
        return new CodePointMatcher(i);
    }

    private CodePointMatcher(int i) {
        this.cp = i;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!stringSegment.startsWith(this.cp)) {
            return false;
        }
        stringSegment.adjustOffsetByCodePoint();
        parsedNumber.setCharsConsumed(stringSegment);
        return false;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.cp);
    }

    public String toString() {
        return "<CodePointMatcher U+" + Integer.toHexString(this.cp) + ">";
    }
}
