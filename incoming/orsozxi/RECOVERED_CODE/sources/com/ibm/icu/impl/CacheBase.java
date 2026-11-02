package com.ibm.icu.impl;

/* loaded from: classes2.dex */
public abstract class CacheBase<K, V, D> {
    protected abstract V createInstance(K k, D d);

    public abstract V getInstance(K k, D d);
}
