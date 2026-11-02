package org.apache.commons.collections4;

import java.util.Iterator;

/* loaded from: classes3.dex */
public interface OrderedIterator<E> extends Iterator<E> {
    boolean hasPrevious();

    E previous();
}
