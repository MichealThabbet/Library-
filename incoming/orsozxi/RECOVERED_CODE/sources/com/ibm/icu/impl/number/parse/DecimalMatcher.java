package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.UnicodeSet;

/* loaded from: classes2.dex */
public class DecimalMatcher implements NumberParseMatcher {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String decimalSeparator;
    private final UnicodeSet decimalUniSet;
    private final String[] digitStrings;
    private final int grouping1;
    private final int grouping2;
    private final boolean groupingDisabled;
    private final String groupingSeparator;
    private final UnicodeSet groupingUniSet;
    private final boolean integerOnly;
    private final UnicodeSet leadSet;
    private final boolean requireGroupingMatch;
    private final UnicodeSet separatorSet;

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
    }

    public String toString() {
        return "<DecimalMatcher>";
    }

    public static DecimalMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int i) {
        return new DecimalMatcher(decimalFormatSymbols, grouper, i);
    }

    private DecimalMatcher(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int i) {
        if ((i & 2) != 0) {
            this.groupingSeparator = decimalFormatSymbols.getMonetaryGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getMonetaryDecimalSeparatorString();
        } else {
            this.groupingSeparator = decimalFormatSymbols.getGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getDecimalSeparatorString();
        }
        boolean z = (i & 4) != 0;
        StaticUnicodeSets.Key key = z ? StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS : StaticUnicodeSets.Key.ALL_SEPARATORS;
        this.groupingUniSet = StaticUnicodeSets.get(key);
        StaticUnicodeSets.Key keyChooseFrom = StaticUnicodeSets.chooseFrom(this.decimalSeparator, z ? StaticUnicodeSets.Key.STRICT_COMMA : StaticUnicodeSets.Key.COMMA, z ? StaticUnicodeSets.Key.STRICT_PERIOD : StaticUnicodeSets.Key.PERIOD);
        if (keyChooseFrom != null) {
            this.decimalUniSet = StaticUnicodeSets.get(keyChooseFrom);
        } else if (!this.decimalSeparator.isEmpty()) {
            this.decimalUniSet = new UnicodeSet().add(this.decimalSeparator.codePointAt(0)).freeze();
        } else {
            this.decimalUniSet = UnicodeSet.EMPTY;
        }
        if (key != null && keyChooseFrom != null) {
            this.separatorSet = this.groupingUniSet;
            this.leadSet = StaticUnicodeSets.get(z ? StaticUnicodeSets.Key.DIGITS_OR_ALL_SEPARATORS : StaticUnicodeSets.Key.DIGITS_OR_STRICT_ALL_SEPARATORS);
        } else {
            this.separatorSet = new UnicodeSet().addAll(this.groupingUniSet).addAll(this.decimalUniSet).freeze();
            this.leadSet = null;
        }
        int codePointZero = decimalFormatSymbols.getCodePointZero();
        if (codePointZero == -1 || !UCharacter.isDigit(codePointZero) || UCharacter.digit(codePointZero) != 0) {
            this.digitStrings = decimalFormatSymbols.getDigitStringsLocal();
        } else {
            this.digitStrings = null;
        }
        this.requireGroupingMatch = (i & 8) != 0;
        this.groupingDisabled = (i & 32) != 0;
        this.integerOnly = (i & 16) != 0;
        this.grouping1 = grouper.getPrimary();
        this.grouping2 = grouper.getSecondary();
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return match(stringSegment, parsedNumber, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0178 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01b7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x01b5 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x013e  */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean match(com.ibm.icu.impl.StringSegment r23, com.ibm.icu.impl.number.parse.ParsedNumber r24, int r25) {
        /*
            Method dump skipped, instructions count: 654
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.parse.DecimalMatcher.match(com.ibm.icu.impl.StringSegment, com.ibm.icu.impl.number.parse.ParsedNumber, int):boolean");
    }

    private boolean validateGroup(int i, int i2, boolean z) {
        if (!this.requireGroupingMatch) {
            return (i == 1 && i2 == 1) ? false : true;
        }
        if (i == -1) {
            return true;
        }
        if (i == 0) {
            if (z) {
                return true;
            }
            return i2 != 0 && i2 <= this.grouping2;
        }
        if (i == 1) {
            return z ? i2 == this.grouping1 : i2 == this.grouping2;
        }
        return true;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        UnicodeSet unicodeSet;
        if (this.digitStrings == null && (unicodeSet = this.leadSet) != null) {
            return stringSegment.startsWith(unicodeSet);
        }
        if (stringSegment.startsWith(this.separatorSet) || UCharacter.isDigit(stringSegment.getCodePoint())) {
            return true;
        }
        if (this.digitStrings == null) {
            return false;
        }
        int i = 0;
        while (true) {
            String[] strArr = this.digitStrings;
            if (i >= strArr.length) {
                return false;
            }
            if (stringSegment.startsWith(strArr[i])) {
                return true;
            }
            i++;
        }
    }
}
