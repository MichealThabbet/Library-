package com.google.android.gms.tagmanager;

import android.util.LruCache;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes.dex */
final class zzdc<K, V> extends LruCache<K, V> {
    private final /* synthetic */ zzs zzahz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdc(zzdb zzdbVar, int i, zzs zzsVar) {
        super(i);
        this.zzahz = zzsVar;
    }

    @Override // android.util.LruCache
    protected final int sizeOf(K k, V v) {
        return this.zzahz.sizeOf(k, v);
    }
}
