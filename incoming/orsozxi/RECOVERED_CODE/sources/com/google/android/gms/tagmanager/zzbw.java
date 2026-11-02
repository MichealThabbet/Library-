package com.google.android.gms.tagmanager;

import android.text.TextUtils;

/* loaded from: classes.dex */
final class zzbw {
    private final long zzabb;
    private final long zzagy;
    private final long zzagz;
    private String zzaha;

    final long zzih() {
        return this.zzagy;
    }

    final long zzii() {
        return this.zzagz;
    }

    zzbw(long j, long j2, long j3) {
        this.zzagy = j;
        this.zzabb = j2;
        this.zzagz = j3;
    }

    final String zzij() {
        return this.zzaha;
    }

    final void zzbc(String str) {
        if (str == null || TextUtils.isEmpty(str.trim())) {
            return;
        }
        this.zzaha = str;
    }
}
