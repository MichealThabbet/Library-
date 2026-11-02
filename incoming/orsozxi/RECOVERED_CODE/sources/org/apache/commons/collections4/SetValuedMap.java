package org.apache.commons.collections4;

import java.util.Set;

/* loaded from: classes3.dex */
public interface SetValuedMap<K, V> extends MultiValuedMap<K, V> {
    @Override // org.apache.commons.collections4.MultiValuedMap
    Set<V> get(K k);

    @Override // org.apache.commons.collections4.MultiValuedMap
    Set<V> remove(Object obj);
}
