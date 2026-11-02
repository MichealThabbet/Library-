package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import java.util.HashMap;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes.dex */
public final class zzv extends com.google.android.gms.analytics.zzi<zzv> {
    private String zzuj;
    public int zzuk;
    public int zzul;
    public int zzum;
    public int zzun;
    public int zzuo;

    public final String toString() {
        HashMap map = new HashMap();
        map.put(FFmpegMediaMetadataRetriever.METADATA_KEY_LANGUAGE, this.zzuj);
        map.put("screenColors", Integer.valueOf(this.zzuk));
        map.put("screenWidth", Integer.valueOf(this.zzul));
        map.put("screenHeight", Integer.valueOf(this.zzum));
        map.put("viewportWidth", Integer.valueOf(this.zzun));
        map.put("viewportHeight", Integer.valueOf(this.zzuo));
        return zza((Object) map);
    }

    public final String getLanguage() {
        return this.zzuj;
    }

    public final void setLanguage(String str) {
        this.zzuj = str;
    }

    @Override // com.google.android.gms.analytics.zzi
    public final /* synthetic */ void zzb(com.google.android.gms.analytics.zzi zziVar) {
        zzv zzvVar = (zzv) zziVar;
        int i = this.zzuk;
        if (i != 0) {
            zzvVar.zzuk = i;
        }
        int i2 = this.zzul;
        if (i2 != 0) {
            zzvVar.zzul = i2;
        }
        int i3 = this.zzum;
        if (i3 != 0) {
            zzvVar.zzum = i3;
        }
        int i4 = this.zzun;
        if (i4 != 0) {
            zzvVar.zzun = i4;
        }
        int i5 = this.zzuo;
        if (i5 != 0) {
            zzvVar.zzuo = i5;
        }
        if (TextUtils.isEmpty(this.zzuj)) {
            return;
        }
        zzvVar.zzuj = this.zzuj;
    }
}
