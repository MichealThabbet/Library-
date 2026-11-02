package com.ibm.icu.impl;

import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public abstract class SoftCache<K, V, D> extends CacheBase<K, V, D> {
    private ConcurrentHashMap<K, Object> map = new ConcurrentHashMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.ibm.icu.impl.CacheBase
    public final V getInstance(K k, D d) {
        V v = (V) this.map.get(k);
        if (v != 0) {
            if (!(v instanceof CacheValue)) {
                return v;
            }
            CacheValue cacheValue = (CacheValue) v;
            if (cacheValue.isNull()) {
                return null;
            }
            V v2 = (V) cacheValue.get();
            return v2 != null ? v2 : (V) cacheValue.resetIfCleared(createInstance(k, d));
        }
        V vCreateInstance = createInstance(k, d);
        V v3 = (V) this.map.putIfAbsent(k, (vCreateInstance == null || !CacheValue.futureInstancesWillBeStrong()) ? CacheValue.getInstance(vCreateInstance) : vCreateInstance);
        return v3 == 0 ? vCreateInstance : !(v3 instanceof CacheValue) ? v3 : (V) ((CacheValue) v3).resetIfCleared(vCreateInstance);
    }
}
