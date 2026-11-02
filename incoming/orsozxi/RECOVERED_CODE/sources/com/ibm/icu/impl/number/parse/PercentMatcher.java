package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
public class PercentMatcher extends SymbolMatcher {
    private static final PercentMatcher DEFAULT = new PercentMatcher();

    public String toString() {
        return "<PercentMatcher>";
    }

    public static PercentMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String percentString = decimalFormatSymbols.getPercentString();
        if (DEFAULT.uniSet.contains(percentString)) {
            return DEFAULT;
        }
        return new PercentMatcher(percentString);
    }

    private PercentMatcher(String str) {
        super(str, DEFAULT.uniSet);
    }

    private PercentMatcher() {
        super(StaticUnicodeSets.Key.PERCENT_SIGN);
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return (parsedNumber.flags & 2) != 0;
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 2;
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
