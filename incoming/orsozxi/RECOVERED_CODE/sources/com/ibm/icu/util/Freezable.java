package com.ibm.icu.util;

/* loaded from: classes2.dex */
public interface Freezable<T> extends Cloneable {
    T cloneAsThawed();

    T freeze();

    boolean isFrozen();
}
