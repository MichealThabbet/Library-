package com.google.android.gms.internal.gtm;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
class zztc<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzaut;
    private final int zzbdy;
    private List<zztj> zzbdz;
    private Map<K, V> zzbea;
    private volatile zztl zzbeb;
    private Map<K, V> zzbec;
    private volatile zztf zzbed;

    static <FieldDescriptorType extends zzqv<FieldDescriptorType>> zztc<FieldDescriptorType, Object> zzbu(int i) {
        return new zztd(i);
    }

    private zztc(int i) {
        this.zzbdy = i;
        this.zzbdz = Collections.emptyList();
        this.zzbea = Collections.emptyMap();
        this.zzbec = Collections.emptyMap();
    }

    public void zzmi() {
        Map<K, V> mapUnmodifiableMap;
        Map<K, V> mapUnmodifiableMap2;
        if (this.zzaut) {
            return;
        }
        if (this.zzbea.isEmpty()) {
            mapUnmodifiableMap = Collections.emptyMap();
        } else {
            mapUnmodifiableMap = Collections.unmodifiableMap(this.zzbea);
        }
        this.zzbea = mapUnmodifiableMap;
        if (this.zzbec.isEmpty()) {
            mapUnmodifiableMap2 = Collections.emptyMap();
        } else {
            mapUnmodifiableMap2 = Collections.unmodifiableMap(this.zzbec);
        }
        this.zzbec = mapUnmodifiableMap2;
        this.zzaut = true;
    }

    public final boolean isImmutable() {
        return this.zzaut;
    }

    public final int zzra() {
        return this.zzbdz.size();
    }

    public final Map.Entry<K, V> zzbv(int i) {
        return this.zzbdz.get(i);
    }

    public final Iterable<Map.Entry<K, V>> zzrb() {
        if (this.zzbea.isEmpty()) {
            return zztg.zzrg();
        }
        return this.zzbea.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzbdz.size() + this.zzbea.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zztc<K, V>) comparable) >= 0 || this.zzbea.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zztc<K, V>) comparable);
        if (iZza >= 0) {
            return (V) this.zzbdz.get(iZza).getValue();
        }
        return this.zzbea.get(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k, V v) {
        zzrd();
        int iZza = zza((zztc<K, V>) k);
        if (iZza >= 0) {
            return (V) this.zzbdz.get(iZza).setValue(v);
        }
        zzrd();
        if (this.zzbdz.isEmpty() && !(this.zzbdz instanceof ArrayList)) {
            this.zzbdz = new ArrayList(this.zzbdy);
        }
        int i = -(iZza + 1);
        if (i >= this.zzbdy) {
            return zzre().put(k, v);
        }
        int size = this.zzbdz.size();
        int i2 = this.zzbdy;
        if (size == i2) {
            zztj zztjVarRemove = this.zzbdz.remove(i2 - 1);
            zzre().put((Comparable) zztjVarRemove.getKey(), zztjVarRemove.getValue());
        }
        this.zzbdz.add(i, new zztj(this, k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzrd();
        if (!this.zzbdz.isEmpty()) {
            this.zzbdz.clear();
        }
        if (this.zzbea.isEmpty()) {
            return;
        }
        this.zzbea.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzrd();
        Comparable comparable = (Comparable) obj;
        int iZza = zza((zztc<K, V>) comparable);
        if (iZza >= 0) {
            return zzbw(iZza);
        }
        if (this.zzbea.isEmpty()) {
            return null;
        }
        return this.zzbea.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzbw(int i) {
        zzrd();
        V v = (V) this.zzbdz.remove(i).getValue();
        if (!this.zzbea.isEmpty()) {
            Iterator<Map.Entry<K, V>> it2 = zzre().entrySet().iterator();
            this.zzbdz.add(new zztj(this, it2.next()));
            it2.remove();
        }
        return v;
    }

    private final int zza(K k) {
        int size = this.zzbdz.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzbdz.get(size).getKey());
            if (iCompareTo > 0) {
                return -(size + 2);
            }
            if (iCompareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int iCompareTo2 = k.compareTo((Comparable) this.zzbdz.get(i2).getKey());
            if (iCompareTo2 < 0) {
                size = i2 - 1;
            } else {
                if (iCompareTo2 <= 0) {
                    return i2;
                }
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzbeb == null) {
            this.zzbeb = new zztl(this, null);
        }
        return this.zzbeb;
    }

    final Set<Map.Entry<K, V>> zzrc() {
        if (this.zzbed == null) {
            this.zzbed = new zztf(this, null);
        }
        return this.zzbed;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzrd() {
        if (this.zzaut) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzre() {
        zzrd();
        if (this.zzbea.isEmpty() && !(this.zzbea instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzbea = treeMap;
            this.zzbec = treeMap.descendingMap();
        }
        return (SortedMap) this.zzbea;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zztc)) {
            return super.equals(obj);
        }
        zztc zztcVar = (zztc) obj;
        int size = size();
        if (size != zztcVar.size()) {
            return false;
        }
        int iZzra = zzra();
        if (iZzra != zztcVar.zzra()) {
            return entrySet().equals(zztcVar.entrySet());
        }
        for (int i = 0; i < iZzra; i++) {
            if (!zzbv(i).equals(zztcVar.zzbv(i))) {
                return false;
            }
        }
        if (iZzra != size) {
            return this.zzbea.equals(zztcVar.zzbea);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzra = zzra();
        int iHashCode = 0;
        for (int i = 0; i < iZzra; i++) {
            iHashCode += this.zzbdz.get(i).hashCode();
        }
        return this.zzbea.size() > 0 ? iHashCode + this.zzbea.hashCode() : iHashCode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zztc<K, V>) obj, (Comparable) obj2);
    }

    /* synthetic */ zztc(int i, zztd zztdVar) {
        this(i);
    }
}
