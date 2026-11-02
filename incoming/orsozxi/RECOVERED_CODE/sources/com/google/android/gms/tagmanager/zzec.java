package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzec implements zzfw {
    private final /* synthetic */ zzeb zzaik;

    zzec(zzeb zzebVar) {
        this.zzaik = zzebVar;
    }

    @Override // com.google.android.gms.tagmanager.zzfw
    public final void zza(zzbw zzbwVar) {
        this.zzaik.zze(zzbwVar.zzih());
    }

    @Override // com.google.android.gms.tagmanager.zzfw
    public final void zzb(zzbw zzbwVar) {
        this.zzaik.zze(zzbwVar.zzih());
        long jZzih = zzbwVar.zzih();
        StringBuilder sb = new StringBuilder(57);
        sb.append("Permanent failure dispatching hitId: ");
        sb.append(jZzih);
        zzdi.zzab(sb.toString());
    }

    @Override // com.google.android.gms.tagmanager.zzfw
    public final void zzc(zzbw zzbwVar) {
        long jZzii = zzbwVar.zzii();
        if (jZzii == 0) {
            this.zzaik.zzb(zzbwVar.zzih(), this.zzaik.zzsd.currentTimeMillis());
            return;
        }
        if (jZzii + 14400000 < this.zzaik.zzsd.currentTimeMillis()) {
            this.zzaik.zze(zzbwVar.zzih());
            long jZzih = zzbwVar.zzih();
            StringBuilder sb = new StringBuilder(47);
            sb.append("Giving up on failed hitId: ");
            sb.append(jZzih);
            zzdi.zzab(sb.toString());
        }
    }
}
