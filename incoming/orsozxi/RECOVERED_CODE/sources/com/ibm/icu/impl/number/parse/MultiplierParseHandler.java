package com.ibm.icu.impl.number.parse;

import com.ibm.icu.number.Scale;

/* loaded from: classes2.dex */
public class MultiplierParseHandler extends ValidationMatcher {
    private final Scale multiplier;

    public MultiplierParseHandler(Scale scale) {
        this.multiplier = scale;
    }

    @Override // com.ibm.icu.impl.number.parse.NumberParseMatcher
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.quantity != null) {
            this.multiplier.applyReciprocalTo(parsedNumber.quantity);
        }
    }

    public String toString() {
        return "<MultiplierHandler " + this.multiplier + ">";
    }
}
