package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;

/* loaded from: classes2.dex */
public interface NumberParseMatcher {

    public interface Flexible {
    }

    boolean match(StringSegment stringSegment, ParsedNumber parsedNumber);

    void postProcess(ParsedNumber parsedNumber);

    boolean smokeTest(StringSegment stringSegment);
}
