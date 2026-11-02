package com.ibm.icu.text;

import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes2.dex */
class NumberFormatServiceShim extends NumberFormat.NumberFormatShim {
    private static ICULocaleService service = new NFService();

    NumberFormatServiceShim() {
    }

    @Override // com.ibm.icu.text.NumberFormat.NumberFormatShim
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override // com.ibm.icu.text.NumberFormat.NumberFormatShim
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    private static final class NFFactory extends ICULocaleService.LocaleKeyFactory {
        private NumberFormat.NumberFormatFactory delegate;

        NFFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
            super(numberFormatFactory.visible());
            this.delegate = numberFormatFactory;
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory, com.ibm.icu.impl.ICUService.Factory
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!handlesKey(key) || !(key instanceof ICULocaleService.LocaleKey)) {
                return null;
            }
            ICULocaleService.LocaleKey localeKey = (ICULocaleService.LocaleKey) key;
            NumberFormat numberFormatCreateFormat = this.delegate.createFormat(localeKey.canonicalLocale(), localeKey.kind());
            return numberFormatCreateFormat == null ? iCUService.getKey(key, null, this) : numberFormatCreateFormat;
        }

        @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
        protected Set<String> getSupportedIDs() {
            return this.delegate.getSupportedLocaleNames();
        }
    }

    @Override // com.ibm.icu.text.NumberFormat.NumberFormatShim
    Object registerFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
        return service.registerFactory(new NFFactory(numberFormatFactory));
    }

    @Override // com.ibm.icu.text.NumberFormat.NumberFormatShim
    boolean unregister(Object obj) {
        return service.unregisterFactory((ICUService.Factory) obj);
    }

    @Override // com.ibm.icu.text.NumberFormat.NumberFormatShim
    NumberFormat createInstance(ULocale uLocale, int i) {
        ULocale[] uLocaleArr = new ULocale[1];
        NumberFormat numberFormat = (NumberFormat) service.get(uLocale, i, uLocaleArr);
        if (numberFormat == null) {
            throw new MissingResourceException("Unable to construct NumberFormat", "", "");
        }
        NumberFormat numberFormat2 = (NumberFormat) numberFormat.clone();
        if (i == 1 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9) {
            numberFormat2.setCurrency(Currency.getInstance(uLocale));
        }
        ULocale uLocale2 = uLocaleArr[0];
        numberFormat2.setLocale(uLocale2, uLocale2);
        return numberFormat2;
    }

    private static class NFService extends ICULocaleService {
        NFService() {
            super("NumberFormat");
            registerFactory(new ICULocaleService.ICUResourceBundleFactory() { // from class: com.ibm.icu.text.NumberFormatServiceShim.NFService.1RBNumberFormatFactory
                @Override // com.ibm.icu.impl.ICULocaleService.ICUResourceBundleFactory, com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
                protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
                    return NumberFormat.createInstance(uLocale, i);
                }
            });
            markDefault();
        }
    }
}
