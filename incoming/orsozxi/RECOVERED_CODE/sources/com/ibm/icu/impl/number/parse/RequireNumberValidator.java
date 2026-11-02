package com.ibm.icu.impl.number.parse;

/* loaded from: classes2.dex */
public class RequireNumberValidator extends ValidationMatcher {
    public String toString() {
        return "<RequireNumber>";
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.seenNumber()) {
            return;
        }
        parsedNumber.flags |= 256;
    }
}
