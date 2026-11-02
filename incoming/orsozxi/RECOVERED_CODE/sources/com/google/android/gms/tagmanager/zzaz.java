package com.google.android.gms.tagmanager;

import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzaz extends zzgh {
    private static final String ID = com.google.android.gms.internal.gtm.zza.DATA_LAYER_WRITE.toString();
    private static final String VALUE = com.google.android.gms.internal.gtm.zzb.VALUE.toString();
    private static final String zzagi = com.google.android.gms.internal.gtm.zzb.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer zzaed;

    public zzaz(DataLayer dataLayer) {
        super(ID, VALUE);
        this.zzaed = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzgh
    public final void zzd(Map<String, com.google.android.gms.internal.gtm.zzl> map) throws InterruptedException {
        String strZzc;
        com.google.android.gms.internal.gtm.zzl zzlVar = map.get(VALUE);
        if (zzlVar != null && zzlVar != zzgj.zzjw()) {
            Object objZzh = zzgj.zzh(zzlVar);
            if (objZzh instanceof List) {
                for (Object obj : (List) objZzh) {
                    if (obj instanceof Map) {
                        this.zzaed.push((Map) obj);
                    }
                }
            }
        }
        com.google.android.gms.internal.gtm.zzl zzlVar2 = map.get(zzagi);
        if (zzlVar2 == null || zzlVar2 == zzgj.zzjw() || (strZzc = zzgj.zzc(zzlVar2)) == zzgj.zzkb()) {
            return;
        }
        this.zzaed.zzaq(strZzc);
    }
}
