package org.acra.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* loaded from: classes3.dex */
public final class ImmutableList<E> implements List<E>, Serializable {
    private final List<E> mList;

    @SafeVarargs
    public ImmutableList(E... eArr) {
        this(Arrays.asList(eArr));
    }

    public ImmutableList(Collection<E> collection) {
        this.mList = new ArrayList(collection);
    }

    @Override // java.util.List
    public void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.mList.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.mList.containsAll(collection);
    }

    @Override // java.util.List
    public E get(int i) {
        return this.mList.get(i);
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.mList.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.mList.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new UnmodifiableIteratorWrapper(this.mList.iterator());
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.mList.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return new UnmodifiableListIteratorWrapper(this.mList.listIterator());
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        return new UnmodifiableListIteratorWrapper(this.mList.listIterator(i));
    }

    @Override // java.util.List
    public E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.mList.size();
    }

    @Override // java.util.List
    public List<E> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.mList.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.mList.toArray(tArr);
    }

    public String toString() {
        return "ImmutableList{" + this.mList.toString() + "}";
    }
}
