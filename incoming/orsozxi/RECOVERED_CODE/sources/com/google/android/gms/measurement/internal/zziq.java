package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
final class zziq implements Runnable {
    final /* synthetic */ zzp zza;
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcf zzb;
    final /* synthetic */ zzjk zzc;

    zziq(zzjk zzjkVar, zzp zzpVar, com.google.android.gms.internal.measurement.zzcf zzcfVar) {
        this.zzc = zzjkVar;
        this.zza = zzpVar;
        this.zzb = zzcfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfu zzfuVar;
        String strZzl = null;
        try {
            try {
                if (this.zzc.zzs.zzd().zzi().zzh()) {
                    zzed zzedVar = this.zzc.zzb;
                    if (zzedVar == null) {
                        this.zzc.zzs.zzau().zzb().zza("Failed to get app instance id");
                        zzfuVar = this.zzc.zzs;
                    } else {
                        Preconditions.checkNotNull(this.zza);
                        strZzl = zzedVar.zzl(this.zza);
                        if (strZzl != null) {
                            this.zzc.zzs.zzk().zzE(strZzl);
                            this.zzc.zzs.zzd().zze.zzb(strZzl);
                        }
                        this.zzc.zzP();
                        zzfuVar = this.zzc.zzs;
                    }
                } else {
                    this.zzc.zzs.zzau().zzh().zza("Analytics storage consent denied; will not get app instance id");
                    this.zzc.zzs.zzk().zzE(null);
                    this.zzc.zzs.zzd().zze.zzb(null);
                    zzfuVar = this.zzc.zzs;
                }
            } catch (RemoteException e) {
                this.zzc.zzs.zzau().zzb().zzb("Failed to get app instance id", e);
                zzfuVar = this.zzc.zzs;
            }
            zzfuVar.zzl().zzad(this.zzb, strZzl);
        } catch (Throwable th) {
            this.zzc.zzs.zzl().zzad(this.zzb, null);
            throw th;
        }
    }
}
