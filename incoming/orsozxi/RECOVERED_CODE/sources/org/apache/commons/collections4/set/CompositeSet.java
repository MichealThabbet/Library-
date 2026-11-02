package org.apache.commons.collections4.set;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.iterators.EmptyIterator;
import org.apache.commons.collections4.iterators.IteratorChain;
import org.apache.commons.collections4.list.UnmodifiableList;

/* loaded from: classes3.dex */
public class CompositeSet<E> implements Set<E>, Serializable {
    private static final long serialVersionUID = 5185069727540378940L;
    private final List<Set<E>> all = new ArrayList();
    private SetMutator<E> mutator;

    public interface SetMutator<E> extends Serializable {
        boolean add(CompositeSet<E> compositeSet, List<Set<E>> list, E e);

        boolean addAll(CompositeSet<E> compositeSet, List<Set<E>> list, Collection<? extends E> collection);

        void resolveCollision(CompositeSet<E> compositeSet, Set<E> set, Set<E> set2, Collection<E> collection);
    }

    public CompositeSet() {
    }

    public CompositeSet(Set<E> set) {
        addComposited(set);
    }

    public CompositeSet(Set<E>... setArr) {
        addComposited(setArr);
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        Iterator<Set<E>> it2 = this.all.iterator();
        int size = 0;
        while (it2.hasNext()) {
            size += it2.next().size();
        }
        return size;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            if (!it2.next().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            if (it2.next().contains(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        if (this.all.isEmpty()) {
            return EmptyIterator.emptyIterator();
        }
        IteratorChain iteratorChain = new IteratorChain();
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            iteratorChain.addIterator(it2.next().iterator());
        }
        return iteratorChain;
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        Object[] objArr = new Object[size()];
        Iterator<E> it2 = iterator();
        int i = 0;
        while (it2.hasNext()) {
            objArr[i] = it2.next();
            i++;
        }
        return objArr;
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        int length = tArr.length;
        Object[] objArr = tArr;
        if (length < size) {
            objArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        }
        int i = 0;
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            Iterator<E> it3 = it2.next().iterator();
            while (it3.hasNext()) {
                objArr[i] = it3.next();
                i++;
            }
        }
        if (objArr.length > size) {
            objArr[size] = null;
        }
        return (T[]) objArr;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e) {
        SetMutator<E> setMutator = this.mutator;
        if (setMutator == null) {
            throw new UnsupportedOperationException("add() is not supported on CompositeSet without a SetMutator strategy");
        }
        return setMutator.add(this, this.all, e);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        for (Set<E> set : getSets()) {
            if (set.contains(obj)) {
                return set.remove(obj);
            }
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it2 = collection.iterator();
        while (it2.hasNext()) {
            if (!contains(it2.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        SetMutator<E> setMutator = this.mutator;
        if (setMutator == null) {
            throw new UnsupportedOperationException("addAll() is not supported on CompositeSet without a SetMutator strategy");
        }
        return setMutator.addAll(this, this.all, collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        boolean zRemoveAll = false;
        if (collection.size() == 0) {
            return false;
        }
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            zRemoveAll |= it2.next().removeAll(collection);
        }
        return zRemoveAll;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        Iterator<Set<E>> it2 = this.all.iterator();
        boolean zRetainAll = false;
        while (it2.hasNext()) {
            zRetainAll |= it2.next().retainAll(collection);
        }
        return zRetainAll;
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        Iterator<Set<E>> it2 = this.all.iterator();
        while (it2.hasNext()) {
            it2.next().clear();
        }
    }

    public void setMutator(SetMutator<E> setMutator) {
        this.mutator = setMutator;
    }

    public synchronized void addComposited(Set<E> set) {
        for (Set<E> set2 : getSets()) {
            Collection<E> collectionIntersection = CollectionUtils.intersection(set2, set);
            if (collectionIntersection.size() > 0) {
                if (this.mutator == null) {
                    throw new UnsupportedOperationException("Collision adding composited set with no SetMutator set");
                }
                getMutator().resolveCollision(this, set2, set, collectionIntersection);
                if (CollectionUtils.intersection(set2, set).size() > 0) {
                    throw new IllegalArgumentException("Attempt to add illegal entry unresolved by SetMutator.resolveCollision()");
                }
            }
        }
        this.all.add(set);
    }

    public void addComposited(Set<E> set, Set<E> set2) {
        addComposited(set);
        addComposited(set2);
    }

    public void addComposited(Set<E>... setArr) {
        for (Set<E> set : setArr) {
            addComposited(set);
        }
    }

    public void removeComposited(Set<E> set) {
        this.all.remove(set);
    }

    public Set<E> toSet() {
        return new HashSet(this);
    }

    public List<Set<E>> getSets() {
        return UnmodifiableList.unmodifiableList(this.all);
    }

    protected SetMutator<E> getMutator() {
        return this.mutator;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        return set.size() == size() && set.containsAll(this);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        Iterator<E> it2 = iterator();
        int iHashCode = 0;
        while (it2.hasNext()) {
            E next = it2.next();
            iHashCode += next == null ? 0 : next.hashCode();
        }
        return iHashCode;
    }
}
