package org.acra.collections;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public final class ImmutableSet<E> implements Set<E>, Serializable {
    private static final ImmutableSet<Object> EMPTY = new ImmutableSet<>();
    private final Set<E> mSet;

    public static <T> ImmutableSet<T> empty() {
        return (ImmutableSet<T>) EMPTY;
    }

    private ImmutableSet() {
        this.mSet = Collections.emptySet();
    }

    @SafeVarargs
    public ImmutableSet(E... eArr) {
        this(Arrays.asList(eArr));
    }

    public ImmutableSet(Collection<E> collection) {
        this.mSet = new LinkedHashSet(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        return this.mSet.contains(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.mSet.containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.mSet.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new UnmodifiableIteratorWrapper(this.mSet.iterator());
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.mSet.size();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.mSet.toArray();
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.mSet.toArray(tArr);
    }

    public static final class Builder<E> {
        private final Set<E> mSet = new LinkedHashSet();

        public void add(E e) {
            this.mSet.add(e);
        }

        public ImmutableSet<E> build() {
            return new ImmutableSet<>(this.mSet);
        }
    }
}
