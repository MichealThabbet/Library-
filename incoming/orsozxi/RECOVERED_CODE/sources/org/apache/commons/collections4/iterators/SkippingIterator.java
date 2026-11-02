package org.apache.commons.collections4.iterators;

import java.util.Iterator;

/* loaded from: classes3.dex */
public class SkippingIterator<E> extends AbstractIteratorDecorator<E> {
    private final long offset;
    private long pos;

    public SkippingIterator(Iterator<E> it2, long j) {
        super(it2);
        if (j < 0) {
            throw new IllegalArgumentException("Offset parameter must not be negative.");
        }
        this.offset = j;
        this.pos = 0L;
        init();
    }

    private void init() {
        while (this.pos < this.offset && hasNext()) {
            next();
        }
    }

    @Override // org.apache.commons.collections4.iterators.AbstractIteratorDecorator, java.util.Iterator
    public E next() {
        E e = (E) super.next();
        this.pos++;
        return e;
    }

    @Override // org.apache.commons.collections4.iterators.AbstractUntypedIteratorDecorator, java.util.Iterator
    public void remove() {
        if (this.pos <= this.offset) {
            throw new IllegalStateException("remove() can not be called before calling next()");
        }
        super.remove();
    }
}
