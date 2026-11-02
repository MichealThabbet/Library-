package org.apache.commons.collections4.map;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.ResettableIterator;
import org.apache.commons.collections4.iterators.EmptyIterator;
import org.apache.commons.collections4.iterators.EmptyMapIterator;

/* loaded from: classes3.dex */
public class Flat3Map<K, V> implements IterableMap<K, V>, Serializable, Cloneable {
    private static final long serialVersionUID = -6701087419741928296L;
    private transient AbstractHashedMap<K, V> delegateMap;
    private transient int hash1;
    private transient int hash2;
    private transient int hash3;
    private transient K key1;
    private transient K key2;
    private transient K key3;
    private transient int size;
    private transient V value1;
    private transient V value2;
    private transient V value3;

    public Flat3Map() {
    }

    public Flat3Map(Map<? extends K, ? extends V> map) {
        putAll(map);
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public V get(Object obj) {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.get(obj);
        }
        if (obj == null) {
            int i = this.size;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return null;
                    }
                    if (this.key3 == null) {
                        return this.value3;
                    }
                }
                if (this.key2 == null) {
                    return this.value2;
                }
            }
            if (this.key1 == null) {
                return this.value1;
            }
            return null;
        }
        if (this.size <= 0) {
            return null;
        }
        int iHashCode = obj.hashCode();
        int i2 = this.size;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    return null;
                }
                if (this.hash3 == iHashCode && obj.equals(this.key3)) {
                    return this.value3;
                }
            }
            if (this.hash2 == iHashCode && obj.equals(this.key2)) {
                return this.value2;
            }
        }
        if (this.hash1 == iHashCode && obj.equals(this.key1)) {
            return this.value1;
        }
        return null;
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public int size() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.size();
        }
        return this.size;
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public boolean containsKey(Object obj) {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.containsKey(obj);
        }
        if (obj == null) {
            int i = this.size;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return false;
                    }
                    if (this.key3 == null) {
                        return true;
                    }
                }
                if (this.key2 == null) {
                    return true;
                }
            }
            return this.key1 == null;
        }
        if (this.size <= 0) {
            return false;
        }
        int iHashCode = obj.hashCode();
        int i2 = this.size;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    return false;
                }
                if (this.hash3 == iHashCode && obj.equals(this.key3)) {
                    return true;
                }
            }
            if (this.hash2 == iHashCode && obj.equals(this.key2)) {
                return true;
            }
        }
        return this.hash1 == iHashCode && obj.equals(this.key1);
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public boolean containsValue(Object obj) {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.containsValue(obj);
        }
        if (obj == null) {
            int i = this.size;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return false;
                    }
                    if (this.value3 == null) {
                        return true;
                    }
                }
                if (this.value2 == null) {
                    return true;
                }
            }
            return this.value1 == null;
        }
        int i2 = this.size;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    return false;
                }
                if (obj.equals(this.value3)) {
                    return true;
                }
            }
            if (obj.equals(this.value2)) {
                return true;
            }
        }
        return obj.equals(this.value1);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x002d  */
    @Override // java.util.Map, org.apache.commons.collections4.Put
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public V put(K r6, V r7) {
        /*
            r5 = this;
            org.apache.commons.collections4.map.AbstractHashedMap<K, V> r0 = r5.delegateMap
            if (r0 == 0) goto L9
            java.lang.Object r6 = r0.put(r6, r7)
            return r6
        L9:
            r0 = 3
            r1 = 2
            r2 = 1
            if (r6 != 0) goto L32
            int r3 = r5.size
            if (r3 == r2) goto L29
            if (r3 == r1) goto L20
            if (r3 == r0) goto L17
            goto L76
        L17:
            K r0 = r5.key3
            if (r0 != 0) goto L20
            V r6 = r5.value3
            r5.value3 = r7
            return r6
        L20:
            K r0 = r5.key2
            if (r0 != 0) goto L29
            V r6 = r5.value2
            r5.value2 = r7
            return r6
        L29:
            K r0 = r5.key1
            if (r0 != 0) goto L76
            V r6 = r5.value1
            r5.value1 = r7
            return r6
        L32:
            int r3 = r5.size
            if (r3 <= 0) goto L76
            int r3 = r6.hashCode()
            int r4 = r5.size
            if (r4 == r2) goto L65
            if (r4 == r1) goto L54
            if (r4 == r0) goto L43
            goto L76
        L43:
            int r0 = r5.hash3
            if (r0 != r3) goto L54
            K r0 = r5.key3
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L54
            V r6 = r5.value3
            r5.value3 = r7
            return r6
        L54:
            int r0 = r5.hash2
            if (r0 != r3) goto L65
            K r0 = r5.key2
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L65
            V r6 = r5.value2
            r5.value2 = r7
            return r6
        L65:
            int r0 = r5.hash1
            if (r0 != r3) goto L76
            K r0 = r5.key1
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L76
            V r6 = r5.value1
            r5.value1 = r7
            return r6
        L76:
            int r0 = r5.size
            r3 = 0
            r4 = 0
            if (r0 == 0) goto La5
            if (r0 == r2) goto L97
            if (r0 == r1) goto L89
            r5.convertToMap()
            org.apache.commons.collections4.map.AbstractHashedMap<K, V> r0 = r5.delegateMap
            r0.put(r6, r7)
            return r3
        L89:
            if (r6 != 0) goto L8c
            goto L90
        L8c:
            int r4 = r6.hashCode()
        L90:
            r5.hash3 = r4
            r5.key3 = r6
            r5.value3 = r7
            goto Lb2
        L97:
            if (r6 != 0) goto L9a
            goto L9e
        L9a:
            int r4 = r6.hashCode()
        L9e:
            r5.hash2 = r4
            r5.key2 = r6
            r5.value2 = r7
            goto Lb2
        La5:
            if (r6 != 0) goto La8
            goto Lac
        La8:
            int r4 = r6.hashCode()
        Lac:
            r5.hash1 = r4
            r5.key1 = r6
            r5.value1 = r7
        Lb2:
            int r6 = r5.size
            int r6 = r6 + r2
            r5.size = r6
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.collections4.map.Flat3Map.put(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map, org.apache.commons.collections4.Put
    public void putAll(Map<? extends K, ? extends V> map) {
        int size = map.size();
        if (size == 0) {
            return;
        }
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            abstractHashedMap.putAll(map);
            return;
        }
        if (size < 4) {
            for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return;
        }
        convertToMap();
        this.delegateMap.putAll(map);
    }

    private void convertToMap() {
        AbstractHashedMap<K, V> abstractHashedMapCreateDelegateMap = createDelegateMap();
        this.delegateMap = abstractHashedMapCreateDelegateMap;
        int i = this.size;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        abstractHashedMapCreateDelegateMap.put(this.key3, this.value3);
                    } else {
                        throw new IllegalStateException("Invalid map index: " + this.size);
                    }
                }
                this.delegateMap.put(this.key2, this.value2);
            }
            this.delegateMap.put(this.key1, this.value1);
        }
        this.size = 0;
        this.hash3 = 0;
        this.hash2 = 0;
        this.hash1 = 0;
        this.key3 = null;
        this.key2 = null;
        this.key1 = null;
        this.value3 = null;
        this.value2 = null;
        this.value1 = null;
    }

    protected AbstractHashedMap<K, V> createDelegateMap() {
        return new HashedMap();
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public V remove(Object obj) {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.remove(obj);
        }
        int i = this.size;
        if (i == 0) {
            return null;
        }
        if (obj == null) {
            if (i != 1) {
                if (i == 2) {
                    K k = this.key2;
                    if (k == null) {
                        V v = this.value2;
                        this.hash2 = 0;
                        this.key2 = null;
                        this.value2 = null;
                        this.size = 1;
                        return v;
                    }
                    if (this.key1 != null) {
                        return null;
                    }
                    V v2 = this.value1;
                    this.hash1 = this.hash2;
                    this.key1 = k;
                    this.value1 = this.value2;
                    this.hash2 = 0;
                    this.key2 = null;
                    this.value2 = null;
                    this.size = 1;
                    return v2;
                }
                if (i == 3) {
                    K k2 = this.key3;
                    if (k2 == null) {
                        V v3 = this.value3;
                        this.hash3 = 0;
                        this.key3 = null;
                        this.value3 = null;
                        this.size = 2;
                        return v3;
                    }
                    if (this.key2 == null) {
                        V v4 = this.value2;
                        this.hash2 = this.hash3;
                        this.key2 = k2;
                        this.value2 = this.value3;
                        this.hash3 = 0;
                        this.key3 = null;
                        this.value3 = null;
                        this.size = 2;
                        return v4;
                    }
                    if (this.key1 != null) {
                        return null;
                    }
                    V v5 = this.value1;
                    this.hash1 = this.hash3;
                    this.key1 = k2;
                    this.value1 = this.value3;
                    this.hash3 = 0;
                    this.key3 = null;
                    this.value3 = null;
                    this.size = 2;
                    return v5;
                }
            } else if (this.key1 == null) {
                V v6 = this.value1;
                this.hash1 = 0;
                this.key1 = null;
                this.value1 = null;
                this.size = 0;
                return v6;
            }
        } else if (i > 0) {
            int iHashCode = obj.hashCode();
            int i2 = this.size;
            if (i2 != 1) {
                if (i2 == 2) {
                    if (this.hash2 == iHashCode && obj.equals(this.key2)) {
                        V v7 = this.value2;
                        this.hash2 = 0;
                        this.key2 = null;
                        this.value2 = null;
                        this.size = 1;
                        return v7;
                    }
                    if (this.hash1 != iHashCode || !obj.equals(this.key1)) {
                        return null;
                    }
                    V v8 = this.value1;
                    this.hash1 = this.hash2;
                    this.key1 = this.key2;
                    this.value1 = this.value2;
                    this.hash2 = 0;
                    this.key2 = null;
                    this.value2 = null;
                    this.size = 1;
                    return v8;
                }
                if (i2 == 3) {
                    if (this.hash3 == iHashCode && obj.equals(this.key3)) {
                        V v9 = this.value3;
                        this.hash3 = 0;
                        this.key3 = null;
                        this.value3 = null;
                        this.size = 2;
                        return v9;
                    }
                    if (this.hash2 == iHashCode && obj.equals(this.key2)) {
                        V v10 = this.value2;
                        this.hash2 = this.hash3;
                        this.key2 = this.key3;
                        this.value2 = this.value3;
                        this.hash3 = 0;
                        this.key3 = null;
                        this.value3 = null;
                        this.size = 2;
                        return v10;
                    }
                    if (this.hash1 != iHashCode || !obj.equals(this.key1)) {
                        return null;
                    }
                    V v11 = this.value1;
                    this.hash1 = this.hash3;
                    this.key1 = this.key3;
                    this.value1 = this.value3;
                    this.hash3 = 0;
                    this.key3 = null;
                    this.value3 = null;
                    this.size = 2;
                    return v11;
                }
            } else if (this.hash1 == iHashCode && obj.equals(this.key1)) {
                V v12 = this.value1;
                this.hash1 = 0;
                this.key1 = null;
                this.value1 = null;
                this.size = 0;
                return v12;
            }
        }
        return null;
    }

    @Override // java.util.Map, org.apache.commons.collections4.Put
    public void clear() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            abstractHashedMap.clear();
            this.delegateMap = null;
            return;
        }
        this.size = 0;
        this.hash3 = 0;
        this.hash2 = 0;
        this.hash1 = 0;
        this.key3 = null;
        this.key2 = null;
        this.key1 = null;
        this.value3 = null;
        this.value2 = null;
        this.value1 = null;
    }

    @Override // org.apache.commons.collections4.IterableGet
    public MapIterator<K, V> mapIterator() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.mapIterator();
        }
        if (this.size == 0) {
            return EmptyMapIterator.emptyMapIterator();
        }
        return new FlatMapIterator(this);
    }

    static class FlatMapIterator<K, V> implements MapIterator<K, V>, ResettableIterator<K> {
        private final Flat3Map<K, V> parent;
        private int nextIndex = 0;
        private boolean canRemove = false;

        FlatMapIterator(Flat3Map<K, V> flat3Map) {
            this.parent = flat3Map;
        }

        @Override // org.apache.commons.collections4.MapIterator, java.util.Iterator
        public boolean hasNext() {
            return this.nextIndex < ((Flat3Map) this.parent).size;
        }

        @Override // org.apache.commons.collections4.MapIterator, java.util.Iterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next() entry in the iteration");
            }
            this.canRemove = true;
            this.nextIndex++;
            return getKey();
        }

        @Override // org.apache.commons.collections4.MapIterator, java.util.Iterator
        public void remove() {
            if (!this.canRemove) {
                throw new IllegalStateException("remove() can only be called once after next()");
            }
            this.parent.remove(getKey());
            this.nextIndex--;
            this.canRemove = false;
        }

        @Override // org.apache.commons.collections4.MapIterator
        public K getKey() {
            if (!this.canRemove) {
                throw new IllegalStateException("getKey() can only be called after next() and before remove()");
            }
            int i = this.nextIndex;
            if (i == 1) {
                return (K) ((Flat3Map) this.parent).key1;
            }
            if (i == 2) {
                return (K) ((Flat3Map) this.parent).key2;
            }
            if (i == 3) {
                return (K) ((Flat3Map) this.parent).key3;
            }
            throw new IllegalStateException("Invalid map index: " + this.nextIndex);
        }

        @Override // org.apache.commons.collections4.MapIterator
        public V getValue() {
            if (!this.canRemove) {
                throw new IllegalStateException("getValue() can only be called after next() and before remove()");
            }
            int i = this.nextIndex;
            if (i == 1) {
                return (V) ((Flat3Map) this.parent).value1;
            }
            if (i == 2) {
                return (V) ((Flat3Map) this.parent).value2;
            }
            if (i == 3) {
                return (V) ((Flat3Map) this.parent).value3;
            }
            throw new IllegalStateException("Invalid map index: " + this.nextIndex);
        }

        @Override // org.apache.commons.collections4.MapIterator
        public V setValue(V v) {
            if (!this.canRemove) {
                throw new IllegalStateException("setValue() can only be called after next() and before remove()");
            }
            V value = getValue();
            int i = this.nextIndex;
            if (i == 1) {
                ((Flat3Map) this.parent).value1 = v;
            } else if (i == 2) {
                ((Flat3Map) this.parent).value2 = v;
            } else if (i == 3) {
                ((Flat3Map) this.parent).value3 = v;
            } else {
                throw new IllegalStateException("Invalid map index: " + this.nextIndex);
            }
            return value;
        }

        @Override // org.apache.commons.collections4.ResettableIterator
        public void reset() {
            this.nextIndex = 0;
            this.canRemove = false;
        }

        public String toString() {
            if (!this.canRemove) {
                return "Iterator[]";
            }
            return "Iterator[" + getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue() + "]";
        }
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public Set<Map.Entry<K, V>> entrySet() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.entrySet();
        }
        return new EntrySet(this);
    }

    static class EntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {
        private final Flat3Map<K, V> parent;

        EntrySet(Flat3Map<K, V> flat3Map) {
            this.parent = flat3Map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.parent.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.parent.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Object key = ((Map.Entry) obj).getKey();
            boolean zContainsKey = this.parent.containsKey(key);
            this.parent.remove(key);
            return zContainsKey;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            if (((Flat3Map) this.parent).delegateMap != null) {
                return ((Flat3Map) this.parent).delegateMap.entrySet().iterator();
            }
            if (this.parent.size() == 0) {
                return EmptyIterator.emptyIterator();
            }
            return new EntrySetIterator(this.parent);
        }
    }

    static class FlatMapEntry<K, V> implements Map.Entry<K, V> {
        private final int index;
        private final Flat3Map<K, V> parent;
        private volatile boolean removed = false;

        public FlatMapEntry(Flat3Map<K, V> flat3Map, int i) {
            this.parent = flat3Map;
            this.index = i;
        }

        void setRemoved(boolean z) {
            this.removed = z;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.removed) {
                throw new IllegalStateException("getKey() can only be called after next() and before remove()");
            }
            int i = this.index;
            if (i == 1) {
                return (K) ((Flat3Map) this.parent).key1;
            }
            if (i == 2) {
                return (K) ((Flat3Map) this.parent).key2;
            }
            if (i == 3) {
                return (K) ((Flat3Map) this.parent).key3;
            }
            throw new IllegalStateException("Invalid map index: " + this.index);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.removed) {
                throw new IllegalStateException("getValue() can only be called after next() and before remove()");
            }
            int i = this.index;
            if (i == 1) {
                return (V) ((Flat3Map) this.parent).value1;
            }
            if (i == 2) {
                return (V) ((Flat3Map) this.parent).value2;
            }
            if (i == 3) {
                return (V) ((Flat3Map) this.parent).value3;
            }
            throw new IllegalStateException("Invalid map index: " + this.index);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.removed) {
                throw new IllegalStateException("setValue() can only be called after next() and before remove()");
            }
            V value = getValue();
            int i = this.index;
            if (i == 1) {
                ((Flat3Map) this.parent).value1 = v;
            } else if (i == 2) {
                ((Flat3Map) this.parent).value2 = v;
            } else if (i == 3) {
                ((Flat3Map) this.parent).value3 = v;
            } else {
                throw new IllegalStateException("Invalid map index: " + this.index);
            }
            return value;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this.removed || !(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K key = getKey();
            V value = getValue();
            if (key == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!key.equals(entry.getKey())) {
                return false;
            }
            Object value2 = entry.getValue();
            if (value == null) {
                if (value2 != null) {
                    return false;
                }
            } else if (!value.equals(value2)) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (this.removed) {
                return 0;
            }
            K key = getKey();
            V value = getValue();
            return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0);
        }

        public String toString() {
            if (this.removed) {
                return "";
            }
            return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
        }
    }

    static abstract class EntryIterator<K, V> {
        private final Flat3Map<K, V> parent;
        private int nextIndex = 0;
        private FlatMapEntry<K, V> currentEntry = null;

        public EntryIterator(Flat3Map<K, V> flat3Map) {
            this.parent = flat3Map;
        }

        public boolean hasNext() {
            return this.nextIndex < ((Flat3Map) this.parent).size;
        }

        public Map.Entry<K, V> nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next() entry in the iteration");
            }
            Flat3Map<K, V> flat3Map = this.parent;
            int i = this.nextIndex + 1;
            this.nextIndex = i;
            FlatMapEntry<K, V> flatMapEntry = new FlatMapEntry<>(flat3Map, i);
            this.currentEntry = flatMapEntry;
            return flatMapEntry;
        }

        public void remove() {
            FlatMapEntry<K, V> flatMapEntry = this.currentEntry;
            if (flatMapEntry == null) {
                throw new IllegalStateException("remove() can only be called once after next()");
            }
            flatMapEntry.setRemoved(true);
            this.parent.remove(this.currentEntry.getKey());
            this.nextIndex--;
            this.currentEntry = null;
        }
    }

    static class EntrySetIterator<K, V> extends EntryIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        EntrySetIterator(Flat3Map<K, V> flat3Map) {
            super(flat3Map);
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public Set<K> keySet() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.keySet();
        }
        return new KeySet(this);
    }

    static class KeySet<K> extends AbstractSet<K> {
        private final Flat3Map<K, ?> parent;

        KeySet(Flat3Map<K, ?> flat3Map) {
            this.parent = flat3Map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.parent.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.parent.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return this.parent.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean zContainsKey = this.parent.containsKey(obj);
            this.parent.remove(obj);
            return zContainsKey;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            if (((Flat3Map) this.parent).delegateMap != null) {
                return ((Flat3Map) this.parent).delegateMap.keySet().iterator();
            }
            if (this.parent.size() == 0) {
                return EmptyIterator.emptyIterator();
            }
            return new KeySetIterator(this.parent);
        }
    }

    static class KeySetIterator<K> extends EntryIterator<K, Object> implements Iterator<K> {
        KeySetIterator(Flat3Map<K, ?> flat3Map) {
            super(flat3Map);
        }

        @Override // java.util.Iterator
        public K next() {
            return nextEntry().getKey();
        }
    }

    @Override // java.util.Map, org.apache.commons.collections4.Get
    public Collection<V> values() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.values();
        }
        return new Values(this);
    }

    static class Values<V> extends AbstractCollection<V> {
        private final Flat3Map<?, V> parent;

        Values(Flat3Map<?, V> flat3Map) {
            this.parent = flat3Map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.parent.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            this.parent.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return this.parent.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            if (((Flat3Map) this.parent).delegateMap != null) {
                return ((Flat3Map) this.parent).delegateMap.values().iterator();
            }
            if (this.parent.size() == 0) {
                return EmptyIterator.emptyIterator();
            }
            return new ValuesIterator(this.parent);
        }
    }

    static class ValuesIterator<V> extends EntryIterator<Object, V> implements Iterator<V> {
        ValuesIterator(Flat3Map<?, V> flat3Map) {
            super(flat3Map);
        }

        @Override // java.util.Iterator
        public V next() {
            return nextEntry().getValue();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        MapIterator<K, V> mapIterator = mapIterator();
        while (mapIterator.hasNext()) {
            objectOutputStream.writeObject(mapIterator.next());
            objectOutputStream.writeObject(mapIterator.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        if (i > 3) {
            this.delegateMap = createDelegateMap();
        }
        while (i > 0) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
            i--;
        }
    }

    public Flat3Map<K, V> clone() {
        try {
            Flat3Map<K, V> flat3Map = (Flat3Map) super.clone();
            if (flat3Map.delegateMap != null) {
                flat3Map.delegateMap = flat3Map.delegateMap.clone();
            }
            return flat3Map;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x006d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x006e  */
    @Override // java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L4
            return r0
        L4:
            org.apache.commons.collections4.map.AbstractHashedMap<K, V> r1 = r4.delegateMap
            if (r1 == 0) goto Ld
            boolean r5 = r1.equals(r5)
            return r5
        Ld:
            boolean r1 = r5 instanceof java.util.Map
            r2 = 0
            if (r1 != 0) goto L13
            return r2
        L13:
            java.util.Map r5 = (java.util.Map) r5
            int r1 = r4.size
            int r3 = r5.size()
            if (r1 == r3) goto L1e
            return r2
        L1e:
            int r1 = r4.size
            if (r1 <= 0) goto L82
            if (r1 == r0) goto L65
            r3 = 2
            if (r1 == r3) goto L48
            r3 = 3
            if (r1 == r3) goto L2b
            goto L82
        L2b:
            K r1 = r4.key3
            boolean r1 = r5.containsKey(r1)
            if (r1 != 0) goto L34
            return r2
        L34:
            K r1 = r4.key3
            java.lang.Object r1 = r5.get(r1)
            V r3 = r4.value3
            if (r3 != 0) goto L41
            if (r1 == 0) goto L48
            goto L47
        L41:
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L48
        L47:
            return r2
        L48:
            K r1 = r4.key2
            boolean r1 = r5.containsKey(r1)
            if (r1 != 0) goto L51
            return r2
        L51:
            K r1 = r4.key2
            java.lang.Object r1 = r5.get(r1)
            V r3 = r4.value2
            if (r3 != 0) goto L5e
            if (r1 == 0) goto L65
            goto L64
        L5e:
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L65
        L64:
            return r2
        L65:
            K r1 = r4.key1
            boolean r1 = r5.containsKey(r1)
            if (r1 != 0) goto L6e
            return r2
        L6e:
            K r1 = r4.key1
            java.lang.Object r5 = r5.get(r1)
            V r1 = r4.value1
            if (r1 != 0) goto L7b
            if (r5 == 0) goto L82
            goto L81
        L7b:
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L82
        L81:
            return r2
        L82:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.collections4.map.Flat3Map.equals(java.lang.Object):boolean");
    }

    @Override // java.util.Map
    public int hashCode() {
        int iHashCode;
        int iHashCode2;
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.hashCode();
        }
        int i = this.size;
        if (i == 0) {
            return 0;
        }
        if (i != 1) {
            if (i == 2) {
                iHashCode2 = 0;
            } else if (i == 3) {
                int i2 = this.hash3;
                V v = this.value3;
                iHashCode2 = (i2 ^ (v == null ? 0 : v.hashCode())) + 0;
            } else {
                throw new IllegalStateException("Invalid map index: " + this.size);
            }
            int i3 = this.hash2;
            V v2 = this.value2;
            iHashCode = iHashCode2 + (i3 ^ (v2 == null ? 0 : v2.hashCode()));
        } else {
            iHashCode = 0;
        }
        int i4 = this.hash1;
        V v3 = this.value1;
        return ((v3 != null ? v3.hashCode() : 0) ^ i4) + iHashCode;
    }

    public String toString() {
        AbstractHashedMap<K, V> abstractHashedMap = this.delegateMap;
        if (abstractHashedMap != null) {
            return abstractHashedMap.toString();
        }
        if (this.size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append('{');
        int i = this.size;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    Object obj = this.key3;
                    if (obj == this) {
                        obj = "(this Map)";
                    }
                    sb.append(obj);
                    sb.append('=');
                    Object obj2 = this.value3;
                    if (obj2 == this) {
                        obj2 = "(this Map)";
                    }
                    sb.append(obj2);
                    sb.append(',');
                } else {
                    throw new IllegalStateException("Invalid map index: " + this.size);
                }
            }
            Object obj3 = this.key2;
            if (obj3 == this) {
                obj3 = "(this Map)";
            }
            sb.append(obj3);
            sb.append('=');
            Object obj4 = this.value2;
            if (obj4 == this) {
                obj4 = "(this Map)";
            }
            sb.append(obj4);
            sb.append(',');
        }
        Object obj5 = this.key1;
        if (obj5 == this) {
            obj5 = "(this Map)";
        }
        sb.append(obj5);
        sb.append('=');
        V v = this.value1;
        sb.append(v != this ? v : "(this Map)");
        sb.append('}');
        return sb.toString();
    }
}
