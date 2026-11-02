package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class PaddingMatcher extends SymbolMatcher implements NumberParseMatcher.Flexible {
    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return false;
    }

    public String toString() {
        return "<PaddingMatcher>";
    }

    public static PaddingMatcher getInstance(String str) {
        return new PaddingMatcher(str);
    }

    private PaddingMatcher(String str) {
        super(str, UnicodeSet.EMPTY);
    }
}
