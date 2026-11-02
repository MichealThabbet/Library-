package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes.dex */
public final class zzaa extends com.google.android.gms.analytics.zzi<zzaa> {
    private String zzva;
    private int zzvb;
    private int zzvc;
    private String zzvd;
    private String zzve;
    private boolean zzvf;
    private boolean zzvg;

    public zzaa() {
        this(false);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private zzaa(boolean z) {
        UUID uuidRandomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (uuidRandomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits == 0 && (leastSignificantBits = (int) (uuidRandomUUID.getMostSignificantBits() & 2147483647L)) == 0) {
            Log.e("GAv4", "UUID.randomUUID() returned 0.");
            leastSignificantBits = Integer.MAX_VALUE;
        }
        this(false, leastSignificantBits);
    }

    private zzaa(boolean z, int i) {
        Preconditions.checkNotZero(i);
        this.zzvb = i;
        this.zzvg = false;
    }

    public final String zzca() {
        return this.zzva;
    }

    public final int zzcb() {
        return this.zzvb;
    }

    public final String zzcc() {
        return this.zzve;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("screenName", this.zzva);
        map.put("interstitial", Boolean.valueOf(this.zzvf));
        map.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, Boolean.valueOf(this.zzvg));
        map.put("screenId", Integer.valueOf(this.zzvb));
        map.put("referrerScreenId", Integer.valueOf(this.zzvc));
        map.put("referrerScreenName", this.zzvd);
        map.put("referrerUri", this.zzve);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzaa zzaaVar = (zzaa) zziVar;
        if (!TextUtils.isEmpty(this.zzva)) {
            zzaaVar.zzva = this.zzva;
        }
        int i = this.zzvb;
        if (i != 0) {
            zzaaVar.zzvb = i;
        }
        int i2 = this.zzvc;
        if (i2 != 0) {
            zzaaVar.zzvc = i2;
        }
        if (!TextUtils.isEmpty(this.zzvd)) {
            zzaaVar.zzvd = this.zzvd;
        }
        if (!TextUtils.isEmpty(this.zzve)) {
            String str = this.zzve;
            if (TextUtils.isEmpty(str)) {
                zzaaVar.zzve = null;
            } else {
                zzaaVar.zzve = str;
            }
        }
        boolean z = this.zzvf;
        if (z) {
            zzaaVar.zzvf = z;
        }
        boolean z2 = this.zzvg;
        if (z2) {
            zzaaVar.zzvg = z2;
        }
    }
}
