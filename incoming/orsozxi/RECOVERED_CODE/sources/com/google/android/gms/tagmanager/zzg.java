package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.Map;

/* loaded from: classes.dex */
final class zzg implements DataLayer.zzb {
    private final Context zzrm;

    public zzg(Context context) {
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.zzb
    public final void zzc(Map<String, Object> map) {
        String queryParameter;
        Object obj;
        Object obj2 = map.get("gtm.url");
        if (obj2 == null && (obj = map.get("gtm")) != null && (obj instanceof Map)) {
            obj2 = ((Map) obj).get("url");
        }
        if (obj2 == null || !(obj2 instanceof String) || (queryParameter = Uri.parse((String) obj2).getQueryParameter("referrer")) == null) {
            return;
        }
        zzcw.zzf(this.zzrm, queryParameter);
    }
}
