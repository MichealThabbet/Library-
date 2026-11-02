package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzot;
import com.google.android.gms.internal.gtm.zzox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
final class zzfi {
    private zzot zzakl;
    private final Set<zzox> zzajx = new HashSet();
    private final Map<zzox, List<zzot>> zzakh = new HashMap();
    private final Map<zzox, List<String>> zzakj = new HashMap();
    private final Map<zzox, List<zzot>> zzaki = new HashMap();
    private final Map<zzox, List<String>> zzakk = new HashMap();

    public final Set<zzox> zzjj() {
        return this.zzajx;
    }

    public final void zza(zzox zzoxVar) {
        this.zzajx.add(zzoxVar);
    }

    public final Map<zzox, List<zzot>> zzjk() {
        return this.zzakh;
    }

    public final Map<zzox, List<String>> zzjl() {
        return this.zzakj;
    }

    public final Map<zzox, List<String>> zzjm() {
        return this.zzakk;
    }

    public final void zza(zzox zzoxVar, zzot zzotVar) {
        List<zzot> arrayList = this.zzakh.get(zzoxVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzakh.put(zzoxVar, arrayList);
        }
        arrayList.add(zzotVar);
    }

    public final void zza(zzox zzoxVar, String str) {
        List<String> arrayList = this.zzakj.get(zzoxVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzakj.put(zzoxVar, arrayList);
        }
        arrayList.add(str);
    }

    public final Map<zzox, List<zzot>> zzjn() {
        return this.zzaki;
    }

    public final void zzb(zzox zzoxVar, zzot zzotVar) {
        List<zzot> arrayList = this.zzaki.get(zzoxVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzaki.put(zzoxVar, arrayList);
        }
        arrayList.add(zzotVar);
    }

    public final void zzb(zzox zzoxVar, String str) {
        List<String> arrayList = this.zzakk.get(zzoxVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzakk.put(zzoxVar, arrayList);
        }
        arrayList.add(str);
    }

    public final zzot zzjo() {
        return this.zzakl;
    }

    public final void zzb(zzot zzotVar) {
        this.zzakl = zzotVar;
    }
}
