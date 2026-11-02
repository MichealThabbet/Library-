package com.google.android.gms.tagmanager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class zzef extends zzbq {
    private static final String zzags = com.google.android.gms.internal.gtm.zzb.ARG0.toString();
    private static final String zzaio = com.google.android.gms.internal.gtm.zzb.ARG1.toString();

    public zzef(String str) {
        super(str, zzags, zzaio);
    }

    protected abstract boolean zza(com.google.android.gms.internal.gtm.zzl zzlVar, com.google.android.gms.internal.gtm.zzl zzlVar2, Map<String, com.google.android.gms.internal.gtm.zzl> map);

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        Iterator<com.google.android.gms.internal.gtm.zzl> it2 = map.values().iterator();
        do {
            boolean zZza = false;
            if (!it2.hasNext()) {
                com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzags);
                com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzaio);
                if (zzlVar != null && zzlVar2 != null) {
                    zZza = zza(zzlVar, zzlVar2, map);
                }
                return zzgj.zzi(Boolean.valueOf(zZza));
            }
        } while (it2.next() != zzgj.zzkc());
        return zzgj.zzi(false);
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final /* bridge */ /* synthetic */ Set zzig() {
        return super.zzig();
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final /* bridge */ /* synthetic */ String zzif() {
        return super.zzif();
    }
}
