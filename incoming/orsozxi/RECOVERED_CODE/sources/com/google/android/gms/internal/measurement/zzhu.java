package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import androidx.collection.ArrayMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzhu implements zzhe {
    private static final Map<String, zzhu> zza = new ArrayMap();
    private final SharedPreferences zzb;
    private final SharedPreferences.OnSharedPreferenceChangeListener zzc;

    static zzhu zza(Context context, String str) {
        zzhu zzhuVar;
        if (zzgw.zza()) {
            throw null;
        }
        synchronized (zzhu.class) {
            zzhuVar = zza.get(null);
            if (zzhuVar == null) {
                StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                try {
                    throw null;
                } catch (Throwable th) {
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                    throw th;
                }
            }
        }
        return zzhuVar;
    }

    static synchronized void zzb() {
        Iterator<zzhu> it2 = zza.values().iterator();
        if (it2.hasNext()) {
            zzhu next = it2.next();
            SharedPreferences sharedPreferences = next.zzb;
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = next.zzc;
            throw null;
        }
        zza.clear();
    }

    @Override // com.google.android.gms.internal.measurement.zzhe
    public final Object zze(String str) {
        throw null;
    }
}
