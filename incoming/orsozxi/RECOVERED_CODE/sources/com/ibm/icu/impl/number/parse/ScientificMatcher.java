package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class ScientificMatcher implements NumberParseMatcher {
    private final String customMinusSign;
    private final String customPlusSign;
    private final DecimalMatcher exponentMatcher;
    private final String exponentSeparatorString;
    private final IgnorablesMatcher ignorablesMatcher = IgnorablesMatcher.getInstance(32768);

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public static ScientificMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper) {
        return new ScientificMatcher(decimalFormatSymbols, grouper);
    }

    private ScientificMatcher(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper) {
        this.exponentSeparatorString = decimalFormatSymbols.getExponentSeparator();
        this.exponentMatcher = DecimalMatcher.getInstance(decimalFormatSymbols, grouper, 48);
        String minusSignString = decimalFormatSymbols.getMinusSignString();
        this.customMinusSign = minusSignSet().contains(minusSignString) ? null : minusSignString;
        String plusSignString = decimalFormatSymbols.getPlusSignString();
        this.customPlusSign = plusSignSet().contains(plusSignString) ? null : plusSignString;
    }

    private static UnicodeSet minusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN);
    }

    private static UnicodeSet plusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN);
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!parsedNumber.seenNumber() || (parsedNumber.flags & 8) != 0) {
            return false;
        }
        int offset = stringSegment.getOffset();
        int commonPrefixLength = stringSegment.getCommonPrefixLength(this.exponentSeparatorString);
        if (commonPrefixLength != this.exponentSeparatorString.length()) {
            return commonPrefixLength == stringSegment.length();
        }
        if (stringSegment.length() == commonPrefixLength) {
            return true;
        }
        stringSegment.adjustOffset(commonPrefixLength);
        this.ignorablesMatcher.match(stringSegment, null);
        if (stringSegment.length() == 0) {
            stringSegment.setOffset(offset);
            return true;
        }
        int i = -1;
        if (stringSegment.startsWith(minusSignSet())) {
            stringSegment.adjustOffsetByCodePoint();
        } else {
            if (stringSegment.startsWith(plusSignSet())) {
                stringSegment.adjustOffsetByCodePoint();
            } else if (stringSegment.startsWith(this.customMinusSign)) {
                int commonPrefixLength2 = stringSegment.getCommonPrefixLength(this.customMinusSign);
                if (commonPrefixLength2 != this.customMinusSign.length()) {
                    stringSegment.setOffset(offset);
                    return true;
                }
                stringSegment.adjustOffset(commonPrefixLength2);
            } else if (stringSegment.startsWith(this.customPlusSign)) {
                int commonPrefixLength3 = stringSegment.getCommonPrefixLength(this.customPlusSign);
                if (commonPrefixLength3 != this.customPlusSign.length()) {
                    stringSegment.setOffset(offset);
                    return true;
                }
                stringSegment.adjustOffset(commonPrefixLength3);
            }
            i = 1;
        }
        if (stringSegment.length() == 0) {
            stringSegment.setOffset(offset);
            return true;
        }
        this.ignorablesMatcher.match(stringSegment, null);
        if (stringSegment.length() == 0) {
            stringSegment.setOffset(offset);
            return true;
        }
        boolean z = parsedNumber.quantity == null;
        if (z) {
            parsedNumber.quantity = new DecimalQuantity_DualStorageBCD();
        }
        int offset2 = stringSegment.getOffset();
        boolean zMatch = this.exponentMatcher.match(stringSegment, parsedNumber, i);
        if (z) {
            parsedNumber.quantity = null;
        }
        if (stringSegment.getOffset() != offset2) {
            parsedNumber.flags |= 8;
        } else {
            stringSegment.setOffset(offset);
        }
        return zMatch;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.exponentSeparatorString);
    }

    public String toString() {
        return "<ScientificMatcher " + this.exponentSeparatorString + ">";
    }
}
