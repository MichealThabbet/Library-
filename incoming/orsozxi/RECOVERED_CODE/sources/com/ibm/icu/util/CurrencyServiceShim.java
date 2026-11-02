package com.ibm.icu.util;

import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.util.Currency;
import java.util.Locale;

/* loaded from: classes2.dex */
final class CurrencyServiceShim extends Currency.ServiceShim {
    static final ICULocaleService service = new CFService();

    CurrencyServiceShim() {
    }

    @Override // com.ibm.icu.util.Currency.ServiceShim
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override // com.ibm.icu.util.Currency.ServiceShim
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    @Override // com.ibm.icu.util.Currency.ServiceShim
    Currency createInstance(ULocale uLocale) {
        if (service.isDefault()) {
            return Currency.createCurrency(uLocale);
        }
        return (Currency) service.get(uLocale);
    }

    @Override // com.ibm.icu.util.Currency.ServiceShim
    Object registerInstance(Currency currency, ULocale uLocale) {
        return service.registerObject(currency, uLocale);
    }

    @Override // com.ibm.icu.util.Currency.ServiceShim
    boolean unregister(Object obj) {
        return service.unregisterFactory((ICUService.Factory) obj);
    }

    private static class CFService extends ICULocaleService {
        CFService() {
            super("Currency");
            registerFactory(new ICULocaleService.ICUResourceBundleFactory() { // from class: com.ibm.icu.util.CurrencyServiceShim.CFService.1CurrencyFactory
                @Override // com.ibm.icu.impl.ICULocaleService.ICUResourceBundleFactory, com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
                protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
                    return Currency.createCurrency(uLocale);
                }
            });
            markDefault();
        }
    }
}
