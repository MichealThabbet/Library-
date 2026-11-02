package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzdz<V> {
    private static final Object zzf = new Object();
    private final String zza;
    private final zzdx<V> zzb;
    private final V zzc;
    private final V zzd;
    private final Object zze = new Object();
    private volatile V zzg = null;
    private volatile V zzh = null;

    /* JADX WARN: Multi-variable type inference failed */
    /* synthetic */ zzdz(String str, Object obj, Object obj2, zzdx zzdxVar, zzdw zzdwVar) {
        this.zza = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zzb = zzdxVar;
    }

    public final String zza() {
        return this.zza;
    }

    public final V zzb(V v) {
        synchronized (this.zze) {
        }
        if (v != null) {
            return v;
        }
        if (zzdy.zza == null) {
            return this.zzc;
        }
        synchronized (zzf) {
            if (zzz.zza()) {
                return this.zzh == null ? this.zzc : this.zzh;
            }
            try {
                for (zzdz zzdzVar : zzea.zzaG) {
                    if (zzz.zza()) {
                        throw new IllegalStateException("Refreshing flag cache must be done on a worker thread.");
                    }
                    V vZza = null;
                    try {
                        zzdx<V> zzdxVar = zzdzVar.zzb;
                        if (zzdxVar != null) {
                            vZza = zzdxVar.zza();
                        }
                    } catch (IllegalStateException unused) {
                    }
                    synchronized (zzf) {
                        zzdzVar.zzh = vZza;
                    }
                }
            } catch (SecurityException unused2) {
            }
            zzdx<V> zzdxVar2 = this.zzb;
            if (zzdxVar2 == null) {
                return this.zzc;
            }
            try {
                return zzdxVar2.zza();
            } catch (IllegalStateException unused3) {
                return this.zzc;
            } catch (SecurityException unused4) {
                return this.zzc;
            }
        }
    }
}
