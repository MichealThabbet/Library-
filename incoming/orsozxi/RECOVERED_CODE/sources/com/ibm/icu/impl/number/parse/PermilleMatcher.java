package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
public class PermilleMatcher extends SymbolMatcher {
    private static final PermilleMatcher DEFAULT = new PermilleMatcher();

    public String toString() {
        return "<PermilleMatcher>";
    }

    public static PermilleMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols) {
        String perMillString = decimalFormatSymbols.getPerMillString();
        if (DEFAULT.uniSet.contains(perMillString)) {
            return DEFAULT;
        }
        return new PermilleMatcher(perMillString);
    }

    private PermilleMatcher(String str) {
        super(str, DEFAULT.uniSet);
    }

    private PermilleMatcher() {
        super(StaticUnicodeSets.Key.PERMILLE_SIGN);
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return (parsedNumber.flags & 4) != 0;
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 4;
        parsedNumber.setCharsConsumed(stringSegment);
    }
}
