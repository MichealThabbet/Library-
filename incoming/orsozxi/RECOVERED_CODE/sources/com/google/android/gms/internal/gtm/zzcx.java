package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzcx extends zzam implements zzbp<zzcy> {
    private final zzcy zzacl;

    public zzcx(zzap zzapVar) {
        super(zzapVar);
        this.zzacl = new zzcy();
    }

    @Override // com.google.android.gms.internal.gtm.zzbp
    public final void zzb(String str, String str2) {
        this.zzacl.zzacs.put(str, str2);
    }

    @Override // com.google.android.gms.internal.gtm.zzbp
    public final void zzc(String str, String str2) {
        if ("ga_trackingId".equals(str)) {
            this.zzacl.zzacm = str2;
            return;
        }
        if ("ga_sampleFrequency".equals(str)) {
            try {
                this.zzacl.zzacn = Double.parseDouble(str2);
                return;
            } catch (NumberFormatException e) {
                zzc("Error parsing ga_sampleFrequency value", str2, e);
                return;
            }
        }
        zzd("string configuration name not recognized", str);
    }

    @Override // com.google.android.gms.internal.gtm.zzbp
    public final void zza(String str, boolean z) {
        if ("ga_autoActivityTracking".equals(str)) {
            this.zzacl.zzacp = z ? 1 : 0;
        } else if ("ga_anonymizeIp".equals(str)) {
            this.zzacl.zzacq = z ? 1 : 0;
        } else if ("ga_reportUncaughtExceptions".equals(str)) {
            this.zzacl.zzacr = z ? 1 : 0;
        } else {
            zzd("bool configuration name not recognized", str);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzbp
    public final void zzb(String str, int i) {
        if ("ga_sessionTimeout".equals(str)) {
            this.zzacl.zzaco = i;
        } else {
            zzd("int configuration name not recognized", str);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzbp
    public final /* synthetic */ zzbn zzel() {
        return this.zzacl;
    }
}
