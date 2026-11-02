package com.google.android.gms.analytics;

import android.os.Build;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzg {
    private final zzj zzsc;
    private final Clock zzsd;
    private boolean zzse;
    private long zzsf;
    private long zzsg;
    private long zzsh;
    private long zzsi;
    private long zzsj;
    private boolean zzsk;
    private final Map<Class<? extends zzi>, zzi> zzsl;
    private final List<zzo> zzsm;

    public final zzg zzai() {
        return new zzg(this);
    }

    public final void zza(zzi zziVar) {
        Preconditions.checkNotNull(zziVar);
        Class<?> cls = zziVar.getClass();
        if (cls.getSuperclass() != zzi.class) {
            throw new IllegalArgumentException();
        }
        zziVar.zzb(zzb(cls));
    }

    public final <T extends zzi> T zza(Class<T> cls) {
        return (T) this.zzsl.get(cls);
    }

    public final <T extends zzi> T zzb(Class<T> cls) {
        T t = (T) this.zzsl.get(cls);
        if (t != null) {
            return t;
        }
        T t2 = (T) zzc(cls);
        this.zzsl.put(cls, t2);
        return t2;
    }

    public final Collection<zzi> zzaj() {
        return this.zzsl.values();
    }

    public final List<zzo> zzak() {
        return this.zzsm;
    }

    public final long zzal() {
        return this.zzsf;
    }

    public final void zza(long j) {
        this.zzsg = j;
    }

    public final void zzam() {
        this.zzsc.zzas().zze(this);
    }

    zzg(zzj zzjVar, Clock clock) {
        Preconditions.checkNotNull(zzjVar);
        Preconditions.checkNotNull(clock);
        this.zzsc = zzjVar;
        this.zzsd = clock;
        this.zzsi = 1800000L;
        this.zzsj = 3024000000L;
        this.zzsl = new HashMap();
        this.zzsm = new ArrayList();
    }

    private zzg(zzg zzgVar) {
        this.zzsc = zzgVar.zzsc;
        this.zzsd = zzgVar.zzsd;
        this.zzsf = zzgVar.zzsf;
        this.zzsg = zzgVar.zzsg;
        this.zzsh = zzgVar.zzsh;
        this.zzsi = zzgVar.zzsi;
        this.zzsj = zzgVar.zzsj;
        this.zzsm = new ArrayList(zzgVar.zzsm);
        this.zzsl = new HashMap(zzgVar.zzsl.size());
        for (Map.Entry<Class<? extends zzi>, zzi> entry : zzgVar.zzsl.entrySet()) {
            zzi zziVarZzc = zzc(entry.getKey());
            entry.getValue().zzb(zziVarZzc);
            this.zzsl.put(entry.getKey(), zziVarZzc);
        }
    }

    private static <T extends zzi> T zzc(Class<T> cls) {
        try {
            return cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            if (e instanceof InstantiationException) {
                throw new IllegalArgumentException("dataType doesn't have default constructor", e);
            }
            if (e instanceof IllegalAccessException) {
                throw new IllegalArgumentException("dataType default constructor is not accessible", e);
            }
            if (Build.VERSION.SDK_INT >= 19 && (e instanceof ReflectiveOperationException)) {
                throw new IllegalArgumentException("Linkage exception", e);
            }
            throw new RuntimeException(e);
        }
    }

    public final boolean zzan() {
        return this.zzse;
    }

    final void zzao() {
        this.zzsh = this.zzsd.elapsedRealtime();
        long j = this.zzsg;
        if (j != 0) {
            this.zzsf = j;
        } else {
            this.zzsf = this.zzsd.currentTimeMillis();
        }
        this.zzse = true;
    }

    final zzj zzap() {
        return this.zzsc;
    }

    final boolean zzaq() {
        return this.zzsk;
    }

    final void zzar() {
        this.zzsk = true;
    }
}
