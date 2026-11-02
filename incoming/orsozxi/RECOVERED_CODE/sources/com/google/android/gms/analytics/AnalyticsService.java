package com.google.android.gms.analytics;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.gtm.zzcq;
import com.google.android.gms.internal.gtm.zzcu;

/* loaded from: classes.dex */
public final class AnalyticsService extends Service implements zzcu {
    private zzcq<AnalyticsService> zzrd;

    private final zzcq<AnalyticsService> zzad() {
        if (this.zzrd == null) {
            this.zzrd = new zzcq<>(this);
        }
        return this.zzrd;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        zzad().onCreate();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        zzad().onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zzad().onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        zzad();
        return null;
    }

    @Override // com.google.android.gms.internal.gtm.zzcu
    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @Override // com.google.android.gms.internal.gtm.zzcu
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }
}
