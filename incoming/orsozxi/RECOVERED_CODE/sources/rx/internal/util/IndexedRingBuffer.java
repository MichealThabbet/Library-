package rx.internal.util;

import android.R;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import rx.Subscription;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public final class IndexedRingBuffer<E> implements Subscription {
    private static final ObjectPool<IndexedRingBuffer<?>> POOL = new ObjectPool<IndexedRingBuffer<?>>() { // from class: rx.internal.util.IndexedRingBuffer.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // rx.internal.util.ObjectPool
        public IndexedRingBuffer<?> createObject() {
            return new IndexedRingBuffer<>();
        }
    };
    static final int SIZE;
    static int _size;
    private final ElementSection<E> elements;
    final AtomicInteger index;
    private final IndexSection removed;
    final AtomicInteger removedIndex;

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return false;
    }

    static {
        _size = 256;
        if (PlatformDependent.isAndroid()) {
            _size = 8;
        }
        String property = System.getProperty("rx.indexed-ring-buffer.size");
        if (property != null) {
            try {
                _size = Integer.parseInt(property);
            } catch (Exception e) {
                System.err.println("Failed to set 'rx.indexed-ring-buffer.size' with value " + property + " => " + e.getMessage());
            }
        }
        SIZE = _size;
    }

    public static final <T> IndexedRingBuffer<T> getInstance() {
        return (IndexedRingBuffer) POOL.borrowObject();
    }

    public void releaseToPool() {
        int i = this.index.get();
        int i2 = 0;
        loop0: for (ElementSection<E> elementSection = this.elements; elementSection != null; elementSection = (ElementSection) ((ElementSection) elementSection).next.get()) {
            int i3 = 0;
            while (i3 < SIZE) {
                if (i2 >= i) {
                    break loop0;
                }
                ((ElementSection) elementSection).array.set(i3, null);
                i3++;
                i2++;
            }
        }
        this.index.set(0);
        this.removedIndex.set(0);
        POOL.returnObject(this);
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        releaseToPool();
    }

    private IndexedRingBuffer() {
        this.elements = new ElementSection<>();
        this.removed = new IndexSection();
        this.index = new AtomicInteger();
        this.removedIndex = new AtomicInteger();
    }

    public int add(E e) {
        int indexForAdd = getIndexForAdd();
        int i = SIZE;
        if (indexForAdd < i) {
            ((ElementSection) this.elements).array.set(indexForAdd, e);
            return indexForAdd;
        }
        ((ElementSection) getElementSection(indexForAdd)).array.set(indexForAdd % i, e);
        return indexForAdd;
    }

    public E remove(int i) {
        E e;
        int i2 = SIZE;
        if (i < i2) {
            e = (E) ((ElementSection) this.elements).array.getAndSet(i, null);
        } else {
            e = (E) ((ElementSection) getElementSection(i)).array.getAndSet(i % i2, null);
        }
        pushRemovedIndex(i);
        return e;
    }

    private IndexSection getIndexSection(int i) {
        int i2 = SIZE;
        if (i < i2) {
            return this.removed;
        }
        int i3 = i / i2;
        IndexSection next = this.removed;
        for (int i4 = 0; i4 < i3; i4++) {
            next = next.getNext();
        }
        return next;
    }

    private ElementSection<E> getElementSection(int i) {
        int i2 = SIZE;
        if (i < i2) {
            return this.elements;
        }
        int i3 = i / i2;
        ElementSection<E> next = this.elements;
        for (int i4 = 0; i4 < i3; i4++) {
            next = next.getNext();
        }
        return next;
    }

    private synchronized int getIndexForAdd() {
        int andIncrement;
        int indexFromPreviouslyRemoved = getIndexFromPreviouslyRemoved();
        if (indexFromPreviouslyRemoved >= 0) {
            if (indexFromPreviouslyRemoved < SIZE) {
                andIncrement = this.removed.getAndSet(indexFromPreviouslyRemoved, -1);
            } else {
                andIncrement = getIndexSection(indexFromPreviouslyRemoved).getAndSet(indexFromPreviouslyRemoved % SIZE, -1);
            }
            if (andIncrement == this.index.get()) {
                this.index.getAndIncrement();
            }
        } else {
            andIncrement = this.index.getAndIncrement();
        }
        return andIncrement;
    }

    private synchronized int getIndexFromPreviouslyRemoved() {
        int i;
        int i2;
        do {
            i = this.removedIndex.get();
            if (i <= 0) {
                return -1;
            }
            i2 = i - 1;
        } while (!this.removedIndex.compareAndSet(i, i2));
        return i2;
    }

    private synchronized void pushRemovedIndex(int i) {
        int andIncrement = this.removedIndex.getAndIncrement();
        if (andIncrement < SIZE) {
            this.removed.set(andIncrement, i);
        } else {
            getIndexSection(andIncrement).set(andIncrement % SIZE, i);
        }
    }

    public int forEach(Func1<? super E, Boolean> func1) {
        return forEach(func1, 0);
    }

    public int forEach(Func1<? super E, Boolean> func1, int i) {
        int iForEach = forEach(func1, i, this.index.get());
        if (i > 0 && iForEach == this.index.get()) {
            return forEach(func1, 0, i);
        }
        if (iForEach == this.index.get()) {
            return 0;
        }
        return iForEach;
    }

    private int forEach(Func1<? super E, Boolean> func1, int i, int i2) {
        ElementSection<E> elementSection;
        int i3;
        int i4 = this.index.get();
        ElementSection<E> elementSection2 = this.elements;
        if (i >= SIZE) {
            ElementSection<E> elementSection3 = getElementSection(i);
            i3 = i;
            i %= SIZE;
            elementSection = elementSection3;
        } else {
            elementSection = elementSection2;
            i3 = i;
        }
        loop0: while (elementSection != null) {
            while (i < SIZE) {
                if (i3 >= i4 || i3 >= i2) {
                    break loop0;
                }
                R.attr attrVar = (Object) ((ElementSection) elementSection).array.get(i);
                if (attrVar != null && !func1.call(attrVar).booleanValue()) {
                    return i3;
                }
                i++;
                i3++;
            }
            elementSection = (ElementSection) ((ElementSection) elementSection).next.get();
            i = 0;
        }
        return i3;
    }

    private static class ElementSection<E> {
        private final AtomicReferenceArray<E> array;
        private final AtomicReference<ElementSection<E>> next;

        private ElementSection() {
            this.array = new AtomicReferenceArray<>(IndexedRingBuffer.SIZE);
            this.next = new AtomicReference<>();
        }

        ElementSection<E> getNext() {
            if (this.next.get() != null) {
                return this.next.get();
            }
            ElementSection<E> elementSection = new ElementSection<>();
            return this.next.compareAndSet(null, elementSection) ? elementSection : this.next.get();
        }
    }

    private static class IndexSection {
        private final AtomicReference<IndexSection> _next;
        private final AtomicIntegerArray unsafeArray;

        private IndexSection() {
            this.unsafeArray = new AtomicIntegerArray(IndexedRingBuffer.SIZE);
            this._next = new AtomicReference<>();
        }

        public int getAndSet(int i, int i2) {
            return this.unsafeArray.getAndSet(i, i2);
        }

        public void set(int i, int i2) {
            this.unsafeArray.set(i, i2);
        }

        IndexSection getNext() {
            if (this._next.get() != null) {
                return this._next.get();
            }
            IndexSection indexSection = new IndexSection();
            return this._next.compareAndSet(null, indexSection) ? indexSection : this._next.get();
        }
    }
}
