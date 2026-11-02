package org.acra.collections;

import java.util.ListIterator;

/* loaded from: classes3.dex */
class UnmodifiableListIteratorWrapper<E> implements ListIterator<E> {
    private final ListIterator<E> mIterator;

    UnmodifiableListIteratorWrapper(ListIterator<E> listIterator) {
        this.mIterator = listIterator;
    }

    @Override // java.util.ListIterator
    public void add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        return this.mIterator.hasNext();
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return this.mIterator.hasPrevious();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public E next() {
        return this.mIterator.next();
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.mIterator.nextIndex();
    }

    @Override // java.util.ListIterator
    public E previous() {
        return this.mIterator.previous();
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.mIterator.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public void set(E e) {
        throw new UnsupportedOperationException();
    }
}
