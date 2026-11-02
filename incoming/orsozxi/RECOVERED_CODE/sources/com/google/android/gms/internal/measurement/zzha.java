package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzha implements zzhe {
    private final ContentResolver zzc;
    private final Uri zzd;
    private volatile Map<String, String> zzg;
    private static final Map<Uri, zzha> zzb = new ArrayMap();
    public static final String[] zza = {"key", "value"};
    private final ContentObserver zze = new zzgz(this, null);
    private final Object zzf = new Object();
    private final List<zzhb> zzh = new ArrayList();

    private zzha(ContentResolver contentResolver, Uri uri) {
        if (contentResolver == null || uri == null) {
            throw null;
        }
        this.zzc = contentResolver;
        this.zzd = uri;
        contentResolver.registerContentObserver(uri, false, this.zze);
    }

    public static zzha zza(ContentResolver contentResolver, Uri uri) {
        zzha zzhaVar;
        synchronized (zzha.class) {
            zzhaVar = zzb.get(uri);
            if (zzhaVar == null) {
                try {
                    zzha zzhaVar2 = new zzha(contentResolver, uri);
                    try {
                        zzb.put(uri, zzhaVar2);
                    } catch (SecurityException unused) {
                    }
                    zzhaVar = zzhaVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzhaVar;
    }

    static synchronized void zzd() {
        for (zzha zzhaVar : zzb.values()) {
            zzhaVar.zzc.unregisterContentObserver(zzhaVar.zze);
        }
        zzb.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map<String, String> zzb() {
        Map<String, String> map;
        Map<String, String> map2;
        Map<String, String> map3 = this.zzg;
        Map<String, String> map4 = map3;
        if (map3 == null) {
            synchronized (this.zzf) {
                Map<String, String> map5 = this.zzg;
                map = map5;
                if (map5 == null) {
                    StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        try {
                            map2 = (Map) zzhc.zza(new zzhd(this) { // from class: com.google.android.gms.internal.measurement.zzgy
                                private final zzha zza;

                                {
                                    this.zza = this;
                                }

                                @Override // com.google.android.gms.internal.measurement.zzhd
                                public final Object zza() {
                                    return this.zza.zzf();
                                }
                            });
                        } finally {
                            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        }
                    } catch (SQLiteException | IllegalStateException | SecurityException unused) {
                        Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        map2 = null;
                    }
                    this.zzg = map2;
                    threadPolicyAllowThreadDiskReads = map2;
                    map = threadPolicyAllowThreadDiskReads;
                }
            }
            map4 = map;
        }
        return map4 != null ? map4 : Collections.emptyMap();
    }

    public final void zzc() {
        synchronized (this.zzf) {
            this.zzg = null;
            zzht.zzc();
        }
        synchronized (this) {
            Iterator<zzhb> it2 = this.zzh.iterator();
            while (it2.hasNext()) {
                it2.next().zza();
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhe
    public final /* bridge */ /* synthetic */ Object zze(String str) {
        return zzb().get(str);
    }

    final /* synthetic */ Map zzf() {
        Cursor cursorQuery = this.zzc.query(this.zzd, zza, null, null, null);
        if (cursorQuery == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursorQuery.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            Map arrayMap = count <= 256 ? new ArrayMap(count) : new HashMap(count, 1.0f);
            while (cursorQuery.moveToNext()) {
                arrayMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return arrayMap;
        } finally {
            cursorQuery.close();
        }
    }
}
