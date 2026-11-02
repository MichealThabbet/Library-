package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzot;
import com.google.android.gms.internal.gtm.zzox;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
final class zzfe implements zzfg {
    private final /* synthetic */ Map zzakb;
    private final /* synthetic */ Map zzakc;
    private final /* synthetic */ Map zzakd;
    private final /* synthetic */ Map zzake;

    zzfe(zzfb zzfbVar, Map map, Map map2, Map map3, Map map4) {
        this.zzakb = map;
        this.zzakc = map2;
        this.zzakd = map3;
        this.zzake = map4;
    }

    @Override // com.google.android.gms.tagmanager.zzfg
    public final void zza(zzox zzoxVar, Set<zzot> set, Set<zzot> set2, zzeq zzeqVar) {
        List list = (List) this.zzakb.get(zzoxVar);
        this.zzakc.get(zzoxVar);
        if (list != null) {
            set.addAll(list);
            zzeqVar.zzio();
        }
        List list2 = (List) this.zzakd.get(zzoxVar);
        this.zzake.get(zzoxVar);
        if (list2 != null) {
            set2.addAll(list2);
            zzeqVar.zzip();
        }
    }
}
