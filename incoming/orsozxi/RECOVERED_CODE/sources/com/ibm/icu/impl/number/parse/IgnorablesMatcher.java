package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class IgnorablesMatcher extends SymbolMatcher implements NumberParseMatcher.Flexible {
    private static final IgnorablesMatcher DEFAULT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES));
    private static final IgnorablesMatcher STRICT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.STRICT_IGNORABLES));
    private static final IgnorablesMatcher JAVA_COMPATIBILITY = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.EMPTY));

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    @Override // com.ibm.icu.impl.number.parse.SymbolMatcher
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return false;
    }

    public String toString() {
        return "<IgnorablesMatcher>";
    }

    public static IgnorablesMatcher getInstance(int i) {
        if ((65536 & i) != 0) {
            return JAVA_COMPATIBILITY;
        }
        if ((i & 32768) != 0) {
            return STRICT;
        }
        return DEFAULT;
    }

    private IgnorablesMatcher(UnicodeSet unicodeSet) {
        super("", unicodeSet);
    }
}
