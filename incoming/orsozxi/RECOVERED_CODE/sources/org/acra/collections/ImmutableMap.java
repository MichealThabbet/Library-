package org.acra.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.acra.collections.ImmutableSet;

/* loaded from: classes3.dex */
public final class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    private final Map<K, V> mMap;

    public ImmutableMap(Map<K, V> map) {
        this.mMap = new HashMap(map);
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.mMap.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.mMap.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> setEntrySet = this.mMap.entrySet();
        ImmutableSet.Builder builder = new ImmutableSet.Builder();
        Iterator<Map.Entry<K, V>> it2 = setEntrySet.iterator();
        while (it2.hasNext()) {
            builder.add(new ImmutableEntryWrapper(it2.next()));
        }
        return builder.build();
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return this.mMap.get(obj);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new ImmutableSet(this.mMap.keySet());
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public int size() {
        return this.mMap.size();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new ImmutableList(this.mMap.values());
    }

    private static class ImmutableEntryWrapper<K, V> implements Map.Entry<K, V> {
        private final Map.Entry<K, V> mEntry;

        ImmutableEntryWrapper(Map.Entry<K, V> entry) {
            this.mEntry = entry;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.mEntry.getKey();
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.mEntry.getValue();
        }

        @Override // java.util.Map.Entry
        public V setValue(Object obj) {
            throw new UnsupportedOperationException();
        }
    }
}
