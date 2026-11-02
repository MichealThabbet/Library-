package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.http.client.config.CookieSpecs;

/* loaded from: classes2.dex */
final class BreakIteratorFactory extends BreakIterator.BreakIteratorServiceShim {
    static final ICULocaleService service = new BFService();
    private static final String[] KIND_NAMES = {"grapheme", "word", "line", "sentence", "title"};

    BreakIteratorFactory() {
    }

    @Override // com.ibm.icu.text.BreakIterator.BreakIteratorServiceShim
    public Object registerInstance(BreakIterator breakIterator, ULocale uLocale, int i) {
        breakIterator.setText(new java.text.StringCharacterIterator(""));
        return service.registerObject(breakIterator, uLocale, i);
    }

    @Override // com.ibm.icu.text.BreakIterator.BreakIteratorServiceShim
    public boolean unregister(Object obj) {
        if (service.isDefault()) {
            return false;
        }
        return service.unregisterFactory((ICUService.Factory) obj);
    }

    @Override // com.ibm.icu.text.BreakIterator.BreakIteratorServiceShim
    public Locale[] getAvailableLocales() {
        ICULocaleService iCULocaleService = service;
        if (iCULocaleService == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return iCULocaleService.getAvailableLocales();
    }

    @Override // com.ibm.icu.text.BreakIterator.BreakIteratorServiceShim
    public ULocale[] getAvailableULocales() {
        ICULocaleService iCULocaleService = service;
        if (iCULocaleService == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return iCULocaleService.getAvailableULocales();
    }

    @Override // com.ibm.icu.text.BreakIterator.BreakIteratorServiceShim
    public BreakIterator createBreakIterator(ULocale uLocale, int i) {
        if (service.isDefault()) {
            return createBreakInstance(uLocale, i);
        }
        ULocale[] uLocaleArr = new ULocale[1];
        BreakIterator breakIterator = (BreakIterator) service.get(uLocale, i, uLocaleArr);
        breakIterator.setLocale(uLocaleArr[0], uLocaleArr[0]);
        return breakIterator;
    }

    private static class BFService extends ICULocaleService {
        @Override // com.ibm.icu.impl.ICULocaleService
        public String validateFallbackLocale() {
            return "";
        }

        BFService() {
            super("BreakIterator");
            registerFactory(new ICULocaleService.ICUResourceBundleFactory() { // from class: com.ibm.icu.text.BreakIteratorFactory.BFService.1RBBreakIteratorFactory
                @Override // com.ibm.icu.impl.ICULocaleService.ICUResourceBundleFactory, com.ibm.icu.impl.ICULocaleService.LocaleKeyFactory
                protected Object handleCreate(ULocale uLocale, int i, ICUService iCUService) {
                    return BreakIteratorFactory.createBreakInstance(uLocale, i);
                }
            });
            markDefault();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BreakIterator createBreakInstance(ULocale uLocale, int i) {
        String str;
        String str2;
        String keywordValue;
        String keywordValue2;
        ICUResourceBundle bundleInstance = ICUResourceBundle.getBundleInstance(ICUData.ICU_BRKITR_BASE_NAME, uLocale, ICUResourceBundle.OpenType.LOCALE_ROOT);
        RuleBasedBreakIterator instanceFromCompiledRules = null;
        if (i == 2 && (keywordValue2 = uLocale.getKeywordValue("lb")) != null && (keywordValue2.equals("strict") || keywordValue2.equals("normal") || keywordValue2.equals("loose"))) {
            str = BaseLocale.SEP + keywordValue2;
        } else {
            str = null;
        }
        try {
            if (str == null) {
                str2 = KIND_NAMES[i];
            } else {
                str2 = KIND_NAMES[i] + str;
            }
            try {
                instanceFromCompiledRules = RuleBasedBreakIterator.getInstanceFromCompiledRules(ICUBinary.getData("brkitr/" + bundleInstance.getStringWithFallback("boundaries/" + str2)));
            } catch (IOException e) {
                Assert.fail(e);
            }
            ULocale uLocaleForLocale = ULocale.forLocale(bundleInstance.getLocale());
            instanceFromCompiledRules.setLocale(uLocaleForLocale, uLocaleForLocale);
            return (i == 3 && (keywordValue = uLocale.getKeywordValue("ss")) != null && keywordValue.equals(CookieSpecs.STANDARD)) ? FilteredBreakIteratorBuilder.getInstance(new ULocale(uLocale.getBaseName())).wrapIteratorWithFilter(instanceFromCompiledRules) : instanceFromCompiledRules;
        } catch (Exception e2) {
            throw new MissingResourceException(e2.toString(), "", "");
        }
    }
}
