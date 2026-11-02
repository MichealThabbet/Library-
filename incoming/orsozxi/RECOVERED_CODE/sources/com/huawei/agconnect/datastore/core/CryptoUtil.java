package com.huawei.agconnect.datastore.core;

import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.annotation.ICrypto;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class CryptoUtil {
    private static ICrypto createWhenProguard(Class<?> cls) {
        try {
            return (ICrypto) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException unused) {
            return null;
        }
    }

    public static ICrypto getHelper(Class<?> cls) {
        try {
            if (!DefaultCrypto.class.equals(cls) && !DefaultCrypto.class.isAssignableFrom(cls)) {
                return (ICrypto) cls.getMethod("get", new Class[0]).invoke(null, new Object[0]);
            }
        } catch (IllegalAccessException | InvocationTargetException unused) {
        } catch (NoSuchMethodException unused2) {
            return createWhenProguard(cls);
        }
        return null;
    }
}
