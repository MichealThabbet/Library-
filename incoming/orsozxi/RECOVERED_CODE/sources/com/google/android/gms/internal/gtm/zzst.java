package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzst {
    private static final zzsr zzbdp = zzqr();
    private static final zzsr zzbdq = new zzss();

    static zzsr zzqp() {
        return zzbdp;
    }

    static zzsr zzqq() {
        return zzbdq;
    }

    private static zzsr zzqr() {
        try {
            return (zzsr) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
