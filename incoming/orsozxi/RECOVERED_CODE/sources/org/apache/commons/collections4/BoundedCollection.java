package org.apache.commons.collections4;

import java.util.Collection;

/* loaded from: classes3.dex */
public interface BoundedCollection<E> extends Collection<E> {
    boolean isFull();

    int maxSize();
}
