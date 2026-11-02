package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zzil extends zzig {
    zzil() {
    }

    @Override // com.google.android.gms.internal.measurement.zzig
    public final void zza(Throwable th, Throwable th2) {
        th.addSuppressed(th2);
    }
}
