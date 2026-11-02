package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.IntentFilter;

/* loaded from: classes.dex */
final class zzfn extends zzfm {
    private static final Object zzakn = new Object();
    private static zzfn zzaky;
    private Context zzako;
    private zzcb zzakp;
    private volatile zzby zzakq;
    private zzfq zzakv;
    private zzdn zzakw;
    private int zzakr = 1800000;
    private boolean zzaks = true;
    private boolean zzakt = false;
    private boolean connected = true;
    private boolean zzaku = true;
    private zzcc zzaig = new zzfo(this);
    private boolean zzakx = false;

    public static zzfn zzjq() {
        if (zzaky == null) {
            zzaky = new zzfn();
        }
        return zzaky;
    }

    private zzfn() {
    }

    final synchronized void zza(Context context, zzby zzbyVar) {
        if (this.zzako != null) {
            return;
        }
        this.zzako = context.getApplicationContext();
        if (this.zzakq == null) {
            this.zzakq = zzbyVar;
        }
    }

    final synchronized zzcb zzjr() {
        if (this.zzakp == null) {
            if (this.zzako == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzakp = new zzeb(this.zzaig, this.zzako);
        }
        if (this.zzakv == null) {
            zzfr zzfrVar = new zzfr(this, null);
            this.zzakv = zzfrVar;
            if (this.zzakr > 0) {
                zzfrVar.zzh(this.zzakr);
            }
        }
        this.zzakt = true;
        if (this.zzaks) {
            dispatch();
            this.zzaks = false;
        }
        if (this.zzakw == null && this.zzaku) {
            zzdn zzdnVar = new zzdn(this);
            this.zzakw = zzdnVar;
            Context context = this.zzako;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(zzdnVar, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter2.addCategory(context.getPackageName());
            context.registerReceiver(zzdnVar, intentFilter2);
        }
        return this.zzakp;
    }

    @Override // com.google.android.gms.tagmanager.zzfm
    public final synchronized void dispatch() {
        if (!this.zzakt) {
            zzdi.zzab("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzaks = true;
        } else {
            this.zzakq.zzc(new zzfp(this));
        }
    }

    final synchronized void zza(boolean z, boolean z2) {
        boolean zIsPowerSaveMode = isPowerSaveMode();
        this.zzakx = z;
        this.connected = z2;
        if (isPowerSaveMode() == zIsPowerSaveMode) {
            return;
        }
        if (isPowerSaveMode()) {
            this.zzakv.cancel();
            zzdi.zzab("PowerSaveMode initiated.");
        } else {
            this.zzakv.zzh(this.zzakr);
            zzdi.zzab("PowerSaveMode terminated.");
        }
    }

    @Override // com.google.android.gms.tagmanager.zzfm
    public final synchronized void zzf(boolean z) {
        zza(this.zzakx, z);
    }

    @Override // com.google.android.gms.tagmanager.zzfm
    public final synchronized void zzjp() {
        if (!isPowerSaveMode()) {
            this.zzakv.zzjt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPowerSaveMode() {
        return this.zzakx || !this.connected || this.zzakr <= 0;
    }
}
