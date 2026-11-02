package com.ibm.icu.text;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.impl.coll.CollationLoader;
import com.ibm.icu.text.Collator;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes2.dex */
final class CollatorServiceShim extends Collator.ServiceShim {
    private static ICULocaleService service = new CService();

    CollatorServiceShim() {
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    Collator getInstance(ULocale uLocale) {
        try {
            Collator collator = (Collator) service.get(uLocale, new ULocale[1]);
            if (collator == null) {
                throw new MissingResourceException("Could not locate Collator data", "", "");
            }
            return (Collator) collator.clone();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    Object registerInstance(Collator collator, ULocale uLocale) {
        collator.setLocale(uLocale, uLocale);
        return service.registerObject(collator, uLocale);
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    Object registerFactory(Collator.CollatorFactory collatorFactory) {
        return service.registerFactory(new ICULocaleService.LocaleKeyFactory(collatorFactory) { // from class: com.ibm.icu.text.CollatorServiceShim.1CFactory
            Collator.CollatorFactory delegate;

            {
                super(collatorFactory.visible());
                this.delegate = collatorFactory;
            }

            @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
            public Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
                return this.delegate.createCollator(uLocale);
            }

            @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory, com.ibm.icu.impl.ICUService.Factory
            public String getDisplayName(String str, ULocale uLocale) {
                return this.delegate.getDisplayName(new ULocale(str), uLocale);
            }

            @Override // com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
            public Set<String> getSupportedIDs() {
                return this.delegate.getSupportedLocaleIDs();
            }
        });
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    boolean unregister(Object obj) {
        return service.unregisterFactory((ICUService.Factory) obj);
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales(ICUData.ICU_COLLATION_BASE_NAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return service.getAvailableLocales();
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales(ICUData.ICU_COLLATION_BASE_NAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return service.getAvailableULocales();
    }

    @Override // com.ibm.icu.text.Collator.ServiceShim
    String getDisplayName(ULocale uLocale, ULocale uLocale2) {
        return service.getDisplayName(uLocale.getName(), uLocale2);
    }

    private static class CService extends ICULocaleService {
        @Override // com.ibm.icu.impl.ICULocaleService
        public String validateFallbackLocale() {
            return "";
        }

        CService() {
            super("Collator");
            registerFactory(new ICULocaleService.ICUResourceBundleFactory() { // from class: com.ibm.icu.text.CollatorServiceShim.CService.1CollatorFactory
                @Override // com.ibm.icu.impl.ICULocaleService.ICUResourceBundleFactory, com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
                protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
                    return CollatorServiceShim.makeInstance(uLocale);
                }
            });
            markDefault();
        }

        @Override // com.ibm.icu.impl.ICUService
        protected Object handleDefault(ICUService.Key key, String[] strArr) {
            if (strArr != null) {
                strArr[0] = "root";
            }
            try {
                return CollatorServiceShim.makeInstance(ULocale.ROOT);
            } catch (MissingResourceException unused) {
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Collator makeInstance(ULocale uLocale) {
        Output output = new Output(ULocale.ROOT);
        return new RuleBasedCollator(CollationLoader.loadTailoring(uLocale, output), (ULocale) output.value);
    }
}
