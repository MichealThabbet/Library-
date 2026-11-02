package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzop;

/* loaded from: classes.dex */
final class zzad implements zzdh<zzop> {
    private final /* synthetic */ zzy zzafg;

    private zzad(zzy zzyVar) {
        this.zzafg = zzyVar;
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final void zzhj() {
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final void zzs(int i) {
        if (this.zzafg.zzafb) {
            return;
        }
        this.zzafg.zzk(0L);
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final /* synthetic */ void zze(zzop zzopVar) {
        com.google.android.gms.internal.gtm.zzk zzkVar;
        zzop zzopVar2 = zzopVar;
        if (zzopVar2.zzauy != null) {
            zzkVar = zzopVar2.zzauy;
        } else {
            com.google.android.gms.internal.gtm.zzi zziVar = zzopVar2.zzqk;
            com.google.android.gms.internal.gtm.zzk zzkVar2 = new com.google.android.gms.internal.gtm.zzk();
            zzkVar2.zzqk = zziVar;
            zzkVar2.zzqj = null;
            zzkVar2.zzql = zziVar.version;
            zzkVar = zzkVar2;
        }
        this.zzafg.zza(zzkVar, zzopVar2.zzaux, true);
    }

    /* synthetic */ zzad(zzy zzyVar, zzz zzzVar) {
        this(zzyVar);
    }
}
