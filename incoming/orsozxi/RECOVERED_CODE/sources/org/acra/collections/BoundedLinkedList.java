package org.acra.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public final class BoundedLinkedList<E> extends LinkedList<E> {
    private final int maxSize;

    public BoundedLinkedList(int i) {
        this.maxSize = i;
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        return super.add(e);
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        super.add(i, e);
    }

    @Override // java.util.LinkedList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        if (size > this.maxSize) {
            collection = new ArrayList(collection).subList(size - this.maxSize, size);
        }
        int size2 = (size() + collection.size()) - this.maxSize;
        if (size2 > 0) {
            removeRange(0, size2);
        }
        return super.addAll(collection);
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        if (i == size()) {
            return super.addAll(i, collection);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addFirst(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addLast(E e) {
        add(e);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it2 = iterator();
        while (it2.hasNext()) {
            sb.append(it2.next().toString());
        }
        return sb.toString();
    }

    @Override // java.util.LinkedList, java.util.Deque, java.util.Queue
    public boolean offer(E e) {
        return add(e);
    }

    @Override // java.util.LinkedList, java.util.Deque
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override // java.util.LinkedList, java.util.Deque
    public boolean offerLast(E e) {
        return add(e);
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void push(E e) {
        add(e);
    }
}
