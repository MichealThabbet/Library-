package com.ibm.icu.number;

import com.ibm.icu.util.Currency;

/* loaded from: classes2.dex */
public abstract class CurrencyPrecision extends Precision {
    CurrencyPrecision() {
    }

    public Precision withCurrency(Currency currency) {
        if (currency != null) {
            Precision precisionConstructFromCurrency = constructFromCurrency(this, currency);
            precisionConstructFromCurrency.trailingZeroDisplay = this.trailingZeroDisplay;
            return precisionConstructFromCurrency;
        }
        throw new IllegalArgumentException("Currency must not be null");
    }
}
