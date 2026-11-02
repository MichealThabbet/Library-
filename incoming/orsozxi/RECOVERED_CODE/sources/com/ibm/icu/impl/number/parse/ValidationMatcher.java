package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;

/* loaded from: classes2.dex */
public abstract class ValidationMatcher implements NumberParseMatcher {
    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return false;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        return false;
    }
}
