package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
public class PlusSignMatcher extends SymbolMatcher {
    private static final PlusSignMatcher DEFAULT = new PlusSignMatcher(false);
    private static final PlusSignMatcher DEFAULT_ALLOW_TRAILING = new PlusSignMatcher(true);
    private final boolean allowTrailing;

    public String toString() {
        return "<PlusSignMatcher>";
    }

    public static PlusSignMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, boolean z) {
        String plusSignString = decimalFormatSymbols.getPlusSignString();
        if (DEFAULT.uniSet.contains(plusSignString)) {
            return z ? DEFAULT_ALLOW_TRAILING : DEFAULT;
        }
        return new PlusSignMatcher(plusSignString, z);
    }

    private PlusSignMatcher(String str, boolean z) {
        super(str, DEFAULT.uniSet);
        this.allowTrailing = z;
    }

    private PlusSignMatcher(boolean z) {
        super(StaticUnicodeSets.Key.PLUS_SIGN);
        this.allowTrailing = z;
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return !this.allowTrailing && parsedNumber.seenNumber();
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
