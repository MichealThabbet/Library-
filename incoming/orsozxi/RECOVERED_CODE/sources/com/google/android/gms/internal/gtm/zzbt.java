package com.google.android.gms.internal.gtm;

import android.os.Looper;

/* loaded from: classes.dex */
final class zzbt implements Runnable {
    private final /* synthetic */ zzbs zzyu;

    zzbt(zzbs zzbsVar) {
        this.zzyu = zzbsVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.zzyu.zzwc.zzcq().zza(this);
            return;
        }
        boolean zZzez = this.zzyu.zzez();
        zzbs.zza(this.zzyu, 0L);
        if (zZzez) {
            this.zzyu.run();
        }
    }
}
