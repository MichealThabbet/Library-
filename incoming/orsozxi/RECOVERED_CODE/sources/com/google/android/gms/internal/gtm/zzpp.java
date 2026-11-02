package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzpp {
    private static final Class<?> zzavt = zzcx("libcore.io.Memory");
    private static final boolean zzavu;

    static boolean zzna() {
        return (zzavt == null || zzavu) ? false : true;
    }

    static Class<?> zznb() {
        return zzavt;
    }

    private static <T> Class<T> zzcx(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        zzavu = zzcx("org.robolectric.Robolectric") != null;
    }
}
