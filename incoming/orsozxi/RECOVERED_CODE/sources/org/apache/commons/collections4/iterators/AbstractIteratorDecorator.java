package org.apache.commons.collections4.iterators;

import java.util.Iterator;

/* loaded from: classes3.dex */
public abstract class AbstractIteratorDecorator<E> extends AbstractUntypedIteratorDecorator<E, E> {
    protected AbstractIteratorDecorator(Iterator<E> it2) {
        super(it2);
    }

    @Override // java.util.Iterator
    public E next() {
        return getIterator().next();
    }
}
