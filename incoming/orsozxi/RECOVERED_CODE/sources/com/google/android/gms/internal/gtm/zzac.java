package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzac extends com.google.android.gms.analytics.zzi<zzac> {
    public String mCategory;
    public String zzvk;
    public long zzvl;
    public String zzvm;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("variableName", this.zzvk);
        map.put("timeInMillis", Long.valueOf(this.zzvl));
        map.put("category", this.mCategory);
        map.put(Constants.ScionAnalytics.PARAM_LABEL, this.zzvm);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzac zzacVar = (zzac) zziVar;
        if (!TextUtils.isEmpty(this.zzvk)) {
            zzacVar.zzvk = this.zzvk;
        }
        long j = this.zzvl;
        if (j != 0) {
            zzacVar.zzvl = j;
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            zzacVar.mCategory = this.mCategory;
        }
        if (TextUtils.isEmpty(this.zzvm)) {
            return;
        }
        zzacVar.zzvm = this.zzvm;
    }
}
