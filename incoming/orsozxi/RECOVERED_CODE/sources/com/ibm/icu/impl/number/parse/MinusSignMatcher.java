package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
public class MinusSignMatcher extends SymbolMatcher {
    private static final MinusSignMatcher DEFAULT = new MinusSignMatcher(false);
    private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);
    private final boolean allowTrailing;

    public String toString() {
        return "<MinusSignMatcher>";
    }

    public static MinusSignMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, boolean z) {
        String minusSignString = decimalFormatSymbols.getMinusSignString();
        if (DEFAULT.uniSet.contains(minusSignString)) {
            return z ? DEFAULT_ALLOW_TRAILING : DEFAULT;
        }
        return new MinusSignMatcher(minusSignString, z);
    }

    private MinusSignMatcher(String str, boolean z) {
        super(str, DEFAULT.uniSet);
        this.allowTrailing = z;
    }

    private MinusSignMatcher(boolean z) {
        super(StaticUnicodeSets.Key.MINUS_SIGN);
        this.allowTrailing = z;
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return !this.allowTrailing && parsedNumber.seenNumber();
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 1;
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
