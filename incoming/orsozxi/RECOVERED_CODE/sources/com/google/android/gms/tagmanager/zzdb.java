package com.google.android.gms.tagmanager;

import android.util.LruCache;

/* loaded from: classes.dex */
final class zzdb<K, V> implements zzp<K, V> {
    private LruCache<K, V> zzahy;

    zzdb(int i, zzs<K, V> zzsVar) {
        this.zzahy = new zzdc(this, 1048576, zzsVar);
    }

    @Override // com.google.android.gms.tagmanager.zzp
    public final void zza(K k, V v) {
        this.zzahy.put(k, v);
    }

    @Override // com.google.android.gms.tagmanager.zzp
    public final V get(K k) {
        return this.zzahy.get(k);
    }
}
