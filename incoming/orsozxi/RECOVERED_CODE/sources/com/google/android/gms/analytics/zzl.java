package com.google.android.gms.analytics;

import java.util.Iterator;

/* loaded from: classes.dex */
final class zzl implements Runnable {
    private final /* synthetic */ zzg zzsw;
    private final /* synthetic */ zzk zzsx;

    zzl(zzk zzkVar, zzg zzgVar) {
        this.zzsx = zzkVar;
        this.zzsw = zzgVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzsw.zzap().zza(this.zzsw);
        Iterator it2 = this.zzsx.zzsr.iterator();
        while (it2.hasNext()) {
            ((zzn) it2.next()).zza(this.zzsw);
        }
        zzk zzkVar = this.zzsx;
        zzk.zzb(this.zzsw);
    }
}
