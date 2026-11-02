package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzot {
    private final zzl zzakg;
    private final Map<String, zzl> zzats;

    private zzot(Map<String, zzl> map, zzl zzlVar) {
        this.zzats = map;
        this.zzakg = zzlVar;
    }

    public static zzou zzml() {
        return new zzou();
    }

    public final void zza(String str, zzl zzlVar) {
        this.zzats.put(str, zzlVar);
    }

    public final Map<String, zzl> zzlu() {
        return Collections.unmodifiableMap(this.zzats);
    }

    public final zzl zzji() {
        return this.zzakg;
    }

    public final String toString() {
        String strValueOf = String.valueOf(Collections.unmodifiableMap(this.zzats));
        String strValueOf2 = String.valueOf(this.zzakg);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 32 + String.valueOf(strValueOf2).length());
        sb.append("Properties: ");
        sb.append(strValueOf);
        sb.append(" pushAfterEvaluate: ");
        sb.append(strValueOf2);
        return sb.toString();
    }
}
