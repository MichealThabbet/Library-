package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzqs {
    private static final zzqq<?> zzaxm = new zzqr();
    private static final zzqq<?> zzaxn = zzos();

    private static zzqq<?> zzos() {
        try {
            return (zzqq) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zzqq<?> zzot() {
        return zzaxm;
    }

    static zzqq<?> zzou() {
        zzqq<?> zzqqVar = zzaxn;
        if (zzqqVar != null) {
            return zzqqVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
