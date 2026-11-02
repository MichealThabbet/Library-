package com.google.android.gms.internal.gtm;

import java.lang.Thread;

/* loaded from: classes.dex */
final class zzaq implements Thread.UncaughtExceptionHandler {
    private final /* synthetic */ zzap zzwt;

    zzaq(zzap zzapVar) {
        this.zzwt = zzapVar;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        zzci zzciVarZzdd = this.zzwt.zzdd();
        if (zzciVarZzdd != null) {
            zzciVarZzdd.zze("Job execution failed", th);
        }
    }
}
