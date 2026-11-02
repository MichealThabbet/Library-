package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzy extends com.google.android.gms.analytics.zzi<zzy> {
    public String zzuq;
    public boolean zzur;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("description", this.zzuq);
        map.put("fatal", Boolean.valueOf(this.zzur));
        return zza((Object) map);
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzy zzyVar = (zzy) zziVar;
        if (!TextUtils.isEmpty(this.zzuq)) {
            zzyVar.zzuq = this.zzuq;
        }
        boolean z = this.zzur;
        if (z) {
            zzyVar.zzur = z;
        }
    }
}
