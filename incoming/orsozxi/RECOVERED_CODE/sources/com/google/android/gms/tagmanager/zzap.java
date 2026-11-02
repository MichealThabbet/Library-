package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.List;

/* loaded from: classes.dex */
final class zzap implements zzaq {
    private final /* synthetic */ DataLayer zzafv;

    zzap(DataLayer dataLayer) {
        this.zzafv = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzaq
    public final void zzc(List<DataLayer.zza> list) {
        for (DataLayer.zza zzaVar : list) {
            this.zzafv.zze(DataLayer.zzg(zzaVar.mKey, zzaVar.mValue));
        }
        this.zzafv.zzafu.countDown();
    }
}
