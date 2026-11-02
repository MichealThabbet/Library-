package com.ibm.icu.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: classes2.dex */
public class ClassLoaderUtil {
    private static volatile ClassLoader BOOTSTRAP_CLASSLOADER;

    private static class BootstrapClassLoader extends ClassLoader {
        BootstrapClassLoader() {
            super(Object.class.getClassLoader());
        }
    }

    private static ClassLoader getBootstrapClassLoader() {
        ClassLoader bootstrapClassLoader;
        if (BOOTSTRAP_CLASSLOADER == null) {
            synchronized (ClassLoaderUtil.class) {
                if (BOOTSTRAP_CLASSLOADER == null) {
                    if (System.getSecurityManager() != null) {
                        bootstrapClassLoader = (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: com.ibm.icu.impl.ClassLoaderUtil.1
                            @Override // java.security.PrivilegedAction
                            /* renamed from: run, reason: merged with bridge method [inline-methods] */
                            public ClassLoader run2() {
                                return new BootstrapClassLoader();
                            }
                        });
                    } else {
                        bootstrapClassLoader = new BootstrapClassLoader();
                    }
                    BOOTSTRAP_CLASSLOADER = bootstrapClassLoader;
                }
            }
        }
        return BOOTSTRAP_CLASSLOADER;
    }

    public static ClassLoader getClassLoader(Class<?> cls) {
        ClassLoader classLoader = cls.getClassLoader();
        return classLoader == null ? getClassLoader() : classLoader;
    }

    public static ClassLoader getClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        return systemClassLoader == null ? getBootstrapClassLoader() : systemClassLoader;
    }
}
