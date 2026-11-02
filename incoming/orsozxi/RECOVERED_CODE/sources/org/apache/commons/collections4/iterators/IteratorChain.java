package org.apache.commons.collections4.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes3.dex */
public class IteratorChain<E> implements Iterator<E> {
    private final Queue<Iterator<? extends E>> iteratorChain = new LinkedList();
    private Iterator<? extends E> currentIterator = null;
    private Iterator<? extends E> lastUsedIterator = null;
    private boolean isLocked = false;

    public IteratorChain() {
    }

    public IteratorChain(Iterator<? extends E> it2) {
        addIterator(it2);
    }

    public IteratorChain(Iterator<? extends E> it2, Iterator<? extends E> it3) {
        addIterator(it2);
        addIterator(it3);
    }

    public IteratorChain(Iterator<? extends E>... itArr) {
        for (Iterator<? extends E> it2 : itArr) {
            addIterator(it2);
        }
    }

    public IteratorChain(Collection<Iterator<? extends E>> collection) {
        Iterator<Iterator<? extends E>> it2 = collection.iterator();
        while (it2.hasNext()) {
            addIterator(it2.next());
        }
    }

    public void addIterator(Iterator<? extends E> it2) {
        checkLocked();
        if (it2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        this.iteratorChain.add(it2);
    }

    public int size() {
        return this.iteratorChain.size();
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    private void checkLocked() {
        if (this.isLocked) {
            throw new UnsupportedOperationException("IteratorChain cannot be changed after the first use of a method from the Iterator interface");
        }
    }

    private void lockChain() {
        if (this.isLocked) {
            return;
        }
        this.isLocked = true;
    }

    protected void updateCurrentIterator() {
        if (this.currentIterator == null) {
            if (this.iteratorChain.isEmpty()) {
                this.currentIterator = EmptyIterator.emptyIterator();
            } else {
                this.currentIterator = this.iteratorChain.remove();
            }
            this.lastUsedIterator = this.currentIterator;
        }
        while (!this.currentIterator.hasNext() && !this.iteratorChain.isEmpty()) {
            this.currentIterator = this.iteratorChain.remove();
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        lockChain();
        updateCurrentIterator();
        Iterator<? extends E> it2 = this.currentIterator;
        this.lastUsedIterator = it2;
        return it2.hasNext();
    }

    @Override // java.util.Iterator
    public E next() {
        lockChain();
        updateCurrentIterator();
        Iterator<? extends E> it2 = this.currentIterator;
        this.lastUsedIterator = it2;
        return it2.next();
    }

    @Override // java.util.Iterator
    public void remove() {
        lockChain();
        if (this.currentIterator == null) {
            updateCurrentIterator();
        }
        this.lastUsedIterator.remove();
    }
}
