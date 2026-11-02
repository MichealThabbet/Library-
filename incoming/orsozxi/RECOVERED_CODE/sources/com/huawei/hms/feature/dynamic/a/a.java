package com.huawei.hms.feature.dynamic.a;

import android.util.Log;
import dalvik.system.PathClassLoader;

/* loaded from: classes2.dex */
public final class a extends PathClassLoader {
    private static final String a = a.class.getSimpleName();

    public a(String str, ClassLoader classLoader) {
        super(str, classLoader);
    }

    @Override // java.lang.ClassLoader
    protected final Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException unused) {
                Log.w(a, "Cannot find The class:".concat(String.valueOf(str)));
            }
        }
        return super.loadClass(str, z);
    }
}
