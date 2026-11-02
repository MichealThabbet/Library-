package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import android.util.Pair;
import com.nhaarman.listviewanimations.util.Insertable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class InsertQueue<T> {
    private final Insertable<T> mInsertable;
    private final Collection<AtomicInteger> mActiveIndexes = new HashSet();
    private final List<Pair<Integer, T>> mPendingItemsToInsert = new ArrayList();

    public InsertQueue(Insertable<T> insertable) {
        this.mInsertable = insertable;
    }

    public void insert(int i, T t) {
        if (this.mActiveIndexes.isEmpty() && this.mPendingItemsToInsert.isEmpty()) {
            this.mActiveIndexes.add(new AtomicInteger(i));
            this.mInsertable.add(i, t);
        } else {
            this.mPendingItemsToInsert.add(new Pair<>(Integer.valueOf(i), t));
        }
    }

    public void insert(Pair<Integer, T>... pairArr) {
        insert(Arrays.asList(pairArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void insert(Collection<Pair<Integer, T>> collection) {
        if (this.mActiveIndexes.isEmpty() && this.mPendingItemsToInsert.isEmpty()) {
            for (Pair<Integer, T> pair : collection) {
                for (AtomicInteger atomicInteger : this.mActiveIndexes) {
                    if (atomicInteger.intValue() >= ((Integer) pair.first).intValue()) {
                        atomicInteger.incrementAndGet();
                    }
                }
                this.mActiveIndexes.add(new AtomicInteger(((Integer) pair.first).intValue()));
                this.mInsertable.add(((Integer) pair.first).intValue(), pair.second);
            }
            return;
        }
        this.mPendingItemsToInsert.addAll(collection);
    }

    public void clearActive() {
        this.mActiveIndexes.clear();
        insertPending();
    }

    public void removeActiveIndex(int i) {
        Iterator<AtomicInteger> it2 = this.mActiveIndexes.iterator();
        boolean z = false;
        while (it2.hasNext() && !z) {
            if (it2.next().get() == i) {
                it2.remove();
                z = true;
            }
        }
        if (this.mActiveIndexes.isEmpty()) {
            insertPending();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void insertPending() {
        for (Pair<Integer, T> pair : this.mPendingItemsToInsert) {
            for (AtomicInteger atomicInteger : this.mActiveIndexes) {
                if (atomicInteger.intValue() >= ((Integer) pair.first).intValue()) {
                    atomicInteger.incrementAndGet();
                }
            }
            this.mActiveIndexes.add(new AtomicInteger(((Integer) pair.first).intValue()));
            this.mInsertable.add(((Integer) pair.first).intValue(), pair.second);
        }
        this.mPendingItemsToInsert.clear();
    }

    public Collection<Integer> getActiveIndexes() {
        HashSet hashSet = new HashSet();
        Iterator<AtomicInteger> it2 = this.mActiveIndexes.iterator();
        while (it2.hasNext()) {
            hashSet.add(Integer.valueOf(it2.next().get()));
        }
        return hashSet;
    }

    public List<Pair<Integer, T>> getPendingItemsToInsert() {
        return this.mPendingItemsToInsert;
    }
}
