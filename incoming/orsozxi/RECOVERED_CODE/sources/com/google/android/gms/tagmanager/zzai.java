package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Random;

/* loaded from: classes.dex */
public final class zzai {
    private final String zzaec;
    private final Random zzafj;
    private final Context zzrm;

    public zzai(Context context, String str) {
        this(context, str, new Random());
    }

    private zzai(Context context, String str, Random random) {
        this.zzrm = (Context) Preconditions.checkNotNull(context);
        this.zzaec = (String) Preconditions.checkNotNull(str);
        this.zzafj = random;
    }

    public final long zzhl() {
        return zza(7200000L, 259200000L) + 43200000;
    }

    public final long zzhm() {
        return zza(600000L, 86400000L) + 3600000;
    }

    private final long zza(long j, long j2) {
        long jMax = Math.max(0L, zzhp().getLong("FORBIDDEN_COUNT", 0L));
        return (long) (this.zzafj.nextFloat() * (j + ((long) ((jMax / ((jMax + Math.max(0L, r0.getLong("SUCCESSFUL_COUNT", 0L))) + 1)) * (j2 - j)))));
    }

    public final void zzhn() {
        SharedPreferences sharedPreferencesZzhp = zzhp();
        long j = sharedPreferencesZzhp.getLong("FORBIDDEN_COUNT", 0L);
        long j2 = sharedPreferencesZzhp.getLong("SUCCESSFUL_COUNT", 0L);
        SharedPreferences.Editor editorEdit = sharedPreferencesZzhp.edit();
        long jMin = j == 0 ? 3L : Math.min(10L, j + 1);
        long jMax = Math.max(0L, Math.min(j2, 10 - jMin));
        editorEdit.putLong("FORBIDDEN_COUNT", jMin);
        editorEdit.putLong("SUCCESSFUL_COUNT", jMax);
        editorEdit.apply();
    }

    public final void zzho() {
        SharedPreferences sharedPreferencesZzhp = zzhp();
        long j = sharedPreferencesZzhp.getLong("SUCCESSFUL_COUNT", 0L);
        long j2 = sharedPreferencesZzhp.getLong("FORBIDDEN_COUNT", 0L);
        long jMin = Math.min(10L, j + 1);
        long jMax = Math.max(0L, Math.min(j2, 10 - jMin));
        SharedPreferences.Editor editorEdit = sharedPreferencesZzhp.edit();
        editorEdit.putLong("SUCCESSFUL_COUNT", jMin);
        editorEdit.putLong("FORBIDDEN_COUNT", jMax);
        editorEdit.apply();
    }

    private final SharedPreferences zzhp() {
        Context context = this.zzrm;
        String strValueOf = String.valueOf(this.zzaec);
        return context.getSharedPreferences(strValueOf.length() != 0 ? "_gtmContainerRefreshPolicy_".concat(strValueOf) : new String("_gtmContainerRefreshPolicy_"), 0);
    }
}
