package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class NanMatcher extends SymbolMatcher {
    private static final NanMatcher DEFAULT = new NanMatcher("NaN");

    public String toString() {
        return "<NanMatcher>";
    }

    public static NanMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, int i) {
        String naN = decimalFormatSymbols.getNaN();
        if (DEFAULT.string.equals(naN)) {
            return DEFAULT;
        }
        return new NanMatcher(naN);
    }

    private NanMatcher(String str) {
        super(str, UnicodeSet.EMPTY);
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return parsedNumber.seenNumber();
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 64;
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
