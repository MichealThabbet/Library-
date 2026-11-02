package org.apache.commons.collections4.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public class PeekingIterator<E> implements Iterator<E> {
    private final Iterator<? extends E> iterator;
    private E slot;
    private boolean exhausted = false;
    private boolean slotFilled = false;

    public static <E> PeekingIterator<E> peekingIterator(Iterator<? extends E> it2) {
        if (it2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (it2 instanceof PeekingIterator) {
            return (PeekingIterator) it2;
        }
        return new PeekingIterator<>(it2);
    }

    public PeekingIterator(Iterator<? extends E> it2) {
        this.iterator = it2;
    }

    private void fill() {
        if (this.exhausted || this.slotFilled) {
            return;
        }
        if (this.iterator.hasNext()) {
            this.slot = this.iterator.next();
            this.slotFilled = true;
        } else {
            this.exhausted = true;
            this.slot = null;
            this.slotFilled = false;
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.exhausted) {
            return false;
        }
        if (this.slotFilled) {
            return true;
        }
        return this.iterator.hasNext();
    }

    public E peek() {
        fill();
        if (this.exhausted) {
            return null;
        }
        return this.slot;
    }

    public E element() {
        fill();
        if (this.exhausted) {
            throw new NoSuchElementException();
        }
        return this.slot;
    }

    @Override // java.util.Iterator
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        E next = this.slotFilled ? this.slot : this.iterator.next();
        this.slot = null;
        this.slotFilled = false;
        return next;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (this.slotFilled) {
            throw new IllegalStateException("peek() or element() called before remove()");
        }
        this.iterator.remove();
    }
}
