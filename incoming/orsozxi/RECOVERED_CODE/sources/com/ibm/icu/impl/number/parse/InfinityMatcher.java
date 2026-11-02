package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
public class InfinityMatcher extends SymbolMatcher {
    private static final InfinityMatcher DEFAULT = new InfinityMatcher();

    public String toString() {
        return "<InfinityMatcher>";
    }

    public static InfinityMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String infinity = decimalFormatSymbols.getInfinity();
        if (DEFAULT.uniSet.contains(infinity)) {
            return DEFAULT;
        }
        return new InfinityMatcher(infinity);
    }

    private InfinityMatcher(String str) {
        super(str, DEFAULT.uniSet);
    }

    private InfinityMatcher() {
        super(StaticUnicodeSets.Key.INFINITY_SIGN);
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return (parsedNumber.flags & 128) != 0;
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 128;
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
