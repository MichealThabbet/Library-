package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
public class AffixTokenMatcherFactory {
    public Currency currency;
    public IgnorablesMatcher ignorables;
    public ULocale locale;
    public int parseFlags;
    public DecimalFormatSymbols symbols;

    public MinusSignMatcher minusSign() {
        return MinusSignMatcher.getInstance(this.symbols, true);
    }

    public PlusSignMatcher plusSign() {
        return PlusSignMatcher.getInstance(this.symbols, true);
    }

    public PercentMatcher percent() {
        return PercentMatcher.getInstance(this.symbols);
    }

    public PermilleMatcher permille() {
        return PermilleMatcher.getInstance(this.symbols);
    }

    public CombinedCurrencyMatcher currency() {
        return CombinedCurrencyMatcher.getInstance(this.currency, this.symbols, this.parseFlags);
    }

    public IgnorablesMatcher ignorables() {
        return this.ignorables;
    }
}
