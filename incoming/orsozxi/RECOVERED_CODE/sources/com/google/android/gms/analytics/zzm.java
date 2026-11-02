package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.zzk;
import java.lang.Thread;
import java.util.concurrent.FutureTask;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
final class zzm<T> extends FutureTask<T> {
    private final /* synthetic */ zzk.zza zzsy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzm(zzk.zza zzaVar, Runnable runnable, Object obj) {
        super(runnable, obj);
        this.zzsy = zzaVar;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = zzk.this.zzsv;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
        } else if (Log.isLoggable("GAv4", 6)) {
            String strValueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 37);
            sb.append("MeasurementExecutor: job failed with ");
            sb.append(strValueOf);
            Log.e("GAv4", sb.toString());
        }
        super.setException(th);
    }
}
