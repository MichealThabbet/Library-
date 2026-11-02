package org.acra.collections;

import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class WeakStack<T> extends AbstractCollection<T> {
    private final List<WeakReference<T>> contents = new ArrayList();

    private void cleanup() {
        for (WeakReference<T> weakReference : this.contents) {
            if (weakReference.get() == null) {
                this.contents.remove(weakReference);
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        cleanup();
        return this.contents.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        Iterator<WeakReference<T>> it2 = this.contents.iterator();
        while (it2.hasNext()) {
            if (obj.equals(it2.next().get())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return new WeakIterator(this.contents.iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean add(T t) {
        return this.contents.add(new WeakReference<>(t));
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        if (obj != null) {
            for (int i = 0; i < this.contents.size(); i++) {
                if (obj.equals(this.contents.get(i).get())) {
                    this.contents.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public T peek() {
        for (int size = this.contents.size() - 1; size >= 0; size--) {
            T t = this.contents.get(size).get();
            if (t != null) {
                return t;
            }
        }
        throw new EmptyStackException();
    }

    public T pop() {
        T tPeek = peek();
        remove(tPeek);
        return tPeek;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        this.contents.clear();
    }

    private static class WeakIterator<T> implements Iterator<T> {
        private final Iterator<WeakReference<T>> iterator;
        private T next;

        private WeakIterator(Iterator<WeakReference<T>> it2) {
            this.iterator = it2;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.next != null) {
                return true;
            }
            while (this.iterator.hasNext()) {
                T t = this.iterator.next().get();
                if (t != null) {
                    this.next = t;
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Iterator
        public T next() {
            T t = this.next;
            this.next = null;
            while (t == null) {
                t = this.iterator.next().get();
            }
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            this.iterator.remove();
        }
    }
}
