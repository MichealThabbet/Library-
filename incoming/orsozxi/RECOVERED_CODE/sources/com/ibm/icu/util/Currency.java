package com.ibm.icu.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ExceptionCode;
import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.TextTrieMap;
import com.ibm.icu.text.CurrencyDisplayNames;
import com.ibm.icu.text.CurrencyMetaInfo;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

/* loaded from: classes2.dex */
public class Currency extends MeasureUnit {
    private static SoftReference<Set<String>> ALL_CODES_AS_SET = null;
    private static SoftReference<List<String>> ALL_TENDER_CODES = null;
    public static final int FORMAL_SYMBOL_NAME = 4;
    public static final int LONG_NAME = 1;
    public static final int NARROW_SYMBOL_NAME = 3;
    public static final int PLURAL_LONG_NAME = 2;
    public static final int SYMBOL_NAME = 0;
    public static final int VARIANT_SYMBOL_NAME = 5;
    private static final long serialVersionUID = -5839973855554750484L;
    private static ServiceShim shim;
    private final String isoCode;
    private static final boolean DEBUG = ICUDebug.enabled(FirebaseAnalytics.Param.CURRENCY);
    private static ICUCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>> CURRENCY_NAME_CACHE = new SimpleCache();
    private static final CacheBase<String, Currency, Void> regionCurrencyCache = new SoftCache<String, Currency, Void>() { // from class: com.ibm.icu.util.Currency.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public Currency createInstance(String str, Void r2) {
            return Currency.loadCurrency(str);
        }
    };
    private static final ULocale UND = new ULocale("und");
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final int[] POW10 = {1, 10, 100, 1000, 10000, 100000, 1000000, ExceptionCode.CRASH_EXCEPTION, 100000000, 1000000000};

    public enum CurrencyUsage {
        STANDARD,
        CASH
    }

    static abstract class ServiceShim {
        abstract Currency createInstance(ULocale uLocale);

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract Object registerInstance(Currency currency, ULocale uLocale);

        abstract boolean unregister(Object obj);

        ServiceShim() {
        }
    }

    private static ServiceShim getShim() {
        if (shim == null) {
            try {
                shim = (ServiceShim) Class.forName("com.ibm.icu.util.CurrencyServiceShim").newInstance();
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return shim;
    }

    public static Currency getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    public static Currency getInstance(ULocale uLocale) {
        String keywordValue = uLocale.getKeywordValue(FirebaseAnalytics.Param.CURRENCY);
        if (keywordValue != null) {
            return getInstance(keywordValue);
        }
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return createCurrency(uLocale);
        }
        return serviceShim.createInstance(uLocale);
    }

    public static String[] getAvailableCurrencyCodes(ULocale uLocale, Date date) {
        List<String> tenderCurrencies = getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.onDate(date).withRegion(ULocale.getRegionForSupplementalData(uLocale, false)));
        if (tenderCurrencies.isEmpty()) {
            return null;
        }
        return (String[]) tenderCurrencies.toArray(new String[tenderCurrencies.size()]);
    }

    public static String[] getAvailableCurrencyCodes(Locale locale, Date date) {
        return getAvailableCurrencyCodes(ULocale.forLocale(locale), date);
    }

    public static Set<Currency> getAvailableCurrencies() {
        List<String> listCurrencies = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all());
        HashSet hashSet = new HashSet(listCurrencies.size());
        Iterator<String> it2 = listCurrencies.iterator();
        while (it2.hasNext()) {
            hashSet.add(getInstance(it2.next()));
        }
        return hashSet;
    }

    static Currency createCurrency(ULocale uLocale) {
        return regionCurrencyCache.getInstance(ULocale.getRegionForSupplementalData(uLocale, false), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Currency loadCurrency(String str) {
        List<String> listCurrencies = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(str));
        if (listCurrencies.isEmpty()) {
            return null;
        }
        return getInstance(listCurrencies.get(0));
    }

    public static Currency getInstance(String str) {
        if (str == null) {
            throw new NullPointerException("The input currency code is null.");
        }
        if (!isAlpha3Code(str)) {
            throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
        }
        return (Currency) MeasureUnit.internalGetInstance(FirebaseAnalytics.Param.CURRENCY, str.toUpperCase(Locale.ENGLISH));
    }

    private static boolean isAlpha3Code(String str) {
        if (str.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 'A' || ((cCharAt > 'Z' && cCharAt < 'a') || cCharAt > 'z')) {
                return false;
            }
        }
        return true;
    }

    public static Currency fromJavaCurrency(java.util.Currency currency) {
        return getInstance(currency.getCurrencyCode());
    }

    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(getCurrencyCode());
    }

    public static Object registerInstance(Currency currency, ULocale uLocale) {
        return getShim().registerInstance(currency, uLocale);
    }

    public static boolean unregister(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("registryKey must not be null");
        }
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return false;
        }
        return serviceShim.unregister(obj);
    }

    public static Locale[] getAvailableLocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return serviceShim.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return serviceShim.getAvailableULocales();
    }

    public static final String[] getKeywordValuesForLocale(String str, ULocale uLocale, boolean z) {
        if (!FirebaseAnalytics.Param.CURRENCY.equals(str)) {
            return EMPTY_STRING_ARRAY;
        }
        if (!z) {
            return (String[]) getAllTenderCurrencies().toArray(new String[0]);
        }
        if (UND.equals(uLocale)) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> tenderCurrencies = getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.now().withRegion(ULocale.getRegionForSupplementalData(uLocale, true)));
        if (tenderCurrencies.size() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return (String[]) tenderCurrencies.toArray(new String[tenderCurrencies.size()]);
    }

    public String getCurrencyCode() {
        return this.subType;
    }

    public int getNumericCode() {
        try {
            return UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("codeMap").get(this.subType).getInt();
        } catch (MissingResourceException unused) {
            return 0;
        }
    }

    public String getSymbol() {
        return getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getSymbol(Locale locale) {
        return getSymbol(ULocale.forLocale(locale));
    }

    public String getSymbol(ULocale uLocale) {
        return getName(uLocale, 0, (boolean[]) null);
    }

    public String getName(Locale locale, int i, boolean[] zArr) {
        return getName(ULocale.forLocale(locale), i, zArr);
    }

    public String getName(ULocale uLocale, int i, boolean[] zArr) {
        if (zArr != null) {
            zArr[0] = false;
        }
        CurrencyDisplayNames currencyDisplayNames = CurrencyDisplayNames.getInstance(uLocale);
        if (i == 0) {
            return currencyDisplayNames.getSymbol(this.subType);
        }
        if (i == 1) {
            return currencyDisplayNames.getName(this.subType);
        }
        if (i == 3) {
            return currencyDisplayNames.getNarrowSymbol(this.subType);
        }
        if (i == 4) {
            return currencyDisplayNames.getFormalSymbol(this.subType);
        }
        if (i == 5) {
            return currencyDisplayNames.getVariantSymbol(this.subType);
        }
        throw new IllegalArgumentException("bad name style: " + i);
    }

    public String getName(Locale locale, int i, String str, boolean[] zArr) {
        return getName(ULocale.forLocale(locale), i, str, zArr);
    }

    public String getName(ULocale uLocale, int i, String str, boolean[] zArr) {
        if (i != 2) {
            return getName(uLocale, i, zArr);
        }
        if (zArr != null) {
            zArr[0] = false;
        }
        return CurrencyDisplayNames.getInstance(uLocale).getPluralName(this.subType, str);
    }

    public String getDisplayName() {
        return getName(Locale.getDefault(), 1, (boolean[]) null);
    }

    public String getDisplayName(Locale locale) {
        return getName(locale, 1, (boolean[]) null);
    }

    @Deprecated
    public static String parse(ULocale uLocale, String str, int i, ParsePosition parsePosition) {
        List<TextTrieMap<CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(uLocale);
        TextTrieMap textTrieMap = currencyTrieVec.get(1);
        CurrencyNameResultHandler currencyNameResultHandler = new CurrencyNameResultHandler();
        textTrieMap.find(str, parsePosition.getIndex(), currencyNameResultHandler);
        String bestCurrencyISOCode = currencyNameResultHandler.getBestCurrencyISOCode();
        int bestMatchLength = currencyNameResultHandler.getBestMatchLength();
        if (i != 1) {
            TextTrieMap textTrieMap2 = currencyTrieVec.get(0);
            CurrencyNameResultHandler currencyNameResultHandler2 = new CurrencyNameResultHandler();
            textTrieMap2.find(str, parsePosition.getIndex(), currencyNameResultHandler2);
            if (currencyNameResultHandler2.getBestMatchLength() > bestMatchLength) {
                bestCurrencyISOCode = currencyNameResultHandler2.getBestCurrencyISOCode();
                bestMatchLength = currencyNameResultHandler2.getBestMatchLength();
            }
        }
        parsePosition.setIndex(parsePosition.getIndex() + bestMatchLength);
        return bestCurrencyISOCode;
    }

    @Deprecated
    public static TextTrieMap<CurrencyStringInfo> getParsingTrie(ULocale uLocale, int i) {
        List<TextTrieMap<CurrencyStringInfo>> currencyTrieVec = getCurrencyTrieVec(uLocale);
        if (i == 1) {
            return currencyTrieVec.get(1);
        }
        return currencyTrieVec.get(0);
    }

    private static List<TextTrieMap<CurrencyStringInfo>> getCurrencyTrieVec(ULocale uLocale) {
        List<TextTrieMap<CurrencyStringInfo>> list = CURRENCY_NAME_CACHE.get(uLocale);
        if (list != null) {
            return list;
        }
        TextTrieMap textTrieMap = new TextTrieMap(true);
        TextTrieMap textTrieMap2 = new TextTrieMap(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(textTrieMap2);
        arrayList.add(textTrieMap);
        setupCurrencyTrieVec(uLocale, arrayList);
        CURRENCY_NAME_CACHE.put(uLocale, arrayList);
        return arrayList;
    }

    private static void setupCurrencyTrieVec(ULocale uLocale, List<TextTrieMap<CurrencyStringInfo>> list) {
        TextTrieMap<CurrencyStringInfo> textTrieMap = list.get(0);
        TextTrieMap<CurrencyStringInfo> textTrieMap2 = list.get(1);
        CurrencyDisplayNames currencyDisplayNames = CurrencyDisplayNames.getInstance(uLocale);
        for (Map.Entry<String, String> entry : currencyDisplayNames.symbolMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            StaticUnicodeSets.Key keyChooseCurrency = StaticUnicodeSets.chooseCurrency(key);
            CurrencyStringInfo currencyStringInfo = new CurrencyStringInfo(value, key);
            if (keyChooseCurrency != null) {
                Iterator<String> it2 = StaticUnicodeSets.get(keyChooseCurrency).iterator();
                while (it2.hasNext()) {
                    textTrieMap.put(it2.next(), currencyStringInfo);
                }
            } else {
                textTrieMap.put(key, currencyStringInfo);
            }
        }
        for (Map.Entry<String, String> entry2 : currencyDisplayNames.nameMap().entrySet()) {
            String key2 = entry2.getKey();
            textTrieMap2.put(key2, new CurrencyStringInfo(entry2.getValue(), key2));
        }
    }

    @Deprecated
    public static final class CurrencyStringInfo {
        private String currencyString;
        private String isoCode;

        @Deprecated
        public CurrencyStringInfo(String str, String str2) {
            this.isoCode = str;
            this.currencyString = str2;
        }

        @Deprecated
        public String getISOCode() {
            return this.isoCode;
        }

        @Deprecated
        public String getCurrencyString() {
            return this.currencyString;
        }
    }

    private static class CurrencyNameResultHandler implements TextTrieMap.ResultHandler<CurrencyStringInfo> {
        private String bestCurrencyISOCode;
        private int bestMatchLength;

        private CurrencyNameResultHandler() {
        }

        @Override // com.ibm.icu.impl.TextTrieMap.ResultHandler
        public boolean handlePrefixMatch(int i, Iterator<CurrencyStringInfo> it2) {
            if (!it2.hasNext()) {
                return true;
            }
            this.bestCurrencyISOCode = it2.next().getISOCode();
            this.bestMatchLength = i;
            return true;
        }

        public String getBestCurrencyISOCode() {
            return this.bestCurrencyISOCode;
        }

        public int getBestMatchLength() {
            return this.bestMatchLength;
        }
    }

    public int getDefaultFractionDigits() {
        return getDefaultFractionDigits(CurrencyUsage.STANDARD);
    }

    public int getDefaultFractionDigits(CurrencyUsage currencyUsage) {
        return CurrencyMetaInfo.getInstance().currencyDigits(this.subType, currencyUsage).fractionDigits;
    }

    public double getRoundingIncrement() {
        return getRoundingIncrement(CurrencyUsage.STANDARD);
    }

    public double getRoundingIncrement(CurrencyUsage currencyUsage) {
        int i;
        CurrencyMetaInfo.CurrencyDigits currencyDigits = CurrencyMetaInfo.getInstance().currencyDigits(this.subType, currencyUsage);
        int i2 = currencyDigits.roundingIncrement;
        if (i2 != 0 && (i = currencyDigits.fractionDigits) >= 0) {
            if (i < POW10.length) {
                return i2 / r3[i];
            }
        }
        return 0.0d;
    }

    @Override // com.ibm.icu.util.MeasureUnit
    public String toString() {
        return this.subType;
    }

    protected Currency(String str) {
        super(FirebaseAnalytics.Param.CURRENCY, str);
        this.isoCode = str;
    }

    private static synchronized List<String> getAllTenderCurrencies() {
        List<String> listUnmodifiableList;
        listUnmodifiableList = ALL_TENDER_CODES == null ? null : ALL_TENDER_CODES.get();
        if (listUnmodifiableList == null) {
            listUnmodifiableList = Collections.unmodifiableList(getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.all()));
            ALL_TENDER_CODES = new SoftReference<>(listUnmodifiableList);
        }
        return listUnmodifiableList;
    }

    private static synchronized Set<String> getAllCurrenciesAsSet() {
        Set<String> setUnmodifiableSet;
        setUnmodifiableSet = ALL_CODES_AS_SET == null ? null : ALL_CODES_AS_SET.get();
        if (setUnmodifiableSet == null) {
            setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all())));
            ALL_CODES_AS_SET = new SoftReference<>(setUnmodifiableSet);
        }
        return setUnmodifiableSet;
    }

    public static boolean isAvailable(String str, Date date, Date date2) {
        if (!isAlpha3Code(str)) {
            return false;
        }
        if (date != null && date2 != null && date.after(date2)) {
            throw new IllegalArgumentException("To is before from");
        }
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        if (!getAllCurrenciesAsSet().contains(upperCase)) {
            return false;
        }
        if (date == null && date2 == null) {
            return true;
        }
        return CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(date, date2).withCurrency(upperCase)).contains(upperCase);
    }

    private static List<String> getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return CurrencyMetaInfo.getInstance().currencies(currencyFilter.withTender());
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
    }

    private Object readResolve() throws ObjectStreamException {
        return getInstance(this.isoCode);
    }
}
