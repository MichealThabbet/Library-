package com.ibm.icu.text;

import com.facebook.internal.AnalyticsEvents;
import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class NumberingSystem {
    private static final String[] OTHER_NS_KEYWORDS = {AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE, "traditional", "finance"};
    public static final NumberingSystem LATIN = lookupInstanceByName("latn");
    private static CacheBase<String, NumberingSystem, LocaleLookupData> cachedLocaleData = new SoftCache<String, NumberingSystem, LocaleLookupData>() { // from class: com.ibm.icu.text.NumberingSystem.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public NumberingSystem createInstance(String str, LocaleLookupData localeLookupData) {
            return NumberingSystem.lookupInstanceByLocale(localeLookupData);
        }
    };
    private static CacheBase<String, NumberingSystem, Void> cachedStringData = new SoftCache<String, NumberingSystem, Void>() { // from class: com.ibm.icu.text.NumberingSystem.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ibm.icu.impl.CacheBase
        public NumberingSystem createInstance(String str, Void r2) {
            return NumberingSystem.lookupInstanceByName(str);
        }
    };
    private int radix = 10;
    private boolean algorithmic = false;
    private String desc = "0123456789";
    private String name = "latn";

    public static NumberingSystem getInstance(int i, boolean z, String str) {
        return getInstance(null, i, z, str);
    }

    private static NumberingSystem getInstance(String str, int i, boolean z, String str2) {
        if (i < 2) {
            throw new IllegalArgumentException("Invalid radix for numbering system");
        }
        if (!z && (str2.codePointCount(0, str2.length()) != i || !isValidDigitString(str2))) {
            throw new IllegalArgumentException("Invalid digit string for numbering system");
        }
        NumberingSystem numberingSystem = new NumberingSystem();
        numberingSystem.radix = i;
        numberingSystem.algorithmic = z;
        numberingSystem.desc = str2;
        numberingSystem.name = str;
        return numberingSystem;
    }

    public static NumberingSystem getInstance(Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }

    public static NumberingSystem getInstance(ULocale uLocale) {
        String keywordValue = uLocale.getKeywordValue("numbers");
        String str = "default";
        boolean z = false;
        if (keywordValue != null) {
            String[] strArr = OTHER_NS_KEYWORDS;
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                if (keywordValue.equals(strArr[i])) {
                    break;
                }
                i++;
            }
        } else {
            keywordValue = "default";
        }
        if (z) {
            NumberingSystem instanceByName = getInstanceByName(keywordValue);
            if (instanceByName != null) {
                return instanceByName;
            }
        } else {
            str = keywordValue;
        }
        return cachedLocaleData.getInstance(uLocale.getBaseName() + "@numbers=" + str, new LocaleLookupData(uLocale, str));
    }

    private static class LocaleLookupData {
        public final ULocale locale;
        public final String numbersKeyword;

        LocaleLookupData(ULocale uLocale, String str) {
            this.locale = uLocale;
            this.numbersKeyword = str;
        }
    }

    static NumberingSystem lookupInstanceByLocale(LocaleLookupData localeLookupData) {
        String stringWithFallback;
        try {
            ICUResourceBundle withFallback = ((ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, localeLookupData.locale)).getWithFallback("NumberElements");
            String str = localeLookupData.numbersKeyword;
            while (true) {
                try {
                    stringWithFallback = withFallback.getStringWithFallback(str);
                    break;
                } catch (MissingResourceException unused) {
                    if (!str.equals(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE) && !str.equals("finance")) {
                        if (!str.equals("traditional")) {
                            stringWithFallback = null;
                            break;
                        }
                        str = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE;
                    } else {
                        str = "default";
                    }
                }
            }
            NumberingSystem instanceByName = stringWithFallback != null ? getInstanceByName(stringWithFallback) : null;
            return instanceByName == null ? new NumberingSystem() : instanceByName;
        } catch (MissingResourceException unused2) {
            return new NumberingSystem();
        }
    }

    public static NumberingSystem getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static NumberingSystem getInstanceByName(String str) {
        return cachedStringData.getInstance(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NumberingSystem lookupInstanceByName(String str) {
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "numberingSystems").get("numberingSystems").get(str);
            return getInstance(str, uResourceBundle.get("radix").getInt(), uResourceBundle.get("algorithmic").getInt() == 1, uResourceBundle.getString("desc"));
        } catch (MissingResourceException unused) {
            return null;
        }
    }

    public static String[] getAvailableNames() {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "numberingSystems").get("numberingSystems");
        ArrayList arrayList = new ArrayList();
        UResourceBundleIterator iterator = uResourceBundle.getIterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getKey());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean isValidDigitString(String str) {
        return str.codePointCount(0, str.length()) == 10;
    }

    public int getRadix() {
        return this.radix;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlgorithmic() {
        return this.algorithmic;
    }
}
