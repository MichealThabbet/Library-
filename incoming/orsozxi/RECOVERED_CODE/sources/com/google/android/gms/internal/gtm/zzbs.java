package com.google.android.gms.internal.gtm;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
abstract class zzbs {
    private static volatile Handler handler;
    private final zzap zzwc;
    private final Runnable zzys;
    private volatile long zzyt;

    zzbs(zzap zzapVar) {
        Preconditions.checkNotNull(zzapVar);
        this.zzwc = zzapVar;
        this.zzys = new zzbt(this);
    }

    public abstract void run();

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzyt = this.zzwc.zzcn().currentTimeMillis();
            if (getHandler().postDelayed(this.zzys, j)) {
                return;
            }
            this.zzwc.zzco().zze("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }

    public final void zzi(long j) {
        if (zzez()) {
            if (j < 0) {
                cancel();
                return;
            }
            long jAbs = j - Math.abs(this.zzwc.zzcn().currentTimeMillis() - this.zzyt);
            long j2 = jAbs >= 0 ? jAbs : 0L;
            getHandler().removeCallbacks(this.zzys);
            if (getHandler().postDelayed(this.zzys, j2)) {
                return;
            }
            this.zzwc.zzco().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
        }
    }

    public final long zzey() {
        if (this.zzyt == 0) {
            return 0L;
        }
        return Math.abs(this.zzwc.zzcn().currentTimeMillis() - this.zzyt);
    }

    public final boolean zzez() {
        return this.zzyt != 0;
    }

    public final void cancel() {
        this.zzyt = 0L;
        getHandler().removeCallbacks(this.zzys);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzbs.class) {
            if (handler == null) {
                handler = new zzdj(this.zzwc.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    static /* synthetic */ long zza(zzbs zzbsVar, long j) {
        zzbsVar.zzyt = 0L;
        return 0L;
    }
}
