package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzad;
import com.google.android.gms.internal.gtm.zzao;
import com.google.android.gms.internal.gtm.zzas;
import com.google.android.gms.internal.gtm.zzcd;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.android.gms.internal.gtm.zzq;
import com.ibm.icu.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class zzp implements Runnable {
    private final /* synthetic */ Map zzti;
    private final /* synthetic */ boolean zztj;
    private final /* synthetic */ String zztk;
    private final /* synthetic */ long zztl;
    private final /* synthetic */ boolean zztm;
    private final /* synthetic */ boolean zztn;
    private final /* synthetic */ String zzto;
    private final /* synthetic */ Tracker zztp;

    zzp(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
        this.zztp = tracker;
        this.zzti = map;
        this.zztj = z;
        this.zztk = str;
        this.zztl = j;
        this.zztm = z2;
        this.zztn = z3;
        this.zzto = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zztp.zztf.zzax()) {
            this.zzti.put("sc", "start");
        }
        Map map = this.zzti;
        GoogleAnalytics googleAnalyticsZzcr = this.zztp.zzcr();
        Preconditions.checkNotMainThread("getClientId can not be called from the main thread");
        zzcz.zzc(map, "cid", googleAnalyticsZzcr.zzab().zzdh().zzeh());
        String str = (String) this.zzti.get("sf");
        if (str != null) {
            double dZza = zzcz.zza(str, 100.0d);
            if (zzcz.zza(dZza, (String) this.zzti.get("cid"))) {
                this.zztp.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(dZza));
                return;
            }
        }
        zzad zzadVarZzcx = this.zztp.zzcx();
        if (this.zztj) {
            zzcz.zzb((Map<String, String>) this.zzti, "ate", zzadVarZzcx.zzbw());
            zzcz.zzb((Map<String, String>) this.zzti, "adid", zzadVarZzcx.zzcd());
        } else {
            this.zzti.remove("ate");
            this.zzti.remove("adid");
        }
        zzq zzqVarZzdv = this.zztp.zzcy().zzdv();
        zzcz.zzb((Map<String, String>) this.zzti, "an", zzqVarZzdv.zzaz());
        zzcz.zzb((Map<String, String>) this.zzti, "av", zzqVarZzdv.zzba());
        zzcz.zzb((Map<String, String>) this.zzti, "aid", zzqVarZzdv.zzbb());
        zzcz.zzb((Map<String, String>) this.zzti, "aiid", zzqVarZzdv.zzbc());
        this.zzti.put(DateFormat.ABBR_GENERIC_TZ, "1");
        this.zzti.put("_v", zzao.zzwe);
        zzcz.zzb((Map<String, String>) this.zzti, "ul", this.zztp.zzcz().zzfa().getLanguage());
        zzcz.zzb((Map<String, String>) this.zzti, "sr", this.zztp.zzcz().zzfb());
        if (!(this.zztk.equals("transaction") || this.zztk.equals("item")) && !this.zztp.zzte.zzfm()) {
            this.zztp.zzco().zza(this.zzti, "Too many hits sent too quickly, rate limiting invoked");
            return;
        }
        long jZzag = zzcz.zzag((String) this.zzti.get("ht"));
        if (jZzag == 0) {
            jZzag = this.zztl;
        }
        long j = jZzag;
        if (this.zztm) {
            this.zztp.zzco().zzc("Dry run enabled. Would have sent hit", new zzcd(this.zztp, this.zzti, j, this.zztn));
            return;
        }
        String str2 = (String) this.zzti.get("cid");
        HashMap map2 = new HashMap();
        zzcz.zza(map2, "uid", (Map<String, String>) this.zzti);
        zzcz.zza(map2, "an", (Map<String, String>) this.zzti);
        zzcz.zza(map2, "aid", (Map<String, String>) this.zzti);
        zzcz.zza(map2, "av", (Map<String, String>) this.zzti);
        zzcz.zza(map2, "aiid", (Map<String, String>) this.zzti);
        this.zzti.put("_s", String.valueOf(this.zztp.zzcs().zza(new zzas(0L, str2, this.zzto, !TextUtils.isEmpty((CharSequence) this.zzti.get("adid")), 0L, map2))));
        this.zztp.zzcs().zza(new zzcd(this.zztp, this.zzti, j, this.zztn));
    }
}
