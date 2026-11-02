package com.google.android.gms.tagmanager;

import com.google.android.gms.analytics.Logger;

/* loaded from: classes.dex */
final class zzgg implements Logger {
    zzgg() {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void error(String str) {
        zzdi.zzav(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void error(Exception exc) {
        zzdi.zza("", exc);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void info(String str) {
        zzdi.zzaw(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void verbose(String str) {
        zzdi.zzab(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void warn(String str) {
        zzdi.zzac(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void setLogLevel(int i) {
        zzdi.zzac("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
    }

    @Override // com.google.android.gms.analytics.Logger
    public final int getLogLevel() {
        int i = zzdi.zzyr;
        if (i == 2) {
            return 0;
        }
        if (i == 3 || i == 4) {
            return 1;
        }
        return i != 5 ? 3 : 2;
    }
}
