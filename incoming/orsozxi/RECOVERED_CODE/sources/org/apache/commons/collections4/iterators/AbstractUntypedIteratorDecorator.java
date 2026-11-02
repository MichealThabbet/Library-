package org.apache.commons.collections4.iterators;

import java.util.Iterator;

/* loaded from: classes3.dex */
public abstract class AbstractUntypedIteratorDecorator<I, O> implements Iterator<O> {
    private final Iterator<I> iterator;

    protected AbstractUntypedIteratorDecorator(Iterator<I> it2) {
        if (it2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        this.iterator = it2;
    }

    protected Iterator<I> getIterator() {
        return this.iterator;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public void remove() {
        this.iterator.remove();
    }
}
