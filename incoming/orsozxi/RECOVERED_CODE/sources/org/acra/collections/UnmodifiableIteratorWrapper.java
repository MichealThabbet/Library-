package org.acra.collections;

import java.util.Iterator;

/* loaded from: classes3.dex */
class UnmodifiableIteratorWrapper<E> implements Iterator<E> {
    private final Iterator<E> mIterator;

    UnmodifiableIteratorWrapper(Iterator<E> it2) {
        this.mIterator = it2;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mIterator.hasNext();
    }

    @Override // java.util.Iterator
    public E next() {
        return this.mIterator.next();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
