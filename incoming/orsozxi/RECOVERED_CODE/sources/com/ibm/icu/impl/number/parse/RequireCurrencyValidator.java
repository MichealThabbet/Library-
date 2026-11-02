package com.ibm.icu.impl.number.parse;

/* loaded from: classes2.dex */
public class RequireCurrencyValidator extends ValidationMatcher {
    public String toString() {
        return "<RequireCurrency>";
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.currencyCode == null) {
            parsedNumber.flags |= 256;
        }
    }
}
