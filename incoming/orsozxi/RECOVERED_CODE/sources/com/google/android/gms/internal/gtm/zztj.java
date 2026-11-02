package com.google.android.gms.internal.gtm;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes.dex */
final class zztj<K, V> implements Comparable<zztj>, Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zztc zzbef;

    /* JADX INFO: Incorrect field signature: TK; */
    private final Comparable zzbei;

    zztj(zztc zztcVar, Map.Entry<K, V> entry) {
        this(zztcVar, (Comparable) entry.getKey(), entry.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    zztj(zztc zztcVar, K k, V v) {
        this.zzbef = zztcVar;
        this.zzbei = k;
        this.value = v;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        this.zzbef.zzrd();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return equals(this.zzbei, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.zzbei;
        int iHashCode = comparable == null ? 0 : comparable.hashCode();
        V v = this.value;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzbei);
        String strValueOf2 = String.valueOf(this.value);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length());
        sb.append(strValueOf);
        sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
        sb.append(strValueOf2);
        return sb.toString();
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzbei;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zztj zztjVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zztjVar.getKey());
    }
}
