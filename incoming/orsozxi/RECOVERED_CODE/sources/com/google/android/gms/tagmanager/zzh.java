package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* loaded from: classes.dex */
final class zzh extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.ADWORDS_CLICK_REFERRER.toString();
    private static final String zzadu = com.google.android.gms.internal.gtm.zzb.COMPONENT.toString();
    private static final String zzadv = com.google.android.gms.internal.gtm.zzb.CONVERSION_ID.toString();
    private final Context zzrm;

    public zzh(Context context) {
        super(ID, zzadv);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(zzadv);
        if (zzlVar == null) {
            return zzgj.zzkc();
        }
        String strZzc = zzgj.zzc(zzlVar);
        com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzadu);
        String strZzc2 = zzlVar2 != null ? zzgj.zzc(zzlVar2) : null;
        Context context = this.zzrm;
        String string = zzcw.zzahk.get(strZzc);
        if (string == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            string = sharedPreferences != null ? sharedPreferences.getString(strZzc, "") : "";
            zzcw.zzahk.put(strZzc, string);
        }
        String strZze = zzcw.zze(string, strZzc2);
        return strZze != null ? zzgj.zzi(strZze) : zzgj.zzkc();
    }
}
