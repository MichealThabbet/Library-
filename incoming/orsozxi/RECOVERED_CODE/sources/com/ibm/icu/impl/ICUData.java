package com.ibm.icu.impl;

import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.logging.Logger;

/* loaded from: classes2.dex */
public final class ICUData {
    public static final String ICU_BASE_NAME = "com/ibm/icu/impl/data/icudt69b";
    public static final String ICU_BRKITR_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/brkitr";
    public static final String ICU_BRKITR_NAME = "brkitr";
    public static final String ICU_BUNDLE = "data/icudt69b";
    public static final String ICU_COLLATION_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/coll";
    public static final String ICU_CURR_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/curr";
    static final String ICU_DATA_PATH = "com/ibm/icu/impl/";
    public static final String ICU_LANG_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/lang";
    public static final String ICU_RBNF_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/rbnf";
    public static final String ICU_REGION_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/region";
    public static final String ICU_TRANSLIT_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/translit";
    public static final String ICU_UNIT_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/unit";
    public static final String ICU_ZONE_BASE_NAME = "com/ibm/icu/impl/data/icudt69b/zone";
    static final String PACKAGE_NAME = "icudt69b";
    private static final boolean logBinaryDataFromInputStream = false;
    private static final Logger logger = null;

    private static void checkStreamForBinaryData(InputStream inputStream, String str) {
    }

    public static boolean exists(final String str) {
        URL resource;
        if (System.getSecurityManager() != null) {
            resource = (URL) AccessController.doPrivileged(new PrivilegedAction<URL>() { // from class: com.ibm.icu.impl.ICUData.1
                @Override // java.security.PrivilegedAction
                public URL run() {
                    return ICUData.class.getResource(str);
                }
            });
        } else {
            resource = ICUData.class.getResource(str);
        }
        return resource != null;
    }

    private static InputStream getStream(final Class<?> cls, final String str, boolean z) {
        InputStream resourceAsStream;
        if (System.getSecurityManager() != null) {
            resourceAsStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.ibm.icu.impl.ICUData.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedAction
                public InputStream run() {
                    return cls.getResourceAsStream(str);
                }
            });
        } else {
            resourceAsStream = cls.getResourceAsStream(str);
        }
        if (resourceAsStream == null && z) {
            throw new MissingResourceException("could not locate data " + str, cls.getPackage().getName(), str);
        }
        checkStreamForBinaryData(resourceAsStream, str);
        return resourceAsStream;
    }

    static InputStream getStream(final ClassLoader classLoader, final String str, boolean z) {
        InputStream resourceAsStream;
        if (System.getSecurityManager() != null) {
            resourceAsStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.ibm.icu.impl.ICUData.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedAction
                public InputStream run() {
                    return classLoader.getResourceAsStream(str);
                }
            });
        } else {
            resourceAsStream = classLoader.getResourceAsStream(str);
        }
        if (resourceAsStream == null && z) {
            throw new MissingResourceException("could not locate data", classLoader.toString(), str);
        }
        checkStreamForBinaryData(resourceAsStream, str);
        return resourceAsStream;
    }

    public static InputStream getStream(ClassLoader classLoader, String str) {
        return getStream(classLoader, str, false);
    }

    public static InputStream getRequiredStream(ClassLoader classLoader, String str) {
        return getStream(classLoader, str, true);
    }

    public static InputStream getStream(String str) {
        return getStream((Class<?>) ICUData.class, str, false);
    }

    public static InputStream getRequiredStream(String str) {
        return getStream((Class<?>) ICUData.class, str, true);
    }

    public static InputStream getStream(Class<?> cls, String str) {
        return getStream(cls, str, false);
    }

    public static InputStream getRequiredStream(Class<?> cls, String str) {
        return getStream(cls, str, true);
    }
}
