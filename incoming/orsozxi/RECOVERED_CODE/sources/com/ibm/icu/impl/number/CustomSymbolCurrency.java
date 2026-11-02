package com.ibm.icu.impl.number;

import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;

/* loaded from: classes2.dex */
public class CustomSymbolCurrency extends Currency {
    private static final long serialVersionUID = 2497493016770137670L;
    private String symbol1;
    private String symbol2;

    public static Currency resolve(Currency currency, ULocale uLocale, DecimalFormatSymbols decimalFormatSymbols) {
        if (currency == null) {
            currency = decimalFormatSymbols.getCurrency();
        }
        if (currency == null) {
            return Currency.getInstance("XXX");
        }
        if (!currency.equals(decimalFormatSymbols.getCurrency())) {
            return currency;
        }
        String currencySymbol = decimalFormatSymbols.getCurrencySymbol();
        String internationalCurrencySymbol = decimalFormatSymbols.getInternationalCurrencySymbol();
        String name = currency.getName(decimalFormatSymbols.getULocale(), 0, (boolean[]) null);
        String currencyCode = currency.getCurrencyCode();
        return (name.equals(currencySymbol) && currencyCode.equals(internationalCurrencySymbol)) ? currency : new CustomSymbolCurrency(currencyCode, currencySymbol, internationalCurrencySymbol);
    }

    public CustomSymbolCurrency(String str, String str2, String str3) {
        super(str);
        this.symbol1 = str2;
        this.symbol2 = str3;
    }

    @Override // com.ibm.icu.util.Currency
    public String getName(ULocale uLocale, int i, boolean[] zArr) {
        if (i == 0) {
            if (zArr != null) {
                zArr[0] = false;
            }
            return this.symbol1;
        }
        return super.getName(uLocale, i, zArr);
    }

    @Override // com.ibm.icu.util.Currency
    public String getName(ULocale uLocale, int i, String str, boolean[] zArr) {
        return super.getName(uLocale, i, str, zArr);
    }

    @Override // com.ibm.icu.util.Currency
    public String getCurrencyCode() {
        return this.symbol2;
    }

    @Override // com.ibm.icu.util.MeasureUnit
    public int hashCode() {
        return (super.hashCode() ^ this.symbol1.hashCode()) ^ this.symbol2.hashCode();
    }

    @Override // com.ibm.icu.util.MeasureUnit
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            CustomSymbolCurrency customSymbolCurrency = (CustomSymbolCurrency) obj;
            if (customSymbolCurrency.symbol1.equals(this.symbol1) && customSymbolCurrency.symbol2.equals(this.symbol2)) {
                return true;
            }
        }
        return false;
    }
}
