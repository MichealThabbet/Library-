package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzab extends com.google.android.gms.analytics.zzi<zzab> {
    public String zzvh;
    public String zzvi;
    public String zzvj;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("network", this.zzvh);
        map.put(NativeProtocol.WEB_DIALOG_ACTION, this.zzvi);
        map.put("target", this.zzvj);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzab zzabVar = (zzab) zziVar;
        if (!TextUtils.isEmpty(this.zzvh)) {
            zzabVar.zzvh = this.zzvh;
        }
        if (!TextUtils.isEmpty(this.zzvi)) {
            zzabVar.zzvi = this.zzvi;
        }
        if (TextUtils.isEmpty(this.zzvj)) {
            return;
        }
        zzabVar.zzvj = this.zzvj;
    }
}
