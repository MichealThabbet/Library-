package org.apache.commons.collections4.iterators;

import java.util.Iterator;
import org.apache.commons.collections4.ResettableIterator;

/* loaded from: classes3.dex */
public class IteratorIterable<E> implements Iterable<E> {
    private final Iterator<? extends E> iterator;
    private final Iterator<E> typeSafeIterator;

    private static <E> Iterator<E> createTypesafeIterator(final Iterator<? extends E> it2) {
        return new Iterator<E>() { // from class: org.apache.commons.collections4.iterators.IteratorIterable.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it2.hasNext();
            }

            @Override // java.util.Iterator
            public E next() {
                return (E) it2.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                it2.remove();
            }
        };
    }

    public IteratorIterable(Iterator<? extends E> it2) {
        this(it2, false);
    }

    public IteratorIterable(Iterator<? extends E> it2, boolean z) {
        if (z && !(it2 instanceof ResettableIterator)) {
            this.iterator = new ListIteratorWrapper(it2);
        } else {
            this.iterator = it2;
        }
        this.typeSafeIterator = createTypesafeIterator(this.iterator);
    }

    @Override // java.lang.Iterable
    public Iterator<E> iterator() {
        Iterator<? extends E> it2 = this.iterator;
        if (it2 instanceof ResettableIterator) {
            ((ResettableIterator) it2).reset();
        }
        return this.typeSafeIterator;
    }
}
