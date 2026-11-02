package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;

/* loaded from: classes.dex */
final class zzae implements zzdh<com.google.android.gms.internal.gtm.zzk> {
    private final /* synthetic */ zzy zzafg;

    private zzae(zzy zzyVar) {
        this.zzafg = zzyVar;
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final void zzhj() {
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final void zzs(int i) {
        if (i == zzcz.zzahw) {
            this.zzafg.zzaex.zzhn();
        }
        synchronized (this.zzafg) {
            if (!this.zzafg.isReady()) {
                if (this.zzafg.zzafa != null) {
                    this.zzafg.setResult(this.zzafg.zzafa);
                } else {
                    this.zzafg.setResult(this.zzafg.createFailedResult(Status.RESULT_TIMEOUT));
                }
            }
        }
        this.zzafg.zzk(this.zzafg.zzaex.zzhm());
    }

    @Override // com.google.android.gms.tagmanager.zzdh
    public final /* synthetic */ void zze(com.google.android.gms.internal.gtm.zzk zzkVar) {
        com.google.android.gms.internal.gtm.zzk zzkVar2 = zzkVar;
        this.zzafg.zzaex.zzho();
        synchronized (this.zzafg) {
            if (zzkVar2.zzqk == null) {
                if (this.zzafg.zzafc.zzqk == null) {
                    zzdi.zzav("Current resource is null; network resource is also null");
                    this.zzafg.zzk(this.zzafg.zzaex.zzhm());
                    return;
                }
                zzkVar2.zzqk = this.zzafg.zzafc.zzqk;
            }
            this.zzafg.zza(zzkVar2, this.zzafg.zzsd.currentTimeMillis(), false);
            long j = this.zzafg.zzaeh;
            StringBuilder sb = new StringBuilder(58);
            sb.append("setting refresh time to current time: ");
            sb.append(j);
            zzdi.zzab(sb.toString());
            if (!this.zzafg.zzhi()) {
                this.zzafg.zza(zzkVar2);
            }
        }
    }

    /* synthetic */ zzae(zzy zzyVar, zzz zzzVar) {
        this(zzyVar);
    }
}
