package com.ibm.icu.impl;

import com.ibm.icu.util.Freezable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class Relation<K, V> implements Freezable<Relation<K, V>> {
    private Map<K, Set<V>> data;
    volatile boolean frozen;
    Object[] setComparatorParam;
    Constructor<? extends Set<V>> setCreator;

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> cls) {
        return new Relation<>(map, cls);
    }

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> cls, Comparator<V> comparator) {
        return new Relation<>(map, cls, comparator);
    }

    public Relation(Map<K, Set<V>> map, Class<?> cls) {
        this(map, cls, null);
    }

    public Relation(Map<K, Set<V>> map, Class<?> cls, Comparator<V> comparator) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object[] objArr;
        this.frozen = false;
        if (comparator == null) {
            objArr = null;
        } else {
            try {
                objArr = new Object[]{comparator};
            } catch (Exception e) {
                throw ((RuntimeException) new IllegalArgumentException("Can't create new set").initCause(e));
            }
        }
        this.setComparatorParam = objArr;
        if (comparator == null) {
            Constructor<? extends Set<V>> constructor = (Constructor<? extends Set<V>>) cls.getConstructor(new Class[0]);
            this.setCreator = constructor;
            constructor.newInstance(this.setComparatorParam);
        } else {
            Constructor<? extends Set<V>> constructor2 = (Constructor<? extends Set<V>>) cls.getConstructor(Comparator.class);
            this.setCreator = constructor2;
            constructor2.newInstance(this.setComparatorParam);
        }
        this.data = map == null ? new HashMap<>() : map;
    }

    public void clear() {
        this.data.clear();
    }

    public boolean containsKey(Object obj) {
        return this.data.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        Iterator<Set<V>> it2 = this.data.values().iterator();
        while (it2.hasNext()) {
            if (it2.next().contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        return keyValueSet();
    }

    public Set<Map.Entry<K, Set<V>>> keyValuesSet() {
        return this.data.entrySet();
    }

    public Set<Map.Entry<K, V>> keyValueSet() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (K k : this.data.keySet()) {
            Iterator<V> it2 = this.data.get(k).iterator();
            while (it2.hasNext()) {
                linkedHashSet.add(new SimpleEntry(k, it2.next()));
            }
        }
        return linkedHashSet;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            return this.data.equals(((Relation) obj).data);
        }
        return false;
    }

    public Set<V> getAll(Object obj) {
        return this.data.get(obj);
    }

    public Set<V> get(Object obj) {
        return this.data.get(obj);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public Set<K> keySet() {
        return this.data.keySet();
    }

    public V put(K k, V v) {
        Set<V> set = this.data.get(k);
        if (set == null) {
            Map<K, Set<V>> map = this.data;
            Set<V> setNewSet = newSet();
            map.put(k, setNewSet);
            set = setNewSet;
        }
        set.add(v);
        return v;
    }

    public V putAll(K k, Collection<? extends V> collection) {
        Set<V> set = this.data.get(k);
        if (set == null) {
            Map<K, Set<V>> map = this.data;
            Set<V> setNewSet = newSet();
            map.put(k, setNewSet);
            set = setNewSet;
        }
        set.addAll(collection);
        if (collection.size() == 0) {
            return null;
        }
        return collection.iterator().next();
    }

    public V putAll(Collection<K> collection, V v) {
        Iterator<K> it2 = collection.iterator();
        V vPut = null;
        while (it2.hasNext()) {
            vPut = put(it2.next(), v);
        }
        return vPut;
    }

    private Set<V> newSet() {
        try {
            return this.setCreator.newInstance(this.setComparatorParam);
        } catch (Exception e) {
            throw ((RuntimeException) new IllegalArgumentException("Can't create new set").initCause(e));
        }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void putAll(Relation<? extends K, ? extends V> relation) {
        for (K k : relation.keySet()) {
            Iterator<? extends V> it2 = relation.getAll(k).iterator();
            while (it2.hasNext()) {
                put(k, it2.next());
            }
        }
    }

    public Set<V> removeAll(K k) {
        try {
            return this.data.remove(k);
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public boolean remove(K k, V v) {
        try {
            Set<V> set = this.data.get(k);
            if (set == null) {
                return false;
            }
            boolean zRemove = set.remove(v);
            if (set.size() == 0) {
                this.data.remove(k);
            }
            return zRemove;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    public int size() {
        return this.data.size();
    }

    public Set<V> values() {
        return (Set) values(new LinkedHashSet());
    }

    public <C extends Collection<V>> C values(C c) {
        Iterator<Map.Entry<K, Set<V>>> it2 = this.data.entrySet().iterator();
        while (it2.hasNext()) {
            c.addAll(it2.next().getValue());
        }
        return c;
    }

    public String toString() {
        return this.data.toString();
    }

    static class SimpleEntry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;

        public SimpleEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleEntry(Map.Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }
    }

    public Relation<K, V> addAllInverted(Relation<V, K> relation) {
        for (V v : relation.data.keySet()) {
            Iterator<K> it2 = relation.data.get(v).iterator();
            while (it2.hasNext()) {
                put(it2.next(), v);
            }
        }
        return this;
    }

    public Relation<K, V> addAllInverted(Map<V, K> map) {
        for (Map.Entry<V, K> entry : map.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public Relation<K, V> freeze() {
        if (!this.frozen) {
            for (K k : this.data.keySet()) {
                Map<K, Set<V>> map = this.data;
                map.put(k, Collections.unmodifiableSet(map.get(k)));
            }
            this.data = Collections.unmodifiableMap(this.data);
            this.frozen = true;
        }
        return this;
    }

    @Override // com.ibm.icu.util.Freezable
    public Relation<K, V> cloneAsThawed() {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Relation<K, V> relation) {
        boolean zRemoveAll = false;
        for (K k : relation.keySet()) {
            try {
                Set<V> all = relation.getAll(k);
                if (all != null) {
                    zRemoveAll |= removeAll(k, all);
                }
            } catch (NullPointerException unused) {
            }
        }
        return zRemoveAll;
    }

    @SafeVarargs
    public final Set<V> removeAll(K... kArr) {
        return removeAll((Collection) Arrays.asList(kArr));
    }

    public boolean removeAll(K k, Iterable<V> iterable) {
        Iterator<V> it2 = iterable.iterator();
        boolean zRemove = false;
        while (it2.hasNext()) {
            zRemove |= remove(k, it2.next());
        }
        return zRemove;
    }

    public Set<V> removeAll(Collection<K> collection) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<K> it2 = collection.iterator();
        while (it2.hasNext()) {
            try {
                Set<V> setRemove = this.data.remove(it2.next());
                if (setRemove != null) {
                    linkedHashSet.addAll(setRemove);
                }
            } catch (NullPointerException unused) {
            }
        }
        return linkedHashSet;
    }
}
