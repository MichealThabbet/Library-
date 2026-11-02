package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class zzj<T extends zzj> {
    private final zzk zzsn;
    protected final zzg zzso;
    private final List<zzh> zzsp;

    protected zzj(zzk zzkVar, Clock clock) {
        Preconditions.checkNotNull(zzkVar);
        this.zzsn = zzkVar;
        this.zzsp = new ArrayList();
        zzg zzgVar = new zzg(this, clock);
        zzgVar.zzar();
        this.zzso = zzgVar;
    }

    protected void zza(zzg zzgVar) {
    }

    public zzg zzac() {
        zzg zzgVarZzai = this.zzso.zzai();
        zzd(zzgVarZzai);
        return zzgVarZzai;
    }

    protected final void zzd(zzg zzgVar) {
        Iterator<zzh> it2 = this.zzsp.iterator();
        while (it2.hasNext()) {
            it2.next().zza(this, zzgVar);
        }
    }

    protected final zzk zzas() {
        return this.zzsn;
    }
}
