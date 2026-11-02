package com.ibm.icu.impl;

import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.VersionInfo;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public final class ICUDataVersion {
    private static final String U_ICU_DATA_KEY = "DataVersion";
    private static final String U_ICU_VERSION_BUNDLE = "icuver";

    public static VersionInfo getDataVersion() {
        try {
            return VersionInfo.getInstance(UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, U_ICU_VERSION_BUNDLE, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(U_ICU_DATA_KEY).getString());
        } catch (MissingResourceException unused) {
            return null;
        }
    }
}
