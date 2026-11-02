package com.ibm.icu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.Properties;

/* loaded from: classes2.dex */
public class ICUConfig {
    private static final Properties CONFIG_PROPS = new Properties();
    public static final String CONFIG_PROPS_FILE = "/com/ibm/icu/ICUConfig.properties";

    static {
        try {
            InputStream stream = ICUData.getStream(CONFIG_PROPS_FILE);
            if (stream != null) {
                try {
                    CONFIG_PROPS.load(stream);
                    stream.close();
                } catch (Throwable th) {
                    stream.close();
                    throw th;
                }
            }
        } catch (IOException | MissingResourceException unused) {
        }
    }

    public static String get(String str) {
        return get(str, null);
    }

    public static String get(final String str, String str2) {
        String property;
        if (System.getSecurityManager() != null) {
            try {
                property = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: com.ibm.icu.impl.ICUConfig.1
                    @Override // java.security.PrivilegedAction
                    public String run() {
                        return System.getProperty(str);
                    }
                });
            } catch (AccessControlException unused) {
                property = null;
            }
        } else {
            property = System.getProperty(str);
        }
        return property == null ? CONFIG_PROPS.getProperty(str, str2) : property;
    }
}
