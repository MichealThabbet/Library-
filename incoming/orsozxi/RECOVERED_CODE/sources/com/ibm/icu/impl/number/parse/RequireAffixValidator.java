package com.ibm.icu.impl.number.parse;

/* loaded from: classes2.dex */
public class RequireAffixValidator extends ValidationMatcher {
    public String toString() {
        return "<RequireAffix>";
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.prefix == null || parsedNumber.suffix == null) {
            parsedNumber.flags |= 256;
        }
    }
}
