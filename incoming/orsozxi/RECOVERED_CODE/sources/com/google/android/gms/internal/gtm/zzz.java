package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzz extends com.google.android.gms.analytics.zzi<zzz> {
    private String zzus;
    private String zzut;
    private String zzuu;
    private String zzuv;
    private boolean zzuw;
    private String zzux;
    private boolean zzuy;
    private double zzuz;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("hitType", this.zzus);
        map.put("clientId", this.zzut);
        map.put("userId", this.zzuu);
        map.put("androidAdId", this.zzuv);
        map.put("AdTargetingEnabled", Boolean.valueOf(this.zzuw));
        map.put("sessionControl", this.zzux);
        map.put("nonInteraction", Boolean.valueOf(this.zzuy));
        map.put("sampleRate", Double.valueOf(this.zzuz));
        return zza((Object) map);
    }

    public final String zzbs() {
        return this.zzus;
    }

    public final void zzl(String str) {
        this.zzus = str;
    }

    public final String zzbt() {
        return this.zzut;
    }

    public final void setClientId(String str) {
        this.zzut = str;
    }

    public final String zzbu() {
        return this.zzuu;
    }

    public final void setUserId(String str) {
        this.zzuu = str;
    }

    public final String zzbv() {
        return this.zzuv;
    }

    public final void zzm(String str) {
        this.zzuv = str;
    }

    public final boolean zzbw() {
        return this.zzuw;
    }

    public final void zza(boolean z) {
        this.zzuw = z;
    }

    public final String zzbx() {
        return this.zzux;
    }

    public final boolean zzby() {
        return this.zzuy;
    }

    public final void zzb(boolean z) {
        this.zzuy = true;
    }

    public final double zzbz() {
        return this.zzuz;
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzz zzzVar = (zzz) zziVar;
        if (!TextUtils.isEmpty(this.zzus)) {
            zzzVar.zzus = this.zzus;
        }
        if (!TextUtils.isEmpty(this.zzut)) {
            zzzVar.zzut = this.zzut;
        }
        if (!TextUtils.isEmpty(this.zzuu)) {
            zzzVar.zzuu = this.zzuu;
        }
        if (!TextUtils.isEmpty(this.zzuv)) {
            zzzVar.zzuv = this.zzuv;
        }
        if (this.zzuw) {
            zzzVar.zzuw = true;
        }
        if (!TextUtils.isEmpty(this.zzux)) {
            zzzVar.zzux = this.zzux;
        }
        boolean z = this.zzuy;
        if (z) {
            zzzVar.zzuy = z;
        }
        double d = this.zzuz;
        if (d != 0.0d) {
            Preconditions.checkArgument(d >= 0.0d && d <= 100.0d, "Sample rate must be between 0% and 100%");
            zzzVar.zzuz = d;
        }
    }
}
