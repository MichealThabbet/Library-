package com.ibm.icu.impl.locale;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public abstract class LocaleObjectCache<K, V> {
    private ConcurrentHashMap<K, CacheEntry<K, V>> _map;
    private ReferenceQueue<V> _queue;

    protected abstract V createObject(K k);

    protected K normalizeKey(K k) {
        return k;
    }

    public LocaleObjectCache() {
        this(16, 0.75f, 16);
    }

    public LocaleObjectCache(int i, float f, int i2) {
        this._queue = new ReferenceQueue<>();
        this._map = new ConcurrentHashMap<>(i, f, i2);
    }

    public V get(K k) {
        cleanStaleEntries();
        CacheEntry<K, V> cacheEntry = this._map.get(k);
        V v = cacheEntry != null ? cacheEntry.get() : null;
        if (v != null) {
            return v;
        }
        K kNormalizeKey = normalizeKey(k);
        V vCreateObject = createObject(kNormalizeKey);
        if (kNormalizeKey == null || vCreateObject == null) {
            return null;
        }
        CacheEntry<K, V> cacheEntry2 = new CacheEntry<>(kNormalizeKey, vCreateObject, this._queue);
        while (v == null) {
            cleanStaleEntries();
            CacheEntry<K, V> cacheEntryPutIfAbsent = this._map.putIfAbsent(kNormalizeKey, cacheEntry2);
            if (cacheEntryPutIfAbsent == null) {
                return vCreateObject;
            }
            v = cacheEntryPutIfAbsent.get();
        }
        return v;
    }

    private void cleanStaleEntries() {
        while (true) {
            CacheEntry cacheEntry = (CacheEntry) this._queue.poll();
            if (cacheEntry == null) {
                return;
            } else {
                this._map.remove(cacheEntry.getKey());
            }
        }
    }

    private static class CacheEntry<K, V> extends SoftReference<V> {
        private K _key;

        CacheEntry(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this._key = k;
        }

        K getKey() {
            return this._key;
        }
    }
}
