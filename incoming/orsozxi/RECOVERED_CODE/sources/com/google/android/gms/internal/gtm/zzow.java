package com.google.android.gms.internal.gtm;

import com.google.android.gms.tagmanager.zzgj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzow {
    private String version;
    private final List<zzox> zzatq;
    private final Map<String, List<zzot>> zzatr;
    private int zzpw;

    private zzow() {
        this.zzatq = new ArrayList();
        this.zzatr = new HashMap();
        this.version = "";
        this.zzpw = 0;
    }

    public final zzow zzb(zzox zzoxVar) {
        this.zzatq.add(zzoxVar);
        return this;
    }

    public final zzow zzc(zzot zzotVar) {
        String strZzc = zzgj.zzc(zzotVar.zzlu().get(zzb.INSTANCE_NAME.toString()));
        List<zzot> arrayList = this.zzatr.get(strZzc);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzatr.put(strZzc, arrayList);
        }
        arrayList.add(zzotVar);
        return this;
    }

    public final zzow zzcs(String str) {
        this.version = str;
        return this;
    }

    public final zzow zzaf(int i) {
        this.zzpw = i;
        return this;
    }

    public final zzov zzmp() {
        return new zzov(this.zzatq, this.zzatr, this.version, this.zzpw);
    }
}
