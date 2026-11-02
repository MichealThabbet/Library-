package com.google.android.gms.internal.gtm;

import android.content.SharedPreferences;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class zzcm extends zzan {
    private SharedPreferences zzabv;
    private long zzabw;
    private long zzabx;
    private final zzco zzaby;

    protected zzcm(zzap zzapVar) {
        super(zzapVar);
        this.zzabx = -1L;
        this.zzaby = new zzco(this, "monitoring", zzby.zzaao.get().longValue());
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
        this.zzabv = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public final long zzfv() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (this.zzabw == 0) {
            long j = this.zzabv.getLong("first_run", 0L);
            if (j != 0) {
                this.zzabw = j;
            } else {
                long jCurrentTimeMillis = zzcn().currentTimeMillis();
                SharedPreferences.Editor editorEdit = this.zzabv.edit();
                editorEdit.putLong("first_run", jCurrentTimeMillis);
                if (!editorEdit.commit()) {
                    zzt("Failed to commit first run time");
                }
                this.zzabw = jCurrentTimeMillis;
            }
        }
        return this.zzabw;
    }

    public final zzcv zzfw() {
        return new zzcv(zzcn(), zzfv());
    }

    public final long zzfx() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (this.zzabx == -1) {
            this.zzabx = this.zzabv.getLong("last_dispatch", 0L);
        }
        return this.zzabx;
    }

    public final void zzfy() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        long jCurrentTimeMillis = zzcn().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzabv.edit();
        editorEdit.putLong("last_dispatch", jCurrentTimeMillis);
        editorEdit.apply();
        this.zzabx = jCurrentTimeMillis;
    }

    public final String zzfz() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        String string = this.zzabv.getString("installation_campaign", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public final void zzad(String str) {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        SharedPreferences.Editor editorEdit = this.zzabv.edit();
        if (TextUtils.isEmpty(str)) {
            editorEdit.remove("installation_campaign");
        } else {
            editorEdit.putString("installation_campaign", str);
        }
        if (editorEdit.commit()) {
            return;
        }
        zzt("Failed to commit campaign data");
    }

    public final zzco zzga() {
        return this.zzaby;
    }
}
