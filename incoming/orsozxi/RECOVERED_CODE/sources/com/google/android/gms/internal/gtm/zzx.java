package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.firebase.messaging.Constants;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzx extends com.google.android.gms.analytics.zzi<zzx> {
    private String category;
    private String label;
    private long value;
    private String zzup;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("category", this.category);
        map.put(NativeProtocol.WEB_DIALOG_ACTION, this.zzup);
        map.put(Constants.ScionAnalytics.PARAM_LABEL, this.label);
        map.put("value", Long.valueOf(this.value));
        return zza((Object) map);
    }

    public final String zzbr() {
        return this.category;
    }

    public final String getAction() {
        return this.zzup;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getValue() {
        return this.value;
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzx zzxVar = (zzx) zziVar;
        if (!TextUtils.isEmpty(this.category)) {
            zzxVar.category = this.category;
        }
        if (!TextUtils.isEmpty(this.zzup)) {
            zzxVar.zzup = this.zzup;
        }
        if (!TextUtils.isEmpty(this.label)) {
            zzxVar.label = this.label;
        }
        long j = this.value;
        if (j != 0) {
            zzxVar.value = j;
        }
    }
}
