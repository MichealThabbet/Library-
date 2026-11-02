package com.ibm.icu.impl;

import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

/* loaded from: classes2.dex */
public class ICUResourceTableAccess {
    public static String getTableString(String str, ULocale uLocale, String str2, String str3, String str4) {
        return getTableString((ICUResourceBundle) UResourceBundle.getBundleInstance(str, uLocale.getBaseName()), str2, (String) null, str3, str4);
    }

    public static String getTableString(ICUResourceBundle iCUResourceBundle, String str, String str2, String str3, String str4) {
        String currentLanguageID;
        String strFindStringWithFallback = null;
        while (true) {
            try {
                ICUResourceBundle iCUResourceBundleFindWithFallback = iCUResourceBundle.findWithFallback(str);
                if (iCUResourceBundleFindWithFallback != null) {
                    ICUResourceBundle iCUResourceBundleFindWithFallback2 = str2 != null ? iCUResourceBundleFindWithFallback.findWithFallback(str2) : iCUResourceBundleFindWithFallback;
                    if (iCUResourceBundleFindWithFallback2 != null && (strFindStringWithFallback = iCUResourceBundleFindWithFallback2.findStringWithFallback(str3)) != null) {
                        break;
                    }
                    if (str2 == null) {
                        if (str.equals("Countries")) {
                            currentLanguageID = LocaleIDs.getCurrentCountryID(str3);
                        } else {
                            currentLanguageID = str.equals("Languages") ? LocaleIDs.getCurrentLanguageID(str3) : null;
                        }
                        if (currentLanguageID != null && (strFindStringWithFallback = iCUResourceBundleFindWithFallback.findStringWithFallback(currentLanguageID)) != null) {
                            break;
                        }
                    }
                    String strFindStringWithFallback2 = iCUResourceBundleFindWithFallback.findStringWithFallback("Fallback");
                    if (strFindStringWithFallback2 == null) {
                        return str4;
                    }
                    if (strFindStringWithFallback2.length() == 0) {
                        strFindStringWithFallback2 = "root";
                    }
                    if (strFindStringWithFallback2.equals(iCUResourceBundleFindWithFallback.getULocale().getName())) {
                        return str4;
                    }
                    iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(iCUResourceBundle.getBaseName(), strFindStringWithFallback2);
                } else {
                    return str4;
                }
            } catch (Exception unused) {
            }
        }
        return (strFindStringWithFallback == null || strFindStringWithFallback.length() <= 0) ? str4 : strFindStringWithFallback;
    }
}
