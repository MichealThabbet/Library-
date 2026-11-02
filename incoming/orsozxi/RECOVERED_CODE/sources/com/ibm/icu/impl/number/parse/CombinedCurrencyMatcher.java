package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CombinedCurrencyMatcher implements NumberParseMatcher {
    private final String afterPrefixInsert;
    private final String beforeSuffixInsert;
    private final String currency1;
    private final String currency2;
    private final String isoCode;
    private final String[] localLongNames;
    private final TextTrieMap<Currency.CurrencyStringInfo> longNameTrie;
    private final TextTrieMap<Currency.CurrencyStringInfo> symbolTrie;

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public boolean smokeTest(StringSegment stringSegment) {
        return true;
    }

    public static CombinedCurrencyMatcher getInstance(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int i) {
        return new CombinedCurrencyMatcher(currency, decimalFormatSymbols, i);
    }

    private CombinedCurrencyMatcher(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int i) {
        this.isoCode = currency.getSubtype();
        this.currency1 = currency.getSymbol(decimalFormatSymbols.getULocale());
        this.currency2 = currency.getCurrencyCode();
        this.afterPrefixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, false);
        this.beforeSuffixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, true);
        if ((i & 8192) == 0) {
            this.longNameTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 1);
            this.symbolTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 0);
            this.localLongNames = null;
            return;
        }
        this.longNameTrie = null;
        this.symbolTrie = null;
        this.localLongNames = new String[StandardPlural.COUNT];
        for (int i2 = 0; i2 < StandardPlural.COUNT; i2++) {
            this.localLongNames[i2] = currency.getName(decimalFormatSymbols.getLocale(), 2, StandardPlural.VALUES.get(i2).getKeyword(), (boolean[]) null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean match(com.ibm.icu.impl.StringSegment r6, com.ibm.icu.impl.number.parse.ParsedNumber r7) {
        /*
            r5 = this;
            java.lang.String r0 = r7.currencyCode
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            int r0 = r6.getOffset()
            boolean r2 = r7.seenNumber()
            r3 = 1
            if (r2 == 0) goto L32
            java.lang.String r2 = r5.beforeSuffixInsert
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L32
            java.lang.String r2 = r5.beforeSuffixInsert
            int r2 = r6.getCommonPrefixLength(r2)
            java.lang.String r4 = r5.beforeSuffixInsert
            int r4 = r4.length()
            if (r2 != r4) goto L2a
            r6.adjustOffset(r2)
        L2a:
            int r4 = r6.length()
            if (r2 != r4) goto L32
            r2 = 1
            goto L33
        L32:
            r2 = 0
        L33:
            if (r2 != 0) goto L3e
            boolean r2 = r5.matchCurrency(r6, r7)
            if (r2 == 0) goto L3c
            goto L3e
        L3c:
            r2 = 0
            goto L3f
        L3e:
            r2 = 1
        L3f:
            java.lang.String r4 = r7.currencyCode
            if (r4 != 0) goto L47
            r6.setOffset(r0)
            return r2
        L47:
            boolean r7 = r7.seenNumber()
            if (r7 != 0) goto L70
            java.lang.String r7 = r5.afterPrefixInsert
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L70
            java.lang.String r7 = r5.afterPrefixInsert
            int r7 = r6.getCommonPrefixLength(r7)
            java.lang.String r0 = r5.afterPrefixInsert
            int r0 = r0.length()
            if (r7 != r0) goto L66
            r6.adjustOffset(r7)
        L66:
            if (r2 != 0) goto L6e
            int r6 = r6.length()
            if (r7 != r6) goto L6f
        L6e:
            r1 = 1
        L6f:
            r2 = r1
        L70:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.number.parse.CombinedCurrencyMatcher.match(com.ibm.icu.impl.StringSegment, com.ibm.icu.impl.number.parse.ParsedNumber):boolean");
    }

    private boolean matchCurrency(StringSegment stringSegment, ParsedNumber parsedNumber) {
        int caseSensitivePrefixLength = !this.currency1.isEmpty() ? stringSegment.getCaseSensitivePrefixLength(this.currency1) : -1;
        boolean z = true;
        boolean z2 = caseSensitivePrefixLength == stringSegment.length();
        if (caseSensitivePrefixLength == this.currency1.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(caseSensitivePrefixLength);
            parsedNumber.setCharsConsumed(stringSegment);
            return z2;
        }
        int commonPrefixLength = this.currency2.isEmpty() ? -1 : stringSegment.getCommonPrefixLength(this.currency2);
        boolean z3 = z2 || commonPrefixLength == stringSegment.length();
        if (commonPrefixLength == this.currency2.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(commonPrefixLength);
            parsedNumber.setCharsConsumed(stringSegment);
            return z3;
        }
        if (this.longNameTrie != null) {
            TextTrieMap.Output output = new TextTrieMap.Output();
            Iterator<Currency.CurrencyStringInfo> it2 = this.longNameTrie.get(stringSegment, 0, output);
            z3 = z3 || output.partialMatch;
            if (it2 == null) {
                it2 = this.symbolTrie.get(stringSegment, 0, output);
                if (!z3 && !output.partialMatch) {
                    z = false;
                }
                z3 = z;
            }
            if (it2 != null) {
                parsedNumber.currencyCode = it2.next().getISOCode();
                stringSegment.adjustOffset(output.matchLength);
                parsedNumber.setCharsConsumed(stringSegment);
                return z3;
            }
        } else {
            int length = 0;
            for (int i = 0; i < StandardPlural.COUNT; i++) {
                String str = this.localLongNames[i];
                if (!str.isEmpty()) {
                    int commonPrefixLength2 = stringSegment.getCommonPrefixLength(str);
                    if (commonPrefixLength2 == str.length() && str.length() > length) {
                        length = str.length();
                    }
                    z3 = z3 || commonPrefixLength2 > 0;
                }
            }
            if (length > 0) {
                parsedNumber.currencyCode = this.isoCode;
                stringSegment.adjustOffset(length);
                parsedNumber.setCharsConsumed(stringSegment);
            }
        }
        return z3;
    }

    public String toString() {
        return "<CombinedCurrencyMatcher " + this.isoCode + ">";
    }
}
