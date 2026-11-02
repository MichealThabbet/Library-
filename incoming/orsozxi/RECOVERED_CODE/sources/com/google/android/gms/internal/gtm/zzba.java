package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
public final class zzba extends zzan {
    private final zzq zzsu;

    zzba(zzap zzapVar) {
        super(zzapVar);
        this.zzsu = new zzq();
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        zzcq().zzat().zzb(this.zzsu);
        zzda zzdaVarZzcu = zzcu();
        String strZzaz = zzdaVarZzcu.zzaz();
        if (strZzaz != null) {
            this.zzsu.setAppName(strZzaz);
        }
        String strZzba = zzdaVarZzcu.zzba();
        if (strZzba != null) {
            this.zzsu.setAppVersion(strZzba);
        }
    }

    public final zzq zzdv() {
        zzdb();
        return this.zzsu;
    }
}
