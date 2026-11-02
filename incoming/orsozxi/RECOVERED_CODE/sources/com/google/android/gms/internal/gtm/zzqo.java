package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzqo {
    private static final Class<?> zzaxg = zzom();

    private static Class<?> zzom() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzqp zzon() {
        if (zzaxg != null) {
            try {
                return zzdc("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return zzqp.zzaxk;
    }

    static zzqp zzoo() {
        zzqp zzqpVarZzdc;
        if (zzaxg != null) {
            try {
                zzqpVarZzdc = zzdc("loadGeneratedRegistry");
            } catch (Exception unused) {
            }
        } else {
            zzqpVarZzdc = null;
        }
        if (zzqpVarZzdc == null) {
            zzqpVarZzdc = zzqp.zzoo();
        }
        return zzqpVarZzdc == null ? zzon() : zzqpVarZzdc;
    }

    private static final zzqp zzdc(String str) throws Exception {
        return (zzqp) zzaxg.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
